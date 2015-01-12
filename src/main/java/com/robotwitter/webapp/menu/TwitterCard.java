
package com.robotwitter.webapp.menu;


/**
 * Represents a component which contains basic information on a Twitter account.
 * <p>
 * This component is written in plain HTML because
 * {@link AccountInformationPopup} derives from vaadin's
 * {@link com.vaadin.ui.PopupView.Content} which needs to return its minimised
 * presentation as plain HTML.
 *
 * @author Hagai Akibayov
 */
public final class TwitterCard
{

	/**
	 * Creates a Twitter card as an HTML element.
	 *
	 * @param name
	 *            the Twitter account's name
	 * @param screenname
	 *            the Twitter account's screenname (eg, <code>@CocaCola</code>)
	 * @param image
	 *            the Twitter account's profile image URI
	 * @param isEmpty
	 *            is the account empty
	 * @return an HTML string representing the newly created card
	 */
	public static String createAsHtml(
		String name,
		String screenname,
		String image,
		boolean isEmpty)

	{		// Twitter account name element
		String nameOpen = "<div class=\"" + TWITTER_NAME_STYLENAME + "\">";
		String nameClose = "</div>";
		String nameContent = name;
		String nameElem = nameOpen + nameContent + nameClose;

		// Twitter account screenname element
		String screennameOpen =
			"<div class=\"" + TWITTER_SCREENNAME_STYLENAME + "\">";
		String screennameClose = "</div>";
		String screennameContent = "";
		if (!isEmpty)
		{
			screennameContent += '@';
		}
		screennameContent += screenname;
		String screennameElem =
			screennameOpen + screennameContent + screennameClose;

		// Twitter card names section (contains name and screenname)
		String namesOpen =
			"<div class=\"" + TWITTER_CARD_NAMES_STYLENAME + "\">";
		String namesClose = "</div>";
		String namesContent = nameElem + screennameElem;
		String namesElem = namesOpen + namesContent + namesClose;

		// Twitter profile image
		String imageOpen =
			"<div class=\""
				+ TWITTER_IMAGE_WRAPPER_STYLENAME
				+ "\"><img class=\""
				+ TWITTER_PROFILE_IMAGE_STYLENAME
				+ "\" ";
		imageOpen += "alt=\"" + name + "\" src=\"";
		String imageClose = "\" /></div>";
		String imageContent = image;
		String imageElem = imageOpen + imageContent + imageClose;

		// Root element (Twitter profile card)
		String cardOpen = "<div class=\"" + STYLENAME + "\">";
		String cardClose = "</div>";
		String cardContent = namesElem + imageElem;
		String cardElem = cardOpen + cardContent + cardClose;

		return cardElem;
	}
	
	
	/**
	 * Initialisation is prohibited.
	 *
	 * @throws Exception
	 *             always thrown
	 */
	private TwitterCard() throws Exception
	{
		throw new Exception("Initialisation of class "
			+ this.getClass().getName()
			+ " is prohibited");
	}



	/** The CSS class name to apply to this component. */
	private static final String STYLENAME = "TwitterCard";

	/** The CSS class name to apply to a Twitter name in a profile card. */
	private static final String TWITTER_NAME_STYLENAME = "TwitterCard-name";

	/** The CSS class name to apply to a Twitter profile image. */
	private static final String TWITTER_PROFILE_IMAGE_STYLENAME =
		"TwitterCard-image";

	/** The CSS class name to apply to a Twitter profile image wrapper. */
	private static final String TWITTER_IMAGE_WRAPPER_STYLENAME =
		"TwitterCard-image-wrapper";

	/** The CSS class name to apply to a Twitter screenname in a profile card. */
	private static final String TWITTER_SCREENNAME_STYLENAME =
		"TwitterCard-screenname";

	/** The CSS class name to apply to a names section in a profile card. */
	private static final String TWITTER_CARD_NAMES_STYLENAME =
		"TwitterCard-names";
}
