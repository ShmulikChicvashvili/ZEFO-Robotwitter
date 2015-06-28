
package com.robotwitter.webapp.view.scheduling;


import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.Window;

import com.robotwitter.webapp.messages.IMessagesContainer;
import com.robotwitter.webapp.util.tweeting.TweetPreview;




public class SchedulePreviewWindow extends Window
{
	/**
	 * Instantiates a new tweet response window.
	 *
	 * @param messages
	 *            the message container
	 * @param scheduledTweetsController
	 *            the scheduled tweets controller
	 * @param callbackOnSuccess
	 *            the callback to use on success, or null for none
	 */
	public SchedulePreviewWindow(
		IMessagesContainer messages,
		TweetPreview tweetPreview)
	{
		this.messages = messages;
		this.tweetPreview = tweetPreview;
		initializeLayout();
	}
	
	
	/** Initialise layout. */
	private void initializeLayout()
	{
		setCloseShortcut(KeyCode.ESCAPE, null);
		setModal(true);
		center();
		setResizable(false);
		
		setCaption(messages.get("SchedulePreview.page.title"));
		
		setContent(tweetPreview);
		
		center();
	}
	
	
	
	/** The messages displayed by this view. */
	protected IMessagesContainer messages;
	
	private TweetPreview tweetPreview;
}
