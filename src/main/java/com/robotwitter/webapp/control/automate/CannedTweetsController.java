/**
 *
 */

package com.robotwitter.webapp.control.automate;


import java.util.ArrayList;
import java.util.List;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

import com.google.inject.Inject;

import com.robotwitter.database.interfaces.IDatabaseFollowers;
import com.robotwitter.database.interfaces.IDatabaseResponses;
import com.robotwitter.database.interfaces.IDatabaseTweetPostingPreferences;
import com.robotwitter.database.interfaces.IDatabaseTwitterAccounts;
import com.robotwitter.database.interfaces.returnValues.SqlError;
import com.robotwitter.database.primitives.DBFollower;
import com.robotwitter.database.primitives.DBResponse;
import com.robotwitter.database.primitives.DBTwitterAccount;
import com.robotwitter.posting.NumberedPreference;
import com.robotwitter.posting.Preference;
import com.robotwitter.posting.ResponsePostService;
import com.robotwitter.twitter.TwitterAccount;
import com.robotwitter.twitter.TwitterAppConfiguration;
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
		IDatabaseResponses responseDatabase,
		IDatabaseTweetPostingPreferences postingPreferenceDatabase,
		IDatabaseTwitterAccounts accountsDB, 
		IDatabaseFollowers followersDB)
	{
		this.responseDatabase = responseDatabase;
		prefDB = postingPreferenceDatabase;
		pref = new NumberedPreference();
		this.accountsDB = accountsDB;
		this.followersDB = followersDB;
	}

	/* (non-Javadoc) @see
	 * com.robotwitter.webapp.control.automate.ICannedTweetsController
	 * #getCannedTweets(long) */
	@Override
	public List<Tweet> getCannedTweets()
	{
		ArrayList<Tweet> $ = new ArrayList<>();
		ArrayList<DBResponse> cannedTweets =
			responseDatabase.getUnansweredResponsesOfUser(activeID);
		for (DBResponse res : cannedTweets)
		{
			DBFollower respondingPerson = followersDB.get(res.getResponderID());
			$
			.add(new Tweet(
				res.getId(),
				res.getText(),
				respondingPerson.getName(),
				respondingPerson.getScreenName(),
				respondingPerson.getPicture()));
		}
		return $;
	}


	/* (non-Javadoc) @see
	 * com.robotwitter.webapp.control.automate.ICannedTweetsController
	 * #getResponses(long, long) */
	@Override
	public List<String> getResponses(long tweetID)
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
	public Status removeTweet(long tweetID)
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
	public Status respondToTweet(long tweetID, String text)
	{
		Status $ = Status.SUCCESS;

		ArrayList<String> tweets = pref.generateTweet(text);
		switch (responsePostService.post(tweetID, tweets))
		{
			case SUCCESS:
				$ = Status.SUCCESS;
				responseDatabase.answer(tweetID);
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
	@Override
	public void setTwitterAccount(long id)
	{
		activeID = id;
		DBTwitterAccount account = accountsDB.get(id);
		final TwitterFactory tf =
			new TwitterFactory(
				new TwitterAppConfiguration().getUserConfiguration());
		final TwitterAccount userAccount = new TwitterAccount(tf);
		final Twitter connector = tf.getInstance();
		connector.setOAuthAccessToken(new AccessToken(
			account.getToken(),
			account.getPrivateToken()));
		userAccount.setTwitter(connector);
		userAccount.setAttached(true);
		responsePostService = new ResponsePostService(userAccount);
		// FIXME CHANGE PREF TO NEW ACCOUNTS PREF
	}


	private IDatabaseFollowers followersDB;



	private long activeID;

	private IDatabaseTweetPostingPreferences prefDB;

	private IDatabaseTwitterAccounts accountsDB;

	TwitterAccount twitterAccount;

	ResponsePostService responsePostService;

	IDatabaseResponses responseDatabase;

	Preference pref;
}
