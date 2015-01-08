/**
 * 
 */

package com.robotwitter.twitter;


import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import twitter4j.IDs;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import com.robotwitter.database.interfaces.IDatabaseFollowers;
import com.robotwitter.database.interfaces.IDatabaseNumFollowers;
import com.robotwitter.database.interfaces.IDatabaseTwitterAccounts;
import com.robotwitter.database.primitives.DBFollowersNumber;
import com.robotwitter.database.primitives.DBTwitterAccount;




/**
 * @author Itay
 *
 *         a class handling the backfilling of the followers id database,
 *         meaning that once per day, it will wake up, and retrieve the
 *         followers id's of the user, and update the database accordingly.
 */
public class FollowerIdsBackfiller
{
	/**
	 * @param followersDB
	 * @param accountsDB
	 * @param factory
	 */
	@Inject
	public FollowerIdsBackfiller(
		IDatabaseFollowers followersDB,
		IDatabaseNumFollowers followersNumDB,
		IDatabaseTwitterAccounts accountsDB,
		@Named("User based factory") TwitterFactory factory)
	{
		this.accountsDB = accountsDB;
		this.followersDB = followersDB;
		this.followersNumDB = followersNumDB;
		twitterConnector = factory.getInstance();
		userAccount = null;
	}
	
	
	public void setUser(Long userID)
	{
		userAccount = accountsDB.get(userID);
		if (userAccount == null) { throw new RuntimeException(
			"Tried to track a user that doesn't exist!"); }
		twitterConnector.setOAuthAccessToken(new AccessToken(userAccount
			.getToken(), userAccount.getPrivateToken()));
	}
	
	
	public void start()
	{
		workTimer = new Timer(true);
		workTimer.scheduleAtFixedRate(new TimerTask()
		{
			
			@Override
			public void run()
			{
				doDailyTask();
				
			}
		}, new Date(), MILLIS_IN_SECOND * FULL_DAY);
	}
	
	
	public void stop()
	{
		workTimer.cancel();
	}
	
	
	/**
	 * @param followerNumberEntry
	 */
	private void addToFollowerNumberDatabase(
		DBFollowersNumber followerNumberEntry)
	{
		followersNumDB.insert(followerNumberEntry);
	}
	
	
	/**
	 * @param todaysFollowerNumber
	 * @param newFollowersNumber
	 * @param newUnfollowerNumber
	 * @return
	 */
	private DBFollowersNumber buildFollowerNumberEntry(
		int todaysFollowerNumber,
		int newFollowersNumber,
		int newUnfollowerNumber)
	{
		// TODO Implement after we changed the database.
		return null;
	}
	
	
	/**
	 * @param yesterdaysFirstPage
	 * @param todaysFirstPage
	 * @return
	 */
	private int calcNewFollowersNumber(
		IDs yesterdaysFirstPage,
		IDs todaysFirstPage)
	{
		long[] yesterdaysIDs = yesterdaysFirstPage.getIDs();
		
		long[] todaysIDs = todaysFirstPage.getIDs();
		
		for (int i = 0; i < Math.min(yesterdaysIDs.length, GIVE_UP_LIMIT); i++)
		{
			for (int j = 0; j < todaysIDs.length; j++)
			{
				if (yesterdaysIDs[i] == todaysIDs[j]) { return i - j; }
			}
		}
		return HIGHEST_LIMIT;
	}
	
	
	/**
	 * 
	 */
	private void clearYesterdaysFollowersDatabase()
	{
		// TODO: Implement after the database implemented delete function for
		// user.
	}
	
	
	/**
	 * 
	 */
	private void doDailyTask()
	{
		IDs firstPage = getFirstFollowersPage();
		while (firstPage == null)
		{
			sleepFor(REPAIR_TIME);
			firstPage = getFirstFollowersPage();
		}
		
		// Use todays number of followers, yesterdays number of followers,
		// and the first page from today and yesterday to calculate the
		// number of followers gained and lost.
		int todaysFollowerNumber = saveTodaysFollowersToDatabase(firstPage);
		int newFollowersNumber =
			calcNewFollowersNumber(yesterdaysFirstPage, firstPage);
		int newUnfollowerNumber =
			newFollowersNumber
				- (todaysFollowerNumber - yesterdayFollowerNumber);
		updateFollowerNumberDatabase(
			todaysFollowerNumber,
			newFollowersNumber,
			newUnfollowerNumber);
		
		updateYesterdaysVariables(firstPage, todaysFollowerNumber);
	}
	
	
	/**
	 * @return
	 */
	private IDs getFirstFollowersPage()
	{
		IDs $;
		try
		{
			$ = twitterConnector.getFollowersIDs(-1);
		} catch (TwitterException e)
		{
			e.printStackTrace();
			return null;
		}
		return $;
	}
	
	
	/**
	 * @param followersPage
	 * @return
	 */
	private IDs getNextFollowersPage(IDs followersPage)
	{
		try
		{
			followersPage =
				twitterConnector.getFollowersIDs(followersPage.getNextCursor());
		} catch (TwitterException e)
		{
			e.printStackTrace();
			sleepFor(REPAIR_TIME);
		}
		return followersPage;
	}
	
	
	/**
	 * @param firstPage
	 * @return the total number of followers backfilled
	 */
	private int saveTodaysFollowersToDatabase(IDs firstPage)
	{
		int $ = firstPage.getIDs().length;
		IDs nextPage = firstPage;
		clearYesterdaysFollowersDatabase();
		while (nextPage.getNextCursor() != 0
			&& nextPage.getRateLimitStatus().getRemaining() > 0)
		{
			updatePageToDatabase(nextPage);
			if (nextPage.getRateLimitStatus().getRemaining() == 0)
			{
				// sleep until we can do more API calls.
				sleepFor(nextPage.getRateLimitStatus().getResetTimeInSeconds() + 1);
			}
			nextPage = getNextFollowersPage(nextPage);
			$ += nextPage.getIDs().length;
		}
		return $;
	}
	
	
	/**
	 * @param timeInSeconds
	 */
	private void sleepFor(long timeInSeconds)
	{
		try
		{
			Thread.sleep(1000 * timeInSeconds);
		} catch (InterruptedException e)
		{
			// Its ok, we are just waking up!
		}
		
	}
	
	
	/**
	 * @param todaysFollowerNumber
	 * @param newFollowersNumber
	 * @param newUnfollowerNumber
	 */
	private void updateFollowerNumberDatabase(
		int todaysFollowerNumber,
		int newFollowersNumber,
		int newUnfollowerNumber)
	{
		DBFollowersNumber followerNumberEntry =
			buildFollowerNumberEntry(
				todaysFollowerNumber,
				newFollowersNumber,
				newUnfollowerNumber);
		addToFollowerNumberDatabase(followerNumberEntry);
	}
	
	
	/**
	 * @param followersPage
	 */
	private void updatePageToDatabase(IDs followersPage)
	{
		for (Long followerID : followersPage.getIDs())
		{
			followersDB.insert(userAccount.getUserId(), followerID);
		}
	}
	
	
	/**
	 * @param firstPage
	 * @param todaysFollowerNumber
	 */
	private void updateYesterdaysVariables(
		IDs firstPage,
		int todaysFollowerNumber)
	{
		yesterdaysFirstPage = firstPage;
		yesterdayFollowerNumber = todaysFollowerNumber;
		
	}
	
	
	
	private static final int HIGHEST_LIMIT = 5000;
	
	private static final int GIVE_UP_LIMIT = 100;
	
	private static final long MILLIS_IN_SECOND = 1000;
	
	private static final long FULL_DAY = 60 * 60 * 24;
	
	private static final long REPAIR_TIME = 60;
	
	private IDs yesterdaysFirstPage;
	
	private int yesterdayFollowerNumber;
	
	private DBTwitterAccount userAccount;
	
	private Timer workTimer;
	
	private Twitter twitterConnector;
	
	private IDatabaseNumFollowers followersNumDB;
	
	private IDatabaseFollowers followersDB;
	
	private IDatabaseTwitterAccounts accountsDB;
}
