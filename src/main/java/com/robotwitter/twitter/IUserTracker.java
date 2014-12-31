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
	 * @author Itay, Shmulik
	 *
	 */
	public enum Status {
		SUCCESS,
		
		ACCOUNT_DOESNT_EXIST,
		
		FAILURE
	}
	
	/**
	 * @param listener a stream listener to listen on the user stream
	 * @return the success status of the action performed
	 */
	Status addListener(UserStreamListener listener);
	
	/**
	 * @return the success status of the action performed
	 */
	Status beginTrack();
	
	/**
	 * @param listener a stream listener to listen on the user stream
	 * @return the success status of the action performed
	 */
	Status removeListener(UserStreamListener listener);
	
	/**
	 * @return the success status of the action performed
	 */
	Status stopTrack();	
}
