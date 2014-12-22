
package com.robotwitter.webapp.view.registration;


import com.google.inject.Inject;
import com.google.inject.name.Named;

import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import com.robotwitter.webapp.control.registration.IRegistrationController;
import com.robotwitter.webapp.messages.IMessagesContainer;
import com.robotwitter.webapp.util.AbstractPasswordValidator;
import com.robotwitter.webapp.view.AbstractView;
import com.robotwitter.webapp.view.IUserSession;
import com.robotwitter.webapp.view.dashboard.DashboardView;
import com.robotwitter.webapp.view.login.LoginView;




/**
 * Represents a registration view.
 * <p>
 * Note: Shown whenever an unsigned user attempts to do something which requires
 * a sign in.
 */
public class RegistrationView extends AbstractView
{
	
	/**
	 * Instantiates a new registration view.
	 *
	 * @param messages
	 *            the container of messages to display
	 * @param registrationController
	 *            the registration controller
	 * @param passwordValidator
	 *            the validator of the password field
	 */
	@Inject
	public RegistrationView(
		@Named(NAME) IMessagesContainer messages,
		IRegistrationController registrationController,
		AbstractPasswordValidator passwordValidator)
	{
		super(messages, messages.get("RegistrationView.page.title")); 
		
		this.registrationController = registrationController;
		this.passwordValidator = passwordValidator;
	}
	
	
	@Override
	public final boolean isSignedInProhibited()
	{
		return true;
	}
	
	
	@Override
	public final boolean isSignedInRequired()
	{
		return false;
	}


	/**
	 * Creates a login component consisting of a prompt and a sign in button.
	 *
	 * @return the newly created login component
	 */
	private Component createLoginComponent()
	{
		// Create prompt and button
		Label prompt =
			new Label(messages.get("RegistrationView.label.login-prompt")); 
		Button button =
			new Button(messages.get("RegistrationView.button.sign-in")); 
		button.addClickListener(event -> navigate(LoginView.NAME));
		button.setStyleName(ValoTheme.BUTTON_LINK);

		// Initialise their layout
		HorizontalLayout layout = new HorizontalLayout(prompt, button);
		layout.setSpacing(true);
		layout.setStyleName(LOGIN_STYLENAME);
		return layout;
	}


	/**
	 * Creates a registration form.
	 *
	 * @return The newly created registration form
	 */
	private RegistrationForm createRegistrationForm()
	{
		IUserSession session = getUserSession();
		return new RegistrationForm(
			messages,
			registrationController,
			passwordValidator,
			form -> {
				String email = form.get(RegistrationForm.EMAIL);
				session.sign(email, false);
				navigate(DashboardView.NAME);
			});
	}


	@Override
	protected final void initialise()
	{
		// The title, above the content
		Label title = new Label(messages.get("RegistrationView.label.title")); 
		title.setStyleName(TITLE_STYLENAME);

		// Initialise the box (includes form only)
		VerticalLayout box = new VerticalLayout(createRegistrationForm());
		box.setSpacing(true);
		box.addStyleName(REGISTRATION_STYLENAME);

		// The login component
		Component login = createLoginComponent();

		// Wrapper of the title and the rest of the content
		VerticalLayout wrapper = new VerticalLayout(title, box, login);
		wrapper.setSpacing(true);
		wrapper.setStyleName(WRAPPER_STYLENAME);
		
		addStyleName(STYLENAME);
		setCompositionRoot(wrapper);
	}



	/** The view's name. */
	public static final String NAME = "registration"; 

	/** The CSS class name to apply to the box component. */
	private static final String REGISTRATION_STYLENAME = "RegistrationView-box"; 

	/** Serialisation version unique ID. */
	private static final long serialVersionUID = 1L;
	
	/** The CSS class name to apply to this component. */
	private static final String STYLENAME = "RegistrationView"; 
	
	/** The CSS class name to apply to the registration component. */
	private static final String LOGIN_STYLENAME = "RegistrationView-login"; 
	
	/** The CSS class name to apply to the registration form's title. */
	private static final String TITLE_STYLENAME = "RegistrationView-title"; 
	
	/** The CSS class name to apply to the wrapper component. */
	private static final String WRAPPER_STYLENAME = "RegistrationView-wrapper"; 
	
	/** The registration view's controller. */
	private final IRegistrationController registrationController;

	/** The password's validator. */
	private final AbstractPasswordValidator passwordValidator;
}
