
package com.robotwitter.webapp.view.connect_twitter;


import com.google.inject.Inject;
import com.google.inject.name.Named;

import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import com.robotwitter.webapp.messages.IMessagesContainer;
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
	 */
	@Inject
	public ConnectTwitterView(@Named(NAME) IMessagesContainer messages)
	{
		super(messages, messages.get("ConnectTwitterView.page.title"));
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
		VerticalLayout temp = new VerticalLayout();
		temp.setSizeFull();
		temp.addComponent(new Label("CONNECT A TWITTER ACCOUNT!"));
		setCompositionRoot(temp);
	}
	
	
	
	/** The view's name. */
	public static final String NAME = "connect-twitter";
	
	/** Serialisation version unique ID. */
	private static final long serialVersionUID = 1L;
}
