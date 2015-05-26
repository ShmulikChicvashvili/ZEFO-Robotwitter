/**
 * 
 */
package com.robotwitter.webapp.control.dashboard;

import java.util.List;

import com.robotwitter.webapp.view.dashboard.ConnectedAccountInfo;

/**
 * @author Itay
 * 
 * this is the interface for the controller of the dashboard page (the main
 * page of the website), this page contains notifications and quick need-to-know
 * information about the users account (meaning, of all his twitter accounts!).
 */
public interface IDashboardController
{

	/**
	 * @return
	 */
	List<ConnectedAccountInfo> getConnectedAccountsInfo();

	/**
	 * @return the information of the currently active twitter account.
	 */
	ConnectedAccountInfo getCurrentAccountInfo();	
	
}
