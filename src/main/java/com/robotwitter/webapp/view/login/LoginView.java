
package com.robotwitter.webapp.view.login;


import java.io.Serializable;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

import com.robotwitter.webapp.control.login.EmailPasswordRetrievalController;
import com.robotwitter.webapp.control.login.LoginController;
import com.robotwitter.webapp.control.login.LoginControllerImpl;
import com.robotwitter.webapp.control.login.PasswordRetrievalController;




/** Login user interface view. */
@Theme("robotwitter")
@Title("Sign in - Robotwitter")
public class LoginView extends UI
{

	/** Initialise the forgot password button. */
	private void initialiseForgotPassword()
	{
		forgotPassword =
			new Button(
				Messages.get("LoginView.button.forgot-password"), event -> openPasswordRetrievalWindow()); //$NON-NLS-1$
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
			new PasswordRetrievalForm(retrievalController::retrieve);

		// The view's title
		title = new Label(Messages.get("LoginView.label.title")); //$NON-NLS-1$
		
		// Set style names for SCSS
		content.addStyleName(LOGIN_STYLENAME);
		retrievalForm.addStyleName(PASSWORD_RETRIEVAL_STYLENAME);
		addStyleName(UI_STYLENAME);
		title.addStyleName(TITLE_STYLENAME);
		
		// Wrapper of the title and the rest of the content
		wrapper = new VerticalLayout(title, content);
		setContent(wrapper);
	}


	/** Initialise the login form. */
	private void initialiseLoginForm()
	{
		loginForm =
			new LoginForm(
				(LoginForm.Authenticator & Serializable) loginController::authenticate,
				(LoginForm.LoginHandler & Serializable) (u, p) -> {/* NULL */});
	}
	
	
	/** Initialise the remember user checkbox. */
	private void initialiseRememberUser()
	{
		rememberUser =
			new CheckBox(
				Messages.get("LoginView.checkbox.stay-signed-in"), true); //$NON-NLS-1$
		rememberUser.setDescription(Messages
			.get("LoginView.tooltip.stay-signed-in")); //$NON-NLS-1$
		rememberUser.setEnabled(false);
	}
	
	
	@Override
	protected void init(final VaadinRequest request)
	{
		loginController = new LoginControllerImpl();
		retrievalController = new EmailPasswordRetrievalController();
		initialiseLoginForm();
		initialiseRememberUser();
		initialiseForgotPassword();
		initialiseLayouts();
		
		Page.getCurrent().setTitle(Messages.get("LoginView.page.title")); //$NON-NLS-1$
	}
	
	
	/** Open a password retrieval window. */
	void openPasswordRetrievalWindow()
	{
		final Window window =
			new Window(
				Messages.get("PasswordRetrieval.window.caption"), retrievalForm); //$NON-NLS-1$
		window.setModal(true);
		window.setCloseShortcut(KeyCode.ESCAPE, null);
		window.setResizable(false);
		window.center();

		addWindow(window);
	}



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
	
	/** The login form. */
	private Button forgotPassword;
	
	/** The login view's laid-out remember user and forgot password buttons. */
	private HorizontalLayout rememberAndForgot;
	
	/** The login view's laid-out content. */
	private VerticalLayout content;
	
	/** The login view's laid-out content. */
	private VerticalLayout wrapper;
	
	/** The login view's controller. */
	private LoginController loginController;
	
	/** The password retrieval view's controller. */
	private PasswordRetrievalController retrievalController;
	
}
