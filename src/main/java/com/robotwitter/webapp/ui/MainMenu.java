
package com.robotwitter.webapp.ui;


import com.google.inject.Inject;
import com.google.inject.name.Named;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.PopupView;
import com.vaadin.ui.themes.ValoTheme;

import com.robotwitter.webapp.control.account.ITwitterConnectorController;
import com.robotwitter.webapp.messages.IMessagesContainer;
import com.robotwitter.webapp.view.dashboard.DashboardView;




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
		setCompositionRoot(createMenu());
		setStyleName(STYLENAME);
	}


	@Override
	public final void activateTwitterAccount(long id)
	{
		accountInformation.markAsDirty();
	}


	/**
	 * Creates the account information component.
	 *
	 * @return the newly created account information component
	 */
	private Component createAccountInformation()
	{
		PopupView account = new PopupView(accountInfoPopup);
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
		links.addItem(
			messages.get("MainMenu.link.home"),
			null,
			item -> navigate(DashboardView.NAME));

		// Add analyse button
		links.addItem(
			messages.get("MainMenu.link.analyse"),
			FontAwesome.BAR_CHART_O,
			null);

		// Add tools button
		links.addItem(
			messages.get("MainMenu.link.tools"),
			FontAwesome.WRENCH,
			null);
		
		// Add schedule button
		links.addItem(
			messages.get("MainMenu.link.schedule"),
			FontAwesome.CALENDAR,
			null);
		
		// Add automate button
		links.addItem(
			messages.get("MainMenu.link.automate"),
			FontAwesome.COGS,
			null);
		
		// Set properties and styles
		links.setAutoOpen(true);
		links.addStyleName(ValoTheme.MENUBAR_BORDERLESS);
		links.addStyleName(LINKS_STYLENAME);
		
		return links;
	}


	/** @return a newly created main menu component. */
	private Component createMenu()
	{
		Component links = createLinks();
		accountInformation = createAccountInformation();
		HorizontalLayout menu = new HorizontalLayout(links, accountInformation);
		menu.setComponentAlignment(links, Alignment.TOP_LEFT);
		menu.setComponentAlignment(accountInformation, Alignment.TOP_RIGHT);
		menu.setSizeFull();
		return menu;
	}



	/** The menu's name. */
	public static final String NAME = "main-menu";

	/** The CSS class name to apply to the account information component. */
	private static final String ACCOUNT_STYLENAME = "MainMenu-account";

	/** The CSS class name to apply to the navigation links component. */
	private static final String LINKS_STYLENAME = "MainMenu-links";

	/** Serialisation version unique ID. */
	private static final long serialVersionUID = 1L;

	/** The CSS class name to apply to this component. */
	private static final String STYLENAME = "MainMenu";

	/** The account information pop-up. */
	AccountInformationPopup accountInfoPopup;

	/** The account information component. */
	Component accountInformation;
}
