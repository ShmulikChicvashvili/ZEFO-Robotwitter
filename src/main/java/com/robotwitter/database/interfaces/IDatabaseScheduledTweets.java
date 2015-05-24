package com.robotwitter.database.interfaces;

import java.util.List;

import com.robotwitter.database.interfaces.returnValues.SqlError;
import com.robotwitter.database.primitives.DBScheduledTweet;

public interface IDatabaseScheduledTweets {
	public SqlError insertScheduledTweet(DBScheduledTweet scheduledTwet);
	public List<DBScheduledTweet> getScheduledTweets();
}
