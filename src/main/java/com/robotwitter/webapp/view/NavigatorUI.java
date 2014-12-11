
package com.robotwitter.webapp.view;


import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.navigator.ViewProvider;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;

import com.robotwitter.webapp.Configuration;
import com.robotwitter.webapp.view.dashboard.DashboardView;
import com.robotwitter.webapp.view.login.LoginView;




/**
 * The view navigator which managers transitions between views.
 * <p>
 * An instance of this class is created by the
 * {@link com.robotwitter.webapp.Servlet} when a client-connection is made, and
 * then the UI instance is available only to that client for the client's usage
 * session (Closing browser, losing connection for more than a pre-defined
 * timout, etc).
 *
 * @author Hagai Akibayov
 */
@Theme("robotwitter")
public class NavigatorUI extends UI
{

	/**
	 * Listens to navigation actions (view transitions) and performs operations
	 * before and/or after the transition toke place.
	 * <p>
	 * E.g., this class will deny transitions to certain pages when the user is
	 * not logged in.
	 */
	static class NavigationListener implements ViewChangeListener
	{

		@Override
		public void afterViewChange(ViewChangeEvent event)
		{ /* Do nothing */}


		@Override
		public boolean beforeViewChange(ViewChangeEvent event)
		{
			// Retrieve needed objects
			Navigator navigator = event.getNavigator();
			NavigatorUI ui = (NavigatorUI) navigator.getUI();
			UserSession userSession = ui.getUserSession();
			AbstractView view = (AbstractView) event.getNewView();

			// If the user is not signed in and the view requires signing in
			if (view.isSignedInRequired() && !userSession.isSigned())
			{
				navigator.navigateTo(LoginView.NAME);
				return false;
			}
			
			// If the user is signed in and the view prohibits signed users
			if (view.isSignedInProhibited() && userSession.isSigned())
			{
				navigator.navigateTo(DashboardView.NAME);
				return false;
			}
			
			return true;
		}



		/** Serialisation version unique ID. */
		private static final long serialVersionUID = 1L;
		
	}



	/** @return the current user's browsing session. */
	public final UserSession getUserSession()
	{
		return userSession;
	}
	
	
	/** Initialises the navigator (Vaadin's object). */
	private void initialiseNavigator()
	{
		navigator = new Navigator(this, this);
		
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
		userSession = new UserSession(getSession());
	}
	
	
	@Override
	protected final void init(VaadinRequest request)
	{
		initialiseUserSession();
		initialiseNavigator();
	}
	
	
	
	/** Serialisation version unique ID. */
	private static final long serialVersionUID = 1L;
	
	/** The navigator that controls view-transition inside the application. */
	Navigator navigator;
	
	/** The current user's browsing session. */
	UserSession userSession;
	
}
