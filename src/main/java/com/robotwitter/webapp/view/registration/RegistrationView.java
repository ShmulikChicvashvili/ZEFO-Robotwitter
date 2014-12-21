
package com.robotwitter.webapp.view.registration;


import com.google.inject.Inject;
import com.google.inject.name.Named;

import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import com.robotwitter.webapp.control.registration.IRegistrationController;
import com.robotwitter.webapp.messages.IMessagesContainer;
import com.robotwitter.webapp.util.AbstractPasswordValidator;
import com.robotwitter.webapp.view.AbstractView;
import com.robotwitter.webapp.view.IUserSession;
import com.robotwitter.webapp.view.dashboard.DashboardView;




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
		super(messages, messages.get("RegistrationView.page.title")); //$NON-NLS-1$

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
		Label title = new Label(messages.get("RegistrationView.label.title")); //$NON-NLS-1$
		title.setStyleName(TITLE_STYLENAME);
		
		// Initialise the box (includes form only)
		VerticalLayout box = new VerticalLayout(createRegistrationForm());
		box.setSpacing(true);
		box.addStyleName(REGISTRATION_STYLENAME);
		
		// Wrapper of the title and the rest of the content
		VerticalLayout wrapper = new VerticalLayout(title, box);
		wrapper.setSpacing(true);
		wrapper.setStyleName(WRAPPER_STYLENAME);

		addStyleName(STYLENAME);
		setCompositionRoot(wrapper);
	}
	
	
	
	/** The view's name. */
	public static final String NAME = "registration"; //$NON-NLS-1$
	
	/** The CSS class name to apply to the box component. */
	private static final String REGISTRATION_STYLENAME =
		"RegistrationView	-box"; //$NON-NLS-1$
	
	/** Serialisation version unique ID. */
	private static final long serialVersionUID = 1L;

	/** The CSS class name to apply to this component. */
	private static final String STYLENAME = "RegistrationView"; //$NON-NLS-1$

	/** The CSS class name to apply to the registration form's title. */
	private static final String TITLE_STYLENAME = "RegistrationView-title"; //$NON-NLS-1$

	/** The CSS class name to apply to the wrapper component. */
	private static final String WRAPPER_STYLENAME = "RegistrationView-wrapper"; //$NON-NLS-1$

	/** The registration view's controller. */
	private final IRegistrationController registrationController;
	
	/** The password's validator. */
	private final AbstractPasswordValidator passwordValidator;
}
