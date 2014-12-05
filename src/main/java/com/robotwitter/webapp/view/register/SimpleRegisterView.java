/**
 * @author Doron
 **/

package com.robotwitter.webapp.view.register;


import com.vaadin.data.validator.AbstractValidator;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;




public class SimpleRegisterView extends CustomComponent
	implements
		View,
		Button.ClickListener
{
	
	public static final String NAME = "register";
	
	private final TextField firstName;
	
	private final TextField lastName;
	
	private final TextField email;
	
	private final TextField emailAgain;
	
	private final PasswordField password;
	
	private final PasswordField passwordAgain;
	
	private final Button submitButton;
	
	private final Button menuButton;
	
	
	
	public SimpleRegisterView()
	{
		setSizeFull();
		
		// Create the first name input field
		firstName = new TextField("First name:");
		firstName.setWidth("300px");
		firstName.setRequired(true);
		firstName.setInputPrompt("");
		firstName
			.addValidator(new com.vaadin.data.validator.StringLengthValidator(
				"First name must be at between 2 to 20 characeters",
				2,
				20,
				false));
		firstName.setInvalidAllowed(false);
		
		// Create the last name input field
		lastName = new TextField("Last name:");
		lastName.setWidth("300px");
		lastName.setRequired(true);
		lastName.setInputPrompt("");
		lastName
			.addValidator(new com.vaadin.data.validator.StringLengthValidator(
				"Last name must be at between 2 to 20 characeters",
				2,
				20,
				false));
		lastName.setInvalidAllowed(false);
		
		// Create the email input field
		email = new TextField("Email:");
		email.setWidth("300px");
		email.setRequired(true);
		email.setInputPrompt("Your email (eg. joe@email.com)");
		email.addValidator(new EmailValidator(
			"Username must be an email address"));
		email.setInvalidAllowed(false);
		
		// Create the email input field
		emailAgain = new TextField("Re-Enter Email:");
		emailAgain.setWidth("300px");
		emailAgain.setRequired(true);
		emailAgain.setInputPrompt("Enter the same email as before");
		emailAgain.addValidator(new EmailValidator(
			"Username must be an email address"));
		emailAgain.setInvalidAllowed(false);
		
		// Create the password input field
		password = new PasswordField("Password:");
		password.setWidth("300px");
		password.addValidator(new PasswordValidator());
		password.setRequired(true);
		password.setValue("");
		password.setNullRepresentation("");
		
		// Create the password input field
		passwordAgain = new PasswordField("Re-enter password:");
		passwordAgain.setWidth("300px");
		passwordAgain.addValidator(new PasswordValidator(password.getValue()));
		passwordAgain.setRequired(true);
		passwordAgain.setValue("");
		passwordAgain.setNullRepresentation("");
		// Create Main menu button
		menuButton = new Button("Main menu", this);
		
		// Create Register button
		submitButton = new Button("Submit", this);
		
		HorizontalLayout buttons =
			new HorizontalLayout(submitButton, menuButton);
		
		VerticalLayout fields =
			new VerticalLayout(
				firstName,
				lastName,
				email,
				emailAgain,
				password,
				passwordAgain,
				buttons);
		fields
			.setCaption("Please login to access the application. (test@test.com/passw0rd)");
		fields.setSpacing(true);
		fields.setMargin(new MarginInfo(true, true, true, false));
		fields.setSizeUndefined();
		
		// The view root layout
		VerticalLayout viewLayout = new VerticalLayout(fields);
		viewLayout.setSizeFull();
		viewLayout.setComponentAlignment(fields, Alignment.MIDDLE_CENTER);
		viewLayout.setStyleName(Reindeer.LAYOUT_BLUE);
		setCompositionRoot(viewLayout);
	}
	
	
	@Override
	public void enter(ViewChangeEvent event)
	{
		// focus the username field when user arrives to the login view
		firstName.focus();
	}
	
	
	
	// Validator for validating the passwords
	private static final class PasswordValidator
		extends
			AbstractValidator<String>
	{
		
		public PasswordValidator()
		{
			super("The password provided is not valid");
			this.otherValue = "";
		}
		
		
		public PasswordValidator(String otherValue)
		{
			super("The password provided is not valid");
			this.otherValue = otherValue;
		}
		
		
		@Override
		protected boolean isValidValue(String value)
		{
			//
			// Password must be at least 8 characters long and contain at least
			// one number
			//
			if (value != null
				&& (value.length() < 8 || !value.matches(".*\\d.*")))
			{
				System.out.println("1");
				this
					.setErrorMessage(" Password must be at least 8 characters long and contain at least one number");
				return false;
			} else if (otherValue.compareTo("") != 0
				&& value.compareTo(otherValue) != 0)
			{
				System.out.println("2");
				this.setErrorMessage("Passwords don't match");
				return false;
			}
			
			return true;
		}
		
		
		@Override
		public Class<String> getType()
		{
			return String.class;
		}
		
		
		
		String otherValue;
	}
	
	
	
	@Override
	public void buttonClick(ClickEvent event)
	{
		switch (event.getButton().getCaption())
		{
			case "Submit":
				handleSubmitClick(event);
				break;
			case "Main menu":
				handleBackClick(event);
				break;
		}
	}
	
	
	public void handleSubmitClick(ClickEvent event)
	{
		
		//
		// Validate the fields using the navigator. By using validors for the
		// fields we reduce the amount of queries we have to use to the database
		// for wrongly entered passwords
		//
		if (!firstName.isValid()
			|| !lastName.isValid()
			|| !password.isValid()
			|| !passwordAgain.equals(password)) { return; }
		
		String username = firstName.getValue();
		String password = this.password.getValue();
		
		//
		// Validate username and password with database here. For examples sake
		// I use a dummy username and password.
		//
		boolean isValid =
			username.equals("test@test.com") && password.equals("passw0rd");
		
		if (isValid)
		{
			
			// Store the current user in the service session
			getSession().setAttribute("user", username);
			
			// Navigate to main view
			getUI().getNavigator().navigateTo(SimpleLoginMainView.NAME);//
			
		} else
		{
			
			// Wrong password clear the password field and refocuses it
			this.password.setValue(null);
			this.password.focus();
			
		}
	}
	
	
	public void handleBackClick(ClickEvent event)
	{
		getUI().getNavigator().navigateTo(SimpleLoginMainView.NAME);// navigate
																	// to the
		// main menu
	}
}
