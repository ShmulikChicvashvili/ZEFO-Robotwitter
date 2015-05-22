
package com.robotwitter.webapp.view.tools;


import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.StringEscapeUtils;

import com.vaadin.data.validator.AbstractStringValidator;
import com.vaadin.server.BrowserWindowOpener;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.UserError;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import com.robotwitter.webapp.control.account.ITwitterAccountController;
import com.robotwitter.webapp.control.tools.ITweetingController;
import com.robotwitter.webapp.messages.IMessagesContainer;
import com.robotwitter.webapp.util.RobotwitterCustomComponent;
import com.robotwitter.webapp.view.login.PasswordRetrievalWindow;




/**
 * Represents a Tweet composer that allows a user to compose a new Tweet while
 * seeing a preview of the results.
 *
 * @author Hagai Akibayov
 * @author Eyal Tolchinsky
 * 
 * EDITED by Amir Drutin 8/5/15
 */
public class TweetComposer extends RobotwitterCustomComponent
implements
Button.ClickListener
{

	/** Represents a Tweet's character count. */
	static class CharacterCount implements Serializable
	{
		/** The count. */
		public int count;

		/** Serialisation version unique ID. */
		private static final long serialVersionUID = 1L;
	}
	
	
	
	/** Represents a validator of a Tweet. */
	static class TweetValidator extends AbstractStringValidator
	{

		/**
		 * Instantiates a new tweet validator.
		 *
		 * @param tooLongError
		 *            the error message to display when the Tweet is too long
		 * @param count
		 *            the character count that is updated outside
		 * @param max
		 *            the maximum allowed character count
		 */
		public TweetValidator(
			String tooLongError,
			CharacterCount count,
			CharacterCount max)
		{
			super(tooLongError);
			this.count = count;
			this.max = max;
		}
		
		
		@Override
		protected final boolean isValidValue(final String input)
		{
			if (input.isEmpty()) { return true; }

			if (count.count > max.count) { return false; }
			
			return true;
		}



		/** The current character count. */
		CharacterCount count;
		
		/** The maximum allowed character count. */
		CharacterCount max;
		
		/** Serialisation version unique ID. */
		private static final long serialVersionUID = 1L;
	}



	/**
	 * Converts all the hashtags in the given string to HTML anchor links.
	 * <p>
	 * For example, if the hashtag #dussan exists in the given string, it will
	 * be converted to an HTML anchor linking to:
	 * https://twitter.com/hashtag/dussan
	 *
	 * @param string
	 *            the input string to convert
	 *
	 * @return the converted string
	 */
	private static String hashtagsToTwitterHtmlLinks(String string)
	{
		String converted = string;
		while (true)
		{
			converted =
				string.replaceAll(
					"(^|\\s)#(\\w*[a-zA-Z_]+\\w*)($|\\s)",
					"$1<a class=\"hashtag\" href=\"http://twitter.com/hashtag/$2\""
						+ " target=\"_blank\" >"
						+ "#<span>$2</span></a>$3");
			if (converted.equals(string))
			{
				break;
			}
			string = converted;
		}

		return converted;
	}
	
	
	/**
	 * Instantiates a new Tweet composer.
	 *
	 * @param messages
	 *            the container of messages to display
	 * @param tweetingController
	 *            the tweeting controller
	 */
	public TweetComposer(
		IMessagesContainer messages,
		ITweetingController tweetingController)
	{
		super(messages);

		this.tweetingController = tweetingController;
		count = new CharacterCount();
		max = new CharacterCount();
		
		initialiseLayout();
		getUserSession().observeActiveTwitterAccount(this);
	}


	@Override
	public final void activateTwitterAccount(long id)
	{
		updateBasedOnActiveTwitterAccount();
		updateTweet(tweet.getValue());
	}


	@Override
	public final void buttonClick(ClickEvent event)
	{
		if (!validateTweet()) { return; }
		clearErrorMessage();
		List<String> tweets = tweetingController.breakTweet(tweet.getValue());
		ITwitterAccountController controller =
			getUserSession().getAccountController().getActiveTwitterAccount();
		ITwitterAccountController.Status status =
			controller.postTweetsAsSingleTweet(tweets);

		switch (status)
		{
			case SUCCESS:
				Notification notification =
				new Notification(
					messages.get("TweetComposer.notify.success.title"),
					messages.get("TweetComposer.notify.success.content"),
					Notification.Type.TRAY_NOTIFICATION);
				notification.setStyleName(ValoTheme.NOTIFICATION_SUCCESS
					+ ' '
					+ ValoTheme.NOTIFICATION_TRAY);
				notification.setIcon(FontAwesome.TWITTER);
				notification.show(UI.getCurrent().getPage());
				
				left.removeAllComponents();
				Button button =
					new Button(
						messages.get("TweetComposer.button.compose-another"),
						e -> navigate(ToolsView.NAME));
				button.setIcon(FontAwesome.PENCIL);
				button.addStyleName(COMPOSE_ANOTHER_STYLENAME);
				left.addComponent(button);
				left.setComponentAlignment(button, Alignment.MIDDLE_CENTER);
				return;

			default:
				setErrorMessage(messages.get("TweetComposer.error.unknown"));
		}
	}
	
	
	/** Clears the displayed error message on the composer. */
	private void clearErrorMessage()
	{
		errorMessage.setVisible(false);
		tweet.setComponentError(null);
		tweet.setValidationVisible(false);
	}
	
	
	/** @return A newly created toolbar for the Tweet input text area. */
	private Component createToolbar()
	{
		MenuBar toolbar = new MenuBar();

		MenuItem addPhoto = toolbar.addItem("", FontAwesome.CAMERA, null);
		MenuItem addLink = toolbar.addItem("", FontAwesome.LINK, null);
		addPhoto.setEnabled(false);
		addLink.setEnabled(false);
		
		addPhoto
		.setDescription(messages.get("TweetComposer.caption.add-photo"));
		addLink.setDescription(messages.get("TweetComposer.caption.add-link"));

		toolbar.setSizeFull();
		
		toolbar.addStyleName(TOOLBAR_STYLENAME);
		toolbar.addStyleName(ValoTheme.MENUBAR_BORDERLESS);

		return toolbar;
	}
	
	/** Initialises the settings component. */
	private void initialiseComposerSettings()
	{
		composerSettings =
			new ComposerSettings(messages, tweetingController);
	}
	
	/** Initialises the layout. */
	private void initialiseLayout()
	{
		
		initialiseTweet();
		initialisePreview();
		updateBasedOnActiveTwitterAccount();
		updateTweet("");
		
		Component toolbar = createToolbar();
		Button submit =
			new Button(messages.get("TweetComposer.button.tweet"), this);

		tweet.setIcon(FontAwesome.PENCIL);
		submit.setIcon(FontAwesome.PAPER_PLANE);

		HorizontalLayout toolbarAndSubmit =
			new HorizontalLayout(toolbar, submit, charactersLeft);
		toolbarAndSubmit.setSizeFull();
		toolbarAndSubmit.setExpandRatio(toolbar, 2);
		toolbarAndSubmit.setExpandRatio(submit, 1);

		tweet.setCaption(messages.get("TweetComposer.caption.input-tweet"));
		
		left = new VerticalLayout(tweet, errorMessage, toolbarAndSubmit);
		VerticalLayout right = new VerticalLayout(preview);
		HorizontalLayout layout = new HorizontalLayout(left, right);
		left.setSizeFull();
		right.setSizeFull();
		layout.setSizeFull();
		left.setSpacing(true);
		right.setSpacing(true);
		layout.setSpacing(true);
		
		tweet.addStyleName(TWEET_INPUT_STYLENAME);
		charactersLeft.addStyleName(CHARACTERS_STYLENAME);
		submit.addStyleName(TWEET_BUTTON_STYLENAME);
		submit.addStyleName(ValoTheme.BUTTON_PRIMARY);
		toolbarAndSubmit.addStyleName(TOOLBAR_AND_BUTTON_STYLENAME);
		left.addStyleName(LEFT_STYLENAME);
		right.addStyleName(RIGHT_STYLENAME);
		addStyleName(STYLENAME);

		setCompositionRoot(layout);

		tweet.focus();
	}
	
	
	/** Initialises the Tweet preview component. */
	private void initialisePreview()
	{
		preview = new VerticalLayout();
		preview.setCaption(messages.get("TweetComposer.caption.preview"));
		preview.setIcon(FontAwesome.PAPER_PLANE_O);
		preview.addStyleName(PREVIEW_STYLENAME);
	}
	
	
	/** Initialises the Tweet input text area. */
	private void initialiseTweet()
	{
		tweet = new TextArea();
		tweet.setRows(3);
		tweet.addTextChangeListener(event -> updateTweet(event.getText()));
		tweet.setValidationVisible(false);

		errorMessage = new Label();
		errorMessage.setVisible(false);
		errorMessage.setStyleName(ERROR_STYLENAME);

		charactersLeft = new Label();
		charactersLeft.setDescription(messages
			.get("TweetComposer.tooltip.characters"));
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
		
		// Set the error message on the field
		tweet.setValidationVisible(true);
		tweet.setCursorPosition(tweet.getValue().length());
		if (tweet.getErrorMessage() == null)
		{
			tweet.setComponentError(new UserError(error));
		}
	}


	/** Updates state based on the active Twitter account. */
	private void updateBasedOnActiveTwitterAccount()
	{
		ITwitterAccountController controller =
			getUserSession().getAccountController().getActiveTwitterAccount();

		picture = controller.getImage();
		name = controller.getName();
		screenname = controller.getScreenname();

		max.count = controller.getCurrentMaximumTweetLength();
	}


	/**
	 * Updates the preview component.
	 *
	 * @param tweets
	 *            the tweets to preview
	 */
	private void updatePreview(List<String> tweets)
	{
		preview.removeAllComponents();
		for (String tweetText : tweets)
		{
			preview.addComponent(createTweetPreview(tweetText));
		}

		if (tweets.isEmpty())
		{
			preview.addComponent(createTweetPreview(""));
		}
	}


	/**
	 * Updates the component given the new Tweet's text.
	 *
	 * @param tweetText
	 *            the Tweet's text
	 */
	private void updateTweet(String tweetText)
	{
		List<String> tweets = tweetingController.previewTweet(tweetText);
		updatePreview(tweets);

		count.count = 0;
		for (String brokenTweet : tweets)
		{
			count.count += brokenTweet.length();
		}

		tweet.removeAllValidators();
		tweet.addValidator(new TweetValidator(messages
			.get("TweetComposer.error.tweet-too-long"), count, max));
		
		if (count.count > max.count)
		{
			charactersLeft.addStyleName(CHARACTERS_OVERFLOWN_STYLENAME);
		} else
		{
			charactersLeft.removeStyleName(CHARACTERS_OVERFLOWN_STYLENAME);
		}

		charactersLeft.setContentMode(ContentMode.HTML);
		charactersLeft.setValue(messages
			.get("TweetComposer.caption.characters")
			+ ": <span class=\"count\">"
			+ String.valueOf(count.count)
			+ "</span>/"
			+ String.valueOf(max.count));
		
		// resize textarea to fit text
		String[] lines = tweetText.split("\r\n|\r|\n");
		tweet.setRows(lines.length + 2);
	}
	
	
	/**
	 * Validates the Tweet.
	 *
	 * @return <code>true</code> if the given field is valid, <code>false</code>
	 *         otherwise
	 */
	private boolean validateTweet()
	{
		// Check emptiness
		if (tweet.getValue().isEmpty())
		{
			setErrorMessage(messages.get("TweetComposer.error.tweet-empty"));
			return false;
		}

		// Validate
		if (!tweet.isValid())
		{
			setErrorMessage(messages.get("TweetComposer.error.tweet-too-long"));
			return false;
		}

		return true;
	}


	/**
	 * Creates a tweet preview component.
	 *
	 * @param tweetText
	 *            the tweet's text
	 *
	 * @return the tweet preview component
	 */
	final Component createTweetPreview(String tweetText)
	{
		Image pictureImage = new Image();
		pictureImage.setSource(new ExternalResource(picture));
		pictureImage.setAlternateText(name);
		Button nameButton = new Button(name);
		Label screennameLabel = new Label('@' + screenname);
		
		String tweetHtml = StringEscapeUtils.escapeHtml4(tweetText);

		tweetHtml = hashtagsToTwitterHtmlLinks(tweetHtml);
		
		Label text = new Label(tweetHtml, ContentMode.HTML);
		
		BrowserWindowOpener opener =
			new BrowserWindowOpener("https://twitter.com/" + screenname);
		opener.extend(nameButton);
		
		HorizontalLayout nameAndScreenname =
			new HorizontalLayout(nameButton, screennameLabel);
		VerticalLayout right = new VerticalLayout(nameAndScreenname, text);
		HorizontalLayout layout = new HorizontalLayout(pictureImage, right);

		nameAndScreenname.setSizeFull();
		right.setSizeFull();
		layout.setSizeFull();
		layout.setExpandRatio(right, 1);

		layout.setSpacing(true);

		pictureImage.addStyleName(PREVIEW_PICTURE_STYLENAME);
		nameButton.addStyleName(PREVIEW_NAME_STYLENAME);
		nameButton.addStyleName(ValoTheme.BUTTON_LINK);
		screennameLabel.addStyleName(PREVIEW_SCREENNAME_STYLENAME);
		text.addStyleName(PREVIEW_TEXT_STYLENAME);
		layout.setStyleName(PREVIEW_TWEET_STYLENAME);
		
		return layout;
	}

	
	/** The CSS class name to apply to this component. */
	private static final String STYLENAME = "TweetComposer";
	
	/** The CSS class name to apply to Tweet input text area. */
	private static final String TWEET_INPUT_STYLENAME =
		"TweetComposer-input-textarea";
	
	/** The CSS class name to apply to the left side. */
	private static final String LEFT_STYLENAME = "TweetComposer-left";
	
	/** The CSS class name to apply to the right side. */
	private static final String RIGHT_STYLENAME = "TweetComposer-right";
	
	/** The CSS class name to apply to the toolbar and button wrapper. */
	private static final String TOOLBAR_AND_BUTTON_STYLENAME =
		"TweetComposer-toolbar-and-button";
	
	/** The CSS class name to apply to the toolbar of the Tweet text area. */
	private static final String TOOLBAR_STYLENAME = "TweetComposer-toolbar";
	
	/** The CSS class name to apply to the Tweet button. */
	private static final String TWEET_BUTTON_STYLENAME =
		"TweetComposer-tweet-button";
	
	/** The CSS class name to apply to the characters count label. */
	private static final String CHARACTERS_STYLENAME =
		"TweetComposer-characters";
	
	/** The CSS class name to apply to the char count when it's overflown . */
	private static final String CHARACTERS_OVERFLOWN_STYLENAME =
		"TweetComposer-characters-overflown";

	/** The CSS class name to apply to the error message. */
	private static final String ERROR_STYLENAME = "TweetComposer-error";

	/** The CSS class name to apply to the preview component. */
	private static final String PREVIEW_STYLENAME = "TweetComposer-preview";
	
	/** The CSS class name to apply to a tweet in the preview. */
	private static final String PREVIEW_TWEET_STYLENAME =
		"TweetComposer-preview-tweet";
	
	/** The CSS class name to apply to a tweet's picture in the preview. */
	private static final String PREVIEW_PICTURE_STYLENAME =
		"TweetComposer-preview-picture";

	/** The CSS class name to apply to a tweet's name in the preview. */
	private static final String PREVIEW_NAME_STYLENAME =
		"TweetComposer-preview-name";

	/** The CSS class name to apply to a tweet's screenname in the preview. */
	private static final String PREVIEW_SCREENNAME_STYLENAME =
		"TweetComposer-preview-screenname";

	/** The CSS class name to apply to a tweet's text in the preview. */
	private static final String PREVIEW_TEXT_STYLENAME =
		"TweetComposer-preview-text";

	/** The CSS class name to apply to the compose another button. */
	private static final String COMPOSE_ANOTHER_STYLENAME =
		"TweetComposer-compose-another";

	/** Serialisation version unique ID. */
	private static final long serialVersionUID = 1L;

	/** A label containing the amount of characters left. */
	private Label charactersLeft;
	
	/** The tweet's input text area. */
	private TextArea tweet;

	/** The error message of a failed tweeting attempt. */
	private Label errorMessage;

	/** The Composer Settings component. */
	private ComposerSettings composerSettings;
	
	/** The Tweet preview component. */
	private VerticalLayout preview;
	
	/** The current character count of the Tweet. */
	CharacterCount count;
	
	/** The maximum allowed character count of the Tweet. */
	CharacterCount max;
	
	/** The tweeting controller. */
	ITweetingController tweetingController;
	
	/** The active Twitter follower's picture image. */
	String picture;
	
	/** The active Twitter follower's name label. */
	String name;
	
	/** The active Twitter follower's screenname label. */
	String screenname;
	
	/** Contains the left side of this component (without the preview). */
	private VerticalLayout left;
}
