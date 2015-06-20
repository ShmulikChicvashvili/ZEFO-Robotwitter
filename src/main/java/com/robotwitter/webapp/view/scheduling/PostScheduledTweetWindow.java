/**
 *
 */

package com.robotwitter.webapp.view.scheduling;


import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.Component;
import com.vaadin.ui.Window;

import com.robotwitter.webapp.control.scheduling.IScheduledTweetsController;
import com.robotwitter.webapp.messages.IMessagesContainer;




/**
 * @author Eyal
 *
 */
public class PostScheduledTweetWindow extends Window
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
	public PostScheduledTweetWindow(
		IMessagesContainer messages,
		IScheduledTweetsController scheduledTweetsController,
		Runnable callbackOnSuccess)
	{
		this.messages = messages;
		this.scheduledTweetsController = scheduledTweetsController;
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

		setCaption(messages.get("PostScheduledTweetWindow.caption"));

		final Component content =
			new PostScheduledTweet(messages, scheduledTweetsController, () -> {
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
	private final Runnable callbackOnSuccess;

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** Scheduled controller. */
	IScheduledTweetsController scheduledTweetsController;

	/** The messages displayed by this view. */
	protected IMessagesContainer messages;
}
