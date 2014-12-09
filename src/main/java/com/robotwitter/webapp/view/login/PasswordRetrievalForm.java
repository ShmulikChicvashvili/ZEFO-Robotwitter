
package com.robotwitter.webapp.view.login;


import java.util.function.Consumer;

import com.robotwitter.webapp.control.login.IPasswordRetrievalController;
import com.robotwitter.webapp.control.login.IPasswordRetrievalController.Status;
import com.robotwitter.webapp.messages.IMessagesContainer;
import com.robotwitter.webapp.util.AbstractFormComponent;




/**
 * Represents a password retrieval form.
 * <p>
 * The password retrieval form consists of a single field; the email field.
 *
 * @author Hagai Akibayov
 */
public class PasswordRetrievalForm extends AbstractFormComponent
{
	
	/**
	 * Instantiates a new password retrieval form.
	 *
	 * @param messages
	 *            the container of messages to display
	 * @param retrievalController
	 *            the password retrieval controller
	 * @param confirmHandler
	 *            handles a successful confirmation of the password retrieval
	 *            form. Receives a the email address the user entered as an
	 *            argument. If <code>null</code> is received, no operation will
	 *            be performed on successful submission.
	 */
	public PasswordRetrievalForm(
		IMessagesContainer messages,
		IPasswordRetrievalController retrievalController,
		Consumer<String> confirmHandler)
	{
		super(messages.get("PasswordRetrievalForm.button.confirm"), //$NON-NLS-1$
			null,
			fields -> confirmHandler.accept(fields.get(EMAIL).getValue()));

		this.messages = messages;
		this.retrievalController = retrievalController;

		initialiseEmail();
	}
	
	
	/** Initialises the email address field. */
	private void initialiseEmail()
	{
		addEmailField(EMAIL, messages.get("PasswordRetrievalForm.label.email"), //$NON-NLS-1$
			null,
			messages.get("PasswordRetrievalForm.error.email-empty"), //$NON-NLS-1$
			messages.get("PasswordRetrievalForm.error.email-invalid")); //$NON-NLS-1$
	}
	
	
	@Override
	protected final Error validate()
	{
		final Status status =
			retrievalController.retrieve(get(EMAIL).getValue());
		
		switch (status)
		{
			case SUCCESS:
				return null;
				
			case USER_DOESNT_EXIST:
				return new Error(
					get(EMAIL),
					messages
						.get("PasswordRetrievalForm.error.user-doesnt-exist")); //$NON-NLS-1$

			case FAILURE:
				return new Error(
					null,
					messages.get("PasswordRetrievalForm.error.unknown")); //$NON-NLS-1$
				
			default:
				throw new RuntimeException("Unknown status: " + status); //$NON-NLS-1$
		}
	}
	
	
	
	/** The email field's identifier. */
	private static final String EMAIL = "email"; //$NON-NLS-1$

	/** The displayed messages. */
	IMessagesContainer messages;
	
	/** The password retrieval controller. */
	IPasswordRetrievalController retrievalController;
	
	/** Serialisation version unique ID. */
	private static final long serialVersionUID = 1L;
	
}
