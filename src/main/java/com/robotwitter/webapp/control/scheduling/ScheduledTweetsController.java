package com.robotwitter.webapp.control.scheduling;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import com.google.inject.Inject;
import com.robotwitter.database.interfaces.IDatabaseScheduledTweets;
import com.robotwitter.database.primitives.DBScheduledTweet;
import com.robotwitter.posting.AutomateTweetPostingPeriod;
import com.robotwitter.posting.NumberedPreference;
import com.robotwitter.posting.Preference;

/**
 * The Class CannedTweetsController.
 *
 * @author Shmulik
 */
public class ScheduledTweetsController implements IScheduledTweetsController {

	@Inject
	public ScheduledTweetsController(IDatabaseScheduledTweets scheduledTweetsDB) {
		this.scheduledTweetsDB = scheduledTweetsDB;
		pref = new NumberedPreference();
	}
	
	@Override
	public List<String> previewTweet(String text) {
		return pref.generateTweet(text);
	}

	@Override
	public void addScheduledTweet(String name, String text, long userId,
			Calendar c, AutomateTweetPostingPeriod period) {
		// TODO: talk with eyal add send this function an email too
		DBScheduledTweet scheduledTweet = new DBScheduledTweet("", userId, name, text, new Timestamp(c.getTimeInMillis()), period);
		scheduledTweetsDB.insertScheduledTweet(scheduledTweet);
	}

	
	Preference pref;
	
	IDatabaseScheduledTweets scheduledTweetsDB;
}
