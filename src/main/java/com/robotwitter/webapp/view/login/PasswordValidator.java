
package com.robotwitter.webapp.view.login;


import com.robotwitter.webapp.messages.IMessagesContainer;
import com.robotwitter.webapp.util.AbstractTextFieldValidator;




/** Validator of a user's password. */
public class PasswordValidator extends AbstractTextFieldValidator
{
	
	/**
	 * Instantiates a new password validator.
	 *
	 * @param messages
	 *            the container of messages to display
	 */
	public PasswordValidator(IMessagesContainer messages)
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
			.get("PasswordValidator.error.must-contain-lowercase-letter")); //$NON-NLS-1$
		
		addConstraint(".*[A-Z].*", //$NON-NLS-1$
			messages
			.get("PasswordValidator.error.must-contain-uppercase-letter")); //$NON-NLS-1$
	}
	
	
	
	/** The minimal valid password length. */
	private static final int MIN_VALID_LENGTH = 8;
	
	/** The maximal valid password length. */
	private static final int MAX_VALID_LENGTH = 20;

	/** Serialisation version unique ID. */
	private static final long serialVersionUID = 1L;
	
}
