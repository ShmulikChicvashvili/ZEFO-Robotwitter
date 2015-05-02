/**
 *
 */

package com.robotwitter.webapp.view.automate;


import java.util.ArrayList;
import java.util.List;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Window;

import com.robotwitter.webapp.control.tools.ITweetingController;
import com.robotwitter.webapp.messages.IMessagesContainer;
import com.robotwitter.webapp.util.tweeting.TweetPreview;




/**
 * @author Eyal
 *
 */
public class TweetResponseWindow extends Window
{

	/**
	 * Instantiates a new tweet response window.
	 *
	 * @param messages
	 *            the message container
	 * @param tweetingController
	 *            A controller used for tweeting
	 */
	public TweetResponseWindow(
		IMessagesContainer messages,
		ITweetingController tweetingController)
	{
		this.messages = messages;
		preview = new TweetPreview(messages);
		offeredTweets = new Label();
		((Label) offeredTweets).setValue("Tweet Tweet Tweet");

		initializeLayout();

		// setContent(new TweetComposer(messages, tweetingController));
	}


	/**
	 * Initialize layout.
	 */
	private void initializeLayout()
	{
		setCloseShortcut(KeyCode.ESCAPE, null);
		setModal(true);
		center();
		setResizable(false);

		setCaption(messages.get("TweetResponseWindow.caption"));

		final HorizontalLayout layout =
			new HorizontalLayout(offeredTweets, preview);
		setContent(layout);

		final List<String> tweets = new ArrayList<>();
		tweets.add("1/3 response");
		tweets.add("2/3 response");
		tweets.add("3/3 response");
		preview.updatePreview(tweets);
	}



	/** The messages displayed by this view. */
	protected IMessagesContainer messages;

	/**
	 * The right panel of the layout. Holds the preview of the chosen
	 * possibility.
	 */
	private final TweetPreview preview;

	/**
	 * The left panel of the layout. Holds the different possibilities for
	 * tweets.
	 */
	private final Component offeredTweets;
}
