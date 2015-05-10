/**
 * 
 */
package com.robotwitter.webapp.control.automate;

import java.util.List;

import com.robotwitter.posting.TweetPostService;
import com.robotwitter.twitter.TwitterAccount;
import com.robotwitter.webapp.control.general.Tweet;

/**
 * @author Shmulik
 *
 */
public class CannedTweetsController implements ICannedTweetsController
{
	
	/* (non-Javadoc) @see com.robotwitter.webapp.control.automate.ICannedTweetsController#getCannedTweets(long) */
	@Override
	public List<Tweet> getCannedTweets(long twitterAccountID)
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	
	/* (non-Javadoc) @see com.robotwitter.webapp.control.automate.ICannedTweetsController#getResponses(long, long) */
	@Override
	public List<String> getResponses(long twitterAccountID, long tweetID)
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	
	/* (non-Javadoc) @see com.robotwitter.webapp.control.automate.ICannedTweetsController#removeTweet(long, long) */
	@Override
	public Status removeTweet(long twitterAccountID, long tweetID)
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	
	/* (non-Javadoc) @see com.robotwitter.webapp.control.automate.ICannedTweetsController#respondToTweet(long, long, java.lang.String) */
	@Override
	public Status respondToTweet(
		long twitterAccountID,
		long tweetID,
		String text)
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	TwitterAccount twitterAccount;
	
	TweetPostService responsePostService;
	
	
}
