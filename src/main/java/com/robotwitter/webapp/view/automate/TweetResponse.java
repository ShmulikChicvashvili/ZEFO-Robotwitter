/**
 *
 */

package com.robotwitter.webapp.view.automate;


import java.util.List;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.AbstractOrderedLayout;
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

import com.robotwitter.webapp.control.automate.ICannedTweetsController;
import com.robotwitter.webapp.control.automate.ICannedTweetsController.Status;
import com.robotwitter.webapp.control.general.Tweet;
import com.robotwitter.webapp.messages.IMessagesContainer;
import com.robotwitter.webapp.util.RobotwitterCustomComponent;
import com.robotwitter.webapp.util.tweeting.TweetComposeBox;
import com.robotwitter.webapp.util.tweeting.TweetPreview;




/**
 * Tweet.
 *
 * @author Eyal
 */
public class TweetResponse extends RobotwitterCustomComponent
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
	 * Instantiates a new tweet response component.
	 *
	 * @param messages
	 *            the messages
	 * @param cannedController
	 *            the canned controller
	 * @param originalTweet
	 *            the original tweet
	 * @param onResponseSuccess
	 *            the on response success
	 */
	public TweetResponse(
		IMessagesContainer messages,
		ICannedTweetsController cannedController,
		Tweet originalTweet,
		OnResponseSuccess onResponseSuccess)
	{
		super(messages);
		this.cannedController = cannedController;
		this.originalTweet = originalTweet;
		this.onResponseSuccess = onResponseSuccess;
		
		maxRadioCaptionLen = MAX_RADIO_CAPTION_LENGTH;
		
		maxTweetLen =
			getUserSession()
				.getAccountController()
				.getActiveTwitterAccount()
				.getCurrentMaximumTweetLength();
		
		responses = cannedController.getResponses(originalTweet.getID());
		
		preview = new TweetPreview();
		preview.setCustomFirstTweet(originalTweet);
		
		initializeOfferedResponses();
		initializeLayout();
		
		if (responseRadio.containsId(0))
		{
			responseRadio.setValue(0);
		} else
		{
			responseRadio.setValue(-1);
		}
	}
	
	
	/**
	 * Change response chosen with the radio buttons.
	 *
	 * @param event
	 *            the event
	 */
	@SuppressWarnings("boxing")
	public final void changeResponse(ValueChangeEvent event)
	{
		int chosen = (int) responseRadio.getValue();
		String tweet = getSelectedTweet();
		if (chosen >= 0)
		{
			tweetComposeBox.setEnabled(false);
			
			List<String> tweets = cannedController.previewTweet(tweet);
			preview.updatePreview(tweets);
		} else
		{
			tweetComposeBox.setEnabled(true);
			updateTweetComposeText(tweet);
		}
	}
	
	
	/**
	 * Submit click.
	 *
	 * @param event
	 *            the event
	 */
	public final void submitClick(ClickEvent event)
	{
		if (!validateTweet(cannedController.previewTweet(getSelectedTweet()))) { return; }
		
		Status status =
			cannedController.respondToTweet(getUserSession()
				.getAccountController()
				.getEmail(), originalTweet.getID(), getSelectedTweet());
		
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
	
	
	/**
	 * Adds the reply prefix.
	 *
	 * @param tweet
	 *            the tweet
	 * @return the string
	 */
	private String addReplyPrefix(String tweet)
	{
		return "@" + originalTweet.getScreenName() + " " + tweet;
	}
	
	
	/** Clears the displayed error message on the composer. */
	private void clearErrorMessage()
	{
		errorMessage.setVisible(false);
	}
	
	
	/**
	 * Gets the selected tweet.
	 *
	 * @return the selected tweet
	 */
	private String getSelectedTweet()
	{
		@SuppressWarnings("boxing")
		int chosen = (int) responseRadio.getValue();
		String $ = "";
		if (chosen >= 0)
		{
			$ = responses.get(chosen);
		} else
		{
			$ = tweetComposeBox.getText();
			
		}
		return addReplyPrefix($);
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
		
		AbstractOrderedLayout layout;
		if (isMobile())
		{
			layout = new VerticalLayout(left, preview);
		} else
		{
			layout = new HorizontalLayout(left, preview);
		}
		
		layout.setSpacing(true);
		
		layout.addStyleName(STYLENAME);
		
		if (isMobile())
		{
			layout.addStyleName(MOBILE_STYLENAME);
		}
		
		setCompositionRoot(layout);
	}
	
	
	/**
	 * Initialize offered responses.
	 */
	@SuppressWarnings("boxing")
	private void initializeOfferedResponses()
	{
		responseRadio =
			new OptionGroup(
				messages.get("TweetResponse.caption.offered-responses"));
		responseRadio.setMultiSelect(false);
		responseRadio.addStyleName(RADIO_STYLENAME);
		
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
		tweetComposeBox.addStyleName(COMPOSE_BOX_STYLENAME);
		tweetComposeBox.setSizeFull();
		
		offeredResponses = new VerticalLayout(responseRadio, tweetComposeBox);
		offeredResponses.setSizeFull();
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
		String $ = addReplyPrefix(text);
		updateTweetComposeText($);
		
	}
	
	
	/**
	 * Update tweet compose text.
	 *
	 * @param text
	 *            the text
	 */
	private void updateTweetComposeText(String text)
	{
		List<String> tweets = cannedController.previewTweet(text);
		
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
	 * @param tweets
	 *            the tweets
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
	
	
	
	/** The Constant MAX_RADIO_CAPTION_LENGTH. */
	private static final int MAX_RADIO_CAPTION_LENGTH = 50;
	
	/** The CSS class name to apply to this class. */
	private static final String STYLENAME = "TweetResponse";
	
	/** The CSS class name to apply to this class in mobile browsers. */
	private static final String MOBILE_STYLENAME = "TweetResponse-mobile";
	
	/** The CSS class name to apply to the error message. */
	private static final String ERROR_STYLENAME = "TweetResponse-error";
	
	/** The CSS class name to apply to the Tweet button. */
	private static final String TWEET_BUTTON_STYLENAME =
		"TweetResponse-tweet-button";
	
	/** The CSS class name to apply to the radio. */
	private static final String RADIO_STYLENAME = "TweetResponse-radio";
	
	/** The CSS class name to apply to the compose box. */
	private static final String COMPOSE_BOX_STYLENAME =
		"TweetResponse-compose-box";
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** Canned controller. */
	private ICannedTweetsController cannedController;
	
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
	
	/** The tweet compose box. */
	private TweetComposeBox tweetComposeBox;
	
	/** The max radio caption len. */
	private int maxRadioCaptionLen;
	
	/** The response radio. */
	private OptionGroup responseRadio;
	
	/** The responses. */
	private List<String> responses;
	
	/** The original tweet. */
	private Tweet originalTweet;
	
	/** The max tweet len. */
	private int maxTweetLen;
	
	/** The error message of a failed tweeting attempt. */
	private Label errorMessage;
	
	/** The on response success. */
	private OnResponseSuccess onResponseSuccess;
}
