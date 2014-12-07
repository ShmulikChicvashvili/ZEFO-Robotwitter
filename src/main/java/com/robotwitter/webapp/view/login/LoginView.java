
package com.robotwitter.webapp.view.login;


import java.io.Serializable;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import com.robotwitter.webapp.control.login.EmailPasswordRetrievalController;
import com.robotwitter.webapp.control.login.ILoginController;
import com.robotwitter.webapp.control.login.IPasswordRetrievalController;
import com.robotwitter.webapp.control.login.LoginControllerImpl;
import com.robotwitter.webapp.messages.IMessagesContainer;
import com.robotwitter.webapp.messages.login.LoginMessagesContainer;
import com.robotwitter.webapp.util.WindowWithDescription;
import com.robotwitter.webapp.view.AbstractView;




/** Login user interface view. */
public class LoginView extends AbstractView
{

	/**
	 * Instantiates a new login view.
	 *
	 * @param messages
	 *            the container of messages to display
	 */
	public LoginView(IMessagesContainer messages)
	{
		super(messages, messages.get("LoginView.page.title")); //$NON-NLS-1$
	}
	
	
	@Override
	public final String getName()
	{
		return NAME;
	}


	/** Initialise the forgot password button. */
	private void initialiseForgotPassword()
	{
		forgotPassword =
			new Button(
				new LoginMessagesContainer().get("LoginView.button.forgot-password"), event -> openPasswordRetrievalWindow()); //$NON-NLS-1$
		forgotPassword.setStyleName(ValoTheme.BUTTON_LINK);
	}
	
	
	/** Initialise the layouts. */
	private void initialiseLayouts()
	{
		// Horizontal layout for "remember user" and "forgot password"
		rememberAndForgot = new HorizontalLayout(rememberUser, forgotPassword);
		rememberAndForgot.setSizeFull();
		rememberAndForgot.setComponentAlignment(
			forgotPassword,
			Alignment.TOP_RIGHT);

		// Vertical layout for the login form and the above horizontal
		content = new VerticalLayout(loginForm, rememberAndForgot);
		content.setSpacing(true);
		
		// Initialise the password retrieval form
		retrievalForm =
			new PasswordRetrievalForm(
				new LoginMessagesContainer(),
				retrievalController::retrieve);

		// The view's title
		title =
			new Label(new LoginMessagesContainer().get("LoginView.label.title")); //$NON-NLS-1$
		
		// Set style names for SCSS
		content.addStyleName(LOGIN_STYLENAME);
		retrievalForm.addStyleName(PASSWORD_RETRIEVAL_STYLENAME);
		addStyleName(UI_STYLENAME);
		title.addStyleName(TITLE_STYLENAME);
		
		// Wrapper of the title and the rest of the content
		wrapper = new VerticalLayout(title, content);
		setCompositionRoot(wrapper);
	}
	
	
	/** Initialise the login form. */
	private void initialiseLoginForm()
	{
		loginForm =
			new LoginForm(
				new LoginMessagesContainer(),
				(ILoginController & Serializable) loginController::authenticate,
				(LoginForm.LoginHandler & Serializable) (u, p) -> {/* NULL */},
				new PasswordValidator(new LoginMessagesContainer()));
	}
	
	
	/** Initialise the remember user checkbox. */
	private void initialiseRememberUser()
	{
		rememberUser =
			new CheckBox(
				new LoginMessagesContainer().get("LoginView.checkbox.stay-signed-in"), true); //$NON-NLS-1$
		rememberUser.setDescription(new LoginMessagesContainer()
		.get("LoginView.tooltip.stay-signed-in")); //$NON-NLS-1$
		rememberUser.setEnabled(false);
	}


	@Override
	protected void initialise()
	{
		loginController = new LoginControllerImpl();
		retrievalController = new EmailPasswordRetrievalController();
		initialiseLoginForm();
		initialiseRememberUser();
		initialiseForgotPassword();
		initialiseLayouts();

		getUI().getPage().setTitle(
			new LoginMessagesContainer().get("LoginView.page.title")); //$NON-NLS-1$
	}


	/** Open a password retrieval window. */
	void openPasswordRetrievalWindow()
	{
		final WindowWithDescription window = new WindowWithDescription();
		
		window.setCaption(new LoginMessagesContainer()
		.get("PasswordRetrieval.window.caption")); //$NON-NLS-1$
		window.setDescription(new LoginMessagesContainer()
		.get("PasswordRetrievalView.instructions")); //$NON-NLS-1$
		window.setIcon(FontAwesome.ENVELOPE);

		window.setContent(retrievalForm);
		window.setModal(true);
		window.setCloseShortcut(KeyCode.ESCAPE, null);
		window.setResizable(false);
		window.center();

		getUI().addWindow(window);
	}



	/** The view's name. */
	private static final String NAME = "login"; //$NON-NLS-1$

	/** The CSS class name to apply to the login form component. */
	private static final String LOGIN_STYLENAME = "LoginView-component"; //$NON-NLS-1$

	/** The CSS class name to apply to the password retrieval form component. */
	private static final String PASSWORD_RETRIEVAL_STYLENAME =
		"PasswordRetrievalView-component"; //$NON-NLS-1$
	
	/** The CSS class name to apply to the login form component. */
	private static final String UI_STYLENAME = "LoginView-ui"; //$NON-NLS-1$
	
	/** The CSS class name to apply to the login form's title. */
	private static final String TITLE_STYLENAME = "LoginView-title"; //$NON-NLS-1$
	
	/** Serialisation version unique ID. */
	private static final long serialVersionUID = 1L;
	
	/** The login form's title. */
	private Label title;
	
	/** The login form. */
	private LoginForm loginForm;
	
	/** The login form. */
	private PasswordRetrievalForm retrievalForm;
	
	/** The login form. */
	private CheckBox rememberUser;
	
	/** The login form TODO change. */
	private Button forgotPassword;

	/** The login view's laid-out remember user and forgot password buttons. */
	private HorizontalLayout rememberAndForgot;
	
	/** The login view's laid-out content. */
	private VerticalLayout content;
	
	/** The login view's laid-out content. */
	private VerticalLayout wrapper;

	/** The displayed messages. */
	IMessagesContainer messages;

	/** The login view's controller. */
	private ILoginController loginController;

	/** The password retrieval view's controller. */
	private IPasswordRetrievalController retrievalController;
	
}
