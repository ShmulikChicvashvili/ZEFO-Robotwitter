
package com.robotwitter.webapp.view.login;


import java.io.Serializable;

import com.vaadin.data.validator.AbstractStringValidator;
import com.vaadin.server.FontAwesome;

import com.robotwitter.webapp.control.login.ILoginController;
import com.robotwitter.webapp.control.login.ILoginController.Status;
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
	 * @param handler
	 *            the successful login handler
	 * @param passwordValidator
	 *            the user's password validator
	 */
	public LoginForm(
		IMessagesContainer messages,
		ILoginController loginController,
		LoginHandler handler,
		AbstractStringValidator passwordValidator)
	{
		super(messages.get("LoginForm.button.sign-in"), //$NON-NLS-1$
			FontAwesome.ARROW_CIRCLE_RIGHT);
		
		this.loginController = loginController;
		this.handler = handler;
		this.messages = messages;
		this.passwordValidator = passwordValidator;
		
		initialiseEmail();
		initialisePassword();
	}
	
	
	@Override
	public final void submit()
	{
		handler.login(getField(0).getValue(), getField(1).getValue());
	}
	
	
	/** Initialises the email address field. */
	private void initialiseEmail()
	{
		addEmailField(null, messages.get("LoginForm.label.email"), //$NON-NLS-1$
			messages.get("LoginForm.error.email-empty"), //$NON-NLS-1$
			messages.get("LoginForm.error.email-invalid")); //$NON-NLS-1$
	}
	
	
	/** Initialises the password field. */
	private void initialisePassword()
	{
		addPasswordField(null, messages.get("LoginForm.label.password"), //$NON-NLS-1$
			messages.get("LoginForm.error.password-empty"), //$NON-NLS-1$
			new PasswordValidator(messages));
	}
	
	
	@Override
	protected final Error validate()
	{
		final ILoginController.Status isAuthentic =
			loginController.authenticate(getField(0).getValue(), getField(1)
				.getValue());
		if (isAuthentic != Status.SUCCESS) { return new Error(
			getField(1),
			messages.get("LoginForm.error.email-or-password-incorrect")); //$NON-NLS-1$
		}

		return null;
	}
	
	
	
	/** The displayed messages. */
	IMessagesContainer messages;
	
	/** The user's authenticator. */
	ILoginController loginController;

	/** The user's authenticator. */
	LoginHandler handler;

	/** The user's password validator. */
	AbstractStringValidator passwordValidator;
	
	/** Serialisation version unique ID. */
	private static final long serialVersionUID = 1L;
	
}
