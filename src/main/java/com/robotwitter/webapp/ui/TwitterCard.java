
package com.robotwitter.webapp.ui;


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
	 *
	 * @return an HTML string representing the newly created card
	 */
	static String createAsHtml(String name, String screenname, String image)
	
	{		// Twitter account name element
		String nameOpen = "<div class=\"" + TWITTER_NAME_STYLENAME + "\">"; //$NON-NLS-1$ //$NON-NLS-2$
		String nameClose = "</div>"; //$NON-NLS-1$
		String nameContent = name;
		String nameElem = nameOpen + nameContent + nameClose;

		// Twitter account screenname element
		String screennameOpen =
			"<div class=\"" + TWITTER_SCREENNAME_STYLENAME + "\">"; //$NON-NLS-1$ //$NON-NLS-2$
		String screennameClose = "</div>"; //$NON-NLS-1$
		String screennameContent = '@' + screenname;
		String screennameElem =
			screennameOpen + screennameContent + screennameClose;

		// Twitter card names section (contains name and screenname)
		String namesOpen =
			"<div class=\"" + TWITTER_CARD_NAMES_STYLENAME + "\">"; //$NON-NLS-1$ //$NON-NLS-2$
		String namesClose = "</div>"; //$NON-NLS-1$
		String namesContent = nameElem + screennameElem;
		String namesElem = namesOpen + namesContent + namesClose;

		// Twitter profile image
		String imageOpen =
			"<img class=\"" + TWITTER_PROFILE_IMAGE_STYLENAME + "\" "; //$NON-NLS-1$ //$NON-NLS-2$
		imageOpen += "alt=\"" + name + "\" src=\"";  //$NON-NLS-1$ //$NON-NLS-2$
		String imageClose = "\">"; //$NON-NLS-1$
		String imageContent = image;
		String imageElem = imageOpen + imageContent + imageClose;

		// Root element (Twitter profile card)
		String cardOpen = "<div class=\"" + STYLENAME + "\">"; //$NON-NLS-1$ //$NON-NLS-2$
		String cardClose = "</div>"; //$NON-NLS-1$
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
		throw new Exception("Initialisation of class " //$NON-NLS-1$
			+ this.getClass().getName()
			+ " is prohibited"); //$NON-NLS-1$
	}



	/** The CSS class name to apply to this component. */
	private static final String STYLENAME = "TwitterCard"; //$NON-NLS-1$

	/** The CSS class name to apply to a Twitter name in a profile card. */
	private static final String TWITTER_NAME_STYLENAME = "TwitterCard-name"; //$NON-NLS-1$

	/** The CSS class name to apply to a Twitter profile image. */
	private static final String TWITTER_PROFILE_IMAGE_STYLENAME =
		"TwitterCard-image"; //$NON-NLS-1$

	/** The CSS class name to apply to a Twitter screenname in a profile card. */
	private static final String TWITTER_SCREENNAME_STYLENAME =
		"TwitterCard-screenname"; //$NON-NLS-1$

	/** The CSS class name to apply to a names section in a profile card. */
	private static final String TWITTER_CARD_NAMES_STYLENAME =
		"TwitterCard-names"; //$NON-NLS-1$
}
