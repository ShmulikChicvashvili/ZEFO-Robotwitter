
package com.robotwitter.webapp.util;


import com.google.inject.Injector;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewProvider;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import com.robotwitter.webapp.Configuration;
import com.robotwitter.webapp.IMenuFactory;
import com.robotwitter.webapp.control.account.IAccountController;
import com.robotwitter.webapp.menu.AbstractMenu;
import com.robotwitter.webapp.menu.MainMenu;
import com.robotwitter.webapp.mobile.MobileUI;
import com.robotwitter.webapp.view.NavigationListener;
import com.robotwitter.webapp.view.UserSession;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;




/**
 * Represents an abstract single container of all UI elements.
 *
 * @author Hagai Akibayov
 */
@Theme("robotwitter")
// Contains circular dependency with AbstractView. Can be fix by refractoring
// this class into an interface, but currently there's no reason to.
@SuppressFBWarnings("CD_CIRCULAR_DEPENDENCY")
public abstract class AbstractUI extends UI
{
	
	/** Instantiates a new abstract UI. */
	public AbstractUI()
	{
		isMainMenuShown = false;
	}


	/**
	 * Activates a Twitter account.
	 *
	 * @param id
	 *            the ID of the Twitter account to activate
	 */
	public final void activateTwitterAccount(long id)
	{
		mainMenu.activateTwitterAccount(id);
		userSession.activateTwitterAccount(id);
	}


	/** @return the current user's browsing session. */
	public final UserSession getUserSession()
	{
		return userSession;
	}
	
	
	/** Hides the main menu. */
	public final void hideMainMenu()
	{
		if (!isMainMenuShown) { return; }
		mainMenuAndContentContainer.removeComponent(mainMenu);
		isMainMenuShown = false;
	}
	
	
	/** @return true, if the user is browsing with a mobile phone. */
	public final boolean isMobile()
	{
		return this instanceof MobileUI;
	}


	/** Shows the main menu. */
	public final void showMainMenu()
	{
		if (isMainMenuShown) { return; }
		mainMenuAndContentContainer.addComponentAsFirst(mainMenu);
		isMainMenuShown = true;
		mainMenu.onAttach();
	}
	
	
	/** Initialises the layout. */
	private void initialiseLayout()
	{
		content = new VerticalLayout();
		mainMenuAndContentContainer = new VerticalLayout(content);
		content.setSizeFull();
		mainMenuAndContentContainer.setSizeFull();
		mainMenuAndContentContainer.setExpandRatio(content, 1);
		setContent(mainMenuAndContentContainer);
		
		content.addStyleName(CONTENT_STYLENAME);
		addStyleName(STYLENAME);
	}
	
	
	/** Initialises the main menu. */
	private void initialiseMainMenu()
	{
		IMenuFactory menuFactory =
			(IMenuFactory) VaadinServlet
				.getCurrent()
				.getServletContext()
				.getAttribute(Configuration.MENU_FACTORY);

		mainMenu = menuFactory.create(MainMenu.NAME);
	}
	
	
	/** Initialises the navigator (Vaadin's object). */
	private void initialiseNavigator()
	{
		navigator = new Navigator(this, content);
		
		ViewProvider viewFactory =
			(ViewProvider) VaadinServlet
				.getCurrent()
				.getServletContext()
				.getAttribute(Configuration.VIEW_FACTORY);

		navigator.addProvider(viewFactory);
		navigator.addViewChangeListener(new NavigationListener());
	}
	
	
	/** Initialises the current user's browsing session. */
	private void initialiseUserSession()
	{
		Injector injector =
			(Injector) VaadinServlet
				.getCurrent()
				.getServletContext()
				.getAttribute(Configuration.INJECTOR);

		IAccountController controller =
			injector.getInstance(IAccountController.class);

		userSession = new UserSession(getSession(), controller);
	}
	
	
	@Override
	protected final void init(VaadinRequest request)
	{
		initialiseLayout();
		initialiseNavigator();
		initialiseUserSession();
		initialiseMainMenu();
	}



	/** Serialisation version unique ID. */
	private static final long serialVersionUID = 1L;

	/** The CSS class name to apply the UI's root. */
	private static final String STYLENAME = "RobotwitterUI";

	/** The CSS class name to apply to the content's wrapper. */
	private static final String CONTENT_STYLENAME = "RobotwitterUI-content";

	/** The container of the main-menu and the content. */
	private VerticalLayout mainMenuAndContentContainer;

	/** The content, replaced by the {@link #navigator} on each view transition. */
	ComponentContainer content;
	
	/**
	 * <code>true</code> if the main menu is shown, <code>false</code> if its
	 * hidden.
	 */
	boolean isMainMenuShown;
	
	/** The main menu. */
	AbstractMenu mainMenu;
	
	/** The navigator that controls view-transition inside the application. */
	Navigator navigator;
	
	/** The current user's browsing session. */
	UserSession userSession;
	
}
