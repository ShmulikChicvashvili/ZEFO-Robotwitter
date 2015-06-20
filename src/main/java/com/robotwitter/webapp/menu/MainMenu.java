
package com.robotwitter.webapp.menu;


import java.util.HashMap;
import java.util.Map;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import com.vaadin.server.FontAwesome;
import com.vaadin.server.Resource;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.PopupView;
import com.vaadin.ui.themes.ValoTheme;

import com.robotwitter.webapp.control.account.ITwitterConnectorController;
import com.robotwitter.webapp.messages.IMessagesContainer;
import com.robotwitter.webapp.view.analysis.AnalysisView;
import com.robotwitter.webapp.view.automate.AutomateView;
import com.robotwitter.webapp.view.dashboard.DashboardView;
import com.robotwitter.webapp.view.scheduling.ScheduledViewMock;
import com.robotwitter.webapp.view.tools.ToolsView;




/**
 * Represents the main menu of the application.
 * <p>
 * Contains navigation links to the primary views of the application, as well as
 * profile information about the current signed in user, connected twitter
 * accounts, and more.
 *
 * @author Hagai Akibayov
 */
public class MainMenu extends AbstractMenu
{

	/**
	 * Instantiates a main menu.
	 *
	 * @param messages
	 *            the container of messages to display
	 * @param twitterConnectorController
	 *            the Twitter account connector controller
	 */
	@Inject
	public MainMenu(
		@Named(MainMenu.NAME) IMessagesContainer messages,
		ITwitterConnectorController twitterConnectorController)
	{
		super(messages);

		accountInfoPopup =
			new AccountInformationPopup(messages, twitterConnectorController);
		menuItems = new HashMap<>();

		setCompositionRoot(createMenu());

		setStyleName(STYLENAME);
	}


	@Override
	public final void activateTwitterAccount(long id)
	{
		accountInformation.markAsDirty();
	}
	
	
	@Override
	public final void onAttach()
	{
		activateLink(getViewName(), menuItems.get(getViewName()));
	}


	/**
	 * Activate a link and navigate to its view.
	 *
	 * @param name
	 *            the name of the link's view
	 * @param link
	 *            the link to activate
	 */
	private void activateLink(String name, MenuItem link)
	{
		menuItems.forEach((n, item) -> item.setStyleName(""));
		link.setStyleName(ACTIVE_LINK_STYLENAME);
		navigate(name);
	}


	/**
	 * Adds the link to the given menu.
	 *
	 * @param links
	 *            the links
	 * @param viewName
	 *            the view name to link to
	 * @param text
	 *            the text to show
	 * @param icon
	 *            the icon of the link, or null for none
	 */
	private void addLink(
		MenuBar links,
		String viewName,
		String text,
		Resource icon)
	{
		menuItems.put(
			viewName,
			links.addItem(
				isMobile() ? "" : text,
				icon,
				item -> activateLink(viewName, item)));
	}


	/**
	 * Creates the account information component.
	 *
	 * @return the newly created account information component
	 */
	private Component createAccountInformation()
	{
		final PopupView account = new PopupView(accountInfoPopup);
		accountInfoPopup.setOwner(account);

		// Set properties and styles
		account.setHideOnMouseOut(false);
		account.addStyleName(ACCOUNT_STYLENAME);

		return account;
	}


	/**
	 * Creates the navigation links component.
	 *
	 * @return the newly created navigation links component
	 */
	private Component createLinks()
	{
		MenuBar links = new MenuBar();

		// Add home button
		addLink(
			links,
			DashboardView.NAME,
			messages.get("MainMenu.link.home"),
			null);

		// Add analyse button
		addLink(
			links,
			AnalysisView.NAME,
			messages.get("MainMenu.link.analyse"),
			FontAwesome.BAR_CHART_O);

		// Add tools button
		addLink(
			links,
			ToolsView.NAME,
			messages.get("MainMenu.link.tools"),
			FontAwesome.WRENCH);

		// Add schedule button
		addLink(
			links,
			ScheduledViewMock.NAME,
			messages.get("MainMenu.link.schedule"),
			FontAwesome.CALENDAR);

		// Add automate button
		addLink(
			links,
			AutomateView.NAME,
			messages.get("MainMenu.link.automate"),
			FontAwesome.COGS);

		setInitialActiveLink();

		// Set properties and styles
		links.setAutoOpen(true);
		links.addStyleName(ValoTheme.MENUBAR_BORDERLESS);
		links.addStyleName(LINKS_STYLENAME);

		if (isMobile())
		{
			links.addStyleName(MOBILE_LINKS_STYLENAME);
		}

		return links;
	}
	
	
	/** @return a newly created main menu component. */
	private Component createMenu()
	{
		final Component links = createLinks();
		accountInformation = createAccountInformation();
		final HorizontalLayout menu =
			new HorizontalLayout(links, accountInformation);

		if (!isMobile())
		{
			menu.setSizeFull();
		}

		return menu;
	}
	
	
	/** Sets the initial active link. */
	private void setInitialActiveLink()
	{
		try
		{
			MenuItem item = menuItems.get(getViewName());
			if (item != null)
			{
				item.setStyleName(ACTIVE_LINK_STYLENAME);
			}

		} catch (IndexOutOfBoundsException e)
		{
			System.err.println("Unknown view name: " + getViewName());
		}
	}



	/** The menu's name. */
	public static final String NAME = "main-menu";

	/** The CSS class name to apply to the account information component. */
	private static final String ACCOUNT_STYLENAME = "MainMenu-account";

	/** The CSS class name to apply to the navigation links component. */
	private static final String LINKS_STYLENAME = "MainMenu-links";

	/** The CSS class name to apply to the active link. */
	private static final String ACTIVE_LINK_STYLENAME = "MainMenu-active-link";

	/**
	 * The CSS class name to apply to the navigation links component when
	 * browsing in mobile.
	 */
	private static final String MOBILE_LINKS_STYLENAME =
		"MainMenu-links-mobile";

	/** Serialisation version unique ID. */
	private static final long serialVersionUID = 1L;

	/** The CSS class name to apply to this component. */
	private static final String STYLENAME = "MainMenu";

	/** The account information pop-up. */
	AccountInformationPopup accountInfoPopup;

	/** The account information component. */
	Component accountInformation;
	
	/** The menu's items. */
	Map<String, MenuItem> menuItems;
}
