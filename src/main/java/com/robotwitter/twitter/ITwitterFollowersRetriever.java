/**
 * 
 */

package com.robotwitter.twitter;


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
	 * @return the number of followers the user has according to this date. If
	 *         the user doesnt exist, then -1 is returned.
	 * 
	 */
	int retrieveFollowersAmount(long userID);
	
}
