package com.robotwitter.database.primitives;

import java.sql.Timestamp;
import java.util.Calendar;

import com.robotwitter.posting.AutomateTweetPostingPeriod;
import com.robotwitter.twitter.TwitterAccount;

public class DBScheduledTweet extends DatabaseType {

	public DBScheduledTweet(String email, long userId, String tweetName,
			String tweetText, Timestamp startingDate,
			AutomateTweetPostingPeriod postingPeriod) {
		super(email);
		this.userId = userId;
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

	public Timestamp getStartingDate() {
		return startingDate;
	}

	public void setStartingDate(Timestamp startingDate) {
		this.startingDate = startingDate;
	}

	public AutomateTweetPostingPeriod getPostingPeriod() {
		return postingPeriod;
	}

	public void setPostingPeriod(AutomateTweetPostingPeriod postingPeriod) {
		this.postingPeriod = postingPeriod;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	long userId;
	String tweetName;
	String tweetText;
	Timestamp startingDate;
	AutomateTweetPostingPeriod postingPeriod;
}
