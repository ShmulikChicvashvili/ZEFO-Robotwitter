
package com.robotwitter.webapp.control.account;


import java.io.Serializable;
import java.util.Collection;




/**
 * Controller of a single user account.
 * <p>
 * The controller must first be connected to a user account using
 * {@link #connect} before making any other operations. Neglecting to connect a
 * user will result in succeeding operations returning <code>null</code> values.
 *
 * @author Hagai Akibayov
 */
public interface IAccountController extends Serializable
{
	
	/** Status codes returned by this instance. */
	enum Status
	{
		/** Operation succeeded. */
		SUCCESS,

		/** The received email address is not attached to any existing user. */
		USER_DOESNT_EXIST,

		/** The received Twitter account is not attached to the connected user. */
		TWITTER_ACCOUNT_DOESNT_EXIST,

		/** An unknown failure occurred . */
		FAILURE
	}
	
	
	
	/**
	 * Activates a twitter account.
	 *
	 * @param id
	 *            the Twitter account's ID
	 *
	 * @return the operation's status
	 */
	Status activateTwitterAccount(long id);
	
	
	/**
	 * Connect this controller to an existing user.
	 *
	 * @param email
	 *            The user's email address
	 *
	 * @return the operation's status
	 */
	Status connect(String email);


	/**
	 * Disconnect the currently connected user.
	 * <p>
	 * Note: do nothing if no user is currently connected.
	 */
	void disconnect();
	
	
	/**
	 * @return the user's active Twitter account, or <code>null</code> if no
	 *         user is currently connected to this controller or the user had
	 *         yet to select a Twitter account.
	 */
	ITwitterAccountController getActiveTwitterAccount();
	
	
	/**
	 * @return the connected user's email, or <code>null</code> if no user is
	 *         currently connected to this controller.
	 */
	String getEmail();


	/**
	 * @return the connected user's name, or <code>null</code> if no user is
	 *         currently connected to this controller.
	 */
	String getName();


	/**
	 * @return the twitter accounts connected to the connected used, or
	 *         <code>null</code> if no user is currently connected to this
	 *         controller.
	 */
	Collection<ITwitterAccountController> getTwitterAccounts();
}
