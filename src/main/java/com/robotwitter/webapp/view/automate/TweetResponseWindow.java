/**
 *
 */

package com.robotwitter.webapp.view.automate;


import java.util.ArrayList;
import java.util.List;

import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import com.robotwitter.webapp.control.tools.ITweetingController;
import com.robotwitter.webapp.messages.IMessagesContainer;
import com.robotwitter.webapp.util.tweeting.TweetPreview;
import com.robotwitter.webapp.view.tools.TweetComposer;




/**
 * @author Eyal
 *
 */
public class TweetResponseWindow extends Window
{
	public TweetResponseWindow(
		IMessagesContainer messages,
		ITweetingController tweetingController)
	{
		this.messages = messages;

		setCaption(messages.get("TweetResponseWindow.caption"));

		final List<String> tweets = new ArrayList<>();
		tweets.add("Tweet 1");
		tweets.add("Tweet 2");
		tweets.add("Tweet 3");

		preview = new TweetPreview(messages);
		final VerticalLayout layout = new VerticalLayout(preview);
		setContent(layout);
		preview.updatePreview(tweets);

		setContent(new TweetComposer(messages, tweetingController));
	}



	/** The messages displayed by this view. */
	protected IMessagesContainer messages;

	private final TweetPreview preview;
}
