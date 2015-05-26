/**
 * 
 */

package com.robotwitter.webapp.control.dashboard;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.robotwitter.webapp.view.dashboard.ConnectedAccountInfo;




/**
 * @author Itay
 *
 */
public class DashboardController implements IDashboardController
{
	
	/* (non-Javadoc) @see
	 * com.robotwitter.webapp.control.dashboard.IDashboardController
	 * #getConnectedAccountsInfo() */
	@Override
	public List<ConnectedAccountInfo> getConnectedAccountsInfo()
	{
		ArrayList<ConnectedAccountInfo> $ = new ArrayList<ConnectedAccountInfo>();
		$.add(getCurrentAccountInfo());
		$.add(getCurrentAccountInfo());
		$.add(getCurrentAccountInfo());
		return $;	
	}
	
	
	/* (non-Javadoc) @see
	 * com.robotwitter.webapp.control.dashboard.IDashboardController
	 * #getCurrentAccountInfo() */
	@Override
	public ConnectedAccountInfo getCurrentAccountInfo()
	{
		return new ConnectedAccountInfo(
			0,
			"Itay",
			"itaykh",
			"The man",
			0,
			0,
			"israel",
			0,
			"heb",
			false,
			new Timestamp(0),
			"https://pbs.twimg.com/profile_images/546786848849158145/wS82lZr8.jpeg",
			0,
			0,
			0);
	}
	
}
