
package com.robotwitter.webapp.util;


import com.vaadin.data.validator.AbstractStringValidator;




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
	 * @param id
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
		String id,
		String caption,
		String prompt,
		String emptyError,
		String invalidError);


	/**
	 * Adds a password field to the form.
	 *
	 * @param id
	 *            a unique identifier of the field
	 * @param caption
	 *            the password's caption (or <code>null</code> for none)
	 * @param prompt
	 *            the password's prompt (or <code>null</code> for none)
	 * @param emptyError
	 *            the message to display when the password is empty
	 * @param validator
	 *            the password's validator
	 */
	void addPasswordField(
		String id,
		String caption,
		String prompt,
		String emptyError,
		AbstractStringValidator validator);
	
	
	/**
	 * Adds a plain textfield to the form.
	 *
	 * @param id
	 *            a unique identifier of the field
	 * @param caption
	 *            the textfield's caption (or <code>null</code> for none)
	 * @param prompt
	 *            the textfield's prompt (or <code>null</code> for none)
	 * @param emptyError
	 *            the message to display when the textfield is empty
	 * @param validator
	 *            the textfield's validator
	 */
	void addTextField(
		String id,
		String caption,
		String prompt,
		String emptyError,
		AbstractStringValidator validator);


	/** Disables the form (prohibits further submission of information). */
	void disable();


	/**
	 * Gets a field's value (user's input).
	 *
	 * @param id
	 *            the field's identifier
	 *
	 * @return the field's value respective to the given field's
	 *         <code>identifier</code>, or <code>null</code> if none exists
	 */
	String get(String id);


	/** Submits the form. */
	void submit();
}
