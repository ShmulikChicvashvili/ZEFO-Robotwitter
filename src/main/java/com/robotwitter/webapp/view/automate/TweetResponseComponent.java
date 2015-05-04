/**
 *
 */

package com.robotwitter.webapp.view.automate;


import java.util.List;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.VerticalLayout;

import com.robotwitter.webapp.control.tools.ITweetingController;
import com.robotwitter.webapp.control.tools.tweeting.Tweet;
import com.robotwitter.webapp.messages.IMessagesContainer;
import com.robotwitter.webapp.util.RobotwitterCustomComponent;
import com.robotwitter.webapp.util.tweeting.TweetPreview;




/**
 * @author Eyal
 *
 */
public class TweetResponseComponent extends RobotwitterCustomComponent
implements
ValueChangeListener
{

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
		Tweet originalTweet)
	{
		super(messages);
		this.tweetingController = tweetingController;
		this.originalTweet = originalTweet;

		responses = tweetingController.getOptionalResponses(originalTweet);

		preview = new TweetPreview(messages);

		initializeOfferedResponses();
		initializeLayout();
	}


	/* (non-Javadoc) @see
	 * com.vaadin.data.Property.ValueChangeListener#valueChange
	 * (com.vaadin.data.Property.ValueChangeEvent) */
	@Override
	public void valueChange(ValueChangeEvent event)
	{
		// TODO Auto-generated method stub

		@SuppressWarnings("boxing")
		List<String> tweets =
		tweetingController.previewTweet(responses.get((int) responseRadio
			.getValue()));

		preview.updatePreview(tweets);
	}


	/**
	 * Initialize layout.
	 */
	private void initializeLayout()
	{
		final HorizontalLayout layout =
			new HorizontalLayout(offeredResponses, preview);

		setCompositionRoot(layout);
	}


	@SuppressWarnings("boxing")
	private void initializeOfferedResponses()
	{
		responseRadio =
			new OptionGroup(
				messages.get("TweetResponse.caption.offered-responses"));
		responseRadio.setMultiSelect(false);

		offeredResponses = new VerticalLayout(responseRadio);

		for (int i = 0; i < responses.size(); i++)
		{
			responseRadio.addItem(i);
			responseRadio.setItemCaption(i, responses.get(i));
		}

		responseRadio.addListener(this);
	}



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

	private OptionGroup responseRadio;

	private List<String> responses;

	private Tweet originalTweet;

}
