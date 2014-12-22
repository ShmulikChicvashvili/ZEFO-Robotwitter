
package com.robotwitter.webapp.view.login;


import java.util.function.Consumer;

import com.robotwitter.webapp.control.login.IPasswordRetrievalController;
import com.robotwitter.webapp.control.login.IPasswordRetrievalController.Status;
import com.robotwitter.webapp.messages.IMessagesContainer;
import com.robotwitter.webapp.util.AbstractFormComponent;
import com.robotwitter.webapp.util.IFormComponent;




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
	 *            handles a successful submission of the form. Receives this
	 *            form as a parameter. If <code>null</code> is received, no
	 *            operation will be performed on successful submission.
	 */
	public PasswordRetrievalForm(
		IMessagesContainer messages,
		IPasswordRetrievalController retrievalController,
		Consumer<IFormComponent> confirmHandler)
	{
		super(messages.get("PasswordRetrievalForm.button.confirm"), 
			null,
			confirmHandler);

		this.messages = messages;
		this.retrievalController = retrievalController;

		initialiseEmail();
	}
	
	
	/** Initialises the email address field. */
	private void initialiseEmail()
	{
		addEmailField(EMAIL, messages.get("PasswordRetrievalForm.label.email"), 
			null,
			messages.get("PasswordRetrievalForm.error.email-empty"), 
			messages.get("PasswordRetrievalForm.error.email-invalid")); 
	}
	
	
	@Override
	protected final Error validate()
	{
		final Status status = retrievalController.retrieve(get(EMAIL));
		
		switch (status)
		{
			case SUCCESS:
				return null;
				
			case USER_DOESNT_EXIST:
				return new Error(
					EMAIL,
					messages
						.get("PasswordRetrievalForm.error.user-doesnt-exist")); 

			case FAILURE:
				return new Error(
					null,
					messages.get("PasswordRetrievalForm.error.unknown"), true); 

			default:
				throw new RuntimeException("Unknown status: " + status); 
		}
	}
	
	
	
	/** The email field's identifier. */
	public static final String EMAIL = "email"; 

	/** Serialisation version unique ID. */
	private static final long serialVersionUID = 1L;
	
	/** The displayed messages. */
	IMessagesContainer messages;
	
	/** The password retrieval controller. */
	IPasswordRetrievalController retrievalController;
	
}
