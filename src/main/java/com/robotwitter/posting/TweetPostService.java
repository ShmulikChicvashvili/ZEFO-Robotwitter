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
	public enum ReturnStatus
	{
		SUCCESS,
		OUT_OF_RATES,
		UNATTACHED_ACCOUNT,
		INVALID_PARAMS,
		FAILURE
	}
	
	
	
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
	public ReturnStatus post()
	{
		if (tweet == null) { return ReturnStatus.INVALID_PARAMS; }
		final ArrayList<String> tweetsToPost = preference.generateTweet(tweet);
		Status latestStatus = null;
		StatusUpdate latestUpdate = null;
		try
		{
			if (twitterAccount == null || !twitterAccount.isAttached()) { return ReturnStatus.UNATTACHED_ACCOUNT; }
			if (getTweetingRemainingLimit() < tweetsToPost.size()) { return ReturnStatus.OUT_OF_RATES; }
			
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
						twitterAccount
							.getTwitter()
							.updateStatus(
								latestUpdate.inReplyToStatusId(latestStatus
									.getId()));
				}
			}
		} catch (TwitterException e)
		{
			e.printStackTrace();
			return ReturnStatus.FAILURE;
		}
		return ReturnStatus.SUCCESS;
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
		return 15; //FIXME: get the real limits!
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
