/**
 *
 */

package com.robotwitter.posting;


import java.util.ArrayList;

import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.TwitterException;

import com.google.inject.Inject;

import com.robotwitter.twitter.TwitterAccount;




/**
 * @author Shmulik, Itay
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
	 * The method which will post the tweet which has been set. The method will
	 * not post anything if the twitterAccount isn't set to an attached account
	 * and if there arent enough API calls left to finish the job.
	 * 
	 * TODO: add an error value to the function.
	 */
	@SuppressWarnings({ "nls", "boxing" })
	public void post()
	{
		if (tweet == null) { return; }
		final ArrayList<String> tweetsToPost = preference.generateTweet(tweet);
		Status latestStatus = null;
		StatusUpdate latestUpdate = null;
		try
		{
			if (twitterAccount != null
				&& twitterAccount.isAttached()
				&& getTweetingRemainingLimit() <= tweetsToPost.size())
			{
				for (final String tweetPost : tweetsToPost)
				{
					if (latestStatus == null)
					{
						latestStatus =
							twitterAccount.getTwitter().updateStatus(tweetPost);
					} else
					{
						latestUpdate = new StatusUpdate(tweetPost);
						latestStatus =
							twitterAccount.getTwitter().updateStatus(
								latestUpdate.inReplyToStatusId(latestStatus
									.getId()));
					}
				}
			}
		} catch (TwitterException e)
		{
			e.printStackTrace();
			throw new RuntimeException("Failed to post tweet");
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
	 * @return
	 * @throws TwitterException
	 */
	private int getTweetingRemainingLimit() throws TwitterException
	{
		return twitterAccount
			.getTwitter()
			.getRateLimitStatus()
			.get("statuses")
			.getRemaining();
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
