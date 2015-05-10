/**
 * 
 */

package com.robotwitter.webapp.control.automate;


import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;
import com.mysql.jdbc.SQLError;

import com.robotwitter.database.interfaces.IDatabaseResponses;
import com.robotwitter.database.interfaces.IDatabaseTweetPostingPreferences;
import com.robotwitter.database.interfaces.returnValues.SqlError;
import com.robotwitter.database.primitives.DBResponse;
import com.robotwitter.database.primitives.DBTweetPostingPreferences;
import com.robotwitter.posting.Preference;
import com.robotwitter.posting.ResponsePostService;
import com.robotwitter.posting.TweetPostService;
import com.robotwitter.twitter.TwitterAccount;
import com.robotwitter.webapp.control.general.Tweet;




/**
 * @author Shmulik
 *
 */
public class CannedTweetsController implements ICannedTweetsController
{
	/**
	 * @param responseDatabase
	 * @param postingPreferenceDatabase
	 */
	@Inject
	public CannedTweetsController(
		Preference pref,
		IDatabaseResponses responseDatabase,
		IDatabaseTweetPostingPreferences postingPreferenceDatabase)
	{
		this.responseDatabase = responseDatabase;
		this.pref = pref;
	}
	
	
	/* (non-Javadoc) @see
	 * com.robotwitter.webapp.control.automate.ICannedTweetsController
	 * #getCannedTweets(long) */
	@Override
	public List<Tweet> getCannedTweets(long twitterAccountID)
	{
		ArrayList<DBResponse> cannedTweets =
			responseDatabase.getBadResponsesOfUser(twitterAccountID);
		// TODO Auto-generated method stub
		return null;
	}
	
	
	/* (non-Javadoc) @see
	 * com.robotwitter.webapp.control.automate.ICannedTweetsController
	 * #getResponses(long, long) */
	@Override
	public List<String> getResponses(long twitterAccountID, long tweetID)
	{
		ArrayList<String> $ = new ArrayList<>();
		$
			.add("We are sorry you feel this way, please contact us and we will try to help!");
		$
			.add("We do our best to improve, stick with us and we won't disappoint you.");
		$.add("Man up ya wrinkley bitch");
		return $;
	}
	
	
	/* (non-Javadoc) @see
	 * com.robotwitter.webapp.control.automate.ICannedTweetsController
	 * #removeTweet(long, long) */
	@Override
	public Status removeTweet(long twitterAccountID, long tweetID)
	{
		Status $ = Status.SUCCESS;
		SqlError res = responseDatabase.deleteResponse(tweetID);
		switch (res)
		{
			case INVALID_PARAMS:
				$ = Status.TWEET_DOESNT_EXIST;
				break;
			case FAILURE:
				$ = Status.FAILURE;
				break;
			case SUCCESS:
				$ = Status.SUCCESS;
				break;
			default:
				$ = Status.FAILURE;
				break;
		}
		return $;
	}
	
	
	/* (non-Javadoc) @see
	 * com.robotwitter.webapp.control.automate.ICannedTweetsController
	 * #respondToTweet(long, long, java.lang.String) */
	@Override
	public Status respondToTweet(
		long twitterAccountID,
		long tweetID,
		String text)
	{
		Status $ = Status.SUCCESS;
		
		ArrayList<String> tweets = pref.generateTweet(text);
		switch (responsePostService.post(tweetID, tweets))
		{
			case SUCCESS:
				$ = Status.SUCCESS;
				break;
			
			default:
				$ = Status.FAILURE;
				break;
		}
		
		return $;
	}
	
	
	/**
	 * @param twitterAccount
	 *            the twitter account
	 */
	public void setTwitterAccount(TwitterAccount twitterAccount)
	{
		this.twitterAccount = twitterAccount;
		responsePostService = new ResponsePostService(twitterAccount);
	}
	
	
	
	TwitterAccount twitterAccount;
	
	ResponsePostService responsePostService;
	
	IDatabaseResponses responseDatabase;
	
	Preference pref;
}
