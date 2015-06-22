
package com.robotwitter.webapp.menu;


import java.util.function.Consumer;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import com.vaadin.ui.UI;

import com.robotwitter.webapp.control.account.ITwitterConnectorController;
import com.robotwitter.webapp.control.account.ITwitterConnectorController.Status;
import com.robotwitter.webapp.general.General;
import com.robotwitter.webapp.messages.IMessagesContainer;
import com.robotwitter.webapp.util.AbstractFormComponent;
import com.robotwitter.webapp.util.AbstractTextFieldValidator;
import com.robotwitter.webapp.util.AbstractUI;
import com.robotwitter.webapp.util.IFormComponent;




/**
 * Represents a Twitter account connector form.
 * <p>
 * The connection form consists of a single field; the PIN code field which is
 * received by the user from Twitter during a Twitter account connector use
 * case.
 *
 * @author Hagai Akibayov
 */
public class TwitterConnectorForm extends AbstractFormComponent
{

	/**
	 * Validator of a PIN code by Twitter
	 * <p>
	 * Valid PIN codes consist of exactly 7 characters.
	 */
	static class TwitterPinValidator extends AbstractTextFieldValidator
	{

		/**
		 * Instantiates a new PIN code validator.
		 *
		 * @param messages
		 *            the container of messages to display
		 */
		@Inject
		public TwitterPinValidator(
			@Named(General.MESSAGES) IMessagesContainer messages)
		{
			setMinLength(
				VALID_LENGTH,
				messages
				.get("TwitterConnectorForm.error.pin-must-be-7-characters"));

			setMaxLength(
				VALID_LENGTH,
				messages
				.get("TwitterConnectorForm.error.pin-must-be-7-characters"));

			addConstraint(
				"^[0-9]*$",
				messages
					.get("TwitterConnectorForm.error.must-contain-digits-only"));
		}



		/** The valid PIN code length. */
		public static final int VALID_LENGTH = 7;

		/** Serialisation version unique ID. */
		private static final long serialVersionUID = 1L;

	}



	/**
	 * Instantiates a new Twitter account connector form.
	 *
	 * @param messages
	 *            the container of messages to display
	 * @param twitterConnectorController
	 *            the Twitter account connector controller
	 * @param confirmHandler
	 *            handles a successful submission of the form. Receives this
	 *            form as a parameter. If <code>null</code> is received, no
	 *            operation will be performed on successful submission.
	 */
	public TwitterConnectorForm(
		IMessagesContainer messages,
		ITwitterConnectorController twitterConnectorController,
		Consumer<IFormComponent> confirmHandler)
	{
		super(
			messages.get("TwitterConnectorForm.button.connect"),
			null,
			confirmHandler);

		this.messages = messages;
		this.twitterConnectorController = twitterConnectorController;

		initialisePIN();
	}
	
	
	/** Initialises the PIN code field. */
	private void initialisePIN()
	{
		addTextField(
			PIN,
			messages.get("TwitterConnectorForm.caption.pin"),
			null,
			messages.get("TwitterConnectorForm.error.pin-empty"),
			new TwitterPinValidator(messages));
	}
	
	
	@Override
	protected final Error validate()
	{
		String email =
			((AbstractUI) UI.getCurrent())
				.getUserSession()
				.getAccountController()
				.getEmail();
		Status status = twitterConnectorController.connect(email, get(PIN));
		
		switch (status)
		{
			case SUCCESS:
				return null;
				
			case PIN_IS_INCORRECT:
				return new Error(
					PIN,
					messages.get("TwitterConnectorForm.error.pin-incorrect"));
				
			case FAILURE:
				return new Error(
					null,
					messages.get("TwitterConnectorForm.error.unknown"),
					true);
				
			default:
				assert false;
				return null;
		}
	}
	
	
	
	/** The email field's identifier. */
	public static final String PIN = "pin";

	/** Serialisation version unique ID. */
	private static final long serialVersionUID = 1L;
	
	/** The displayed messages. */
	IMessagesContainer messages;

	/** The Twitter account connector controller. */
	private final ITwitterConnectorController twitterConnectorController;
	
}
