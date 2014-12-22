
package com.robotwitter.webapp.control.account;


import java.io.Serializable;




/**
 * Controller of a user's twitter account connection.
 *
 * @author Hagai Akibayov
 */
public interface ITwitterConnectorController extends Serializable
{
	
	/** Status codes returned by this instance. */
	enum Status
	{
		/** Operation succeeded. */
		SUCCESS,

		/** The received email address is not attached to any existing user. */
		USER_DOESNT_EXIST,

		/** The enter PIN code is incorrect. */
		PIN_IS_INCORRECT,

		/** An unknown failure occurred . */
		FAILURE
	}
	
	
	
	/**
	 * Connect a Twitter account (using PIN code) to a given user.
	 * <p>
	 * {@link #getConnectionURL} must be called beforehand, otherwise
	 * {@link Status#FAILURE} will be returned.
	 *
	 * @param email
	 *            the email of the user to connect the Twitter account to
	 * @param pin
	 *            the PIN code entered by the user (the one the user received
	 *            from Twitter's connection URL).
	 *
	 * @return the operation's status
	 */
	Status connect(String email, String pin);
	
	
	/**
	 * @return the URL of the Twitter user connection page, or <code>null</code>
	 *         on failure.
	 */
	String getConnectionURL();
	
	
	/**
	 * @return the recently connected Twitter account's ID, or <code>-1</code>
	 *         on failure.
	 */
	long getID();
	
	
	/**
	 * @return the recently connected Twitter account's screenname, or
	 *         <code>null</code> on failure.
	 */
	String getScreenname();
}
