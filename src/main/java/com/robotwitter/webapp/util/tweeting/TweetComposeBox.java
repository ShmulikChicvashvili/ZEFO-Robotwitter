/**
 *
 */

package com.robotwitter.webapp.util.tweeting;


import java.io.Serializable;

import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import com.robotwitter.webapp.control.account.ITwitterAccountController;
import com.robotwitter.webapp.messages.IMessagesContainer;
import com.robotwitter.webapp.util.RobotwitterCustomComponent;




/**
 * @author Eyal
 *
 */
public class TweetComposeBox extends RobotwitterCustomComponent
{

	/** Represents a Tweet's character count. */
	static class CharacterCount implements Serializable
	{
		/** Serialisation version unique ID. */
		private static final long serialVersionUID = 1L;

		/** The count. */
		public int count;
	}



	/**
	 * Instantiates a new tweet compose box.
	 *
	 * @param messages
	 *            the messages
	 * @param textChangeListener
	 *            the text change listener
	 */
	public TweetComposeBox(
		IMessagesContainer messages,
		TextChangeListener textChangeListener)
	{
		super(messages);
		this.textChangeListener = textChangeListener;

		count = new CharacterCount();
		max = new CharacterCount();

		initialiseLayout();
		getUserSession().observeActiveTwitterAccount(this);
		updateBasedOnActiveTwitterAccount();
	}


	@Override
	public final void activateTwitterAccount(long id)
	{
		updateBasedOnActiveTwitterAccount();
	}


	/**
	 * @return
	 */
	public String getText()
	{
		return tweet.getValue();
	}


	public void updateTweetLength(int length)
	{
		count.count = length;
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


	/**
	 *
	 */
	private void createTweetTextArea()
	{
		tweet = new TextArea();
		tweet.setRows(3);
		tweet.addTextChangeListener(event -> {
			updateTweet(event.getText());
			textChangeListener.textChange(event);
		});
		tweet.setValidationVisible(false);
		tweet.setIcon(FontAwesome.PENCIL);
		tweet.setCaption(messages.get("TweetComposer.caption.input-tweet"));

		tweet.addStyleName(TWEET_INPUT_STYLENAME);
	}


	/**
	 * Initialise layout.
	 */
	private void initialiseLayout()
	{
		createTweetTextArea();
		Component toolbar = createToolbar();

		charactersLeft = new Label();
		charactersLeft.addStyleName(CHARACTERS_STYLENAME);
		charactersLeft.setDescription(messages
			.get("TweetComposer.tooltip.characters"));

		HorizontalLayout toolbarAndCharactersLeft =
			new HorizontalLayout(toolbar, charactersLeft);
		toolbarAndCharactersLeft.setSizeFull();
		toolbarAndCharactersLeft.addStyleName(TOOLBAR_AND_CHARS_LEFT_STYLENAME);

		VerticalLayout layout =
			new VerticalLayout(tweet, toolbarAndCharactersLeft);
		layout.setSizeFull();
		layout.setSpacing(true);

		setCompositionRoot(layout);
	}


	/**
	 * @param text
	 */

	/** Updates state based on the active Twitter account. */
	private void updateBasedOnActiveTwitterAccount()
	{
		ITwitterAccountController controller =
			getUserSession().getAccountController().getActiveTwitterAccount();

		max.count = controller.getCurrentMaximumTweetLength();
	}



	/**
	 * Update tweet.
	 *
	 * @param text
	 *            the text
	 */
	private void updateTweet(String text)
	{
		// resize textarea to fit text
		String[] lines = text.split("\r\n|\r|\n");
		tweet.setRows(lines.length + 2);
	}

	/**
	 *
	 */
	private static final long serialVersionUID = -2166319252234636636L;

	/** The CSS class name to apply to the toolbar and button wrapper. */
	private static final String TOOLBAR_AND_CHARS_LEFT_STYLENAME =
		"TweetComposer-toolbar-and-button";

	/** The CSS class name to apply to the characters count label. */
	private static final String CHARACTERS_STYLENAME =
		"TweetComposer-characters";

	/** The CSS class name to apply to the char count when it's overflown . */
	private static final String CHARACTERS_OVERFLOWN_STYLENAME =
		"TweetComposer-characters-overflown";

	/** The CSS class name to apply to Tweet input text area. */
	private static final String TWEET_INPUT_STYLENAME =
		"TweetComposer-input-textarea";

	/** The CSS class name to apply to the toolbar of the Tweet text area. */
	private static final String TOOLBAR_STYLENAME = "TweetComposer-toolbar";

	TextChangeListener textChangeListener;

	/** A label containing the amount of characters left. */
	private Label charactersLeft;

	/** The tweet's input text area. */
	private TextArea tweet;

	/** The current character count of the Tweet. */
	CharacterCount count;



	/** The maximum allowed character count of the Tweet. */
	CharacterCount max;
}
