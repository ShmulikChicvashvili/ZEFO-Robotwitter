
package com.robotwitter.webapp.view.analysis;


import java.util.List;

import com.vaadin.server.BrowserWindowOpener;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.MouseEventDetails.MouseButton;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import com.robotwitter.webapp.control.account.ITwitterAccountController;
import com.robotwitter.webapp.control.account.TwitterFollower;
import com.robotwitter.webapp.menu.TwitterCard;
import com.robotwitter.webapp.messages.IMessagesContainer;
import com.robotwitter.webapp.util.RobotwitterCustomComponent;




/**
 * Represents a list of the most influential followers of a Twitter account.
 *
 * @author Hagai Akibayov
 * @author Eyal Tolchinsky
 */
public class MostInfluentialFollowers extends RobotwitterCustomComponent
{
	/**
	 * Gets the shortened value of the given number.
	 *
	 * For example, 1500 will return 1.5K, and 2000000 will return 2M.
	 *
	 * @param n
	 *            the number to shorten
	 *
	 * @return the shortened value
	 */
	static String getShortenedValue(int n)
	{
		String $ = getShortenedValueAux(n);
		$ = $.replace(".0", "");
		return $;
	}
	
	
	/**
	 * Gets the shortened value of the given number.
	 *
	 * For example, 1500 will return 1.5K, and 2000000 will return 2M.
	 *
	 * @param n
	 *            the number to shorten
	 *
	 * @return the shortened value
	 */
	static String getShortenedValueAux(int n)
	{
		final int k = 1000;
		float f = n;

		if (n < k) { return String.valueOf(n); }
		f /= k;
		if (f < k) { return String.format("%.1f", Float.valueOf(f)) + 'K'; }
		f /= k;
		return String.format("%.1f", Float.valueOf(f)) + 'M';
	}


	/**
	 * Instantiates a new most influential followers list.
	 *
	 * @param messages
	 *            the container of messages to display
	 */
	public MostInfluentialFollowers(IMessagesContainer messages)
	{
		super(messages);
		initialiseLayout();
		getUserSession().observeActiveTwitterAccount(this);
	}
	
	
	@Override
	public final void activateTwitterAccount(long id)
	{
		updateList();
	}


	/**
	 * Activates the given follower given its follower's card.
	 *
	 * @param follower
	 *            the follower
	 * @param followerCard
	 *            the follower's card
	 */
	private void activateFollower(
		TwitterFollower follower,
		Component followerCard)
	{
		if (activeCardWrapper != null)
		{
			activeCardWrapper.removeStyleName(ACTIVE_CARD_WRAPPER_STYLENAME);
		}
		activeCardWrapper = followerCard;
		activeCardWrapper.addStyleName(ACTIVE_CARD_WRAPPER_STYLENAME);
		
		picture.setSource(new ExternalResource(follower.getPicture()));
		picture.setAlternateText(follower.getName());
		name.setValue(follower.getName());
		screenname.setValue('@' + follower.getScreenName());
		description.setValue(follower.getDescription());
		location.setValue(follower.getLocation());
		language.setValue(follower.getLanguage());
		
		celebrity.setValue("");
		if (follower.getIsCelebrity())
		{
			celebrity.setValue(messages
				.get("MostInfluentialFollowers.label.celebrity"));
		}

		followingStat.setValue(getShortenedValue(follower.getNumFollowing()));
		followersStat.setValue(getShortenedValue(follower.getNumFollowers()));
		favouritesStat.setValue(getShortenedValue(follower.getNumFavorites()));
		
		if (opener != null)
		{
			link.removeExtension(opener);
		}
		opener =
			new BrowserWindowOpener("https://twitter.com/"
				+ follower.getScreenName());
		opener.extend(link);
	}
	
	
	/** @return A newly created follower information component. */
	private Component createFollowerInformationComponent()
	{
		picture = new Image();
		name = new Label();
		screenname = new Label();
		description = new Label();
		location = new Label();
		language = new Label();
		celebrity = new Label();
		
		followingStat = new Label();
		followersStat = new Label();
		favouritesStat = new Label();

		link =
			new Button(
				messages
					.get("MostInfluentialFollowers.link.to-follower-twitter"));
		link.addStyleName(ValoTheme.BUTTON_LINK);
		opener = null;
		
		// Captions
		description.setCaption(messages
			.get("MostInfluentialFollowers.caption.bio"));
		location.setCaption(messages
			.get("MostInfluentialFollowers.caption.location"));
		language.setCaption(messages
			.get("MostInfluentialFollowers.caption.language"));
		followingStat.setCaption(messages
			.get("MostInfluentialFollowers.caption.following"));
		followersStat.setCaption(messages
			.get("MostInfluentialFollowers.caption.followers"));
		favouritesStat.setCaption(messages
			.get("MostInfluentialFollowers.caption.favourites"));

		// Tooltips
		celebrity.setDescription(messages
			.get("MostInfluentialFollowers.tooltip.celebrity"));

		// Icons
		location.setIcon(FontAwesome.GLOBE);
		link.setIcon(FontAwesome.TWITTER);
		
		HorizontalLayout stats =
			new HorizontalLayout(followingStat, followersStat, favouritesStat);
		stats.setSpacing(true);
		
		VerticalLayout left =
			new VerticalLayout(picture, name, screenname, celebrity, link);
		VerticalLayout right =
			new VerticalLayout(stats, description, location, language);
		right.setSpacing(true);

		HorizontalLayout layout = new HorizontalLayout(left, right);
		layout.setSizeFull();
		
		picture.addStyleName(INFORMATION_PICTURE_STYLENAME);
		name.addStyleName(INFORMATION_NAME_STYLENAME);
		screenname.addStyleName(INFORMATION_SCREENNAME_STYLENAME);
		description.addStyleName(INFORMATION_DESCRIPTION_STYLENAME);
		location.addStyleName(INFORMATION_LOCATION_STYLENAME);
		language.addStyleName(INFORMATION_LANGUAGE_STYLENAME);
		celebrity.addStyleName(INFORMATION_CELEBRITY_STYLENAME);
		followingStat.addStyleName(INFORMATION_STAT_STYLENAME);
		followersStat.addStyleName(INFORMATION_STAT_STYLENAME);
		favouritesStat.addStyleName(INFORMATION_STAT_STYLENAME);
		stats.addStyleName(INFORMATION_STATS_STYLENAME);
		left.addStyleName(INFORMATION_LEFT_STYLENAME);
		right.addStyleName(INFORMATION_RIGHT_STYLENAME);
		layout.addStyleName(INFORMATION_STYLENAME);
		
		return layout;
	}
	
	
	/** Initialises the layout. */
	private void initialiseLayout()
	{
		Component followerInformation = createFollowerInformationComponent();
		followers = new VerticalLayout();
		
		layout = new HorizontalLayout(followerInformation, followers);
		followers.setSizeFull();
		layout.setSizeFull();
		
		followers.addStyleName(FOLLOWERS_LIST_STYLENAME);
		addStyleName(STYLENAME);

		updateList();
	}


	/** Update the followers over time chart. */
	private void updateList()
	{
		ITwitterAccountController controller =
			getUserSession().getAccountController().getActiveTwitterAccount();

		influentialFollowers = controller.getMostInfluentialFollowers();

		if (influentialFollowers.isEmpty())
		{
			setCompositionRoot(new Label(
				messages.get("MostInfluentialFollowers.error.no-data")));
			return;
		}
		setCompositionRoot(layout);

		followers.removeAllComponents();
		activeCardWrapper = null;
		for (TwitterFollower follower : influentialFollowers)
		{
			Label card =
				new Label(TwitterCard.createAsHtml(
					follower.getName(),
					follower.getScreenName(),
					follower.getPicture(),
					false,
					false));
			card.setContentMode(ContentMode.HTML);
			VerticalLayout cardWrapper = new VerticalLayout(card);
			cardWrapper.addStyleName(CARD_WRAPPER_STYLENAME);
			cardWrapper
				.addLayoutClickListener(event -> {
					if (event.getButton().compareTo(MouseButton.LEFT) != 0) { return; }
					activateFollower(follower, cardWrapper);
				});

			followers.addComponent(cardWrapper);
		}

		if (!influentialFollowers.isEmpty())
		{
			activateFollower(
				influentialFollowers.get(0),
				followers.getComponent(0));
		}
	}



	/** The list of influential followers. */
	VerticalLayout followers;
	
	/** A list of the most influential followers. */
	List<TwitterFollower> influentialFollowers;
	
	/** The current active Twitter account card wrapper component. */
	Component activeCardWrapper;
	
	/** The active Twitter follower's picture image. */
	Image picture;
	
	/** The active Twitter follower's name label. */
	Label name;
	
	/** The active Twitter follower's screenname label. */
	Label screenname;
	
	/** The active Twitter follower's description label. */
	Label description;
	
	/** The active Twitter follower's location label. */
	Label location;
	
	/** The active Twitter follower's language label. */
	Label language;
	
	/** The active Twitter follower's celebrity label. */
	Label celebrity;
	
	/** The active Twitter follower's following stat label. */
	Label followingStat;
	
	/** The active Twitter follower's followers stat label. */
	Label followersStat;
	
	/** The active Twitter follower's favourites stat label. */
	Label favouritesStat;

	/** The active Twitter follower's link to Twitter account. */
	Button link;
	
	/** Link browser window opener. */
	private BrowserWindowOpener opener;

	/** The CSS class name to apply to this component. */
	private static final String STYLENAME = "MostInfluentialFollowers";

	/** The CSS class name to apply to the followers list component. */
	private static final String FOLLOWERS_LIST_STYLENAME =
		"MostInfluentialFollowers-followers-list";

	/** The CSS class name to apply to the active follower information. */
	private static final String INFORMATION_STYLENAME =
		"MostInfluentialFollowers-information";

	/** The CSS class name to apply to the information left side. */
	private static final String INFORMATION_LEFT_STYLENAME =
		"MostInfluentialFollowers-information-left";

	/** The CSS class name to apply to the information right side. */
	private static final String INFORMATION_RIGHT_STYLENAME =
		"MostInfluentialFollowers-information-right";

	/** The CSS class name to apply to a picture in the information. */
	private static final String INFORMATION_PICTURE_STYLENAME =
		"MostInfluentialFollowers-information-picture";

	/** The CSS class name to apply to a name in the information. */
	private static final String INFORMATION_NAME_STYLENAME =
		"MostInfluentialFollowers-information-name";

	/** The CSS class name to apply to a screenname in the information. */
	private static final String INFORMATION_SCREENNAME_STYLENAME =
		"MostInfluentialFollowers-information-screenname";

	/** The CSS class name to apply to a stat in the information. */
	private static final String INFORMATION_STAT_STYLENAME =
		"MostInfluentialFollowers-information-stat";

	/** The CSS class name to apply to the stats wrapper in the information. */
	private static final String INFORMATION_STATS_STYLENAME =
		"MostInfluentialFollowers-information-stats";

	/** The CSS class name to apply to a description in the information. */
	private static final String INFORMATION_DESCRIPTION_STYLENAME =
		"MostInfluentialFollowers-information-description";

	/** The CSS class name to apply to a location in the information. */
	private static final String INFORMATION_LOCATION_STYLENAME =
		"MostInfluentialFollowers-information-location";

	/** The CSS class name to apply to a language in the information. */
	private static final String INFORMATION_LANGUAGE_STYLENAME =
		"MostInfluentialFollowers-information-language";

	/** The CSS class name to apply to a celebrity in the information. */
	private static final String INFORMATION_CELEBRITY_STYLENAME =
		"MostInfluentialFollowers-information-celebrity";

	/** The CSS class name to apply to the active card wrapper component. */
	private static final String ACTIVE_CARD_WRAPPER_STYLENAME =
		"MostInfluentialFollowers-active-card-wrapper";
	
	/** The CSS class name to apply to a card wrapper. */
	private static final String CARD_WRAPPER_STYLENAME =
		"MostInfluentialFollowers-card-wrapper";

	/** Serialisation version unique ID. */
	private static final long serialVersionUID = 1L;

	/** The layout (as the composition root). */
	private HorizontalLayout layout;
}
