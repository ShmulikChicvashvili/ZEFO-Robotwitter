
package com.robotwitter.webapp.view.login;


import com.robotwitter.webapp.control.login.IPasswordRetrievalController;
import com.robotwitter.webapp.control.login.IPasswordRetrievalController.Status;
import com.robotwitter.webapp.messages.IMessagesContainer;
import com.robotwitter.webapp.view.util.AbstractFormComponent;




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
	 * @param retriever
	 *            the password retrieval controller
	 */
	public PasswordRetrievalForm(
		IMessagesContainer messages,
		IPasswordRetrievalController retriever)
	{
		super(messages.get("PasswordRetrievalForm.button.confirm"), null);  //$NON-NLS-1$
		
		this.messages = messages;
		this.retriever = retriever;
		
		initialiseEmail();
	}


	@Override
	public final void submit()
	{
		// Do nothing
	}


	/** Initialises the email address field. */
	private void initialiseEmail()
	{
		addEmailField(messages.get("PasswordRetrievalForm.label.email"), //$NON-NLS-1$
			null,
			messages.get("PasswordRetrievalForm.error.email-empty"), //$NON-NLS-1$
			messages.get("PasswordRetrievalForm.error.email-invalid")); //$NON-NLS-1$
	}


	@Override
	protected final Error validate()
	{
		final Status status = retriever.retrieve(getField(0).getValue());

		switch (status)
		{
			case SUCCESS:
				return null;

			case USER_DOESNT_EXIST:
				return new Error(
					getField(0),
					messages
					.get("PasswordRetrievalForm.error.user-doesnt-exist")); //$NON-NLS-1$

			default:
				return new Error(
					getField(0),
					messages
					.get("PasswordRetrievalForm.error.error-sending-email")); //$NON-NLS-1$
		}
	}
	
	
	
	/** The displayed messages. */
	IMessagesContainer messages;

	/** The password retrieval controller. */
	IPasswordRetrievalController retriever;

	/** Serialisation version unique ID. */
	private static final long serialVersionUID = 1L;

}
