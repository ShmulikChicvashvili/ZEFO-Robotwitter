/**
 *
 */

package com.robotwitter.webapp.view.automate;


import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.Component;
import com.vaadin.ui.Window;

import com.robotwitter.webapp.control.tools.ITweetingController;
import com.robotwitter.webapp.control.tools.tweeting.Tweet;
import com.robotwitter.webapp.messages.IMessagesContainer;




/**
 * @author Eyal
 *
 */
public class TweetResponseWindow extends Window
{

	/**
	 * Instantiates a new tweet response window.
	 *
	 * @param messages
	 *            the message container
	 * @param tweetingController
	 *            A controller used for tweeting
	 */
	public TweetResponseWindow(
		IMessagesContainer messages,
		ITweetingController tweetingController,
		Tweet originalTweet)
	{
		this.messages = messages;
		this.tweetingController = tweetingController;
		this.originalTweet = originalTweet;

		initializeLayout();
	}


	/**
	 * Initialize layout.
	 */
	private void initializeLayout()
	{
		setCloseShortcut(KeyCode.ESCAPE, null);
		setModal(true);
		center();
		setResizable(false);

		setCaption(messages.get("TweetResponseWindow.caption"));

		Component content =
			new TweetResponseComponent(
				messages,
				tweetingController,
				originalTweet);

		setContent(content);

		center();
	}



	/**
	 *
	 */
	private static final long serialVersionUID = -4523468889069315800L;

	/** The messages displayed by this view. */
	protected IMessagesContainer messages;

	ITweetingController tweetingController;

	private Tweet originalTweet;
}
