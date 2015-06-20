
package com.robotwitter.webapp.view.dashboard;


import com.google.inject.Inject;
import com.google.inject.name.Named;

import com.vaadin.server.ExternalResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import com.robotwitter.webapp.control.dashboard.IDashboardController;
import com.robotwitter.webapp.messages.IMessagesContainer;
import com.robotwitter.webapp.view.AbstractView;
import com.robotwitter.webapp.view.analysis.FollowersAmountOverview;




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
		$ = $.replace(".0", ""); //$NON-NLS-1$ //$NON-NLS-2$
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
		if (f < k) { return String.format("%.1f", Float.valueOf(f)) + 'K'; } //$NON-NLS-1$
		f /= k;
		return String.format("%.1f", Float.valueOf(f)) + 'M'; //$NON-NLS-1$
	}
	
	
	/**
	 * Instantiates a new dashboard view.
	 *
	 * @param messages
	 *            the container of messages to display
	 * @param controller
	 *            the controller for the dashboard
	 */
	@Inject
	public DashboardView(
		@Named(NAME) IMessagesContainer messages,
		IDashboardController controller)
	{
		super(messages, messages.get("DashboardView.page.title")); //$NON-NLS-1$
		this.controller = controller;
		this.controller.setUser(getUserSession().getAccountController().getEmail());
		getUserSession().observeActiveTwitterAccount(this);
		
	}
	
	
	@Override
	public final void activateTwitterAccount(long id)
	{
		updateActiveTwitterAccountComponent();
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
	 * Creates the accounts component.
	 *
	 * @return the horizontal layout
	 */
	private HorizontalLayout createAccountsComponent()
	{
		accountsComponent = new HorizontalLayout();
		accountsComponent.setSpacing(true);
		
		for (ConnectedAccountInfo twitterAccount : controller
			.getConnectedAccountsInfo())
		{
			accountsComponent
			.addComponent(createSingleAccountComponent(twitterAccount));
		}

		accountsComponent.addStyleName(ACCOUNTS_STYLENAME);
		
		return accountsComponent;
	}
	
	
	/**
	 * Creates the information component.
	 *
	 * @return A newly created follower information component.
	 */
	private Component createInformationComponent()
	{
		FollowersAmountOverview followersAmountOverview =
			new FollowersAmountOverview(messages);
		
		description = new Label();
		location = new Label();
		language = new Label();

		followingStat = new Label();
		favouritesStat = new Label();

		// Captions
		description.setCaption(messages.get("DashboardView.caption.bio")); //$NON-NLS-1$
		location.setCaption(messages.get("DashboardView.caption.location")); //$NON-NLS-1$
		language.setCaption(messages.get("DashboardView.caption.language")); //$NON-NLS-1$
		followingStat.setCaption(messages
			.get("DashboardView.caption.following")); //$NON-NLS-1$
		favouritesStat.setCaption(messages
			.get("DashboardView.caption.favourites")); //$NON-NLS-1$
		
		// Icons
		location.setIcon(FontAwesome.GLOBE);

		VerticalLayout numberStats =
			new VerticalLayout(followingStat, favouritesStat);
		numberStats.setSpacing(true);

		VerticalLayout textStats = new VerticalLayout(location, language);
		textStats.setSpacing(true);

		HorizontalLayout stats =
			new HorizontalLayout(numberStats, description, textStats);
		stats.setSpacing(true);
		
		VerticalLayout layout =
			new VerticalLayout(followersAmountOverview, stats);

		description.addStyleName(INFORMATION_DESCRIPTION_STYLENAME);
		location.addStyleName(INFORMATION_LOCATION_STYLENAME);
		language.addStyleName(INFORMATION_LANGUAGE_STYLENAME);
		followingStat.addStyleName(INFORMATION_STAT_STYLENAME);
		favouritesStat.addStyleName(INFORMATION_STAT_STYLENAME);
		stats.addStyleName(INFORMATION_STATS_STYLENAME);

		updateActiveTwitterAccountComponent();
		
		return layout;
	}
	
	
	/**
	 * Creates the single account component.
	 *
	 * @param twitterAccount
	 *            the twitter account
	 * @return the component
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

		accountFollowing.setIcon(FontAwesome.USERS);
		accountGained.setIcon(FontAwesome.ARROW_UP);
		accountLost.setIcon(FontAwesome.ARROW_DOWN);

		accountFollowing.setValue(getShortenedValue(twitterAccount
			.getNumFollowers()));
		accountGained.setValue(getShortenedValue(twitterAccount
			.getFollowersGained()));
		accountLost.setValue(getShortenedValue(twitterAccount
			.getFollowersLost()));
		
		HorizontalLayout followersInfo =
			new HorizontalLayout(accountFollowing, accountGained, accountLost);
		followersInfo.setSpacing(true);
		
		Label newNotifications = new Label();
		newNotifications.setIcon(FontAwesome.COMMENT);
		Label newNotificationsCaption =
			new Label(
				messages.get("DashboardView.caption.notifications-available")); //$NON-NLS-1$
		newNotifications.setValue(getShortenedValue(twitterAccount
			.getUnansweredMesseges()));

		HorizontalLayout notifications =
			new HorizontalLayout(newNotifications, newNotificationsCaption);
		
		VerticalLayout layout =
			new VerticalLayout(
				accountPicture,
				names,
				followersInfo,
				notifications);

		if (twitterAccount.getUnansweredMesseges() == 0)
		{
			layout.removeComponent(notifications);
		}

		layout.addStyleName(ACCOUNT_STYLENAME);
		accountPicture.addStyleName(ACCOUNT_PICTURE_STYLENAME);
		names.addStyleName(ACCOUNT_NAME_AND_SCREENNNAME_STYLENAME);
		accountName.addStyleName(ACCOUNT_NAME_STYLENAME);
		accountScreenname.addStyleName(ACCOUNT_SCREENNAME_STYLENAME);
		followersInfo.addStyleName(ACCOUNT_STATS_STYLENAME);
		accountFollowing.addStyleName(ACCOUNT_STAT_STYLENAME);
		accountFollowing.addStyleName(ACCOUNT_TOTAL_STYLENAME);
		accountGained.addStyleName(ACCOUNT_STAT_STYLENAME);
		accountGained.addStyleName(ACCOUNT_GAINED_STYLENAME);
		accountLost.addStyleName(ACCOUNT_STAT_STYLENAME);
		accountLost.addStyleName(ACCOUNT_LOST_STYLENAME);
		notifications.addStyleName(ACCOUNT_NOTIFICATIONS_STYLENAME);

		return layout;
	}
	
	
	/**
	 * Update active twitter account component.
	 */
	private void updateActiveTwitterAccountComponent()
	{
		
		ConnectedAccountInfo accountInformation =
			controller.getAccountInfo(getUserSession().getAccountController().getActiveTwitterAccount().getID());

		description.setValue(accountInformation.getDescription());
		location.setValue(accountInformation.getLocation());
		language.setValue(accountInformation.getLanguage());
		
		followingStat.setValue(getShortenedValue(accountInformation
			.getNumFollowing()));
		favouritesStat.setValue(getShortenedValue(accountInformation
			.getNumFavorites()));
	}
	
	
	@Override
	protected final void initialise()
	{
		Label infoHeader =
			new Label(
				messages.get("DashboardView.title.current-twitter-account")); //$NON-NLS-1$
		activeAccountComponent = createInformationComponent();
		Label accountsHeader =
			new Label(
				messages.get("DashboardView.title.other-twitter-accounts")); //$NON-NLS-1$
		accountsComponent = createAccountsComponent();
		VerticalLayout layout =
			new VerticalLayout(
				infoHeader,
				activeAccountComponent,
				accountsHeader,
				accountsComponent);
		
		infoHeader.addStyleName(HEADER_STYLENAME);
		accountsHeader.addStyleName(HEADER_STYLENAME);

		addStyleName(STYLENAME);
		
		setCompositionRoot(layout);
	}
	
	
	
	/** The accounts component. */
	private HorizontalLayout accountsComponent;
	
	/** The active account component. */
	private Component activeAccountComponent;
	
	/** The controller. */
	private IDashboardController controller;
	
	/** The view's name. */
	public static final String NAME = "dashboard"; //$NON-NLS-1$
	
	/** Serialisation version unique ID. */
	private static final long serialVersionUID = 1L;
	
	/** The active Twitter follower's description label. */
	Label description;
	
	/** The active Twitter follower's location label. */
	Label location;
	
	/** The active Twitter follower's language label. */
	Label language;
	
	/** The active Twitter follower's following stat label. */
	Label followingStat;
	
	/** The active Twitter follower's favourites stat label. */
	Label favouritesStat;
	
	/** The CSS class name to apply to this component. */
	private static final String STYLENAME = "DashboardView"; //$NON-NLS-1$
	
	/** The CSS class name to apply to the header component. */
	private static final String HEADER_STYLENAME = "DashboardView-header"; //$NON-NLS-1$
	
	/** The CSS class name to apply to a stat in the information. */
	private static final String INFORMATION_STAT_STYLENAME =
		"DashboardView-information-stat"; //$NON-NLS-1$

	/** The CSS class name to apply to the stats wrapper in the information. */
	private static final String INFORMATION_STATS_STYLENAME =
		"DashboardView-information-stats"; //$NON-NLS-1$

	/** The CSS class name to apply to a description in the information. */
	private static final String INFORMATION_DESCRIPTION_STYLENAME =
		"DashboardView-information-description"; //$NON-NLS-1$

	/** The CSS class name to apply to a location in the information. */
	private static final String INFORMATION_LOCATION_STYLENAME =
		"DashboardView-information-location"; //$NON-NLS-1$

	/** The CSS class name to apply to a language in the information. */
	private static final String INFORMATION_LANGUAGE_STYLENAME =
		"DashboardView-information-language"; //$NON-NLS-1$
	
	/** The CSS class name to apply to the accounts wrapper. */
	private static final String ACCOUNTS_STYLENAME = "DashboardView-accounts"; //$NON-NLS-1$
	
	/** The CSS class name to apply to the accounts wrapper. */
	private static final String ACCOUNT_STYLENAME = "DashboardView-account"; //$NON-NLS-1$
	
	/** The CSS class name to apply to an account's profile picture. */
	private static final String ACCOUNT_PICTURE_STYLENAME =
		"DashboardView-account-picture"; //$NON-NLS-1$

	/** The CSS class name to apply to an account's name. */
	private static final String ACCOUNT_NAME_AND_SCREENNNAME_STYLENAME =
		"DashboardView-account-name-and-screenname"; //$NON-NLS-1$

	/** The CSS class name to apply to an account's name. */
	private static final String ACCOUNT_NAME_STYLENAME =
		"DashboardView-account-name"; //$NON-NLS-1$

	/** The CSS class name to apply to an account's screenname. */
	private static final String ACCOUNT_SCREENNAME_STYLENAME =
		"DashboardView-account-screenname"; //$NON-NLS-1$

	/** The CSS class name to apply to an account's stats wrapper. */
	private static final String ACCOUNT_STATS_STYLENAME =
		"DashboardView-account-stats"; //$NON-NLS-1$

	/** The CSS class name to apply to an account's stat. */
	private static final String ACCOUNT_STAT_STYLENAME =
		"DashboardView-account-stat"; //$NON-NLS-1$

	/** The CSS class name to apply to an account's total followers. */
	private static final String ACCOUNT_TOTAL_STYLENAME =
		"DashboardView-account-total"; //$NON-NLS-1$

	/** The CSS class name to apply to an account's gained followers. */
	private static final String ACCOUNT_GAINED_STYLENAME =
		"DashboardView-account-gained"; //$NON-NLS-1$

	/** The CSS class name to apply to an account's lost followers. */
	private static final String ACCOUNT_LOST_STYLENAME =
		"DashboardView-account-lost"; //$NON-NLS-1$

	/** The CSS class name to apply to an account's notifications. */
	private static final String ACCOUNT_NOTIFICATIONS_STYLENAME =
		"DashboardView-account-notifications"; //$NON-NLS-1$
	
}
