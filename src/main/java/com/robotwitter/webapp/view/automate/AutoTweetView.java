/**
 *
 */

package com.robotwitter.webapp.view.automate;


import com.google.inject.Inject;
import com.google.inject.name.Named;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.VerticalLayout;

import com.robotwitter.webapp.control.tools.ITweetingController;
import com.robotwitter.webapp.messages.IMessagesContainer;
import com.robotwitter.webapp.view.AbstractView;




/**
 * @author Eyal
 *
 */
public class AutoTweetView extends AbstractView
{

	/**
	 * Instantiates a new auto tweet view.
	 *
	 * @param messages
	 *            the messages
	 */
	@Inject
	public AutoTweetView(
		@Named(NAME) IMessagesContainer messages,
		ITweetingController tweetingController)
	{
		super(messages, messages.get("AutoTweetView.page.title"));
		this.tweetingController = tweetingController;
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
		final Button btn =
			new Button("Respond", event -> getUI().addWindow(
				new TweetResponseWindow(messages, tweetingController)));
		final VerticalLayout layout = new VerticalLayout(btn);

		layout.setSizeFull();
		layout.setComponentAlignment(btn, Alignment.BOTTOM_LEFT);

		setCompositionRoot(layout);
	}



	/**
	 *
	 */
	private static final long serialVersionUID = 2343210387167584004L;

	/** The Constant NAME. */
	public static final String NAME = "auto-tweet";

	/** The tweeting controller. */
	private final ITweetingController tweetingController;
}
