package com.robotwitter.database.primitives;

import java.util.Calendar;

import com.robotwitter.posting.AutomateTweetPostingPeriod;
import com.robotwitter.twitter.TwitterAccount;

public class DBScheduledTweet extends DatabaseType {

	public DBScheduledTweet(String email, TwitterAccount twitterAccount,
			String tweetName, String tweetText, Calendar startingDate,
			AutomateTweetPostingPeriod postingPeriod) {
		super(email);
		this.tweetName = tweetName;
		this.tweetText = tweetText;
		this.startingDate = startingDate;
		this.postingPeriod = postingPeriod;
	}

	public String getTweetName() {
		return tweetName;
	}

	public void setTweetName(String tweetName) {
		this.tweetName = tweetName;
	}

	public String getTweetText() {
		return tweetText;
	}

	public void setTweetText(String tweetText) {
		this.tweetText = tweetText;
	}

	public Calendar getStartingDate() {
		return startingDate;
	}

	public void setStartingDate(Calendar startingDate) {
		this.startingDate = startingDate;
	}

	public AutomateTweetPostingPeriod getPostingPeriod() {
		return postingPeriod;
	}

	public void setPostingPeriod(AutomateTweetPostingPeriod postingPeriod) {
		this.postingPeriod = postingPeriod;
	}

	String tweetName;
	String tweetText;
	Calendar startingDate;
	AutomateTweetPostingPeriod postingPeriod;
}
