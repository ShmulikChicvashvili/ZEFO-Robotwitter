/**
 *
 */

package com.robotwitter.webapp.control.scheduling;

import java.util.Calendar;
import java.util.List;

import com.robotwitter.webapp.control.general.Tweet;
import com.robotwitter.database.interfaces.returnValues.SqlError;
import com.robotwitter.database.primitives.DBScheduledTweet;
import com.robotwitter.management.ITwitterTracker.Status;
import com.robotwitter.posting.AutomateTweetPostingPeriod;

/**
 * @author Eyal
 *
 */
public interface IScheduledTweetsController {

	/**
	 * Break the given Tweet.
	 *
	 * The tweet is possibly broken into multiple tweets, and links may be
	 * shortened.
	 *
	 * Note: the returned links are real.
	 *
	 * @param tweet
	 *            the tweet to preview
	 *
	 * @return A list of Tweets that are broken down from the given Tweet
	 */
	List<String> breakTweet(String tweet);

	/**
	 * Preview the given Tweet.
	 *
	 * The tweet is possibly broken into multiple tweets, and links may be
	 * shortened.
	 *
	 * Note: the returned links are not real, and are only used for the preview.
	 *
	 * @param tweet
	 *            the tweet to preview
	 *
	 * @return A list of Tweets that are broken down from the given Tweet
	 */
	List<String> previewTweet(String tweet);

	SqlError addScheduledTweet(String name, String text, long userId,
			Calendar c, AutomateTweetPostingPeriod period);

	SqlError addScheduledTweet(DBScheduledTweet tweet);
	
	SqlError removeScheduledTweet(List<DBScheduledTweet> tweets);
}
