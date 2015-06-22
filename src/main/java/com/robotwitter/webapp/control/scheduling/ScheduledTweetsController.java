package com.robotwitter.webapp.control.scheduling;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.google.inject.Inject;
import com.robotwitter.database.interfaces.IDatabaseScheduledTweets;
import com.robotwitter.database.primitives.DBScheduledTweet;
import com.robotwitter.posting.AutomateTweetPostingPeriod;
import com.robotwitter.posting.NumberedPreference;
import com.robotwitter.posting.Preference;
import com.robotwitter.database.interfaces.IDatabaseTweetPostingPreferences;
import com.robotwitter.database.interfaces.IDatabaseTwitterAccounts;
import com.robotwitter.database.interfaces.returnValues.SqlError;
import com.robotwitter.database.primitives.DBTwitterAccount;

/**
 * The Class CannedTweetsController.
 *
 * @author Shmulik
 */
public class ScheduledTweetsController implements IScheduledTweetsController {

	@Inject
	public ScheduledTweetsController(IDatabaseScheduledTweets dbScheduled, IDatabaseTwitterAccounts dbAccounts, IDatabaseTweetPostingPreferences dbPreference){
		this.dbScheduled = dbScheduled;
		this.dbAccounts = dbAccounts;
		this.dbPreference = dbPreference;
		preference = new NumberedPreference();
	}
	
	@Override
	public final List<String> breakTweet(String tweet)
	{
		return preference.generateTweet(tweet);
	}
	
	@Override
	public final List<String> previewTweet(String tweet)
	{
		return breakTweet(tweet);
	}

	@Override
	public SqlError addScheduledTweet(String name, String text, long userId,
			Calendar c, AutomateTweetPostingPeriod period) {
		twitterAccount=dbAccounts.get(userId);
		String email=twitterAccount.getEMail();
		Date date = c.getTime();
		long time = date.getTime();
		Timestamp t = new Timestamp(time);
		DBScheduledTweet tweet = new DBScheduledTweet(email, userId, name, text, t, period);
		SqlError s = dbScheduled.insertScheduledTweet(tweet);	
		return s;
	}
	
	@Override
	public SqlError addScheduledTweet(DBScheduledTweet tweet) {
		return  dbScheduled.insertScheduledTweet(tweet);	
	}
	
	@Override
	public SqlError removeScheduledTweet(List<DBScheduledTweet> tweets){
		SqlError err=SqlError.INVALID_PARAMS;
		for(DBScheduledTweet tweet : tweets){
			err = dbScheduled.removeScheduledTweet(tweet);
			if(err != SqlError.SUCCESS){
				return err;
			}
		}
		return err;
	}
	
	@Override
	public List<DBScheduledTweet> getAllScheduledTweets() {
		return dbScheduled.getScheduledTweets();
	}
	
	@Override
	public List<DBScheduledTweet> getInitializedScheduledTweets() {
		return dbScheduled.getScheduledTweets();
	}
	
	/** Serialisation version unique ID. */
	private static final long serialVersionUID = 1L;
	
	DBTwitterAccount twitterAccount;
	private Preference preference;
	private IDatabaseScheduledTweets dbScheduled;
	private IDatabaseTwitterAccounts dbAccounts;
	private IDatabaseTweetPostingPreferences dbPreference;
	
}
