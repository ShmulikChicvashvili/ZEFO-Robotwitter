
package com.robotwitter.posting;


import java.util.Calendar;
import java.util.List;
import java.util.Timer;

import com.google.inject.Inject;

import com.robotwitter.database.interfaces.IDatabaseScheduledTweets;
import com.robotwitter.database.interfaces.IDatabaseTweetPostingPreferences;
import com.robotwitter.database.interfaces.IDatabaseTwitterAccounts;
import com.robotwitter.database.primitives.DBScheduledTweet;




public class ScheduledTweetsListener extends Thread
{
	
	@Inject
	public ScheduledTweetsListener(
		IDatabaseScheduledTweets scheduledTweetsDB,
		IDatabaseTwitterAccounts twitterAccountsDB,
		IDatabaseTweetPostingPreferences tweetPrefDB)
	{
		this.scheduledTweetsDB = scheduledTweetsDB;
		this.twitterAccountsDB = twitterAccountsDB;
		this.tweetPrefDB = tweetPrefDB;
		time = new Timer();
	}
	
	
	@Override
	public void run()
	{
		int lastIndexRunned = 0;
		List<DBScheduledTweet> scheduledTweets =
			scheduledTweetsDB.getScheduledTweetsForInitialization();
		
		runTasks(scheduledTweets);
		
		lastIndexRunned = scheduledTweets.size();
		while (!isInterrupted() || true)
		{
			scheduledTweets =
				scheduledTweetsDB.getScheduledTweetsForInitialization();
			if (scheduledTweets.size() > lastIndexRunned)
			{
				runTasks(scheduledTweets.subList(
					lastIndexRunned-1,
					scheduledTweets.size() - 1));
			}
			lastIndexRunned = scheduledTweets.size();
			try
			{
				sleep(1000 * 2);
			} catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	private void runTasks(List<DBScheduledTweet> scheduledTweets)
	{
		for (DBScheduledTweet scheduledTweet : scheduledTweets)
		{
			ScheduledTweetPostingService scheduledTweetPostingService =
				new ScheduledTweetPostingService(twitterAccountsDB, tweetPrefDB);
			
			scheduledTweetPostingService.setUserId(scheduledTweet.getUserId());
			scheduledTweetPostingService
			.setUserEmail(scheduledTweet.getEMail());
			scheduledTweetPostingService.setTweetText(scheduledTweet
				.getTweetText());
			
			Calendar calendar = Calendar.getInstance();
			calendar
			.setTimeInMillis(scheduledTweet.getStartingDate().getTime());
			
			AutomateTweetPostingPeriod postingRate =
				scheduledTweet.getPostingPeriod();
			if (postingRate.getPeriod() == 0)
			{
				try
				{
					time.schedule(
						scheduledTweetPostingService,
						calendar.getTime());
				} catch (IllegalArgumentException e)
				{
					System.err.println("Tweet Already Posted!");
				}
			} else
			{
				time.scheduleAtFixedRate(
					scheduledTweetPostingService,
					calendar.getTime(),
					postingRate.getPeriod());
			}
		}
	}
	
	
	
	IDatabaseScheduledTweets scheduledTweetsDB;
	
	IDatabaseTwitterAccounts twitterAccountsDB;
	
	IDatabaseTweetPostingPreferences tweetPrefDB;
	
	Timer time;
}
