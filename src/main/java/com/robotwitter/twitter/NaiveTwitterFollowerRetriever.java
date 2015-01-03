/**
 * 
 */

package com.robotwitter.twitter;

import java.sql.Timestamp;
import java.util.Date;
import java.util.TreeMap;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;


/**
 * @author Itay
 * 
 *         Implements the retrieveFollowersAmount using 2 application only calls
 *         to the API
 */
public class NaiveTwitterFollowerRetriever
	implements
		ITwitterFollowersRetriever
{
	/**
	 * @param tf
	 *            a TwitterFactory configured for the robotwitter app to create
	 *            Twitter instances
	 */
	public NaiveTwitterFollowerRetriever(TwitterFactory tf)
	{
		twitterConnector = tf.getInstance();
	}
	
	
	
	/* (non-Javadoc) @see com.robotwitter.twitter.ITwitterFollowersRetriever#retrieveFollowersAmount(long) */
	@Override
	public TreeMap<Timestamp, Integer> retrieveFollowersAmount(long userID)
	{
		try
		{
			User twitterUser = twitterConnector.showUser(userID); //This calls the twitter API
			TreeMap<Timestamp,Integer> $ = new TreeMap<Timestamp, Integer>();
			$.put(new Timestamp(new Date().getTime()), twitterUser.getFollowersCount());
			return $;
		} catch (TwitterException e)
		{
			// User doesn't exist, or network issues.
			return null;
		}
	}
	
	
	/* (non-Javadoc) @see
	 * com.robotwitter.twitter.ITwitterFollowersRetriever#retrieveFollowersAmount
	 * (com.robotwitter.twitter.TwitterAccount) */
	@Override
	public int retrieveLatestFollowersAmount(long userID)
	{
		try
		{
			User twitterUser = twitterConnector.showUser(userID); //This calls the twitter API
			return twitterUser.getFollowersCount();
		} catch (TwitterException e)
		{
			// User doesn't exist, or network issues.
			return -1;
		}
		
	}


	private Twitter twitterConnector;
	
}
