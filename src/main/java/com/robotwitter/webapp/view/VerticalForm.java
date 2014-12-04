
package com.robotwitter.webapp.view;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.vaadin.data.validator.AbstractStringValidator;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.UserError;
import com.vaadin.ui.AbstractTextField;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;




/**
 * Vertical form component.
 *
 * @author Hagai Akibayov
 */
public class VerticalForm extends CustomComponent
	implements
		Button.ClickListener
{

	/**
	 * Initialise a field.
	 *
	 * @param field
	 *            The field to initialise
	 * @param caption
	 *            The field's caption (or null for none)
	 * @param prompt
	 *            The field's prompt (or null for none)
	 */
	private static void initialiseField(
		final AbstractTextField field,
		final String caption,
		final String prompt)
	{

		// Set caption
		if (caption != null)
		{
			field.setCaption(caption);
		}

		// Set prompt
		if (prompt != null)
		{
			field.setInputPrompt(prompt);
		}
		
		// Set appearance
		field.setValidationVisible(false);
		field.setSizeFull();
		field.setStyleName(FIELD_STYLENAME);
	}
	
	
	/**
	 * Construct a new vertical form component.
	 *
	 * @param submitCaption
	 *            The submit button's caption
	 * @param submitIcon
	 *            The submit button's icon (or null for none)
	 */
	public VerticalForm(final String submitCaption, final FontAwesome submitIcon)
	{
		// Construct members
		fields = new Vector<>();
		fieldEmptyErrorMessages = new HashMap<>();
		fieldInvalidErrorMessages = new HashMap<>();
		fieldValidators = new HashMap<>();
		
		// Initialise components
		initialiseErrorMessage();
		initialiseSubmit(submitCaption, submitIcon);
		initialiseLayout();
		
		enableSubmissionOnEnter();

		setStyleName(FORM_STYLENAME);
	}


	/**
	 * Add an email address field to the form.
	 *
	 * @param caption
	 *            The email's caption (or null for none)
	 * @param prompt
	 *            The email's prompt (or null for none)
	 * @param emptyError
	 *            The message to display when the email is empty
	 * @param invalidError
	 *            The message to display when the email is invalid
	 */
	public void addEmailField(
		final String caption,
		final String prompt,
		final String emptyError,
		final String invalidError)
	{
		final TextField email = new TextField();
		final EmailValidator validator = new EmailValidator(invalidError);
		addField(email, caption, prompt, emptyError, validator);
	}


	/**
	 * Add a password field to the form.
	 *
	 * @param caption
	 *            The password's caption (or null for none)
	 * @param prompt
	 *            The password's prompt (or null for none)
	 * @param emptyErrorMessage
	 *            The message to display when the password is empty
	 * @param validator
	 *            The password's validator
	 */
	public void addPasswordField(
		final String caption,
		final String prompt,
		final String emptyErrorMessage,
		final AbstractStringValidator validator)
	{
		final PasswordField password = new PasswordField();
		addField(password, caption, prompt, emptyErrorMessage, validator);
	}


	@Override
	public void buttonClick(final ClickEvent event)
	{
		// Validate
		if (!validateAllFields()) { return; }

		// Clear any error messages if validation passed successfully
		clearErrorMessage();
	}


	/**
	 * @param index
	 *            The field's index
	 * @return The field respective to the given index
	 */
	public AbstractTextField getField(final int index)
	{
		if (index > form.getComponentCount() - 2) { throw new IndexOutOfBoundsException(
			"Index " + index + " is out of bounds!"); } //$NON-NLS-1$ //$NON-NLS-2$
		return (AbstractTextField) form.getComponent(index);
	}


	/** Enable submission of the login form upon ENTER key click. */
	private void enableSubmissionOnEnter()
	{
		/** Listener of Enter shortcut keyboard press. */
		class EnterListener extends Button.ClickShortcut
		{
			/** Construct listener on submit button. */
			public EnterListener()
			{
				super(submit, KeyCode.ENTER);
			}


			@Override
			public void handleAction(final Object sender, final Object target)
			{
				if (fields.contains(target))
				{
					button.click();
				}
			}



			/** Serialisation version unique ID. */
			private static final long serialVersionUID = 1L;
		}

		submit.addShortcutListener(new EnterListener());
	}


	/** Initialise the error message displayed upon failed submission. */
	private void initialiseErrorMessage()
	{
		errorMessage = new Label();
		errorMessage.setVisible(false);
		errorMessage.setStyleName(ERROR_STYLENAME);
	}


	/** Initialise the form's layout. */
	private void initialiseLayout()
	{
		form = new VerticalLayout();
		
		fields.forEach(field -> form.addComponent(field));
		form.addComponent(errorMessage);
		form.addComponent(submit);
		
		form.setSpacing(true);
		setCompositionRoot(form);
	}


	/**
	 * Initialise the submit button.
	 *
	 * @param submitCaption
	 *            The submit button's caption
	 * @param submitIcon
	 *            The submit button's icon (optional - can be null);
	 */
	private void initialiseSubmit(
		final String submitCaption,
		final FontAwesome submitIcon)
	{
		submit = new Button(submitCaption, this);
		submit.setSizeFull();
		if (submitIcon != null)
		{
			submit.setIcon(submitIcon);
		}
		submit.setStyleName(SUBMIT_STYLENAME);
	}


	/**
	 * @param field
	 *            The field to validate
	 * @return true if the given field is valid, false otherwise.
	 */
	private boolean validateField(final AbstractTextField field)
	{
		// Check emptiness
		if (field.getValue().isEmpty())
		{
			setErrorMessage(fieldEmptyErrorMessages.get(field), field);
			return false;
		}

		// Validate
		if (!field.isValid())
		{
			errorMessage.setVisible(true);
			field.setValidationVisible(true);
			errorMessage.setValue(fieldValidators.get(field).getErrorMessage());
			return false;
		}

		return true;
	}


	/**
	 * Add a field to the form.
	 *
	 * @param field
	 *            The field to add
	 * @param caption
	 *            The field's caption (or null if none)
	 * @param prompt
	 *            The field's prompt (or null if none)
	 * @param emptyErrorMessage
	 *            The message to display when the field is empty
	 * @param validator
	 *            The field's validator
	 */
	protected void addField(
		final AbstractTextField field,
		final String caption,
		final String prompt,
		final String emptyErrorMessage,
		final AbstractStringValidator validator)
	{
		// Initialise field
		field.addValidator(validator);
		initialiseField(field, caption, prompt);

		// Add field to internal data structures
		fields.add(field);
		fieldEmptyErrorMessages.put(field, emptyErrorMessage);
		fieldValidators.put(field, validator);

		// Add field to form
		final int fieldCount = form.getComponentCount() - 2;
		form.addComponent(field, fieldCount);

		// Focus field if it is the first
		if (fieldCount == 0)
		{
			field.focus();
		}
	}


	/** Clear any displayed error message on the form. */
	protected void clearErrorMessage()
	{
		errorMessage.setVisible(false);

		fields.forEach(field -> {
			field.setComponentError(null);
			field.setValidationVisible(false);
		});
	}


	/**
	 * Display an error message on the login form.
	 *
	 * @param message
	 *            The error message to display
	 * @param field
	 *            If not null, the field to display the error on, otherwise, the
	 *            error will be displayed as a general error
	 */
	protected void setErrorMessage(
		final String message,
		final AbstractTextField field)
	{
		clearErrorMessage();

		errorMessage.setVisible(true);
		errorMessage.setValue(message);

		if (field != null)
		{
			field.setValidationVisible(true);
			field.setComponentError(new UserError(message));
			field.setCursorPosition(field.getValue().length());
		}
	}


	/**
	 * Validate all fields in the form
	 *
	 * @return true if all fields are valid, false otherwise.
	 */
	protected boolean validateAllFields()
	{
		for (final AbstractTextField field : fields)
		{
			if (!validateField(field)) { return false; }
		}
		return true;
	}



	/** The CSS class name to apply to the form's fields. */
	private static final String FIELD_STYLENAME = "VerticalForm-field"; //$NON-NLS-1$

	/** The CSS class name to apply to the form's error message. */
	private static final String ERROR_STYLENAME = "VerticalForm-error"; //$NON-NLS-1$

	/** The CSS class name to apply to the form's submit button. */
	private static final String SUBMIT_STYLENAME = "VerticalForm-submit"; //$NON-NLS-1$

	/** The CSS class name to apply to the form component. */
	private static final String FORM_STYLENAME = "VerticalForm"; //$NON-NLS-1$
	
	/** The form's fields. */
	List<AbstractTextField> fields;
	
	/** The error messages displayed when fields are empty. */
	Map<AbstractTextField, String> fieldEmptyErrorMessages;
	
	/** The default error messages displayed when fields are invalid. */
	Map<AbstractTextField, String> fieldInvalidErrorMessages;
	
	/** Custom validator objects of fields. */
	Map<AbstractTextField, AbstractStringValidator> fieldValidators;

	/** The submission button. */
	Button submit;

	/** The error message of a failed submission attempt. */
	Label errorMessage;

	/** The laid-out form. */
	private VerticalLayout form;

	/** Serialisation version unique ID. */
	private static final long serialVersionUID = 1L;

}
