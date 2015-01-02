/**
 * 
 */

package com.robotwitter.twitter;


import java.sql.Timestamp;
import java.util.TreeMap;




/**
 * @author Itay
 *
 *         A class to retrieve the followers information from twitter.
 */
public interface ITwitterFollowersRetriever
{
	
	/**
	 * @param userID
	 *            the id of the user we want to retrieve the information about
	 * @return a TreeMap (hence it is sorted by time) containing the followers
	 *         count of the user over time.
	 */
	TreeMap<Timestamp, Integer> retrieveFollowersAmount(long userID);
	
	
	/**
	 * @param userID
	 *            the id of the user we want to retrieve the information about
	 * @return the number of followers the user has according to this date. If
	 *         the user doesnt exist, then -1 is returned.
	 * 
	 */
	int retrieveLatestFollowersAmount(long userID);
	
}
