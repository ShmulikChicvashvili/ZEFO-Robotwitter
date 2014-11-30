
package com.robotwitter.webapp.control.login;


/** Login user interface controller. */
public interface PasswordRetrievalController
{
	/**
	 * @param email
	 *            The user's email address
	 * @return true if email exists in DB, false otherwise
	 */
	boolean authenticate(String email);
	
	
	/**
	 * @param email
	 *            The user's email address
	 */
	void retrieve(String email);
}
