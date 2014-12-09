
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

		/** The received credentials are unauthentic. */
		AUTHENTICATION_FAILURE,

		/** An unknown failure occurred . */
		FAILURE
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
