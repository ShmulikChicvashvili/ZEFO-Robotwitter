/**
 * 
 */

package com.robotwitter.twitter;


import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;

import com.robotwitter.database.interfaces.IDatabaseNumFollowers;
import com.robotwitter.database.primitives.DBFollowersNumber;




/**
 * @author Itay
 *
 */
public class TwitterFollowerRetriever implements ITwitterFollowersRetriever
{
	
	/**
	 * @param tf
	 *            a TwitterFactory configured for the robotwitter app to create
	 *            Twitter instances
	 */
	public TwitterFollowerRetriever(TwitterFactory tf, IDatabaseNumFollowers db)
	{
		twitterConnector = tf.getInstance();
		followersCountDB = db;
	}
	
	
	/* (non-Javadoc) @see com.robotwitter.twitter.ITwitterFollowersRetriever#retrieveFollowersAmount(long) */
	@Override
	public TreeMap<Timestamp, Integer> retrieveFollowersAmount(long userID)
	{
		Timestamp now = new Timestamp(new Date().getTime());
		Timestamp latestEntry = null;
		
		List<DBFollowersNumber> followerCounts = followersCountDB.get(userID);
		TreeMap<Timestamp,Integer> $ = new TreeMap<Timestamp,Integer>();
		
		if(followerCounts != null) {
			latestEntry = followerCounts.get(0).getDate();
			for(DBFollowersNumber followerCount: followerCounts) {
				$.put(followerCount.getDate(), followerCount.getNumFollowers());
				if(latestEntry.before(followerCount.getDate())) {
					latestEntry = followerCount.getDate();
				}
			}
		}
		
		if(latestEntry != null && latestEntry.getDay() < now.getDay()) {
			$.put(now, addNewFollowersEntryToDB(userID, now));
		}
		return $;
	}
	
	
	/* (non-Javadoc) @see
	 * com.robotwitter.twitter.ITwitterFollowersRetriever#retrieveFollowersAmount
	 * (com.robotwitter.twitter.TwitterAccount) */
	@SuppressWarnings("deprecation")
	@Override
	public int retrieveLatestFollowersAmount(long userID)
	{
		Timestamp now = new Timestamp(new Date().getTime());
		
		DBFollowersNumber countFromDB = getLatestDBFollowerCount(userID);
		if(countFromDB != null && countFromDB.getDate().getDay() == now.getDay()) {
			return countFromDB.getNumFollowers();
		}
		return addNewFollowersEntryToDB(userID, now);
		
	}


	/**
	 * @param userID
	 * @param now
	 * @return
	 */
	private int addNewFollowersEntryToDB(long userID, Timestamp now)
	{
		User twitterUser;
		try
		{
			twitterUser = twitterConnector.showUser(userID);
			followersCountDB.insert(new DBFollowersNumber(userID, now, twitterUser.getFollowersCount()));
			return twitterUser.getFollowersCount();
		} catch (TwitterException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}
	
	
	
	/**
	 * @param userID
	 *            the twitter account we want to retrieve
	 * @return the latest followers count saved for that user in the database.
	 */
	private DBFollowersNumber getLatestDBFollowerCount(long userID)
	{
		List<DBFollowersNumber> followerCounts = followersCountDB.get(userID);
		if (followerCounts == null) { return null; }
		DBFollowersNumber $ = followerCounts.get(0);
		for (DBFollowersNumber followerCount : followerCounts)
		{
			if ($.getDate().before(followerCount.getDate()))
			{
				$ = followerCount;
			}
		}
		return $;
	}
	
	private IDatabaseNumFollowers followersCountDB;
	
	private int lastFollowersCount;
	
	private Timestamp lastUpdated;

	private Twitter twitterConnector;
	
}
