
package com.robotwitter.webapp.view.login;


import com.google.inject.Inject;
import com.google.inject.name.Named;

import com.vaadin.server.FontAwesome;

import com.robotwitter.webapp.control.login.IPasswordRetrievalController;
import com.robotwitter.webapp.messages.IMessagesContainer;
import com.robotwitter.webapp.util.WindowWithDescription;




/**
 * Represents a window for password retrieval.
 *
 * @author Hagai Akibayov
 */
public class PasswordRetrievalWindow extends WindowWithDescription
{

	/**
	 * Initialises a new password retrieval window.
	 *
	 * @param messages
	 *            the container of messages to display
	 * @param controller
	 *            the password retrieval controller
	 */
	@Inject
	public PasswordRetrievalWindow(
		@Named(LoginView.NAME) IMessagesContainer messages,
		IPasswordRetrievalController controller)
	{
		setCaption(messages.get("PasswordRetrievalWindow.caption")); //$NON-NLS-1$
		setDescription(messages.get("PasswordRetrievalWindow.instructions")); //$NON-NLS-1$
		setIcon(FontAwesome.ENVELOPE);
		setContent(new PasswordRetrievalForm(messages, controller, null));
	}
	
	
	
	/** Serialisation version unique ID. */
	private static final long serialVersionUID = 1L;

}
