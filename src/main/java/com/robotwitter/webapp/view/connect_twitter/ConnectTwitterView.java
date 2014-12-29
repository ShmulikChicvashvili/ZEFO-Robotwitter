
package com.robotwitter.webapp.view.connect_twitter;


import com.google.inject.Inject;
import com.google.inject.name.Named;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import com.robotwitter.webapp.control.account.ITwitterConnectorController;
import com.robotwitter.webapp.messages.IMessagesContainer;
import com.robotwitter.webapp.ui.MainMenu;
import com.robotwitter.webapp.ui.TwitterConnectorWindow;
import com.robotwitter.webapp.view.AbstractView;




/**
 * Connect Twitter account view.
 * <p>
 * Shown whenever a user is signed in but has no connected Twitter account.
 */
public class ConnectTwitterView extends AbstractView
{
	
	/**
	 * Instantiates a new connect Twitter account view.
	 *
	 * @param messages
	 *            the container of messages to display
	 * @param connectorMessages
	 *            the Twitter account connector's messages
	 * @param connectorController
	 *            the Twitter account connector's controller
	 */
	@Inject
	public ConnectTwitterView(
		@Named(NAME) IMessagesContainer messages,
		@Named(MainMenu.NAME) IMessagesContainer connectorMessages,
		ITwitterConnectorController connectorController)
	{
		super(messages, messages.get("ConnectTwitterView.page.title"));
		twitterConnectorWindow =
			new TwitterConnectorWindow(connectorMessages, connectorController);
	}
	
	
	@Override
	public final boolean isSignedInProhibited()
	{
		return false;
	}
	
	
	@Override
	public final boolean isSignedInRequired()
	{
		return true;
	}


	@Override
	protected final void initialise()
	{
		Label header =
			new Label(messages.get("ConnectTwitterView.label.before-we-begin"));
		
		Button connect =
			new Button(
				messages.get("ConnectTwitterView.button.connect"),
				event -> UI.getCurrent().addWindow(twitterConnectorWindow));
		connect.setIcon(FontAwesome.TWITTER);

		VerticalLayout layout = new VerticalLayout(header, connect);
		layout.setSizeFull();

		// Set styles
		header.addStyleName(HEADER_STYLENAME);
		connect.addStyleName(CONNECT_STYLENAME);
		addStyleName(STYLENAME);
		
		setCompositionRoot(layout);
	}



	/** The view's name. */
	public static final String NAME = "connect-twitter";
	
	/** The CSS class name to apply to the header. */
	private static final String HEADER_STYLENAME = "ConnectTwitterView-header";
	
	/** The CSS class name to apply to the connect button. */
	private static final String CONNECT_STYLENAME =
		"ConnectTwitterView-connect";
	
	/** The CSS class name to apply to this component. */
	private static final String STYLENAME = "ConnectTwitterView";
	
	/** The Twitter account connector window. */
	private TwitterConnectorWindow twitterConnectorWindow;
	
	/** Serialisation version unique ID. */
	private static final long serialVersionUID = 1L;
}
