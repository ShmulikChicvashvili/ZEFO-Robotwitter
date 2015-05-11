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
	 */
	public TweetResponseWindow(
		IMessagesContainer messages,
		ITweetingController tweetingController,
		ICannedTweetsController cannedController,
		Tweet originalTweet)
	{
		this.messages = messages;
		this.tweetingController = tweetingController;
		this.cannedController = cannedController;
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
		
		setCaption(messages.get("TweetResponseWindow.caption")
			+ "@"
			+ originalTweet.getScreenName());
		
		Component content =
			new TweetResponse(
				messages,
				cannedController,
				originalTweet,
				() -> close());
		
		setContent(content);
		
		center();
	}
	
	
	
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	
	/** Canned controller. */
	private ICannedTweetsController cannedController;
	
	/** The messages displayed by this view. */
	protected IMessagesContainer messages;
	
	ITweetingController tweetingController;
	
	private Tweet originalTweet;
}
