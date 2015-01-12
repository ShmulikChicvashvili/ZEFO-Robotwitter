
package com.robotwitter.webapp.control.tools;


import java.io.Serializable;
import java.util.List;




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
}
