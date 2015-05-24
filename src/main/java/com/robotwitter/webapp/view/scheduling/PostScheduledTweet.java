/**
 *
 */

package com.robotwitter.webapp.view.scheduling;


import java.util.List;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import com.robotwitter.webapp.control.scheduling.IScheduledTweetsController;
import com.robotwitter.webapp.messages.IMessagesContainer;
import com.robotwitter.webapp.util.RobotwitterCustomComponent;
import com.robotwitter.webapp.util.tweeting.RepeatChooser;
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

		maxTweetLen =
			getUserSession()
				.getAccountController()
				.getActiveTwitterAccount()
				.getCurrentMaximumTweetLength();

		initializeLayout();
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
	 * @param event
	 */
	private void submitClick(ClickEvent event)
	{
		onResponseSuccess.onResponse();
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

	private final int maxTweetLen;

	private TweetComposeBox tweetComposeBox;

	private RepeatChooser repeatChooser;

	private TweetPreview preview;

	private final IScheduledTweetsController schedulingController;

	/** The on response success. */
	private final OnResponseSuccess onResponseSuccess;
}
