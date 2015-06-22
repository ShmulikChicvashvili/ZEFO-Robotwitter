/**
 *
 */

package com.robotwitter.webapp.view.automate;


import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.Component;
import com.vaadin.ui.Window;

import com.robotwitter.webapp.control.automate.ICannedTweetsController;
import com.robotwitter.webapp.control.general.Tweet;
import com.robotwitter.webapp.control.tools.ITweetingController;
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
	 * @param cannedController
	 *            the canned controller
	 * @param originalTweet
	 *            the original tweet
	 * @param callbackOnSuccess
	 *            the callback to use on success, or null for none
	 */
	public TweetResponseWindow(
		IMessagesContainer messages,
		ITweetingController tweetingController,
		ICannedTweetsController cannedController,
		Tweet originalTweet,
		Runnable callbackOnSuccess)
	{
		this.messages = messages;
		this.tweetingController = tweetingController;
		this.cannedController = cannedController;
		this.originalTweet = originalTweet;
		this.callbackOnSuccess = callbackOnSuccess;

		initializeLayout();
	}
	
	
	/** Initialise layout. */
	private void initializeLayout()
	{
		setCloseShortcut(KeyCode.ESCAPE, null);
		setModal(true);
		center();
		setResizable(false);

		setCaption(messages.get("TweetResponseWindow.caption")
			+ "@"
			+ originalTweet.getScreenName());

		Component content =
			new TweetResponse(
				messages,
				cannedController,
				originalTweet,
				() -> {
					close();
					if (callbackOnSuccess != null)
					{
						callbackOnSuccess.run();
					}
				});

		setContent(content);

		center();
	}
	
	
	
	/** The callback on success, or null for none. */
	private Runnable callbackOnSuccess;
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** Canned controller. */
	private ICannedTweetsController cannedController;

	/** The messages displayed by this view. */
	protected IMessagesContainer messages;

	/** The tweeting controller. */
	ITweetingController tweetingController;

	/** The original tweet. */
	private Tweet originalTweet;
}
