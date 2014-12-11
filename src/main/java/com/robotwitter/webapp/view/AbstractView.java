
package com.robotwitter.webapp.view;


import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.CustomComponent;

import com.robotwitter.webapp.messages.IMessagesContainer;




/**
 * Represents an abstract view of the web-application.
 * <p>
 * Inheriting classes should implement the {@link #initialise} methods.
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


	/** @return the current user's browsing session. */
	public final IUserSession getUserSession()
	{
		return ((NavigatorUI) getUI()).getUserSession();
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
	 * Navigates to the given view name.
	 *
	 * @param name
	 *            the view's name
	 */
	protected final void navigate(String name)
	{
		getUI().getNavigator().navigateTo(name);
	}


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
	
	/** The messages displayed by this view. */
	protected IMessagesContainer messages;

}
