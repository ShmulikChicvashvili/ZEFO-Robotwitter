
package com.robotwitter.webapp.ui;


import com.vaadin.server.BrowserWindowOpener;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;

import com.robotwitter.webapp.control.account.ITwitterConnectorController;
import com.robotwitter.webapp.messages.IMessagesContainer;
import com.robotwitter.webapp.util.IFormComponent;
import com.robotwitter.webapp.util.WindowWithDescription;
import com.robotwitter.webapp.view.RobotwitterUI;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;




/**
 * Represents a window for Twitter account connection.
 *
 * @author Hagai Akibayov
 */
public class TwitterConnectorWindow extends WindowWithDescription
{
	
	/**
	 * Initialises a new Twitter account connection window.
	 *
	 * @param messages
	 *            the container of messages to display
	 * @param twitterConnectorController
	 *            the Twitter account connector controller
	 */
	public TwitterConnectorWindow(
		IMessagesContainer messages,
		ITwitterConnectorController twitterConnectorController)
	{
		this.messages = messages;
		this.twitterConnectorController = twitterConnectorController;

		// Set window properties
		setCaption(messages.get("TwitterConnectorWindow.caption"));
		setDescription(messages.get("TwitterConnectorWindow.instructions"));
		setIcon(FontAwesome.TWITTER);
		
		// Initialise the button
		setContent(createTwitterLoginButton());
		
		// Set styling
		addStyleName(STYLENAME);
	}
	
	
	/**
	 * Creates the connect component.
	 *
	 * @return the newly created Twitter account connect.
	 */
	private Component createConnectComponent()
	{
		// Initialise the form
		TwitterConnectorForm connectorForm =
			new TwitterConnectorForm(
				messages,
				twitterConnectorController,
				this::handleSuccessfulConnect);
		
		return connectorForm;
	}


	/**
	 * Creates the twitter login button.
	 *
	 * @return the newly created "Login at Twitter" button component.
	 */
	private Component createTwitterLoginButton()
	{
		BrowserWindowOpener opener =
			new BrowserWindowOpener(
				twitterConnectorController.getConnectionURL());

		Button twitterLogin =
			new Button(
				messages.get("TwitterConnectorWindow.link.login-at-twitter"),
				event -> {
					setContent(createConnectComponent());
				});
		twitterLogin.setSizeFull();

		opener.extend(twitterLogin);

		twitterLogin.addStyleName(TWITTER_LOGIN_STYLENAME);
		return twitterLogin;
	}
	
	
	/**
	 * Handles a successful attempt at a Twitter account connection.
	 *
	 * @param form
	 *            the Twitter account connector form
	 */
	@SuppressFBWarnings("UPM_UNCALLED_PRIVATE_METHOD")
	private void handleSuccessfulConnect(
		@SuppressWarnings("unused") IFormComponent form)
	{
		// Activate the new Twitter account
		long id = twitterConnectorController.getID();
		((RobotwitterUI) UI.getCurrent()).activateTwitterAccount(id);

		// Create description
		String screenname = twitterConnectorController.getScreenname();
		String desc = messages.get("TwitterConnectorForm.notify.connected-to");
		desc += " <b>@" + screenname + "</b>. ";
		desc += messages.get("TwitterConnectorForm.notify.change-active");
		
		// Show notification
		Notification notification =
			new Notification(
				messages.get("TwitterConnectorForm.notify.success.caption"),
				Type.TRAY_NOTIFICATION);
		notification.setHtmlContentAllowed(true);
		notification.setDescription(desc);
		notification.setStyleName(ValoTheme.NOTIFICATION_SUCCESS
			+ " "
			+ ValoTheme.NOTIFICATION_TRAY);
		notification.setDelayMsec(SUCCESS_NOTIFICATION_DELAY);
		notification.show(Page.getCurrent());

		// Reset then close the window
		setContent(createTwitterLoginButton());
		close();
	}
	
	
	
	/**
	 * The closing delay of the successful connection notification (in
	 * milliseconds).
	 */
	private static final int SUCCESS_NOTIFICATION_DELAY = 7000;
	
	/** The Twitter account connector controller. */
	private final ITwitterConnectorController twitterConnectorController;
	
	/** The CSS class name to apply to the password retrieval form. */
	private static final String TWITTER_LOGIN_STYLENAME =
		"TwitterConnectorWindow-login-to-twitter";

	/** Serialisation version unique ID. */
	private static final long serialVersionUID = 1L;
	
	/** The CSS class name to apply to this component. */
	private static final String STYLENAME = "TwitterConnectorWindow";

	/** The messages displayed by this view. */
	protected IMessagesContainer messages;

}
