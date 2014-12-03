
package com.robotwitter.webapp.view.login;


import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button.ClickEvent;

import com.robotwitter.webapp.view.VerticalForm;




/** Login form component. */
public class LoginForm extends VerticalForm
{
	
	/** A user authenticator. */
	interface Authenticator
	{
		/**
		 * @param email
		 *            The user's email address
		 * @param password
		 *            The user's password
		 * @return true if authentic, false otherwise
		 */
		boolean authenticate(String email, String password);
	}
	
	
	
	/** A user login handler. */
	interface LoginHandler
	{
		/**
		 * Handle a successful user login.
		 *
		 * @param email
		 *            The user's email address
		 * @param password
		 *            The user's password
		 */
		void login(String email, String password);
	}



	/**
	 * Construct a new login form.
	 *
	 * @param authenticator
	 *            The user's authenticator
	 * @param handler
	 *            The successful login handler
	 */
	public LoginForm(
		final Authenticator authenticator,
		final LoginHandler handler)
	{
		super(Messages.get("LoginForm.button.sign-in"), //$NON-NLS-1$
			FontAwesome.ARROW_CIRCLE_RIGHT);

		this.authenticator = authenticator;
		this.handler = handler;

		initialiseEmail();
		initialisePassword();
	}


	@Override
	public void buttonClick(final ClickEvent event)
	{
		// Validate
		if (!validateAllFields()) { return; }
		
		// Authenticate
		if (!authenticate()) { return; }

		// Handle successful login
		clearErrorMessage();
		handler.login(getField(0).getValue(), getField(1).getValue());
	}


	/** @return true if the given user input is authentic, false otherwise. */
	private boolean authenticate()
	{
		final boolean isAuthentic =
			authenticator.authenticate(getField(0).getValue(), getField(1)
				.getValue());
		if (!isAuthentic)
		{
			setErrorMessage(
				Messages.get("LoginForm.error.email-or-password-incorrect"), //$NON-NLS-1$
				getField(1));
			return false;
		}

		return true;
	}


	/** Initialise the email address field. */
	private void initialiseEmail()
	{
		addEmailField(null, Messages.get("LoginForm.label.email"), //$NON-NLS-1$
			Messages.get("LoginForm.error.email-empty"), //$NON-NLS-1$
			Messages.get("LoginForm.error.email-invalid")); //$NON-NLS-1$
	}


	/** Initialise the password field. */
	private void initialisePassword()
	{
		addPasswordField(null, Messages.get("LoginForm.label.password"), //$NON-NLS-1$
			Messages.get("LoginForm.error.password-empty"), //$NON-NLS-1$
			new PasswordValidator());
	}
	
	
	
	/** The user's authenticator. */
	Authenticator authenticator;

	/** The user's authenticator. */
	LoginHandler handler;

	/** Serialisation version unique ID. */
	private static final long serialVersionUID = 1L;

}
