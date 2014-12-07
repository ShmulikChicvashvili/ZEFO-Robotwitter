
package com.robotwitter.webapp.util;


import com.vaadin.data.validator.AbstractStringValidator;
import com.vaadin.ui.AbstractTextField;




/**
 * Represents a generic customisable form component. A form consists of input
 * fields that may be submitted by a user.
 *
 * @author Hagai Akibayov
 */
public interface IFormComponent
{
	/**
	 * Adds an email address field to the form.
	 *
	 * @param caption
	 *            the email's caption (or null for none)
	 * @param prompt
	 *            the email's prompt (or null for none)
	 * @param emptyError
	 *            the message to display when the email is empty
	 * @param invalidError
	 *            the message to display when the email is invalid
	 */
	void addEmailField(
		String caption,
		String prompt,
		String emptyError,
		String invalidError);
	
	
	/**
	 * Adds a password field to the form.
	 *
	 * @param caption
	 *            the password's caption (or null for none)
	 * @param prompt
	 *            the password's prompt (or null for none)
	 * @param emptyErrorMessage
	 *            the message to display when the password is empty
	 * @param validator
	 *            the password's validator
	 */
	void addPasswordField(
		String caption,
		String prompt,
		String emptyErrorMessage,
		AbstractStringValidator validator);


	/**
	 * Gets a field.
	 *
	 * @param index
	 *            the field's index
	 *
	 * @return the field respective to the given index, or null if none exists
	 */
	AbstractTextField getField(int index);


	/** Submits the form. */
	void submit();
}
