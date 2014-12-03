
package com.robotwitter.webapp.control.login;


/** Login user interface controller. */
public interface PasswordRetrievalController
{
	/**
	 * Retrieve a user's password.
	 *
	 * @param email
	 *            The user's email address
	 * @return true if password has been retrieved, false otherwise.
	 */
	boolean retrieve(final String email);
}
