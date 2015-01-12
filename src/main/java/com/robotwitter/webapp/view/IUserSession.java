
package com.robotwitter.webapp.view;


import java.io.Serializable;

import com.robotwitter.webapp.control.account.IAccountController;
import com.robotwitter.webapp.util.RobotwitterCustomComponent;




/**
 * Represents a user's browsing session.
 * <p>
 * This class handles all user-related session attributes and cookies. For
 * example, calling {@link #sign}, an implementation might set a session
 * attribute and a cookie to store the currently signed in user.
 *
 * @author Hagai Akibayov
 *
 */
public interface IUserSession extends Serializable
{

	/**
	 * Activates a connected Twitter account.
	 *
	 * @param id
	 *            the Twitter account's ID
	 */
	void activateTwitterAccount(long id);
	
	
	/** @return the current user's account controller. */
	IAccountController getAccountController();
	
	
	/**
	 * @return <code>true</code> if the user is signed in, <code>false</code>
	 *         otherwise.
	 */
	boolean isSigned();


	/**
	 * Observe a change in the active Twitter Account.
	 *
	 * The {@link RobotwitterCustomComponent#activateTwitterAccount} method will
	 * be called on each observing component after the active Twitter Account
	 * changes.
	 *
	 * @param component
	 *            the component that will be the observer
	 */
	void observeActiveTwitterAccount(RobotwitterCustomComponent component);


	/**
	 * Keeps a user signing for this session.
	 * <p>
	 * Note: does nothing when a user is already signed in (i.e., when
	 * {@link #isSigned} returns <code>true</code>).
	 *
	 * @param email
	 *            the user's email address
	 * @param remember
	 *            <code>true</code> if the user should be remembered during the
	 *            next session, <code>false</code> otherwise
	 */
	void sign(String email, boolean remember);
	
	
	/** Signs out a currently signed in user. */
	void unsign();
}
