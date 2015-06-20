
package com.robotwitter.webapp.menu;


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
	
	
	/**
	 * Can be overridden by deriving menus; this method is called when the menu
	 * is attached to the UI by an outside source.
	 */
	public void onAttach()
	{
		/* Do nothing */
	};



	/** Serialisation version unique ID. */
	private static final long serialVersionUID = 1L;

}
