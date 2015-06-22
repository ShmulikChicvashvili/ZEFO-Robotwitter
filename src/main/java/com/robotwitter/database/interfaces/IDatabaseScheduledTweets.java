package com.robotwitter.database.interfaces;

import java.util.List;

import com.robotwitter.database.interfaces.returnValues.SqlError;
import com.robotwitter.database.primitives.DBScheduledTweet;

public interface IDatabaseScheduledTweets {
	/*
	 * The method will insert new scheduled tweet to the database
	 * 
	 * @param scheduledTweet The primitive which holds all the data of the
	 * scheduled tweet
	 */
	public SqlError insertScheduledTweet(DBScheduledTweet scheduledTwet);
	
	public SqlError removeScheduledTweet(DBScheduledTweet scheduledTweet);

	/*
	 * The method which will return all the scheduled tweets that were added
	 */
	public List<DBScheduledTweet> getScheduledTweets();

	/*
	 * The method will return all the scheduled tweets in the system, the method
	 * will be used every time the system starts
	 */
	public List<DBScheduledTweet> getScheduledTweetsForInitialization();
}
