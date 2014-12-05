
package com.robotwitter.webapp.view.login;


import java.io.File;

import com.vaadin.data.validator.EmailValidator;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.UserError;
import com.vaadin.ui.AbstractTextField;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.robotwitter.webapp.control.login.PasswordRetrievalController;
import com.robotwitter.webapp.control.login.PasswordRetrievalController.ReturnStatus;




/** Password retrieval component. */
public class PasswordRetrievalForm extends CustomComponent
implements
Button.ClickListener
{
	/**
	 * Construct a password retrieval component.
	 *
	 * @param retriever
	 *            The password retrieval controller
	 */
	public PasswordRetrievalForm(final PasswordRetrievalController retriever)
	{
		this.retriever = retriever;

		initialiseInstructions();
		initialiseEmail();
		initialiseErrorMessage();
		initialiseConfirmButton();
		initialiseLayout();
		
		enableSubmissionOnEnter();
		
		this.email.focus();
	}


	@Override
	public void buttonClick(final ClickEvent event)
	{
		// Validate
		if (!validateEmail()) { return; }
		
		// Attempt to retrieve
		if (!retrieve()) { return; }
		
		// Handle successful retrieval attempt
		//FIXME: add a success status to the view
		//clearErrorMessage();
	}


	/** Clear any displayed error message on the password retrieval form. */
	private void clearErrorMessage()
	{
		this.errorMessage.setVisible(false);

		this.email.setComponentError(null);

		this.email.setValidationVisible(false);
	}


	/** Enable submission of the password retrieval form upon ENTER key click. */
	private void enableSubmissionOnEnter()
	{
		class EnterListener extends Button.ClickShortcut
		{
			public EnterListener()
			{
				super(PasswordRetrievalForm.this.confirm, KeyCode.ENTER);
			}


			@Override
			public void handleAction(final Object sender, final Object target)
			{
				if (target == PasswordRetrievalForm.this.email)
				{
					this.button.click();
				}
			}



			/** Serialisation version unique ID. */
			private static final long serialVersionUID = 1L;
		}

		this.confirm.addShortcutListener(new EnterListener());
	}
	
	
	/** Initialise the confirm button. */
	private void initialiseConfirmButton()
	{
		this.confirm =
			new Button(
				Messages.get("PasswordRetrievalView.button.confirm"), this); //$NON-NLS-1$
		this.confirm.setSizeFull();
	}
	
	
	/** Initialise the email address field. */
	private void initialiseEmail()
	{
		this.email =
			new TextField(Messages.get("PasswordRetrievalView.label.email")); //$NON-NLS-1$
		this.email.addValidator(new EmailValidator("")); //$NON-NLS-1$
		this.email.setValidationVisible(false);
		this.email.setSizeFull();
		this.email.setStyleName(FIELD_STYLENAME);
	}


	/** Initialise the error message displayed upon failed retrieval. */
	private void initialiseErrorMessage()
	{
		this.errorMessage = new Label();
		this.errorMessage.setVisible(false);
		this.errorMessage.setStyleName(ERROR_MESSAGE_STYLENAME);
	}
	
	
	/** Initialise the instructions label. */
	private void initialiseInstructions()
	{
		this.icon = new Label();
		this.icon.setIcon(FontAwesome.ENVELOPE);
		this.instructions =
			new Label(Messages.get("PasswordRetrievalView.instructions")); //$NON-NLS-1$
	}


	/** Initialise the form's layout. */
	private void initialiseLayout()
	{
		this.iconAndInstructions = new HorizontalLayout(this.icon, this.instructions);
		this.iconAndInstructions.setSizeFull();
		this.form =
			new VerticalLayout(
				this.iconAndInstructions,
				this.email,
				this.errorMessage,
				this.confirm);
		this.form.setSpacing(true);
		setCompositionRoot(this.form);
	}


	/** @return true if the given user input is authentic. */
	private boolean retrieve()
	{
		setErrorMessage((new File("").getAbsolutePath()),this.email);
		ReturnStatus result = this.retriever.retrieve(this.email.getValue());
		switch(result) {
			case SUCCESS:
				setErrorMessage("Email Sent!",this.email);
				return true;
			case ERROR_SENDING_EMAIL:
				setErrorMessage("There was an error sending or building the mail",this.email);
				return false;
			case USER_DOESNT_EXIST:
				setErrorMessage("coulden't find user in DB",this.email);
				return false;
			default:
//				setErrorMessage(result.toString(),this.email);
		}
		return true;
	}
	
	
	/**
	 * Display an error message on the password retrieval form.
	 *
	 * @param message
	 *            The error message to display
	 * @param field
	 *            If not null, the field to display the error on, otherwise, the
	 *            error will be displayed as a general error
	 */
	private void setErrorMessage(
		final String message,
		final AbstractTextField field)
	{
		clearErrorMessage();
		
		this.errorMessage.setVisible(true);
		this.errorMessage.setValue(message);
		
		if (field != null)
		{
			field.setValidationVisible(true);
			field.setComponentError(new UserError(message));
			field.setCursorPosition(field.getValue().length());
		}
	}


	/** @return true, if the given user email address is valid. */
	private boolean validateEmail()
	{
		if (this.email.getValue().isEmpty())
		{
			setErrorMessage(Messages.get("LoginForm.error.email-empty"), this.email); //$NON-NLS-1$
			return false;
		}

		if (!this.email.isValid())
		{
			setErrorMessage(
				Messages.get("LoginForm.error.email-invalid"), this.email); //$NON-NLS-1$
			return false;
		}

		return true;
	}
	
	
	
	/** The CSS class name to apply to the form's fields. */
	private static final String FIELD_STYLENAME = "PasswordRetrieval-field"; //$NON-NLS-1$
	
	/** The CSS class name to apply to the form's fields. */
	private static final String ERROR_MESSAGE_STYLENAME =
		"PasswordRetrieval-error-message"; //$NON-NLS-1$
	
	/** Serialisation version unique ID. */
	private static final long serialVersionUID = 1L;

	/** The error message of a failed password retrieval attempt. */
	Label icon;

	/** The instructions shown regarding the password retrieval. */
	Label instructions;
	
	/** The user's email address. */
	TextField email;

	/** The confirm button. */
	Button confirm;

	/** The error message of a failed password retrieval attempt. */
	Label errorMessage;

	/** The password retriever. */
	PasswordRetrievalController retriever;

	/** The instructions next to the icon. */
	private HorizontalLayout iconAndInstructions;

	/** The laid-out form. */
	private VerticalLayout form;

}
