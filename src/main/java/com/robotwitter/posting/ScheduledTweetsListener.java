package com.robotwitter.posting;

import java.util.Calendar;
import java.util.List;
import java.util.Timer;

import com.google.inject.Inject;
import com.robotwitter.database.interfaces.IDatabaseScheduledTweets;
import com.robotwitter.database.interfaces.IDatabaseTweetPostingPreferences;
import com.robotwitter.database.interfaces.IDatabaseTwitterAccounts;
import com.robotwitter.database.primitives.DBScheduledTweet;

public class ScheduledTweetsListener extends Thread {

	@Inject
	public ScheduledTweetsListener(IDatabaseScheduledTweets scheduledTweetsDB,
			IDatabaseTwitterAccounts twitterAccountsDB,
			IDatabaseTweetPostingPreferences tweetPrefDB) {
		this.scheduledTweetsDB = scheduledTweetsDB;
		this.twitterAccountsDB = twitterAccountsDB;
		this.tweetPrefDB = tweetPrefDB;
		time = new Timer();
	}

	@Override
	public void run() {
		List<DBScheduledTweet> scheduledTweets = scheduledTweetsDB
				.getScheduledTweetsForInitialization();

		runTasks(scheduledTweets);

		while (!isInterrupted() || true) {
			scheduledTweets = scheduledTweetsDB.getScheduledTweets();
			runTasks(scheduledTweets);
		}
	}

	private void runTasks(List<DBScheduledTweet> scheduledTweets) {
		for (DBScheduledTweet scheduledTweet : scheduledTweets) {
			ScheduledTweetPostingService scheduledTweetPostingService = new ScheduledTweetPostingService(
					twitterAccountsDB, tweetPrefDB);

			scheduledTweetPostingService.setUserId(scheduledTweet.getUserId());
			scheduledTweetPostingService
					.setUserEmail(scheduledTweet.getEMail());
			scheduledTweetPostingService.setTweetText(scheduledTweet
					.getTweetText());

			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(scheduledTweet.getStartingDate().getTime());

			AutomateTweetPostingPeriod postingRate = scheduledTweet
					.getPostingPeriod();
			if (postingRate.getPeriod() == 0) {
				try {
					time.schedule(scheduledTweetPostingService,
							calendar.getTime());
				} catch (IllegalArgumentException e) {
					System.err.println("Tweet Already Posted!");
				}
			} else {
				time.scheduleAtFixedRate(scheduledTweetPostingService,
						calendar.getTime(), postingRate.getPeriod());
			}
		}
	}

	IDatabaseScheduledTweets scheduledTweetsDB;
	IDatabaseTwitterAccounts twitterAccountsDB;
	IDatabaseTweetPostingPreferences tweetPrefDB;
	Timer time;
}
