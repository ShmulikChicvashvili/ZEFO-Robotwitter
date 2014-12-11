
package com.robotwitter.webapp.general;


import com.google.inject.Inject;
import com.google.inject.name.Named;

import com.robotwitter.webapp.messages.IMessagesContainer;
import com.robotwitter.webapp.util.AbstractTextFieldValidator;




/**
 * Validator of a user's password.
 * <p>
 * Valid passwords are those that contain at least {@link #MIN_VALID_LENGTH}
 * characters, no more than {@link #MAX_VALID_LENGTH} characters, at least on
 * digit, at least one lower case letter, and at least one upper case letter.
 */
public class PasswordValidator extends AbstractTextFieldValidator
{

	/**
	 * Instantiates a new password validator.
	 *
	 * @param messages
	 *            the container of messages to display
	 */
	@Inject
	public PasswordValidator(
		@Named(General.MESSAGES) IMessagesContainer messages)
	{
		setMinLength(
			MIN_VALID_LENGTH,
			messages.get("PasswordValidator.error.must-be-longer-than")); //$NON-NLS-1$

		setMaxLength(
			MAX_VALID_LENGTH,
			messages.get("PasswordValidator.error.must-be-shorter-than")); //$NON-NLS-1$

		addConstraint(".*[0-9].*", //$NON-NLS-1$
			messages.get("PasswordValidator.error.must-contain-digit")); //$NON-NLS-1$

		addConstraint(".*[a-z].*", //$NON-NLS-1$
			messages
				.get("PasswordValidator.error.must-contain-lower-case-letter")); //$NON-NLS-1$

		addConstraint(".*[A-Z].*", //$NON-NLS-1$
			messages
				.get("PasswordValidator.error.must-contain-upper-case-letter")); //$NON-NLS-1$
	}



	/** The minimal valid password length. */
	public static final int MIN_VALID_LENGTH = 8;

	/** The maximal valid password length. */
	public static final int MAX_VALID_LENGTH = 20;
	
	/** Serialisation version unique ID. */
	private static final long serialVersionUID = 1L;

}
