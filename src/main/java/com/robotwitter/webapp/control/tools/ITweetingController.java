
package com.robotwitter.webapp.control.tools;


import java.io.Serializable;
import java.util.List;

import com.robotwitter.database.interfaces.returnValues.SqlError;
import com.robotwitter.posting.TweetPostingPreferenceType;
import com.robotwitter.webapp.control.tools.tweeting.Tweet;
import com.robotwitter.webapp.control.general.Tweet;





/** Tweeting tools controller. */
public interface ITweetingController extends Serializable
{
	
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
	 * Geta list of options to respond to the given tweet (as the current user).
	 *
	 * @param tweet
	 *            the tweet to respond to
	 * @return the optional responses as the current user.
	 */
	List<String> getOptionalResponses(Tweet tweet);


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
	
	SqlError setPreference(TweetPostingPreferenceType preference);
	
	SqlError setPrefix(String prefix);
	
	SqlError setSuffix(String suffix);
	
	String getPrefix();
	
	String getSuffix();
}
