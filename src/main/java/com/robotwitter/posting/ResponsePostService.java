/**
 *
 */

package com.robotwitter.posting;


import java.util.List;

import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.TwitterException;

import com.google.inject.Inject;

import com.robotwitter.twitter.TwitterAccount;




/**
 * @author Shmulik and Itay Khazon
 *
 */
public class ResponsePostService
{
	/**
	 * @author Shmulik
	 *
	 */
	public enum ReturnStatus
	{
		SUCCESS,
		OUT_OF_RATES,
		UNATTACHED_ACCOUNT,
		INVALID_PARAMS,
		FAILURE
	}



	/**
	 * @param twitterAccount
	 *            The twitter account
	 */
	@Inject
	public ResponsePostService(TwitterAccount twitterAccount)
	{
		this.twitterAccount = twitterAccount;
	}


	@SuppressWarnings({ "nls", "boxing" })
	public ReturnStatus post(long tweetId, List<String> tweetsToPost)
	{
		if (tweetsToPost == null || tweetId < 1) { return ReturnStatus.FAILURE; }
		Status latestStatus = null;
		StatusUpdate latestUpdate = null;
		try
		{
			if (twitterAccount == null || !twitterAccount.isAttached()) { return ReturnStatus.UNATTACHED_ACCOUNT; }
			if (getTweetingRemainingLimit() < tweetsToPost.size()) { return ReturnStatus.OUT_OF_RATES; }

			for (String tweetPost : tweetsToPost)
			{
				if (latestStatus == null)
				{
					latestUpdate = new StatusUpdate(tweetPost);
					latestUpdate.setInReplyToStatusId(tweetId);
					latestStatus =
						twitterAccount.getTwitter().updateStatus(latestUpdate);
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
		} catch (final TwitterException e)
		{
			e.printStackTrace();
			return ReturnStatus.FAILURE;
		}
		return ReturnStatus.SUCCESS;
	}


	/**
	 * @return
	 * @throws TwitterException
	 */
	private int getTweetingRemainingLimit() throws TwitterException
	{
		return 15; // FIXME: get the real limits!
	}



	TwitterAccount twitterAccount;
}
