
package com.robotwitter.webapp.view.dashboard;


import com.google.inject.Inject;
import com.google.inject.name.Named;

import com.vaadin.ui.VerticalLayout;

import com.robotwitter.webapp.messages.IMessagesContainer;
import com.robotwitter.webapp.view.AbstractView;




/** Dashboard view. */
public class DashboardView extends AbstractView
{

	/**
	 * Instantiates a new dashboard view.
	 *
	 * @param messages
	 *            the container of messages to display
	 */
	@Inject
	public DashboardView(@Named(NAME) IMessagesContainer messages)
	{
		super(messages, messages.get("DashboardView.page.title"));
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
		setCompositionRoot(temp);
	}
	
	
	
	/** The view's name. */
	public static final String NAME = "dashboard";
	
	/** Serialisation version unique ID. */
	private static final long serialVersionUID = 1L;
}
