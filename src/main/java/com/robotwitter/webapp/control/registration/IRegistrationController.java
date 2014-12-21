package com.robotwitter.webapp.control.registration;

import java.io.Serializable;

/** Registration controller interface. */
public interface IRegistrationController extends Serializable {

	/** Status codes returned by this instance. */
	enum Status
	{
		/** Operation succeeded. */
		SUCCESS,
		
		/** The received email address is already attached to an existing user. */
		USER_ALREADY_EXISTS,
		
		/** An unknown failure occurred . */
		FAILURE
	}
	
	
	
	/**
	 * Register a new a user.
	 *
	 * @param email
	 *            The user's email address
	 * @param password
	 *            The user's password
	 *
	 * @return the operation's status
	 */
	Status register(String email, String password);
}
