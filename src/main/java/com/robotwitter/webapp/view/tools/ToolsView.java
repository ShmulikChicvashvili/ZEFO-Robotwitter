
package com.robotwitter.webapp.view.tools;


import com.google.inject.Inject;
import com.google.inject.name.Named;

import com.vaadin.ui.VerticalLayout;

import com.robotwitter.webapp.messages.IMessagesContainer;
import com.robotwitter.webapp.view.AbstractView;




/** Dashboard view. */
public class ToolsView extends AbstractView
{

	/**
	 * Instantiates a new tools view.
	 *
	 * @param messages
	 *            the container of messages to display
	 */
	@Inject
	public ToolsView(@Named(NAME) IMessagesContainer messages)
	{
		super(messages, messages.get("ToolsView.page.title"));
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
	public static final String NAME = "tools";
	
	/** Serialisation version unique ID. */
	private static final long serialVersionUID = 1L;
}
