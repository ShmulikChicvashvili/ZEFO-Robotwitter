
package com.robotwitter.webapp.util;


import java.util.Collection;

import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Button;




/**
 * Listener of Enter shortcut keyboard press.
 *
 * @author Hagai Akibayov
 */
class ButtonClickOnEnterListener extends Button.ClickShortcut
{

	/**
	 * Instantiates a new enter listener.
	 *
	 * @param button
	 *            the observed button
	 * @param components
	 *            a list of relevant components. At least one component in this
	 *            list must be focused for this event to fire.
	 */
	public ButtonClickOnEnterListener(
		Button button,
		Collection<? extends AbstractComponent> components)
	{
		super(button, KeyCode.ENTER);
		this.components = components;
	}
	
	
	@Override
	public void handleAction(final Object sender, final Object target)
	{
		if (components.contains(target))
		{
			button.click();
		}
	}



	/** The components that must be focused for the event to fire. */
	final Collection<? extends AbstractComponent> components;

	/** Serialisation version unique ID. */
	private static final long serialVersionUID = 1L;
}
