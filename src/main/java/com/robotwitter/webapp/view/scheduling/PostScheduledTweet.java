/**
 *
 */

package com.robotwitter.webapp.view.scheduling;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import com.robotwitter.database.interfaces.returnValues.SqlError;
import com.robotwitter.posting.AutomateTweetPostingPeriod;
import com.robotwitter.webapp.control.scheduling.IScheduledTweetsController;
import com.robotwitter.webapp.messages.IMessagesContainer;
import com.robotwitter.webapp.util.RobotwitterCustomComponent;
import com.robotwitter.webapp.util.tweeting.RepeatChooser;
import com.robotwitter.webapp.util.tweeting.RepeatChooser.RepeatType;
import com.robotwitter.webapp.util.tweeting.TweetComposeBox;
import com.robotwitter.webapp.util.tweeting.TweetPreview;




/**
 * @author Eyal
 *
 */
public class PostScheduledTweet extends RobotwitterCustomComponent
{

	/**
	 * The Interface OnResponseSuccess.
	 */
	public interface OnResponseSuccess
	{

		/**
		 * On response.
		 */
		void onResponse();
	}



	/**
	 * @param messages
	 * @param schedulingController
	 */
	public PostScheduledTweet(
		IMessagesContainer messages,
		IScheduledTweetsController schedulingController,
		OnResponseSuccess onResponseSuccess)
	{
		super(messages);
		this.schedulingController = schedulingController;
		this.onResponseSuccess = onResponseSuccess;

		initializeLayout();
	}


	private boolean checkErrors(
		String tweet,
		RepeatType repeatType,
		Calendar startDate)
	{
		if (!startDate.getTime().after(new Date()))
		{
			setErrorMessage(messages
				.get("PostScheduledTweet.error.date-passed"));
			return false;
		}

		if (tweetName.getValue().isEmpty())
		{
			setErrorMessage(messages.get("PostScheduledTweet.error.name-empty"));
			return false;
		}

		List<String> tweets = schedulingController.previewTweet(tweet);
		int maxTweetLen =
			getUserSession()
			.getAccountController()
			.getActiveTwitterAccount()
			.getCurrentMaximumTweetLength();
		int c = 0;
		for (String t : tweets)
		{
			c += t.length();
		}
		// Check emptiness
		if (c == 0)
		{
			setErrorMessage(messages
				.get("PostScheduledTweet.error.tweet-empty"));
			return false;
		}

		// Validate
		if (c > maxTweetLen)
		{
			setErrorMessage(messages
				.get("PostScheduledTweet.error.tweet-too-long"));
			return false;
		}

		return true;
	}


	/** Clears the displayed error message on the composer. */
	private void clearErrorMessage()
	{
		errorMessage.setVisible(false);
	}


	/**
	 *
	 */
	private void initializeErrorMessage()
	{
		errorMessage = new Label();
		errorMessage.setVisible(false);
		errorMessage.setStyleName(ERROR_STYLENAME);
	}


	private void initializeLayout()
	{
		final Layout tweetNameLayout = initializeTweetNameLayout();
		initializeTweetComposeBox();
		initializeRepeatLayout();
		final Button submit = initializeSubmitButton();
		initializeErrorMessage();

		initializePreviewLayout();

		final VerticalLayout left =
			new VerticalLayout(
				tweetNameLayout,
				tweetComposeBox,
				repeatChooser,
				submit,
				errorMessage);

		final HorizontalLayout layout = new HorizontalLayout(left, preview);
		layout.setSpacing(true);

		setCompositionRoot(layout);
	}


	/**
	 *
	 */
	private void initializePreviewLayout()
	{
		preview = new TweetPreview();
		preview.updatePreview(new ArrayList<>());
	}


	/**
	 *
	 */
	private void initializeRepeatLayout()
	{
		repeatChooser = new RepeatChooser(messages);
	}


	/**
	 * @return
	 */
	private Button initializeSubmitButton()
	{
		final Button submit =
			new Button(
				messages.get("PostScheduledTweet.button.tweet"),
				event -> submitClick(event));
		submit.setIcon(FontAwesome.PAPER_PLANE);
		submit.addStyleName(TWEET_BUTTON_STYLENAME);
		submit.addStyleName(ValoTheme.BUTTON_PRIMARY);
		return submit;
	}


	/**
	 *
	 */
	private void initializeTweetComposeBox()
	{
		tweetComposeBox =
			new TweetComposeBox(
				messages,
				event -> tweetComposeTextChanged(event.getText()));
		tweetComposeBox.addStyleName(COMPOSE_BOX_STYLENAME);
		tweetComposeBox.setSizeFull();
	}


	/**
	 * @return
	 */
	private Layout initializeTweetNameLayout()
	{
		tweetName = new TextField();
		final Label tweetNameLabel =
			new Label(messages.get("PostScheduledTweet.label.tweet-name"));
		final Layout tweetNameLayout =
			new HorizontalLayout(tweetNameLabel, tweetName);
		return tweetNameLayout;
	}


	/**
	 * Displays an error message on the composer.
	 *
	 * @param error
	 *            the error message to display
	 */
	private void setErrorMessage(String error)
	{
		// Clear any previous error message.
		clearErrorMessage();

		// Set the error message
		errorMessage.setVisible(true);
		errorMessage.setValue(error);
	}


	/**
	 * @param event
	 */
	private void submitClick(ClickEvent event)
	{

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

		RepeatType repeatType = repeatChooser.getChosenRepeatType();
		Calendar startDate = repeatChooser.getChosenDate();

		if (!checkErrors(tweetComposeBox.getText(), repeatType, startDate)) { return; }

		long userId =
			getUserSession()
			.getAccountController()
			.getActiveTwitterAccount()
			.getID();

		AutomateTweetPostingPeriod period = null;
		switch (repeatType)
		{
			case ONE_TIME:
				period = AutomateTweetPostingPeriod.SINGLE;
				break;
			case DAILY:
				period = AutomateTweetPostingPeriod.DAILY;
				break;
			case WEEKLY:
				period = AutomateTweetPostingPeriod.WEEKLY;
				break;
		}
		assert period != null;

		SqlError status =
			schedulingController.addScheduledTweet(
				tweetName.getValue(),
				tweetComposeBox.getText(),
				userId,
				startDate,
				period);

		switch (status)
		{
			case SUCCESS:
				Notification notification =
				new Notification(
					messages.get("PostScheduledTweet.notify.success.title"),
					messages
					.get("PostScheduledTweet.notify.success.content"),
					Notification.Type.TRAY_NOTIFICATION);
				notification.setStyleName(ValoTheme.NOTIFICATION_SUCCESS
					+ ' '
					+ ValoTheme.NOTIFICATION_TRAY);
				notification.setIcon(FontAwesome.TWITTER);
				notification.show(UI.getCurrent().getPage());

				onResponseSuccess.onResponse();
				return;
			default:
				setErrorMessage(messages.get("PostScheduledTweet.error.unknown"));
		}

	}


	/**
	 * @param text
	 */
	private void tweetComposeTextChanged(String text)
	{
		final List<String> tweets = schedulingController.previewTweet(text);

		preview.updatePreview(tweets);

		int c = 0;
		for (final String s : tweets)
		{
			c += s.length();
		}
		tweetComposeBox.updateTweetLength(c);
	}



	/**
	 *
	 */
	private static final long serialVersionUID = -3394439033887847317L;

	/** The CSS class name to apply to the error message. */
	private static final String ERROR_STYLENAME = "PostScheduledTweet-error";

	/** The CSS class name to apply to the Tweet button. */
	private static final String TWEET_BUTTON_STYLENAME =
		"PostScheduledTweet-tweet-button";

	/** The CSS class name to apply to the compose box. */
	private static final String COMPOSE_BOX_STYLENAME =
		"PostScheduledTweet-compose-box";

	/** The error message of a failed tweeting attempt. */
	private Label errorMessage;

	private TextField tweetName;

	private TweetComposeBox tweetComposeBox;

	private RepeatChooser repeatChooser;

	private TweetPreview preview;

	private final IScheduledTweetsController schedulingController;

	/** The on response success. */
	private final OnResponseSuccess onResponseSuccess;
}
