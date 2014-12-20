
package com.robotwitter.webapp.view.dashboard;


import com.google.inject.Inject;
import com.google.inject.name.Named;

import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import com.robotwitter.webapp.messages.IMessagesContainer;
import com.robotwitter.webapp.view.AbstractView;




/** Login user interface view. */
public class DashboardView extends AbstractView
{
	
	/**
	 * Instantiates a new login view.
	 *
	 * @param messages
	 *            the container of messages to display
	 */
	@Inject
	public DashboardView(@Named(NAME) IMessagesContainer messages)
	{
		super(messages, messages.get("DashboardView.page.title")); //$NON-NLS-1$
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
		temp.addComponent(new Label("Nothing to do here...")); //$NON-NLS-1$
		setCompositionRoot(temp);
	}



	/** The view's name. */
	public static final String NAME = "dashboard"; //$NON-NLS-1$

	/** Serialisation version unique ID. */
	private static final long serialVersionUID = 1L;
}
