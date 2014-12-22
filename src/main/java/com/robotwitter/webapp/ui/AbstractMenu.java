
package com.robotwitter.webapp.ui;


import com.robotwitter.webapp.messages.IMessagesContainer;
import com.robotwitter.webapp.util.RobotwitterCustomComponent;




/**
 * Represents a menu component.
 *
 * @author Hagai Akibayov
 *
 */
public abstract class AbstractMenu extends RobotwitterCustomComponent
{
	/**
	 * Instantiates a menu.
	 *
	 * @param messages
	 *            the container of messages to display
	 */
	protected AbstractMenu(IMessagesContainer messages)
	{
		super(messages);
	}
	
	
	
	/** Serialisation version unique ID. */
	private static final long serialVersionUID = 1L;
	
}
