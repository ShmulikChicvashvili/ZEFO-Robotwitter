/**
 *
 */

package com.robotwitter.posting;


import java.util.ArrayList;

import twitter4j.TwitterException;

import com.google.inject.Inject;

import com.robotwitter.twitter.TwitterAccount;




/**
 * @author Shmulik
 *
 */
public class TweetPostService
{
	/**
	 * @param preference
	 *            The preference for the tweets
	 */
	@Inject
	public TweetPostService(Preference preference)
	{
		this.preference = preference;
	}


	/**
	 * The method which will post the tweet which has been set
	 */
	@SuppressWarnings({ "nls", "boxing" })
	public void post()
	{
		if (tweet == null) { return; }
		final ArrayList<String> tweetsToPost = preference.generateTweet(tweet);
		if (twitterAccount.isAttached())
		{
			for (final String tweetPost : tweetsToPost)
			{
				try
				{
					twitterAccount.getTwitter().updateStatus(tweetPost);
				} catch (final TwitterException e)
				{
					throw new RuntimeException("Failed to post tweet");
				}
			}
		}
	}
	
	
	/**
	 * @param tweet
	 *            The tweet to publish
	 */
	public void setTweet(String tweet)
	{
		this.tweet = tweet;
	}
	
	
	/**
	 * @param twitterAccount
	 *            The twitter account which posts the tweet
	 */
	public void setTwitterAccount(TwitterAccount twitterAccount)
	{
		this.twitterAccount = twitterAccount;
	}
	
	
	
	/**
	 * The preference for posting
	 */
	Preference preference;

	/**
	 * The account for posting
	 */
	TwitterAccount twitterAccount;

	/**
	 * The tweet to post
	 */
	String tweet = null;
}
