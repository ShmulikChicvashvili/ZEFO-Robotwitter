
package com.robotwitter.webapp.view.login;


import com.google.inject.Inject;
import com.google.inject.name.Named;

import com.vaadin.ui.AbstractLayout;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import com.robotwitter.webapp.control.login.ILoginController;
import com.robotwitter.webapp.control.login.IPasswordRetrievalController;
import com.robotwitter.webapp.messages.IMessagesContainer;
import com.robotwitter.webapp.util.AbstractPasswordValidator;
import com.robotwitter.webapp.view.AbstractView;
import com.robotwitter.webapp.view.dashboard.DashboardView;
import com.robotwitter.webapp.view.registration.RegistrationView;




/**
 * Represents a login view.
 * <p>
 * Note: Shown whenever an unsigned user attempts to do something which requires
 * a sign in.
 */
public class LoginView extends AbstractView
{

	/**
	 * Instantiates a new login view.
	 *
	 * @param messages
	 *            the container of messages to display
	 * @param loginController
	 *            the login controller
	 * @param retrievalController
	 *            the password retrieval controller
	 * @param passwordValidator
	 *            the validator of the password field
	 */
	@Inject
	public LoginView(
		@Named(NAME) IMessagesContainer messages,
		ILoginController loginController,
		IPasswordRetrievalController retrievalController,
		AbstractPasswordValidator passwordValidator)
	{
		super(messages, messages.get("LoginView.page.title"));
		
		this.loginController = loginController;
		this.retrievalController = retrievalController;
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
	 * Creates a login form.
	 *
	 * @return The newly created login form
	 */
	private LoginForm createLoginForm()
	{
		return new LoginForm(
			messages,
			loginController,
			passwordValidator,
			form -> {
				getUserSession().sign(
					form.get(LoginForm.EMAIL),
					remember.getValue().booleanValue());
				navigate(DashboardView.NAME);
			});
	}
	
	
	/**
	 * Creates a registration component consisting of a prompt and a button.
	 *
	 * @return the newly created registration component
	 */
	private Component createRegistrationComponent()
	{
		// Create prompt and button
		Label prompt =
			new Label(messages.get("LoginView.label.register-prompt"));
		Button button = new Button(messages.get("LoginView.button.sign-up"));
		button.addClickListener(event -> navigate(RegistrationView.NAME));
		button.setStyleName(ValoTheme.BUTTON_LINK);
		
		// Initialise their layout
		HorizontalLayout layout = new HorizontalLayout(prompt, button);
		layout.setSpacing(true);
		layout.setStyleName(REGISTER_STYLENAME);
		return layout;
	}
	
	
	/**
	 * Creates a "remember me" and "forgot password" buttons in a layout.
	 *
	 * @return a layout containing the buttons
	 */
	private AbstractLayout createRememberAndForgotLayout()
	{
		// Initialise remember user button
		remember =
			new CheckBox(
				messages.get("LoginView.checkbox.stay-signed-in"),
				true);

		// add tooltip to remember if not mobile
		if (!isMobile())
		{
			remember.setDescription(messages
				.get("LoginView.tooltip.stay-signed-in"));
		}
		
		// Initialise forgot password button
		Button forgot =
			new Button(messages.get("LoginView.button.forgot-password"));
		forgot.addClickListener(event -> getUI().addWindow(
			passwordRetrievalWindow));
		forgot.setStyleName(ValoTheme.BUTTON_LINK);

		// Initialise their layout
		HorizontalLayout layout = new HorizontalLayout(remember, forgot);
		layout.setSizeFull();
		layout.setComponentAlignment(forgot, Alignment.TOP_RIGHT);
		return layout;
	}
	
	
	/** Initialises the password retrieval window. */
	private void initialisePasswordRetrievalWindow()
	{
		passwordRetrievalWindow =
			new PasswordRetrievalWindow(messages, retrievalController);
	}
	
	
	@Override
	protected final void initialise()
	{
		initialisePasswordRetrievalWindow();

		// The title, above the content
		Label title = new Label(messages.get("LoginView.label.title"));
		title.setStyleName(TITLE_STYLENAME);
		
		// Initialise the box (includes form, remember-me, and forgot-password)
		VerticalLayout box =
			new VerticalLayout(
				createLoginForm(),
				createRememberAndForgotLayout());
		box.setSpacing(true);
		box.addStyleName(LOGIN_STYLENAME);

		// The registration component
		Component register = createRegistrationComponent();
		
		// Wrapper of the title and the rest of the content
		VerticalLayout wrapper = new VerticalLayout(title, box, register);
		wrapper.setSpacing(true);
		wrapper.setStyleName(WRAPPER_STYLENAME);

		addStyleName(STYLENAME);
		setCompositionRoot(wrapper);
	}
	
	
	
	/** The view's name. */
	public static final String NAME = "login";
	
	/** The CSS class name to apply to the box component. */
	private static final String LOGIN_STYLENAME = "LoginView-box";
	
	/** The CSS class name to apply to the registration component. */
	private static final String REGISTER_STYLENAME = "LoginView-register";
	
	/** Serialisation version unique ID. */
	private static final long serialVersionUID = 1L;

	/** The CSS class name to apply to this component. */
	private static final String STYLENAME = "LoginView";
	
	/** The CSS class name to apply to the login form's title. */
	private static final String TITLE_STYLENAME = "LoginView-title";
	
	/** The CSS class name to apply to the wrapper component. */
	private static final String WRAPPER_STYLENAME = "LoginView-wrapper";
	
	/** The login view's controller. */
	private final ILoginController loginController;
	
	/** The password retrieval window. */
	private PasswordRetrievalWindow passwordRetrievalWindow;
	
	/** The password's validator. */
	private final AbstractPasswordValidator passwordValidator;

	/** The password retrieval view's controller. */
	private final IPasswordRetrievalController retrievalController;
	
	/** The "Stay signed in" checkbox. */
	CheckBox remember;
}
