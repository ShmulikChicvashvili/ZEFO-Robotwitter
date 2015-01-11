
package com.robotwitter.webapp.view.tools;


import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import com.robotwitter.webapp.messages.IMessagesContainer;
import com.robotwitter.webapp.util.RobotwitterCustomComponent;




/**
 * Represents a Tweet composer that allows a user to compose a new Tweet while
 * seeing a preview of the results.
 *
 * @author Hagai Akibayov
 * @author Eyal Tolchinsky
 */
public class TweetComposer extends RobotwitterCustomComponent
{

	/**
	 * Instantiates a new Tweet composer.
	 *
	 * @param messages
	 *            the container of messages to display
	 */
	public TweetComposer(IMessagesContainer messages)
	{
		super(messages);
		initialiseLayout();
		getUserSession().observeActiveTwitterAccount(this);
	}
	
	
	@Override
	public final void activateTwitterAccount(long id)
	{
		updateTweet(tweet.getValue());
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
	
	
	/** Initialises the layout. */
	private void initialiseLayout()
	{
		initialiseTweet();
		
		Component toolbar = createToolbar();
		Button submit = new Button(messages.get("TweetComposer.button.tweet"));
		// submit.addShortcutListener(new ButtonClickOnEnterListener(
		// submit,
		// new ArrayList<AbstractComponent>(Arrays.asList(tweet))));

		tweet.setIcon(FontAwesome.PENCIL);
		submit.setIcon(FontAwesome.PAPER_PLANE);

		HorizontalLayout toolbarAndSubmit =
			new HorizontalLayout(toolbar, submit, charactersLeft);
		toolbarAndSubmit.setSizeFull();
		toolbarAndSubmit.setExpandRatio(toolbar, 2);
		toolbarAndSubmit.setExpandRatio(submit, 1);

		tweet.setCaption(messages.get("TweetComposer.caption.input-tweet"));
		
		VerticalLayout left = new VerticalLayout(tweet, toolbarAndSubmit);
		VerticalLayout right = new VerticalLayout();
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
	}
	
	
	/** Initialises the Tweet input text area. */
	private void initialiseTweet()
	{
		tweet = new TextArea();
		tweet.addTextChangeListener(event -> updateTweet(event.getText()));
		tweet.setValidationVisible(false);

		errorMessage = new Label();
		errorMessage.setVisible(false);
		errorMessage.setStyleName(ERROR_STYLENAME);

		charactersLeft = new Label();
		charactersLeft.setDescription(messages
			.get("TweetComposer.tooltip.characters"));
		updateTweet("");
	}
	
	
	/** Updates the component given the new Tweet's text. */
	private void updateTweet(String tweetText)
	{
		int max = 50;
		int count = tweetText.length();
		
		if (count > max)
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
			+ String.valueOf(count)
			+ "</span>/"
			+ String.valueOf(max));
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

	/** Serialisation version unique ID. */
	private static final long serialVersionUID = 1L;

	/** A label containing the amount of characters left. */
	private Label charactersLeft;
	
	/** The tweet's input text area. */
	private TextArea tweet;

	/** The error message of a failed tweeting attempt. */
	private Label errorMessage;

}
