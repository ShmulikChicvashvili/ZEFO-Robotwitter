
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
	 * Retrieves the password of the user whose mail is email. Assumes the user
	 * exists in the system (checked my the function authenticate)
	 * 
	 * @param email
	 *            The user's email address
	 */
	void retrieve(String email);
}
