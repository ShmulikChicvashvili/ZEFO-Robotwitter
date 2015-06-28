
package com.robotwitter.webapp.view.scheduling;


import java.util.List;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import com.robotwitter.database.primitives.DBScheduledTweet;
import com.robotwitter.webapp.control.scheduling.IScheduledTweetsController;
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
		IScheduledTweetsController scheduledController,
		DBScheduledTweet tweet)
	{
		this.messages = messages;
		this.scheduledController = scheduledController;
		this.tweet = tweet;
		initializeLayout();
	}
	
	
	private Layout initializeContent()
	{
		Label name =
			new Label(messages.get("SchedulePreviewWindow.label.name")
				+ tweet.getTweetName());
		
		String typeStr = "";
		
		switch (tweet.getPostingPeriod())
		{
			case SINGLE:
				typeStr = messages.get("SchedulePreviewWindow.repeat.once");
				break;
			case DAILY:
				typeStr = messages.get("SchedulePreviewWindow.repeat.daily");
				break;
			case WEEKLY:
				typeStr = messages.get("SchedulePreviewWindow.repeat.weekly");
				break;
		}
		
		Label type =
			new Label(messages.get("SchedulePreviewWindow.label.type")
				+ typeStr);
		
		Label startDate =
			new Label(messages.get("SchedulePreviewWindow.label.start_date")
				+ tweet.getStartingDate().toString());
		
		TweetPreview preview = new TweetPreview();
		List<String> tweets =
			scheduledController.previewTweet(tweet.getTweetText());
		preview.updatePreview(tweets);
		
		VerticalLayout layout =
			new VerticalLayout(name, type, startDate, preview);
		
		return layout;
		
	}
	
	
	/** Initialise layout. */
	private void initializeLayout()
	{
		setCloseShortcut(KeyCode.ESCAPE, null);
		setModal(true);
		center();
		setResizable(false);
		
		setCaption(messages.get("SchedulePreview.page.title"));
		
		Layout layout = initializeContent();
		
		setContent(layout);
		
		center();
	}
	
	
	
	/** The messages displayed by this view. */
	protected IMessagesContainer messages;
	
	private IScheduledTweetsController scheduledController;
	
	private DBScheduledTweet tweet;
	
}
