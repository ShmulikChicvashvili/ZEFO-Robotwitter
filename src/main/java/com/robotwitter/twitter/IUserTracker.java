/**
 * 
 */

package com.robotwitter.twitter;


/**
 * @author Itay
 * 
 *         An interface which provides following a user via stream. Basically a
 *         wrapper to the UserStream stream in the twitter4j library.
 */
public interface IUserTracker
{
	/**
	 * @author Itay
	 *
	 *         A return status enum.
	 */
	public enum Status
	{
		/** Operation succeeded. */
		SUCCESS,
		
		/** The received twitter id is not attached to any existing user. */
		ACCOUNT_DOESNT_EXIST,
		
		/** An unknown failure occurred . */
		FAILURE
	}
	
}
