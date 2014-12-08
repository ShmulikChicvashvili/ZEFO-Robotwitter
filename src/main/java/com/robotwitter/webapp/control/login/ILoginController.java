
package com.robotwitter.webapp.control.login;


import java.io.Serializable;




/** Login user interface controller. */
public interface ILoginController extends Serializable
{
	
	/** Status codes returned by this instance. */
	enum Status
	{
		/** Operation succeeded. */
		SUCCESS,

		/** The received email address is not attached to any existing user. */
		USER_DOESNT_EXIST,

		/** A communication error has occurred while trying to send the email. */
		AUTHENTICATION_FAILURE
	}



	/**
	 * Authenticates a user's credentials.
	 *
	 * @param email
	 *            The user's email address
	 * @param password
	 *            The user's password
	 * @return true if authentic, false otherwise
	 */
	Status authenticate(String email, String password);
}
