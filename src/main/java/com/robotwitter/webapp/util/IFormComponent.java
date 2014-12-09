
package com.robotwitter.webapp.util;


import com.vaadin.data.validator.AbstractStringValidator;
import com.vaadin.ui.AbstractTextField;




/**
 * Represents a generic customisable form.
 * <p>
 * A form consists of input fields that can be submitted by a client.
 *
 * @author Hagai Akibayov
 */
public interface IFormComponent
{
	/**
	 * Adds an email address field to the form.
	 *
	 * @param identifier
	 *            a unique identifier of the field
	 * @param caption
	 *            the email's caption (or <code>null</code> for none)
	 * @param prompt
	 *            the email's prompt (or <code>null</code> for none)
	 * @param emptyError
	 *            the message to display when the email is empty
	 * @param invalidError
	 *            the message to display when the email is invalid
	 */
	void addEmailField(
		String identifier,
		String caption,
		String prompt,
		String emptyError,
		String invalidError);
	
	
	/**
	 * Adds a password field to the form.
	 *
	 * @param identifier
	 *            a unique identifier of the field
	 * @param caption
	 *            the password's caption (or <code>null</code> for none)
	 * @param prompt
	 *            the password's prompt (or <code>null</code> for none)
	 * @param emptyErrorMessage
	 *            the message to display when the password is empty
	 * @param validator
	 *            the password's validator
	 */
	void addPasswordField(
		String identifier,
		String caption,
		String prompt,
		String emptyErrorMessage,
		AbstractStringValidator validator);


	/**
	 * Gets a field.
	 *
	 * @param identifier
	 *            the field's identifier
	 *
	 * @return the field respective to the given <code>identifier</code>, or
	 *         <code>null</code> if none exists
	 */
	AbstractTextField get(String identifier);


	/** Submits the form. */
	void submit();
}
