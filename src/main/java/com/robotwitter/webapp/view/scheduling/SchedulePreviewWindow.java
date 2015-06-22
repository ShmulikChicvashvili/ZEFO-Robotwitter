package com.robotwitter.webapp.view.scheduling;

import com.robotwitter.webapp.control.scheduling.IScheduledTweetsController;
import com.robotwitter.webapp.messages.IMessagesContainer;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.Component;
import com.vaadin.ui.Window;

public class SchedulePreviewWindow extends Window{
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
		IMessagesContainer messages)
	{
		this.messages = messages;
		initializeLayout();
	}
	

	/** Initialise layout. */
	private void initializeLayout()
	{
		setCloseShortcut(KeyCode.ESCAPE, null);
		setModal(false);
		center();
		setResizable(false);

		setCaption(messages.get("SchedulePreview.page.title"));

		final Component content =
			new SchedulePreview(messages);

		setContent(content);
	}
	
	/** The messages displayed by this view. */
	protected IMessagesContainer messages;
	
}
