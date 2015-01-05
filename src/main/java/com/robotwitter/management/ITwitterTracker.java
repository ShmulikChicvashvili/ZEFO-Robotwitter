/**
 * 
 */

package com.robotwitter.management;

import twitter4j.UserStreamListener;

import com.robotwitter.twitter.IUserTracker;


/**
 * @author Itay, Shmulik
 *
 *         An interface to track all the accounts the Robotwitter system is
 *         managing.
 */
public interface ITwitterTracker
{
	public enum Status
	{
		SUCCESS,
		
		TRACKER_ALREADY_EXISTS,
		
		TRACKER_DOESNT_EXIST,
		
		FAILURE
	}
	
	public Status addListenerToTracker(Long userID, UserStreamListener listener);
	
	public Status addUserTracker(IUserTracker tracker);
	
	public Status removeListenerFromTracker(Long userID, UserStreamListener listener);
	
	public Status removeUserTracker(Long userID);
	
	public Status startTracker(Long userID);
	
	public Status stopTracker(Long userID);
}
