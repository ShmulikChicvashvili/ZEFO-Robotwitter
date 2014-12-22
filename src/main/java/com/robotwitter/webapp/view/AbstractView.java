
package com.robotwitter.webapp.view;


import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;

import com.robotwitter.webapp.messages.IMessagesContainer;
import com.robotwitter.webapp.util.RobotwitterCustomComponent;




/**
 * Represents an abstract view of the web-application.
 * <p>
 * Inheriting classes should implement the {@link #initialise} methods.
 *
 * @author Hagai Akibayov
 */
public abstract class AbstractView extends RobotwitterCustomComponent
implements
View
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
		super(messages);
		this.title = title;
	}
	
	
	@Override
	public final void enter(ViewChangeEvent event)
	{
		setTitle(title);
		initialise();
	}
	
	
	/**
	 * @return <code>true</code> if the user must be signed <b>off</b> to
	 *         navigate to this view, and <code>false</code> otherwise.
	 */
	public abstract boolean isSignedInProhibited();


	/**
	 * @return <code>true</code> if the user must be signed in to navigate to
	 *         this view, and <code>false</code> otherwise.
	 */
	public abstract boolean isSignedInRequired();


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



	/** Serialisation version unique ID. */
	private static final long serialVersionUID = 1L;

	/** The view's title. */
	private final String title;

}
