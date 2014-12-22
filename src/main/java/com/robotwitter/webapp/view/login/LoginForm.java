
package com.robotwitter.webapp.view.login;


import java.util.function.Consumer;

import com.vaadin.data.validator.AbstractStringValidator;
import com.vaadin.server.FontAwesome;

import com.robotwitter.webapp.control.login.ILoginController;
import com.robotwitter.webapp.messages.IMessagesContainer;
import com.robotwitter.webapp.util.AbstractFormComponent;
import com.robotwitter.webapp.util.AbstractPasswordValidator;
import com.robotwitter.webapp.util.IFormComponent;




/**
 * Represents a login form.
 * <p>
 * The login form consists of an email field and a password field.
 *
 * @author Hagai Akibayov
 */
public class LoginForm extends AbstractFormComponent
{

	/**
	 * Instantiates a new login form.
	 *
	 * @param messages
	 *            the container of messages to display
	 * @param loginController
	 *            the login controller
	 * @param passwordValidator
	 *            the user's password validator
	 * @param signInHandler
	 *            handles a successful submission of the form. Receives this
	 *            form as a parameter. If <code>null</code> is received, no
	 *            operation will be performed on successful submission.
	 */
	public LoginForm(
		IMessagesContainer messages,
		ILoginController loginController,
		AbstractPasswordValidator passwordValidator,
		Consumer<IFormComponent> signInHandler)
	{
		super(messages.get("LoginForm.button.sign-in"), 
			FontAwesome.ARROW_CIRCLE_RIGHT,
			signInHandler);
		
		this.messages = messages;
		this.loginController = loginController;
		this.passwordValidator = passwordValidator;
		
		initialiseEmail();
		initialisePassword();
	}
	
	
	/** Initialises the email address field. */
	private void initialiseEmail()
	{
		addEmailField(EMAIL, null, messages.get("LoginForm.label.email"), 
			messages.get("LoginForm.error.email-empty"), 
			messages.get("LoginForm.error.email-invalid")); 
	}
	
	
	/** Initialises the password field. */
	private void initialisePassword()
	{
		addPasswordField(
			PASSWORD,
			null,
			messages.get("LoginForm.label.password"), 
			messages.get("LoginForm.error.password-empty"), 
			passwordValidator);
	}


	@Override
	protected final Error validate()
	{
		final ILoginController.Status status =
			loginController.authenticate(get(EMAIL), get(PASSWORD));
		
		switch (status)
		{
			case SUCCESS:
				return null;
				
			case USER_DOESNT_EXIST:
				return new Error(
					EMAIL,
					messages.get("LoginForm.error.user-doesnt-exist")); 
				
			case AUTHENTICATION_FAILURE:
				return new Error(
					PASSWORD,
					messages.get("LoginForm.error.password-incorrect"));

			case FAILURE:
				return new Error(null, messages.get("LoginForm.error.unknown"), 
					true);
				
			default:
				throw new RuntimeException("Unknown status: " + status); 
		}
	}



	/** The email field's identifier. */
	public static final String EMAIL = "email"; 
	
	/** The password field's identifier. */
	public static final String PASSWORD = "password"; 
	
	/** Serialisation version unique ID. */
	private static final long serialVersionUID = 1L;

	/** The user's authenticator. */
	ILoginController loginController;

	/** The displayed messages. */
	IMessagesContainer messages;

	/** The user's password validator. */
	AbstractStringValidator passwordValidator;
}
