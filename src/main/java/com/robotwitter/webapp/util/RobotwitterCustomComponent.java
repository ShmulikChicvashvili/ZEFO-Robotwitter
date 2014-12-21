
package com.robotwitter.webapp.util;


import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.UI;

import com.robotwitter.webapp.messages.IMessagesContainer;
import com.robotwitter.webapp.view.IUserSession;
import com.robotwitter.webapp.view.RobotwitterUI;




/**
 * Extends the default vaadin's {@link CustomComponent}.
 * <p>
 * Most custom components in the Robotwitter would derive from this class which,
 * in addition to the default behaviour, wraps view navigation using
 * {@link #navigate}, and receives a messages container in the constructor in
 * order to provide the component with its natural language messages.
 *
 * @author Hagai Akibayov
 */
public class RobotwitterCustomComponent extends CustomComponent
{
	/** @return the current user's browsing session. */
	public static final IUserSession getUserSession()
	{
		return ((RobotwitterUI) UI.getCurrent()).getUserSession();
	}
	
	
	/**
	 * Instantiates a new abstract view.
	 *
	 * @param messages
	 *            the container of messages to display
	 */
	public RobotwitterCustomComponent(IMessagesContainer messages)
	{
		this.messages = messages;
	}
	
	
	/**
	 * Navigates to the given view name.
	 *
	 * @param name
	 *            the view's name
	 */
	protected final void navigate(String name)
	{
		getUI().getNavigator().navigateTo(name);
	}
	
	
	
	/** Serialisation version unique ID. */
	private static final long serialVersionUID = 1L;
	
	/** The messages displayed by this component. */
	protected IMessagesContainer messages;
	
}
