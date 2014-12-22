
package com.robotwitter.webapp.view;


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
import com.robotwitter.webapp.ui.AbstractMenu;
import com.robotwitter.webapp.ui.MainMenu;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;




/**
 * Represents the single container of all UI elements.
 * <p>
 * An instance of this class is created by the
 * {@link com.robotwitter.webapp.Servlet} when a user-connection is made, and
 * then the UI instance is available only to that user for the session (Closing
 * browser, losing connection for more than a predefined timeout, etc).
 * Basically, every user communicates with a single UI instance.
 *
 * @author Hagai Akibayov
 */
@Theme("robotwitter")
// Contains circular dependency with AbstractView. Can be fix by refractoring
// this class into an interface, but currently there's no reason to.
@SuppressFBWarnings("CD_CIRCULAR_DEPENDENCY")
public class RobotwitterUI extends UI
{
	
	/** Instantiates a new robotwitter UI. */
	public RobotwitterUI()
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
	
	
	/** Initialises the layout. */
	private void initialiseLayout()
	{
		content = new VerticalLayout();
		mainMenuContainer = new VerticalLayout(content);
		setContent(mainMenuContainer);
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


	/** Hides the main menu. */
	final void hideMainMenu()
	{
		if (!isMainMenuShown) { return; }
		mainMenuContainer.removeComponent(mainMenu);
		isMainMenuShown = false;
	}
	
	
	/** Shows the main menu. */
	final void showMainMenu()
	{
		if (isMainMenuShown) { return; }
		mainMenuContainer.addComponentAsFirst(mainMenu);
		isMainMenuShown = true;
	}
	
	
	
	/** Serialisation version unique ID. */
	private static final long serialVersionUID = 1L;

	/** The container of the main-menu and the content. */
	private VerticalLayout mainMenuContainer;
	
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
