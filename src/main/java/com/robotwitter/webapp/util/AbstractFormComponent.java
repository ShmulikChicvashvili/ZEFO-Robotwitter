
package com.robotwitter.webapp.util;


import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;

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
import com.vaadin.ui.UI;
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
		 * @param fieldID
		 *            the field's identifier
		 * @param message
		 *            the message describing the error
		 */
		public Error(String fieldID, String message)
		{
			this(fieldID, message, false);
		}
		
		
		/**
		 * Instantiates a new error.
		 *
		 * @param fieldID
		 *            the field's identifier
		 * @param message
		 *            the message describing the error
		 * @param disable
		 *            <code>true</code> if the error should disable field, false
		 *            otherwise.
		 */
		public Error(String fieldID, String message, boolean disable)
		{
			this.fieldID = fieldID;
			this.message = message;
			this.disable = disable;
		}
		
		
		
		/**
		 * <code>true</code> if the error should disable field, false otherwise.
		 */
		final boolean disable;

		/**
		 * The identifier of the field that caused the error, or
		 * <code>null</code> if none.
		 */
		final String fieldID;
		
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
	 *            the submit button's icon (or <code>null</code> for none)
	 * @param submitHandler
	 *            handles a successful submission of the form. Receives this
	 *            form as a parameter. If <code>null</code> is received, no
	 *            operation will be performed on successful submission.
	 */
	protected AbstractFormComponent(
		String submitCaption,
		FontAwesome submitIcon,
		Consumer<IFormComponent> submitHandler)
	{
		this.submitHandler = submitHandler;

		// Instantiate mappers
		fields = new LinkedHashMap<>();
		emptyErrorMessages = new HashMap<>();
		validators = new HashMap<>();

		// Initialise components
		initialiseErrorMessage();
		initialiseSubmit(submitCaption, submitIcon);
		initialiseLayout();

		enableSubmissionOnEnter();
		
		setStyleName(STYLENAME);
	}


	@Override
	public final void addEmailField(
		String identifier,
		String caption,
		String prompt,
		String emptyError,
		String invalidError)
	{
		final TextField email = new TextField();
		final EmailValidator validator = new EmailValidator(invalidError);
		addField(identifier, email, caption, prompt, emptyError, validator);
	}
	
	
	@Override
	public final void addPasswordField(
		String identifier,
		String caption,
		String prompt,
		String emptyError,
		AbstractStringValidator validator)
	{
		final PasswordField password = new PasswordField();
		addField(identifier, password, caption, prompt, emptyError, validator);
	}
	
	
	@Override
	public final void addTextField(
		String identifier,
		String caption,
		String prompt,
		String emptyError,
		AbstractStringValidator validator)
	{
		final TextField textfield = new TextField();
		addField(identifier, textfield, caption, prompt, emptyError, validator);
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
	public final void disable()
	{
		fields.values().forEach(field -> field.setEnabled(false));
		submit.setEnabled(false);
	}
	
	
	@Override
	public final String get(String identifier)
	{
		return fields.get(identifier).getValue();
	}


	@Override
	public final void submit()
	{
		if (submitHandler == null) { return; }
		submitHandler.accept(this);
	}
	
	
	/**
	 * Adds a field to the form.
	 *
	 * @param identifier
	 *            a unique identifier of the field
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
		String identifier,
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
		fields.put(identifier, field);
		emptyErrorMessages.put(identifier, emptyErrorMessage);
		validators.put(identifier, validator);
		
		// Focus field if it is the first, and not using mobile
		if (fieldCount == 0 && !((AbstractUI) UI.getCurrent()).isMobile())
		{
			field.focus();
		}
	}


	/** Clears any displayed error message on the form. */
	private void clearErrorMessage()
	{
		errorMessage.setVisible(false);
		
		fields.values().forEach(field -> {
			field.setComponentError(null);
			field.setValidationVisible(false);
		});
	}
	
	
	/** Enables submission of the login form upon ENTER key click. */
	private void enableSubmissionOnEnter()
	{
		submit.addShortcutListener(new ButtonClickOnEnterListener(
			submit,
			fields.values()));
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
		submit.setIcon(submitIcon);
		submit.setStyleName(SUBMIT_STYLENAME);
	}


	/**
	 * Displays an error message on the login form.
	 *
	 * @param error
	 *            the error
	 */
	private void setErrorMessage(Error error)
	{
		// Clear any previous error message.
		clearErrorMessage();
		
		// Set the error message
		errorMessage.setVisible(true);
		errorMessage.setValue(error.message);
		
		// Nothing more to do if no causing field supplied
		if (error.fieldID != null)
		{
			// Otherwise, get the field
			AbstractTextField field = fields.get(error.fieldID);
			
			// Set the error message on the field
			field.setValidationVisible(true);
			field.setCursorPosition(field.getValue().length());
			if (field.getErrorMessage() == null)
			{
				field.setComponentError(new UserError(error.message));
			}
		}

		// Disable form if needed
		if (error.disable)
		{
			disable();
		}
	}
	
	
	/**
	 * Validates all fields in the form.
	 *
	 * @return true if all fields are valid, false otherwise
	 */
	private boolean validateAllFields()
	{
		for (String identifier : fields.keySet())
		{
			if (!validateField(identifier)) { return false; }
		}
		return true;
	}
	
	
	/**
	 * Validates a field.
	 *
	 * @param identifier
	 *            a unique identifier of the field
	 *
	 * @return <code>true</code> if the given field is valid, <code>false</code>
	 *         otherwise
	 */
	private boolean validateField(String identifier)
	{
		AbstractTextField field = fields.get(identifier);

		// Check emptiness
		if (field.getValue().isEmpty())
		{
			setErrorMessage(new Error(
				identifier,
				emptyErrorMessages.get(identifier)));
			return false;
		}
		
		// Validate
		if (!field.isValid())
		{
			setErrorMessage(new Error(identifier, validators
				.get(identifier)
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



	/** The CSS class name to apply to the form's error message. */
	private static final String ERROR_STYLENAME = "AbstractFormComponent-error";

	/** The CSS class name to apply to the form's fields. */
	private static final String FIELD_STYLENAME = "AbstractFormComponent-field";

	/** Serialisation version unique ID. */
	private static final long serialVersionUID = 1L;
	
	/** The CSS class name to apply to this component. */
	private static final String STYLENAME = "AbstractFormComponent";

	/** The CSS class name to apply to the form's submit button. */
	private static final String SUBMIT_STYLENAME =
		"AbstractFormComponent-submit";

	/** A mapping of the error messages displayed when fields are empty. */
	private final Map<String, String> emptyErrorMessages;
	
	/** The error message of a failed submission attempt. */
	private Label errorMessage;
	
	/** The vertically laid-out form. */
	private VerticalLayout layout;

	/** The submission button. */
	private Button submit;

	/** The successful submission handler. */
	private final Consumer<IFormComponent> submitHandler;

	/** A mapping of field validators. */
	private final Map<String, AbstractStringValidator> validators;

	/** A mapping of the fields from their identifiers. */
	final Map<String, AbstractTextField> fields;
}
