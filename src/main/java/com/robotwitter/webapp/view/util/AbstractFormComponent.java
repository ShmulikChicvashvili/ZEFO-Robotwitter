
package com.robotwitter.webapp.view.util;


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
 * Represents an abstract form component in a vertical layout.
 * <p>
 * This type of form contains a submission button that can be clicked to submit
 * the form, and a shortcut ENTER keyboard press which can fire a submission as
 * well.
 * <p>
 * Note: An inheriting class must implement the {@link #validate} and
 * {@link #submit} methods.
 *
 * @author Hagai Akibayov
 */
public abstract class AbstractFormComponent extends CustomComponent
	implements
		IFormComponent,
		Button.ClickListener
{
	
	/** Represents a validation error in the form. */
	protected static class Error
	{

		/**
		 * Instantiates a new error.
		 *
		 * @param field
		 *            the field
		 * @param message
		 *            the message
		 */
		public Error(AbstractTextField field, String message)
		{
			this.field = field;
			this.message = message;
		}



		/** The field responsible for the error, or null if none. */
		final AbstractTextField field;
		
		/** A message describing the error. */
		final String message;
	}
	
	
	
	/**
	 * Initialises a field.
	 *
	 * @param field
	 *            the field to initialise
	 * @param caption
	 *            the field's caption (or null for none)
	 * @param prompt
	 *            the field's prompt (or null for none)
	 */
	private static void initialiseField(
		AbstractTextField field,
		String caption,
		String prompt)
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
	 * Instantiates a new abstract form component.
	 *
	 * @param submitCaption
	 *            the submit button's caption
	 * @param submitIcon
	 *            the submit button's icon (or null for none)
	 */
	protected AbstractFormComponent(String submitCaption, FontAwesome submitIcon)
	{
		// Construct members
		fields = new Vector<>();
		fieldEmptyErrorMessages = new HashMap<>();
		fieldValidators = new HashMap<>();
		
		// Initialise components
		initialiseErrorMessage();
		initialiseSubmit(submitCaption, submitIcon);
		initialiseLayout();
		
		enableSubmissionOnEnter();

		setStyleName(FORM_STYLENAME);
	}


	@Override
	public final void addEmailField(
		String caption,
		String prompt,
		String emptyError,
		String invalidError)
	{
		final TextField email = new TextField();
		final EmailValidator validator = new EmailValidator(invalidError);
		addField(email, caption, prompt, emptyError, validator);
	}
	
	
	@Override
	public final void addPasswordField(
		String caption,
		String prompt,
		String emptyErrorMessage,
		AbstractStringValidator validator)
	{
		final PasswordField password = new PasswordField();
		addField(password, caption, prompt, emptyErrorMessage, validator);
	}


	@Override
	public final void buttonClick(ClickEvent event)
	{
		// Validate
		if (!validateAllFields()) { return; }

		final Error error = validate();
		if (error != null)
		{
			setErrorMessage(error);
			return;
		}
		
		// Clear any error messages if validation passed successfully
		clearErrorMessage();
		submit();
	}


	@Override
	public final AbstractTextField getField(int index)
	{
		return fields.get(index);
	}
	
	
	/**
	 * Adds a field to the form.
	 *
	 * @param field
	 *            the field to add
	 * @param caption
	 *            the field's caption (or null if none)
	 * @param prompt
	 *            the field's prompt (or null if none)
	 * @param emptyErrorMessage
	 *            the message to display when the field is empty
	 * @param validator
	 *            the field's validator
	 */
	private void addField(
		AbstractTextField field,
		String caption,
		String prompt,
		String emptyErrorMessage,
		AbstractStringValidator validator)
	{
		// Initialise field
		field.addValidator(validator);
		initialiseField(field, caption, prompt);

		// Add field to form
		final int fieldCount = fields.size();
		layout.addComponent(field, fieldCount);

		// Add field to internal data structures
		fields.add(field);
		fieldEmptyErrorMessages.put(field, emptyErrorMessage);
		fieldValidators.put(field, validator);

		// Focus field if it is the first
		if (fieldCount == 0)
		{
			field.focus();
		}
	}


	/** Clears any displayed error message on the form. */
	private void clearErrorMessage()
	{
		errorMessage.setVisible(false);

		fields.forEach(field -> {
			field.setComponentError(null);
			field.setValidationVisible(false);
		});
	}
	
	
	/** Enables submission of the login form upon ENTER key click. */
	private void enableSubmissionOnEnter()
	{
		
		submit
			.addShortcutListener(new com.robotwitter.webapp.view.util.ButtonClickOnEnterListener(
				submit,
				fields));
	}


	/** Initialises the error message displayed upon failed submission. */
	private void initialiseErrorMessage()
	{
		errorMessage = new Label();
		errorMessage.setVisible(false);
		errorMessage.setStyleName(ERROR_STYLENAME);
	}


	/** Initialises the form's layout. */
	private void initialiseLayout()
	{
		layout = new VerticalLayout();

		layout.addComponent(errorMessage);
		layout.addComponent(submit);

		layout.setSpacing(true);
		setCompositionRoot(layout);
	}
	
	
	/**
	 * Initialises the submit button.
	 *
	 * @param submitCaption
	 *            the submit button's caption
	 * @param submitIcon
	 *            the submit button's icon (optional - can be null)
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
	 * Displays an error message on the login form.
	 *
	 * @param error
	 *            the error
	 */
	private void setErrorMessage(final Error error)
	{
		// Clear any previous error message.
		clearErrorMessage();

		// Set the error message
		errorMessage.setVisible(true);
		errorMessage.setValue(error.message);

		if (error.field != null)
		{
			// Set the error message on the related field
			error.field.setValidationVisible(true);
			if (error.field.getErrorMessage() == null)
			{
				error.field.setComponentError(new UserError(error.message));
			}
			error.field.setCursorPosition(error.field.getValue().length());
		}
	}
	
	
	/**
	 * Validates all fields in the form.
	 *
	 * @return true if all fields are valid, false otherwise
	 */
	private boolean validateAllFields()
	{
		for (final AbstractTextField field : fields)
		{
			if (!validateField(field)) { return false; }
		}
		return true;
	}


	/**
	 * Validates a field.
	 *
	 * @param field
	 *            the field to validate
	 *
	 * @return true if the given field is valid, false otherwise
	 */
	private boolean validateField(final AbstractTextField field)
	{
		// Check emptiness
		if (field.getValue().isEmpty())
		{
			setErrorMessage(new Error(field, fieldEmptyErrorMessages.get(field)));
			return false;
		}

		// Validate
		if (!field.isValid())
		{
			setErrorMessage(new Error(field, fieldValidators
				.get(field)
				.getErrorMessage()));
			return false;
		}

		return true;
	}


	/**
	 * Validates the form.
	 *
	 * @return an error message if the form is invalid, null otherwise
	 */
	protected abstract Error validate();
	
	
	
	/** The CSS class name to apply to the form's fields. */
	private static final String FIELD_STYLENAME = "AbstractFormComponent-field"; //$NON-NLS-1$

	/** The CSS class name to apply to the form's error message. */
	private static final String ERROR_STYLENAME = "AbstractFormComponent-error"; //$NON-NLS-1$

	/** The CSS class name to apply to the form's submit button. */
	private static final String SUBMIT_STYLENAME =
		"AbstractFormComponent-submit"; //$NON-NLS-1$

	/** The CSS class name to apply to the form component. */
	private static final String FORM_STYLENAME = "AbstractFormComponent"; //$NON-NLS-1$

	/** The vertically laid-out form. */
	private VerticalLayout layout;

	/** The error message of a failed submission attempt. */
	private Label errorMessage;

	/** The submission button. */
	private Button submit;
	
	/** The form's fields. */
	final List<AbstractTextField> fields;
	
	/** The error messages displayed when fields are empty. */
	private final Map<AbstractTextField, String> fieldEmptyErrorMessages;
	
	/** Custom validator objects of fields. */
	private final Map<AbstractTextField, AbstractStringValidator> fieldValidators;
	
	/** Serialisation version unique ID. */
	private static final long serialVersionUID = 1L;
}
