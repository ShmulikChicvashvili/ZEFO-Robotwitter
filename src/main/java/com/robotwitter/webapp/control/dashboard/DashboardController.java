/**
 *
 */

package com.robotwitter.webapp.control.dashboard;


import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;

import com.robotwitter.database.interfaces.IDatabaseFollowers;
import com.robotwitter.database.interfaces.IDatabaseNumFollowers;
import com.robotwitter.database.interfaces.IDatabaseResponses;
import com.robotwitter.database.interfaces.IDatabaseTwitterAccounts;
import com.robotwitter.database.primitives.DBFollower;
import com.robotwitter.database.primitives.DBFollowersNumber;
import com.robotwitter.database.primitives.DBTwitterAccount;
import com.robotwitter.webapp.view.dashboard.ConnectedAccountInfo;




/**
 * @author Itay
 *
 */
public class DashboardController implements IDashboardController
{
	
	/**
	 * @param twitterDB
	 *            the twitter database
	 * @param notificationsDB
	 *            the responses database
	 * @param followerNumberDB
	 *            the followers number database
	 */
	@Inject
	public DashboardController(
		IDatabaseFollowers twitterDB,
		IDatabaseResponses notificationsDB,
		IDatabaseNumFollowers followerNumberDB,
		IDatabaseTwitterAccounts accountsDB)
	{
		this.twitterDB = twitterDB;
		this.notificationsDB = notificationsDB;
		this.followerNumberDB = followerNumberDB;
		this.accountsDB = accountsDB;
		userEmail = null;
	}

	/* (non-Javadoc) @see
	 * com.robotwitter.webapp.control.dashboard.IDashboardController
	 * #getCurrentAccountInfo() */
	@Override
	public ConnectedAccountInfo getAccountInfo(long id)
	{
		DBFollower accountData = twitterDB.get(id);
		DBFollowersNumber followersNum = lastUpdateInDatabase(id);
		int unanswered =
			notificationsDB.getUnansweredResponsesOfUser(id).size();
		
		return new ConnectedAccountInfo(
			accountData.getFollowerId(),
			accountData.getName(),
			accountData.getScreenName(),
			accountData.getDescription(),
			followersNum.getNumFollowers(),
			accountData.getFollowing(),
			accountData.getLocation(),
			accountData.getFavorites(),
			accountData.getLanguage(),
			accountData.getIsCelebrity(),
			accountData.getJoined(),
			accountData.getPicture(),
			unanswered,
			followersNum.getNumJoined(),
			followersNum.getNumLeft());
	}
	
	
	/* (non-Javadoc) @see
	 * com.robotwitter.webapp.control.dashboard.IDashboardController
	 * #getConnectedAccountsInfo() */
	@Override
	public List<ConnectedAccountInfo> getConnectedAccountsInfo()
	{
		ArrayList<ConnectedAccountInfo> $ =
			new ArrayList<ConnectedAccountInfo>();
		ArrayList<DBTwitterAccount> accounts = accountsDB.get(userEmail);
		if (accounts != null) {
			for (DBTwitterAccount account: accounts) {
				$.add(getAccountInfo(account.getUserId()));
			}
		}
		return $;
	}
	
	
	/* (non-Javadoc) @see
	 * com.robotwitter.webapp.control.dashboard.IDashboardController
	 * #setUser(java.lang.String) */
	@Override
	public void setUser(String email)
	{
		userEmail = email;
		
	}
	
	
	private final DBFollowersNumber lastUpdateInDatabase(long id)
	{
		final List<DBFollowersNumber> dbfollowers = followerNumberDB.get(id);
		if (dbfollowers.isEmpty()) { return null; }
		DBFollowersNumber latest = null;
		boolean firstTime = true;
		
		for (final DBFollowersNumber follower : dbfollowers)
		{
			if (firstTime)
			{
				firstTime = false;
				latest = follower;
			} else
			{
				if (follower.getDate().after(latest.getDate()))
				{
					latest = follower;
				}
			}
		}
		return latest;
	}
	
	
	private IDatabaseTwitterAccounts accountsDB;
	
	
	
	private String userEmail;
	
	private IDatabaseFollowers twitterDB;
	
	private IDatabaseResponses notificationsDB;
	
	private IDatabaseNumFollowers followerNumberDB;
	
}
