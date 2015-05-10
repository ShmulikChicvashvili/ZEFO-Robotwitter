
package com.robotwitter.webapp.control.automate;


import java.util.List;

import com.robotwitter.twitter.TwitterAccount;
import com.robotwitter.webapp.control.general.Tweet;




/**
 * Canned tweets controller.
 *
 * @author Hagai Akibayov
 */
public interface ICannedTweetsController
{

	/** Status codes returned by this instance. */
	enum Status
	{
		/** Operation succeeded. */
		SUCCESS,

		/** The received Twitter account is not attached to the connected user. */
		TWITTER_ACCOUNT_DOESNT_EXIST,

		/**
		 * The received Tweet doesn't exist in the Twitter account's canned
		 * tweets list.
		 */
		TWEET_DOESNT_EXIST,

		/** An unknown failure occurred . */
		FAILURE
	}



	/**
	 * Get a list of canned tweets for a given Twitter account.
	 *
	 * @param twitterAccountID
	 *            the Twitter account to get the canned tweets for
	 *
	 * @return a list of the Twitter account's canned tweets or null if the
	 *         given Twitter account doesn't exist.
	 */
	List<Tweet> getCannedTweets(long twitterAccountID);


	/**
	 * Get a list of possible responses for a given Twitter account and tweet.
	 *
	 * @param twitterAccountID
	 *            the Twitter account to get the responses for
	 *
	 * @param tweetID
	 *            the ID of the tweet to get responses for
	 *
	 * @return A list of possible responses or null of either the Twitter
	 *         account doesn't exist, or the the given tweet ID doesn't exist in
	 *         the account's canned tweets list.
	 */
	List<String> getResponses(long twitterAccountID, long tweetID);


	/**
	 * Remove the given tweet from a Twitter account's canned tweets list.
	 *
	 * @param twitterAccountID
	 *            the Twitter account to get the responses for
	 *
	 * @param tweetID
	 *            the ID of the tweet to get responses for
	 *
	 * @return the operation's status
	 */
	Status removeTweet(long twitterAccountID, long tweetID);


	/**
	 * Respond to the given tweet from a Twitter account's canned tweets list.
	 *
	 * @param twitterAccountID
	 *            the Twitter account to get the responses for
	 *
	 * @param tweetID
	 *            the ID of the tweet to get responses for
	 * @param text
	 *            the response text. This text may be broken into multiple
	 *            replies if its length exceeds the maximum allowed.
	 *
	 * @return the operation's status
	 */
	Status respondToTweet(long twitterAccountID, long tweetID, String text);


	/**
	 * Set the current active Twitter account.
	 *
	 * @param twitterAccount
	 *            the Twitter account
	 */
	void setTwitterAccount(TwitterAccount twitterAccount);
}
