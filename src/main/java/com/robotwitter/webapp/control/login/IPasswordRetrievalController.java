
package com.robotwitter.webapp.control.login;


import java.io.Serializable;




/** Login user interface controller. */
public interface IPasswordRetrievalController extends Serializable
{
	
	/** Status codes returned by this instance. */
	enum Status
	{
		/** Operation succeeded. */
		SUCCESS,

		/** The received email address is not attached to any existing user. */
		USER_DOESNT_EXIST,

		/** A communication error has occurred while trying to send the email. */
		ERROR_SENDING_EMAIL,

		/** An unknown failure occurred . */
		FAILURE
	}
	
	
	
	/**
	 * Retrieves a user's password.
	 *
	 * @param email
	 *            The user's email address
	 *
	 * @return status code
	 */
	Status retrieve(final String email);
}
