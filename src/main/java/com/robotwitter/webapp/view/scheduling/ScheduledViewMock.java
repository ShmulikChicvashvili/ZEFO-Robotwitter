/**
 *
 */

package com.robotwitter.webapp.view.scheduling;


import com.google.inject.Inject;
import com.google.inject.name.Named;

import com.vaadin.ui.Button;

import com.robotwitter.webapp.control.scheduling.ScheduledTweetsControllerMock;
import com.robotwitter.webapp.messages.IMessagesContainer;
import com.robotwitter.webapp.view.AbstractView;




/**
 * @author Eyal
 *
 */
public class ScheduledViewMock extends AbstractView
{
	@Inject
	public ScheduledViewMock(@Named(NAME) IMessagesContainer messages)
	{
		super(messages, messages.get("AutomateView.page.title"));
	}


	/* (non-Javadoc) @see
	 * com.robotwitter.webapp.view.AbstractView#isSignedInProhibited() */
	@Override
	public boolean isSignedInProhibited()
	{
		return false;
	}


	/* (non-Javadoc) @see
	 * com.robotwitter.webapp.view.AbstractView#isSignedInRequired() */
	@Override
	public boolean isSignedInRequired()
	{
		return true;
	}


	/* (non-Javadoc) @see com.robotwitter.webapp.view.AbstractView#initialise() */
	@Override
	protected void initialise()
	{
		final Button button =
			new Button("Post scheduled", event -> getUI().addWindow(
				new PostScheduledTweetWindow(
					messages,
					new ScheduledTweetsControllerMock(),
					null)));

		setCompositionRoot(button);
	}



	/** The view's name. */
	public static final String NAME = "schedule-mock";
}
