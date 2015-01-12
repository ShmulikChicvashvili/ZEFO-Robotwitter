/**
 * 
 */

package com.robotwitter.twitter;

import twitter4j.UserStreamListener;


/**
 * @author Itay, Shmulik
 * 
 *         An interface which provides following a user via stream. Basically a
 *         wrapper to the UserStream stream in the twitter4j library.
 */
public interface IUserTracker
{
	
	/**
	 * @param backfiller a REST API backfiller to listen on the user
	 * @return the success status of the action performed
	 */
	void addBackfiller(IUserBackfiller backfiller);
	
	/**
	 * @param listener a stream listener to listen on the user stream
	 * @return the success status of the action performed
	 */
	void addListener(UserStreamListener listener);
	
	/**
	 * @return the success status of the action performed
	 */
	void beginTrack();
	
	/**
	 * @return the account id of the user the tracker is tracking
	 */
	Long getTrackedUser();
	
	/**
	 * @param backfiller a REST API backfiller to listen on the user
	 * @return the success status of the action performed
	 */
	void removeBackfiller(IUserBackfiller backfiller);
	
	/**
	 * @param listener a stream listener to listen on the user stream
	 * @return the success status of the action performed
	 */
	void removeListener(UserStreamListener listener);
	
	/**
	 * @return the success status of the action performed
	 */
	void stopTrack();
}
