
package com.robotwitter.webapp.view.dashboard;


import com.google.inject.Inject;
import com.google.inject.name.Named;

import com.vaadin.server.BrowserWindowOpener;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import com.robotwitter.webapp.control.dashboard.IDashboardController;
import com.robotwitter.webapp.messages.IMessagesContainer;
import com.robotwitter.webapp.view.AbstractView;




/** Dashboard view. */
public class DashboardView extends AbstractView
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
	 * Instantiates a new dashboard view.
	 *
	 * @param messages
	 *            the container of messages to display
	 */
	@Inject
	public DashboardView(@Named("analysis") IMessagesContainer messages,  // FIXME:
																			// change
																			// to
																			// dashboard
		IDashboardController controller)
	{
		super(messages, messages.get("DashboardView.page.title"));
		this.controller = controller;
		getUserSession().observeActiveTwitterAccount(this);
	}
	
	
	@Override
	public final void activateTwitterAccount(long id)
	{
		activeAccountComponent = createInformationComponent();
	}
	
	
	@Override
	public final boolean isSignedInProhibited()
	{
		return false;
	}
	
	
	@Override
	public final boolean isSignedInRequired()
	{
		return true;
	}
	
	
	/**
	 * @return
	 */
	private HorizontalLayout createAccountsComponent()
	{
		accountsComponent = new HorizontalLayout();
		for (ConnectedAccountInfo twitterAccount : controller
			.getConnectedAccountsInfo())
		{
			accountsComponent
				.addComponent(createSingleAccountComponent(twitterAccount));
		}
		
		return accountsComponent;
	}
	
	
	/** @return A newly created follower information component. */
	private Component createInformationComponent()
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
	
	
	/**
	 * @param twitterAccount
	 * @return
	 */
	private Component createSingleAccountComponent(
		ConnectedAccountInfo twitterAccount)
	{
		Image accountPicture = new Image();
		accountPicture.setSource(new ExternalResource(twitterAccount
			.getPicture()));
		accountPicture.setAlternateText(twitterAccount.getName());
		
		Label accountName = new Label();
		Label accountScreenname = new Label();
		
		accountName.setValue(twitterAccount.getName());
		accountScreenname.setValue('@' + twitterAccount.getScreenName());
		
		VerticalLayout names =
			new VerticalLayout(accountName, accountScreenname);
		
		Label accountFollowing = new Label();
		Label accountGained = new Label();
		Label accountLost = new Label();
		accountFollowing.setCaption("Followers:");
		accountGained.setCaption("Followers Gained:");
		accountLost.setCaption("Followers Lost:");
		accountFollowing.setValue(getShortenedValue(twitterAccount
			.getNumFollowers()));
		accountGained.setValue(getShortenedValue(twitterAccount
			.getFollowersGained()));
		accountLost.setValue(getShortenedValue(twitterAccount
			.getFollowersLost()));
		
		HorizontalLayout followersInfo =
			new HorizontalLayout(accountFollowing, accountGained, accountLost);
		
		Label newMessages = new Label();
		newMessages.setCaption("Your Unanswered Messeges:");
		newMessages.setValue(getShortenedValue(twitterAccount
			.getUnansweredMesseges()));
		
		return new VerticalLayout(
			accountPicture,
			names,
			followersInfo,
			newMessages);
	}
	
	
	private void showAccount()
	{
		ConnectedAccountInfo accountInformation =
			controller.getCurrentAccountInfo();
		
		picture
			.setSource(new ExternalResource(accountInformation.getPicture()));
		picture.setAlternateText(accountInformation.getName());
		name.setValue(accountInformation.getName());
		screenname.setValue('@' + accountInformation.getScreenName());
		description.setValue(accountInformation.getDescription());
		location.setValue(accountInformation.getLocation());
		language.setValue(accountInformation.getLanguage());
		
		celebrity.setValue("");
		if (accountInformation.getIsCelebrity())
		{
			celebrity.setValue(messages
				.get("MostInfluentialFollowers.label.celebrity"));
		}
		
		followingStat.setValue(getShortenedValue(accountInformation
			.getNumFollowing()));
		followersStat.setValue(getShortenedValue(accountInformation
			.getNumFollowers()));
		favouritesStat.setValue(getShortenedValue(accountInformation
			.getNumFavorites()));
		
		if (opener != null)
		{
			link.removeExtension(opener);
		}
		opener =
			new BrowserWindowOpener("https://twitter.com/"
				+ accountInformation.getScreenName());
		opener.extend(link);
	}
	
	
	@Override
	protected final void initialise()
	{
		Label infoHeader = new Label("Currently Connected As:"); // FIXME:
																	// turn
																	// to
																	// messeges
		activeAccountComponent = createInformationComponent();
		showAccount();
		Label accountsHeader = new Label("Your Twitter Accounts:"); // FIXME:
																	// turn
																	// to
																	// messeges
		accountsComponent = createAccountsComponent();
		VerticalLayout layout =
			new VerticalLayout(
				infoHeader,
				activeAccountComponent,
				accountsHeader,
				accountsComponent);
		
		infoHeader.addStyleName(INFO_HEADER_STYLENAME);
		accountsHeader.addStyleName(INFO_HEADER_STYLENAME);
		
		setCompositionRoot(layout);
	}
	
	
	
	private HorizontalLayout accountsComponent;
	
	private Component activeAccountComponent;
	
	private IDashboardController controller;
	
	/** The view's name. */
	public static final String NAME = "dashboard"; // FIXME: change to dashboard
	
	/** Serialisation version unique ID. */
	private static final long serialVersionUID = 1L;
	
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
	BrowserWindowOpener opener;
	
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
	
	/** The CSS class name to apply to the header component. */
	private static final String INFO_HEADER_STYLENAME = "AnalysisView-header";
	
}
