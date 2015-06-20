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
	 * @return the information of the  twitter account.
	 */
	ConnectedAccountInfo getAccountInfo(long id);	

	/**
	 * @return
	 */
	List<ConnectedAccountInfo> getConnectedAccountsInfo();

	/**
	 * @param email the email of the user
	 */
	void setUser(String email);
	
}
