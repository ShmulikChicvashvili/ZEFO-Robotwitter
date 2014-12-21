
package com.robotwitter.webapp.view.registration;


import java.util.function.Consumer;

import com.vaadin.data.validator.AbstractStringValidator;
import com.vaadin.server.FontAwesome;

import com.robotwitter.webapp.control.registration.IRegistrationController;
import com.robotwitter.webapp.control.registration.IRegistrationController.Status;
import com.robotwitter.webapp.messages.IMessagesContainer;
import com.robotwitter.webapp.util.AbstractFormComponent;
import com.robotwitter.webapp.util.AbstractPasswordValidator;
import com.robotwitter.webapp.util.IFormComponent;




/**
 * Represents a registration form.
 * <p>
 * The registration form consists of an email field and a password field.
 *
 * @author Hagai Akibayov
 */
public class RegistrationForm extends AbstractFormComponent
{
	
	/**
	 * Instantiates a new registration form.
	 *
	 * @param messages
	 *            the container of messages to display
	 * @param registrationController
	 *            the registration controller
	 * @param passwordValidator
	 *            the user's password validator
	 * @param signUpHandler
	 *            handles a successful submission of the form. Receives this
	 *            form as a parameter. If <code>null</code> is received, no
	 *            operation will be performed on successful submission.
	 */
	public RegistrationForm(
		IMessagesContainer messages,
		IRegistrationController registrationController,
		AbstractPasswordValidator passwordValidator,
		Consumer<IFormComponent> signUpHandler)
	{
		super(messages.get("RegistrationForm.button.sign-up"), //$NON-NLS-1$
			FontAwesome.ARROW_CIRCLE_RIGHT,
			signUpHandler);

		this.messages = messages;
		this.registrationController = registrationController;
		this.passwordValidator = passwordValidator;

		initialiseEmail();
		initialisePassword();
	}


	/** Initialises the email address field. */
	private void initialiseEmail()
	{
		addEmailField(EMAIL, messages.get("RegistrationForm.label.email"), //$NON-NLS-1$
			null,
			messages.get("RegistrationForm.error.email-empty"), //$NON-NLS-1$
			messages.get("RegistrationForm.error.email-invalid")); //$NON-NLS-1$
	}


	/** Initialises the password field. */
	private void initialisePassword()
	{
		addPasswordField(
			PASSWORD,
			messages.get("RegistrationForm.label.password"), //$NON-NLS-1$
			null,
			messages.get("RegistrationForm.error.password-empty"), //$NON-NLS-1$
			passwordValidator);
	}
	
	
	@Override
	protected final Error validate()
	{
		final Status status =
			registrationController.register(get(EMAIL), get(PASSWORD));

		switch (status)
		{
			case SUCCESS:
				return null;

			case USER_ALREADY_EXISTS:
				return new Error(
					EMAIL,
					messages.get("RegistrationForm.error.user-already-exist")); //$NON-NLS-1$
				
			case FAILURE:
				return new Error(
					null,
					messages.get("RegistrationForm.error.unknown"), //$NON-NLS-1$
					true);

			default:
				assert false;
				return null;
		}
	}
	
	
	
	/** The email field's identifier. */
	public static final String EMAIL = "email"; //$NON-NLS-1$

	/** The password field's identifier. */
	public static final String PASSWORD = "password"; //$NON-NLS-1$

	/** Serialisation version unique ID. */
	private static final long serialVersionUID = 1L;
	
	/** The user's registration controller. */
	IRegistrationController registrationController;
	
	/** The displayed messages. */
	IMessagesContainer messages;
	
	/** The user's password validator. */
	AbstractStringValidator passwordValidator;
}
