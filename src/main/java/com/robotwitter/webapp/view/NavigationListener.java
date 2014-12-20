
package com.robotwitter.webapp.view;


import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener;

import com.robotwitter.webapp.view.dashboard.DashboardView;
import com.robotwitter.webapp.view.login.LoginView;




/**
 * Listens to navigation actions (view transitions) and performs operations
 * before and/or after the transition toke place.
 * <p>
 * E.g., this class will deny transitions to certain pages when the user is not
 * logged in.
 */
class NavigationListener implements ViewChangeListener
{
	
	@Override
	public void afterViewChange(ViewChangeEvent event)
	{ /* Do nothing */}
	
	
	@Override
	public boolean beforeViewChange(ViewChangeEvent event)
	{
		// Retrieve needed objects
		Navigator navigator = event.getNavigator();
		RobotwitterUI ui = (RobotwitterUI) navigator.getUI();
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
		
		// Show main menu if the user is signed in, otherwise hide
		if (userSession.isSigned())
		{
			ui.showMainMenu();
		} else
		{
			ui.hideMainMenu();
		}
		
		return true;
	}
	
	
	
	/** Serialisation version unique ID. */
	private static final long serialVersionUID = 1L;
	
}
