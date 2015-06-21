
package com.robotwitter.webapp.view.automate;


import java.util.List;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.AbstractOrderedLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import com.robotwitter.webapp.control.automate.ICannedTweetsController;
import com.robotwitter.webapp.control.general.Tweet;
import com.robotwitter.webapp.control.tools.ITweetingController;
import com.robotwitter.webapp.messages.IMessagesContainer;
import com.robotwitter.webapp.util.tweeting.TweetPreview;
import com.robotwitter.webapp.view.AbstractView;




/**
 * Automate view.
 *
 * @author Hagai Akibayov
 */
public class AutomateView extends AbstractView
{

	/**
	 * Instantiates a new automate view.
	 *
	 * @param messages
	 *            the container of messages to display
	 * @param tweetingController
	 *            the tweeting controller
	 * @param cannedController
	 *            the canned controller
	 */
	@Inject
	public AutomateView(
		@Named(NAME) IMessagesContainer messages,
		ITweetingController tweetingController,
		ICannedTweetsController cannedController)
	{
		super(messages, messages.get("AutomateView.page.title"));

		this.tweetingController = tweetingController;
		this.cannedController = cannedController;
		
		tweets = new VerticalLayout();
		tweets.setSpacing(true);
		tweets.addStyleName(TWEETS_STYLENAME);

		getUserSession().observeActiveTwitterAccount(this);
	}


	/* (non-Javadoc) @see
	 * com.robotwitter.webapp.util.RobotwitterCustomComponent#
	 * activateTwitterAccount(long) */
	@Override
	public final void activateTwitterAccount(long id)
	{
		updateBaseOnTwitterAccount(id);
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
	 * @param tweet
	 *            the tweet
	 *
	 * @return the newly created tweet
	 */
	private Component createTweet(Tweet tweet)
	{
		TweetPreview preview = new TweetPreview();
		preview.setCustomFirstTweet(tweet);

		Button respond =
			new Button(
				messages.get("AutomateView.button.respond"),
				event -> getUI().addWindow(
					new TweetResponseWindow(
						messages,
						tweetingController,
						cannedController,
						tweet,
						() -> updateTweets())));
		respond.setIcon(FontAwesome.PAPER_PLANE);
		
		Button delete =
			new Button(messages.get("AutomateView.button.delete"), event -> {
				cannedController.removeTweet(tweet.getID());
				updateTweets();
			});
		delete.setIcon(FontAwesome.TRASH_O);
		delete.addStyleName(ValoTheme.BUTTON_DANGER);
		
		AbstractOrderedLayout buttons;
		if (isMobile())
		{
			buttons = new VerticalLayout(respond, delete);
		} else
		{
			buttons = new HorizontalLayout(respond, delete);
		}

		VerticalLayout tweetComponent = new VerticalLayout(preview, buttons);

		respond.addStyleName(ValoTheme.BUTTON_SMALL);
		delete.addStyleName(ValoTheme.BUTTON_SMALL);

		respond.addStyleName(RESPOND_STYLENAME);
		delete.addStyleName(DELETE_STYLENAME);
		buttons.addStyleName(BUTTONS_STYLENAME);
		tweetComponent.addStyleName(TWEET_STYLENAME);

		return tweetComponent;
	}


	/** update Tweets for canned-response. */
	private void updateTweets()
	{
		List<Tweet> cannedTweets = cannedController.getCannedTweets();
		
		tweets.removeAllComponents();
		for (Tweet tweet : cannedTweets)
		{
			tweets.addComponent(createTweet(tweet));
		}

		if (cannedTweets.isEmpty())
		{
			desc.setValue(messages.get("AutomateView.label.no-canned-tweets"));
		} else
		{
			if (isMobile())
			{
				desc.setValue(messages
					.get("AutomateView.mobile.label.description"));
			} else
			{
				desc.setValue(messages.get("AutomateView.label.description"));
			}
		}
	}
	
	
	@Override
	protected final void initialise()
	{
		Label header = new Label(messages.get("AutomateView.label.header"));
		desc = new Label();
		updateBaseOnTwitterAccount(getUserSession()
			.getAccountController()
			.getActiveTwitterAccount()
			.getID());

		VerticalLayout layout = new VerticalLayout(header, desc, tweets);
		layout.setWidth("100%");

		header.addStyleName(HEADER_STYLENAME);
		desc.addStyleName(DESC_STYLENAME);
		setStyleName(STYLENAME);
		
		if (isMobile())
		{
			addStyleName(MOBILE_STYLENAME);
		}

		setCompositionRoot(layout);
	}


	/**
	 * Update base on Twitter account.
	 *
	 * @param id
	 *            the ID of the Twitter account
	 */
	final void updateBaseOnTwitterAccount(long id)
	{
		cannedController.setTwitterAccount(id);
		updateTweets();
	}
	
	
	
	/** The list of canned tweets. */
	private VerticalLayout tweets;
	
	/** The view's name. */
	public static final String NAME = "automate";

	/** The CSS class name to apply to this view. */
	private static final String STYLENAME = "AutomateView";

	/** The CSS class name to apply to this view in mobile browsers. */
	private static final String MOBILE_STYLENAME = "AutomateView-mobile";

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

	/** Canned controller. */
	private ICannedTweetsController cannedController;

	/** Tweeting controller. */
	private ITweetingController tweetingController;
	
	/** The page's description. */
	private Label desc;
}
