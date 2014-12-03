
package com.robotwitter.webapp.control.login;


/** Login user interface controller. */
public interface PasswordRetrievalController
{

	/** Status codes returned by this class. */
	enum ReturnStatus
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
	 * Retrieve a user's password.
	 *
	 * @param email
	 *            The user's email address
	 * @return status code
	 */
	ReturnStatus retrieve(final String email);
}
