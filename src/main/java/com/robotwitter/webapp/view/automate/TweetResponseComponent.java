/**
 *
 */

package com.robotwitter.webapp.view.automate;


import java.util.List;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import com.robotwitter.webapp.control.account.ITwitterAccountController;
import com.robotwitter.webapp.control.tools.ITweetingController;
import com.robotwitter.webapp.control.tools.tweeting.Tweet;
import com.robotwitter.webapp.messages.IMessagesContainer;
import com.robotwitter.webapp.util.RobotwitterCustomComponent;
import com.robotwitter.webapp.util.tweeting.TweetComposeBox;
import com.robotwitter.webapp.util.tweeting.TweetPreview;




/**
 * @author Eyal
 *
 */
public class TweetResponseComponent extends RobotwitterCustomComponent
{
	
	public interface OnResponseSuccess
	{
		public void onResponse();
	}
	
	
	
	/**
	 * Instantiates a new tweet response component.
	 *
	 * @param messages
	 *            the messages
	 * @param tweetingController
	 *            the tweeting controller
	 * @param originalTweet
	 *            the original tweet
	 */
	public TweetResponseComponent(
		IMessagesContainer messages,
		ITweetingController tweetingController,
		Tweet originalTweet,
		OnResponseSuccess onResponseSuccess)
	{
		super(messages);
		this.tweetingController = tweetingController;
		this.originalTweet = originalTweet;
		this.onResponseSuccess = onResponseSuccess;
		
		maxRadioCaptionLen = 20;
		
		maxTweetLen =
			getUserSession()
			.getAccountController()
			.getActiveTwitterAccount()
			.getCurrentMaximumTweetLength();
		
		responses = tweetingController.getOptionalResponses(originalTweet);
		
		preview = new TweetPreview(messages);
		
		initializeOfferedResponses();
		initializeLayout();
		
		responseRadio.setValue(0);
	}
	
	
	/**
	 * Change response chosen with the radio buttons.
	 *
	 * @param event
	 *            the event
	 */
	@SuppressWarnings("boxing")
	public void changeResponse(ValueChangeEvent event)
	{
		// TODO Auto-generated method stub
		int chosen = (int) responseRadio.getValue();
		String tweet = getSelectedTweet();
		if (chosen >= 0)
		{
			tweetComposeBox.setEnabled(false);
			
			List<String> tweets = tweetingController.previewTweet(tweet);
			preview.updatePreview(tweets);
		} else
		{
			tweetComposeBox.setEnabled(true);
			tweetComposeTextChanged(tweet);
		}
	}
	
	
	public final void submitClick(ClickEvent event)
	{
		String tweet = getSelectedTweet();
		List<String> tweets = tweetingController.breakTweet(tweet);
		if (!validateTweet(tweets)) { return; }
		clearErrorMessage();
		ITwitterAccountController controller =
			getUserSession().getAccountController().getActiveTwitterAccount();
		ITwitterAccountController.Status status =
			controller.postTweetsAsSingleResponseTweet(
				originalTweet.getId(),
				tweets);
		
		switch (status)
		{
			case SUCCESS:
				Notification notification =
				new Notification(
					messages.get("TweetResponse.notify.success.title"),
					messages.get("TweetResponse.notify.success.content"),
					Notification.Type.TRAY_NOTIFICATION);
				notification.setStyleName(ValoTheme.NOTIFICATION_SUCCESS
					+ ' '
					+ ValoTheme.NOTIFICATION_TRAY);
				notification.setIcon(FontAwesome.TWITTER);
				notification.show(UI.getCurrent().getPage());
				
				onResponseSuccess.onResponse();
				return;
				
			default:
				setErrorMessage(messages.get("TweetResponse.error.unknown"));
		}
	}
	
	
	/** Clears the displayed error message on the composer. */
	private void clearErrorMessage()
	{
		errorMessage.setVisible(false);
	}
	
	
	private String getSelectedTweet()
	{
		@SuppressWarnings("boxing")
		int chosen = (int) responseRadio.getValue();
		if (chosen >= 0) { return responses.get(chosen); }
		
		return tweetComposeBox.getText();
	}
	
	
	/**
	 * Initialize layout.
	 */
	private void initializeLayout()
	{
		Button submit =
			new Button(
				messages.get("TweetResponse.button.tweet"),
				event -> submitClick(event));
		submit.setIcon(FontAwesome.PAPER_PLANE);
		submit.addStyleName(TWEET_BUTTON_STYLENAME);
		submit.addStyleName(ValoTheme.BUTTON_PRIMARY);
		
		errorMessage = new Label();
		errorMessage.setVisible(false);
		errorMessage.setStyleName(ERROR_STYLENAME);
		
		VerticalLayout left =
			new VerticalLayout(offeredResponses, submit, errorMessage);
		
		final HorizontalLayout layout = new HorizontalLayout(left, preview);
		
		setCompositionRoot(layout);
	}
	
	
	@SuppressWarnings("boxing")
	private void initializeOfferedResponses()
	{
		responseRadio =
			new OptionGroup(
				messages.get("TweetResponse.caption.offered-responses"));
		responseRadio.setMultiSelect(false);
		
		for (int i = 0; i < responses.size(); i++)
		{
			responseRadio.addItem(i);
			String caption = responses.get(i);
			if (caption.length() > maxRadioCaptionLen)
			{
				caption = caption.substring(0, maxRadioCaptionLen) + "...";
			}
			responseRadio.setItemCaption(i, caption);
		}
		
		int otherId = -1;
		responseRadio.addItem(otherId);
		responseRadio.setItemCaption(
			otherId,
			messages.get("TweetResponse.radio.other"));
		
		responseRadio.addValueChangeListener(event -> changeResponse(event));
		
		tweetComposeBox =
			new TweetComposeBox(
				messages,
				event -> tweetComposeTextChanged(event.getText()));
		
		offeredResponses = new VerticalLayout(responseRadio, tweetComposeBox);
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
	 * Update tweet.
	 *
	 * @param text
	 *            the text
	 */
	private void tweetComposeTextChanged(String text)
	{
		List<String> tweets = tweetingController.previewTweet(text);
		
		preview.updatePreview(tweets);
		
		int c = 0;
		for (String s : tweets)
		{
			c += s.length();
		}
		tweetComposeBox.updateTweetLength(c);
	}
	
	
	/**
	 * Validates the Tweet.
	 *
	 * @return <code>true</code> if the given field is valid, <code>false</code>
	 *         otherwise
	 */
	private boolean validateTweet(List<String> tweets)
	{
		int c = 0;
		for (String t : tweets)
		{
			c += t.length();
		}
		// Check emptiness
		if (c == 0)
		{
			setErrorMessage(messages.get("TweetResponse.error.tweet-empty"));
			return false;
		}
		
		// Validate
		if (c > maxTweetLen)
		{
			setErrorMessage(messages.get("TweetResponse.error.tweet-too-long"));
			return false;
		}
		
		return true;
	}
	
	
	
	/** The CSS class name to apply to the error message. */
	private static final String ERROR_STYLENAME = "TweetComposer-error";
	
	/** The CSS class name to apply to the Tweet button. */
	private static final String TWEET_BUTTON_STYLENAME =
		"TweetComposer-tweet-button";
	
	/**
	 *
	 */
	private static final long serialVersionUID = 7559345052989581150L;
	
	ITweetingController tweetingController;
	
	/**
	 * The right panel of the layout. Holds the preview of the chosen
	 * possibility.
	 */
	private final TweetPreview preview;
	
	/**
	 * The left panel of the layout. Holds the different possibilities for
	 * tweets.
	 */
	private Component offeredResponses;
	
	private TweetComposeBox tweetComposeBox;
	
	private int maxRadioCaptionLen;
	
	private OptionGroup responseRadio;
	
	private List<String> responses;
	
	private Tweet originalTweet;
	
	private int maxTweetLen;
	
	/** The error message of a failed tweeting attempt. */
	private Label errorMessage;
	
	private OnResponseSuccess onResponseSuccess;
}
