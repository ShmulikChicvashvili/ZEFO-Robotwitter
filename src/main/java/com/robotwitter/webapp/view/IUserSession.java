
package com.robotwitter.webapp.view;


import java.io.Serializable;




/**
 * Represents a user's browsing session.
 * <p>
 * This class handles all user-related session attributes and cookies. For
 * example, calling {@link #sign}, an implementation might set a session
 * attribute and a cookie to store the currently signed in user. This
 * information can be fetched later using {@link #get}.
 *
 * @author Hagai Akibayov
 *
 */
public interface IUserSession extends Serializable
{
	
	/** @return the currently signed in user's email address. */
	String get();
	
	
	/**
	 * @return <code>true</code> if the user is signed in, <code>false</code>
	 *         otherwise.
	 */
	boolean isSigned();


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
