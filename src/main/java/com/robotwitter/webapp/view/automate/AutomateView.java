
package com.robotwitter.webapp.view.automate;


import java.util.Arrays;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import com.robotwitter.webapp.messages.IMessagesContainer;
import com.robotwitter.webapp.util.tweeting.TweetPreview;
import com.robotwitter.webapp.view.AbstractView;




/** Automate view. */
public class AutomateView extends AbstractView
{
	
	/**
	 * Instantiates a new automate view.
	 *
	 * @param messages
	 *            the container of messages to display
	 */
	@Inject
	public AutomateView(@Named(NAME) IMessagesContainer messages)
	{
		super(messages, messages.get("AutomateView.page.title"));
	}
	
	
	@Override
	public final boolean isSignedInProhibited()
	{
		return false;
	}
	
	
	@Override
	public final boolean isSignedInRequired()
	{
		return true;
	}


	/**
	 * Create a tweet preview with respond and delete buttons.
	 *
	 * @param text
	 *            the tweet's text
	 *
	 * @return the newly created tweet
	 */
	private Component createTweet(String text)
	{
		TweetPreview preview = new TweetPreview();
		preview.updatePreview(Arrays.asList(text));
		
		Button respond =
			new Button(messages.get("AutomateView.button.respond"));
		Button delete = new Button(messages.get("AutomateView.button.delete"));
		HorizontalLayout buttons = new HorizontalLayout(respond, delete);
		
		VerticalLayout tweet = new VerticalLayout(preview, buttons);
		
		respond.addStyleName(ValoTheme.BUTTON_SMALL);
		delete.addStyleName(ValoTheme.BUTTON_SMALL);

		respond.addStyleName(RESPOND_STYLENAME);
		delete.addStyleName(DELETE_STYLENAME);
		buttons.addStyleName(BUTTONS_STYLENAME);
		tweet.addStyleName(TWEET_STYLENAME);

		return tweet;
	}


	/** @return new Tweets for canned-response. */
	private Component createTweets()
	{
		Component t1 = createTweet("Tweet 1 #YOLO");
		Component t2 = createTweet("Tweet 2 #Swag");
		Component t3 = createTweet("Tweet 3 #Fuck");

		VerticalLayout tweets = new VerticalLayout(t1, t2, t3);
		tweets.setSpacing(true);
		
		tweets.addStyleName(TWEETS_STYLENAME);
		
		return tweets;
	}


	@Override
	protected final void initialise()
	{
		Label header = new Label(messages.get("AutomateView.label.header"));
		Label desc = new Label(messages.get("AutomateView.label.description"));
		Component tweets = createTweets();

		VerticalLayout layout = new VerticalLayout(header, desc, tweets);
		layout.setWidth("100%");

		header.addStyleName(HEADER_STYLENAME);
		desc.addStyleName(DESC_STYLENAME);
		setStyleName(STYLENAME);
		
		setCompositionRoot(layout);
	}



	/** The view's name. */
	public static final String NAME = "automate";

	/** The CSS class name to apply to this view. */
	private static final String STYLENAME = "AutomateView";
	
	/** The CSS class name to apply to the header component. */
	private static final String HEADER_STYLENAME = "AutomateView-header";
	
	/** The CSS class name to apply to the description component. */
	private static final String DESC_STYLENAME = "AutomateView-desc";
	
	/** The CSS class name to apply to the tweets component. */
	private static final String TWEETS_STYLENAME = "AutomateView-tweets";
	
	/** The CSS class name to apply to a tweet component. */
	private static final String TWEET_STYLENAME = "AutomateView-tweet";
	
	/** The CSS class name to apply to a buttons wrapper. */
	private static final String BUTTONS_STYLENAME = "AutomateView-buttons";
	
	/** The CSS class name to apply to a respond button. */
	private static final String RESPOND_STYLENAME = "AutomateView-respond";
	
	/** The CSS class name to apply to a delete button. */
	private static final String DELETE_STYLENAME = "AutomateView-delete";

	/** Serialisation version unique ID. */
	private static final long serialVersionUID = 1L;
}
