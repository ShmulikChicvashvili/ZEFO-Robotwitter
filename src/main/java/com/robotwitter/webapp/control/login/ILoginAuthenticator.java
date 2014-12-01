
package com.robotwitter.webapp.control.login;


/** Login user interface controller. */
public interface ILoginAuthenticator
{
	/**
	 * @param email
	 *            The user's email address
	 * @param password
	 *            The user's password
	 * @return true if authentic, false otherwise
	 */
	boolean authenticate(String email, String password);
}
