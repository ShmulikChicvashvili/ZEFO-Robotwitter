
package com.robotwitter.webapp.view;


import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.CustomComponent;

import com.robotwitter.webapp.messages.IMessagesContainer;




/**
 * Represents an abstract view of the web-application. Inheriting classes should
 * implement the {@link #getName} and {@link #initialise} methods.
 *
 * @author Hagai Akibayov
 */
public abstract class AbstractView extends CustomComponent implements View
{
	
	/**
	 * Instantiates a new abstract view.
	 *
	 * @param messages
	 *            the container of messages to display
	 * @param title
	 *            the title of the view (shown in the browser's title)
	 */
	public AbstractView(IMessagesContainer messages, String title)
	{
		this.messages = messages;
		this.title = title;
	}


	@Override
	public final void enter(ViewChangeEvent event)
	{
		setTitle(title);
		initialise();
	}
	
	
	/**
	 * Gets the name of the view.
	 *
	 * @return the name of the view
	 */
	public abstract String getName();
	
	
	/** Initialises the view. */
	protected abstract void initialise();
	
	
	/**
	 * Sets the title of the view.
	 *
	 * @param title
	 *            the new title
	 */
	protected final void setTitle(String title)
	{
		getUI().getPage().setTitle(title);
	}
	
	
	
	/** The messages displayed by this view. */
	protected IMessagesContainer messages;
	
	/** The view's title. */
	private final String title;

	/** Serialisation version unique ID. */
	private static final long serialVersionUID = 1L;
	
}
