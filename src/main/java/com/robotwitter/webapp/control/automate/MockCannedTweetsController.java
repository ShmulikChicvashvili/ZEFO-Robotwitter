
package com.robotwitter.webapp.control.automate;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.inject.Inject;

import com.robotwitter.webapp.control.general.Tweet;




/**
 * The Class MockCannedTweetsController.
 *
 * @author Hagai Akibayov
 */
public class MockCannedTweetsController implements ICannedTweetsController
{

	/**
	 * Instantiates a new mock canned tweets controller.
	 */
	@Inject
	public MockCannedTweetsController()
	{
		activeID = 0;
	}


	/* (non-Javadoc) @see
	 * com.robotwitter.webapp.control.automate.ICannedTweetsController
	 * #getCannedTweets(long) */
	@Override
	public final List<Tweet> getCannedTweets()
	{
		Tweet tweet1 =
			new Tweet(
				0,
				"Fuck this shit #YOLO #Swag",
				"Don Akibayov",
				"DonAkibayov",
				"https://pbs.twimg.com/profile_images/546786848849158145/wS82lZr8.jpeg");
		Tweet tweet2 =
			new Tweet(
				1,
				"Fuck this shit too",
				"Don Akibayov",
				"DonAkibayov",
				"https://pbs.twimg.com/profile_images/1252701360/09_bar-refaeli_27.jpg");
		Tweet tweet3 =
			new Tweet(
				2,
				"Fuck this shit in particular",
				"Don Akibayov",
				"DonAkibayov",
				"https://pbs.twimg.com/profile_images/498202852624330752/kUeefRWm.jpeg");

		if (activeID == 2910240756L)
		{
			tweet1.setText("Second account #HOLYMOLLY");
		}
		
		return Arrays.asList(tweet1, tweet2, tweet3, tweet1, tweet2, tweet3);
	}


	/* (non-Javadoc) @see
	 * com.robotwitter.webapp.control.automate.ICannedTweetsController
	 * #getResponses(long, long) */
	@Override
	public final List<String> getResponses(long tweetID)
	{
		ArrayList<String> $ = new ArrayList<>();
		$
		.add("We are sorry you feel this way, please contact us and we will try to help!");
		$
		.add("We do our best to improve, stick with us and we won't disappoint you.");
		
		if (activeID == 2910240756L)
		{
			$.add("Man up ya wrinkley bitch");
		} else
		{
			$.add("Second account");
		}
		
		return $;
	}


	/* (non-Javadoc) @see
	 * com.robotwitter.webapp.control.automate.ICannedTweetsController
	 * #removeTweet(long, long) */
	@Override
	public final Status removeTweet(long tweetID)
	{
		switch ((int) tweetID)
		{
			case 0:
			case 1:
			case 2:
				return Status.SUCCESS;
			case -1:
				return Status.FAILURE;
			default:
				return Status.TWEET_DOESNT_EXIST;
		}
	}


	/* (non-Javadoc) @see
	 * com.robotwitter.webapp.control.automate.ICannedTweetsController
	 * #respondToTweet(long, long, java.lang.String) */
	@Override
	public final Status respondToTweet(String email, long tweetID, String text)
	{
		switch ((int) tweetID)
		{
			case 0:
			case 1:
			case 2:
				return Status.SUCCESS;
			case -1:
				return Status.FAILURE;
			default:
				return Status.TWEET_DOESNT_EXIST;
		}
	}


	/**
	 * Sets the twitter account.
	 *
	 * @param id
	 *            the new twitter account
	 */
	@Override
	public final void setTwitterAccount(long id)
	{
		activeID = id;
	}



	/** The active id. */
	private long activeID;
}
