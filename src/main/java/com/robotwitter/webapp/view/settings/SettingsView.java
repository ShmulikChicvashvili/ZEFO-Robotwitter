package com.robotwitter.webapp.view.settings;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.robotwitter.webapp.messages.IMessagesContainer;
import com.robotwitter.webapp.view.AbstractView;


/**
 * @author Amir Drutin
 */
public class SettingsView extends AbstractView{
	
	/**
	 * Instantiates a new settings view.
	 *
	 * @param messages
	 *            the container of messages to display
	 */
	@Inject
	public SettingsView(@Named(NAME) IMessagesContainer messages)
	{
		super(messages, messages.get("SettingsView.page.title"));
	}

	@Override
	public boolean isSignedInProhibited() {
		return false;
	}





	@Override
	public boolean isSignedInRequired() {
		return true;
	}





	@Override
	protected void initialise() {
		// TODO Auto-generated method stub
		
	} 

	/** The view's name. */
	public static final String NAME = "login";

}


