
package com.robotwitter.webapp.view.login;


import java.io.Serializable;

import com.vaadin.data.validator.AbstractStringValidator;
import com.vaadin.server.FontAwesome;

import com.robotwitter.webapp.control.login.ILoginController;
import com.robotwitter.webapp.general.PasswordValidator;
import com.robotwitter.webapp.messages.IMessagesContainer;
import com.robotwitter.webapp.util.AbstractFormComponent;




/**
 * Represents a login form.
 * <p>
 * The login form consists of an email field and a password field.
 *
 * @author Hagai Akibayov
 */
public class LoginForm extends AbstractFormComponent
{

	/** A user login handler. */
	interface LoginHandler extends Serializable
	{
		/**
		 * Handles a successful user login.
		 *
		 * @param email
		 *            The user's email address
		 * @param password
		 *            The user's password
		 */
		void login(String email, String password);
	}



	/**
	 * Instantiates a new login form.
	 *
	 * @param messages
	 *            the container of messages to display
	 * @param loginController
	 *            the login controller
	 * @param passwordValidator
	 *            the user's password validator
	 * @param handler
	 *            the successful login handler
	 */
	public LoginForm(
		IMessagesContainer messages,
		ILoginController loginController,
		AbstractStringValidator passwordValidator,
		LoginHandler handler)
	{
		super(messages.get("LoginForm.button.sign-in"), //$NON-NLS-1$
			FontAwesome.ARROW_CIRCLE_RIGHT,
			null);

		this.messages = messages;
		this.loginController = loginController;
		this.passwordValidator = passwordValidator;
		this.handler = handler;

		initialiseEmail();
		initialisePassword();
	}


	/** Initialises the email address field. */
	private void initialiseEmail()
	{
		addEmailField(EMAIL, null, messages.get("LoginForm.label.email"), //$NON-NLS-1$
			messages.get("LoginForm.error.email-empty"), //$NON-NLS-1$
			messages.get("LoginForm.error.email-invalid")); //$NON-NLS-1$
	}


	/** Initialises the password field. */
	private void initialisePassword()
	{
		addPasswordField(
			PASSWORD,
			null,
			messages.get("LoginForm.label.password"), //$NON-NLS-1$
			messages.get("LoginForm.error.password-empty"), //$NON-NLS-1$
			new PasswordValidator(messages));
	}


	@Override
	protected final Error validate()
	{
		final ILoginController.Status status =
			loginController.authenticate(get(EMAIL).getValue(), get(PASSWORD)
				.getValue());

		switch (status)
		{
			case SUCCESS:
				return null;

			case USER_DOESNT_EXIST:
				return new Error(
					get(EMAIL),
					messages.get("LoginForm.error.user-doesnt-exist")); //$NON-NLS-1$

			case AUTHENTICATION_FAILURE:
				return new Error(
					get(PASSWORD),
					messages.get("LoginForm.error.password-incorrect"));//$NON-NLS-1$
				
			case FAILURE:
				return new Error(null, messages.get("LoginForm.error.unknown")); //$NON-NLS-1$

			default:
				throw new RuntimeException("Unknown status: " + status); //$NON-NLS-1$
		}
	}



	/** The email field's identifier. */
	private static final String EMAIL = "email"; //$NON-NLS-1$

	/** The password field's identifier. */
	private static final String PASSWORD = "password"; //$NON-NLS-1$

	/** The displayed messages. */
	IMessagesContainer messages;

	/** The user's authenticator. */
	ILoginController loginController;
	
	/** The user's password validator. */
	AbstractStringValidator passwordValidator;
	
	/** The user's authenticator. */
	LoginHandler handler;

	/** Serialisation version unique ID. */
	private static final long serialVersionUID = 1L;

}
