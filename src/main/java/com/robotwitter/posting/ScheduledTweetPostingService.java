
package com.robotwitter.posting;


import java.util.ArrayList;
import java.util.TimerTask;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

import com.google.inject.Inject;
import com.robotwitter.database.interfaces.IDatabaseTweetPostingPreferences;
import com.robotwitter.database.interfaces.IDatabaseTwitterAccounts;
import com.robotwitter.database.primitives.DBTweetPostingPreferences;
import com.robotwitter.database.primitives.DBTwitterAccount;
import com.robotwitter.twitter.TwitterAccount;
import com.robotwitter.twitter.TwitterAppConfiguration;




public class ScheduledTweetPostingService extends TimerTask
{
	
	@Inject
	public ScheduledTweetPostingService(
		IDatabaseTwitterAccounts twitterAccountsDB,
		IDatabaseTweetPostingPreferences tweetPrefDB)
	{
		this.twitterAccountsDB = twitterAccountsDB;
		this.tweetPrefDB = tweetPrefDB;
		userId = null;
		tweetText = null;
		userEmail = null;
	}
	
	
	@Override
	public void run()
	{
		if (userId == null
			|| userId < 0
			|| tweetText == null
			|| userEmail == null) { return; }
		
		TwitterAccount twitterAccount = createTwitterAccount(userId);
		TweetPostService tweetPostingService =
			new TweetPostService(twitterAccount);
		
		Preference pref = null;
		DBTweetPostingPreferences userPref = tweetPrefDB.get(userEmail);
		if (userPref == null)
		{
			System.err
				.println("error occured in initialization! message printed from ScheduledTweetPostingService.java");
			pref = new NumberedPreference();
		} else
		{
			TweetPostingPreferenceType prefType =
				userPref.getPostingPreference();
			switch (prefType)
			{
				case BASIC:
					pref = new BasicPreference();
					break;
				case NUMBERED:
					pref = new NumberedPreference();
					break;
				case PREFIX:
					pref = new PrefixPreference(userPref.getPrefix()); // TODO:
																		// could
																		// be
																		// null?
					break;
				case POSTFIX:
					pref = new PostfixPreference(userPref.getPostfix()); // TODO:
																			// same
																			// here
					break;
				default:
					break;
			}
			if (pref == null)
			{
				System.err
					.println("Error from ScheduledTweetPostingSerivce.java with preference");
				return;
			}
		}
		ArrayList<String> tweetToPost = pref.generateTweet(tweetText);
		
		tweetPostingService.post(tweetToPost);
	}
	
	
	public void setTweetText(String tweetText)
	{
		this.tweetText = tweetText;
	}
	
	
	public void setUserEmail(String userEmail)
	{
		this.userEmail = userEmail;
	}
	
	
	public void setUserId(long userId)
	{
		this.userId = userId;
	}
	
	
	private TwitterAccount createTwitterAccount(long userId)
	{
		final TwitterFactory tf =
			new TwitterFactory(
				new TwitterAppConfiguration().getUserConfiguration());
		final TwitterAccount userAccount = new TwitterAccount(tf);
		final Twitter connector = tf.getInstance();
		final DBTwitterAccount account = twitterAccountsDB.get(userId);
		connector.setOAuthAccessToken(new AccessToken(
			account.getToken(),
			account.getPrivateToken()));
		userAccount.setTwitter(connector);
		userAccount.setAttached(true);
		
		return userAccount;
	}
	
	
	
	Long userId;
	
	String tweetText;
	
	String userEmail;
	
	IDatabaseTwitterAccounts twitterAccountsDB;
	
	IDatabaseTweetPostingPreferences tweetPrefDB;
	
}
