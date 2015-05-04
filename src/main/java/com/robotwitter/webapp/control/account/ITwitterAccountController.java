
package com.robotwitter.webapp.control.account;


import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;




/**
 * Controller of a single Twitter account of a user.
 *
 * @author Hagai Akibayov
 * @author Eyal Tolchinsky
 */
public interface ITwitterAccountController extends Serializable
{

	/** Status codes returned by this instance. */
	enum Status
	{
		/** Operation succeeded. */
		SUCCESS,

		/** An unknown failure occurred . */
		FAILURE
	}



	/**
	 * Gets the amount of followers in the given date range.
	 *
	 * @param from
	 *            the earliest day to retrieve amount of followers from
	 * @param to
	 *            the latest day to retrieve amount of followers from
	 *
	 * @return the amount of followers per date (as a map) between
	 *         <code>from</code> and <code>to</code>.
	 */
	Map<Date, Integer> getAmountOfFollowers(Date from, Date to);


	/**
	 * Gets the current maximum tweet length considering multiple tweets that
	 * are broken from a single long tweet.
	 *
	 * @return the current maximum tweet length
	 */
	int getCurrentMaximumTweetLength();


	/**
	 * Gets the followers amount by displayed language.
	 *
	 * @return a map from a displayed language to the amount of followers our
	 *         twitter account has with this displayed language.
	 */
	Map<String, Integer> getFollowersAmountByDisplayedLanguage();


	/**
	 * Gets a list of amounts of followers with a number of followers in the
	 * corresponding ranges. the number of ranges is determined by
	 * 'subdivisions' parameter.
	 * <p>
	 * If the amount of followers is between min and max, each range (exept the
	 * end ones) should have approximately (max-min)/subdivisions numbers. <br>
	 * if this number is i, then the ranges should be about:<br>
	 * [-inf,min+i),[min+i,min+2i),...,[min+(subdivisions-1)*i,inf]<br>
	 * the function will not return an impossible range (e.g [3,3)), and the
	 * ranges will be disjoint and cover all the numbers
	 * <p>
	 * For example: given we have 3 followers: <br>
	 * - one who has 5 followers<br>
	 * - one who has 9 followers <br>
	 * - one who has 15 followers.<br>
	 * Minimum and maximum amounts are 5 and 15.
	 * <p>
	 * subdivisions 1 will return [3],[] <br>
	 * subdivisions 2 will return subdivisions 2 will return [2,1],[10]<br>
	 * subdivisions 3 will return [1,1,1],[8,11]
	 *
	 * @param subdivisions
	 *            - the number of subdivisions
	 * @param amounts
	 *            - a list that will be filled with the amounts for each
	 *            corresponding range
	 * @param separators
	 *            - a list that will be filled with the separators of the
	 *            ranges. each consecutive pair determines a range from the left
	 *            (inclusive) to the right (exclusive). for example for the
	 *            ranges [-inf,5),[5,7),[7,inf) it will hold [5,7].
	 */
	void getFollowersAmountByTheirFollowersAmount(
		int subdivisions,
		List<Integer> amounts,
		List<Integer> separators);


	/**
	 * Gets a list of amounts of followers with a number of accounts they're
	 * following in the corresponding ranges. the number of ranges is determined
	 * by 'subdivisions' parameter.
	 * <p>
	 * If the amount of accounts they're following is between min and max, each
	 * range (exept the end ones) should have approximately
	 * (max-min)/subdivisions numbers. <br>
	 * if this number is i, then the ranges should be about:<br>
	 * [-inf,min+i),[min+i,min+2i),...,[min+(subdivisions-1)*i,inf]<br>
	 * the function will not return an impossible range (e.g [3,3)), and the
	 * ranges will be disjoint and cover all the numbers
	 * <p>
	 * For example: given we have 3 followers: <br>
	 * - one who follows 5 accounts <br>
	 * - one who follows 9 accounts <br>
	 * - one who follows 15 accounts. <br>
	 * Minimum and maximum amounts are 5 and 15.
	 * <p>
	 * subdivisions 1 will return [3],[] <br>
	 * subdivisions 2 will return subdivisions 2 will return [2,1],[10]<br>
	 * subdivisions 3 will return [1,1,1],[8,11]
	 *
	 * @param subdivisions
	 *            - the number of subdivisions
	 * @param amounts
	 *            - a list that will be filled with the amounts for each
	 *            corresponding range
	 * @param separators
	 *            - a list that will be filled with the separators of the
	 *            ranges. each consecutive pair determines a range from the left
	 *            (inclusive) to the right (exclusive). for example for the
	 *            ranges [-inf,5),[5,7),[7,inf) it will hold [5,7].
	 */
	void getFollowersAmountByTheirFollowingAmount(
		int subdivisions,
		List<Integer> amounts,
		List<Integer> separators);


	/** @return The Twitter account's ID. */
	long getID();


	/** @return The Twitter account's image. */
	String getImage();


	/** @return The last known amount of followers. */
	int getLastKnownAmountOfFollowers();


	/** @return The last known amount of gained followers. */
	int getLastKnownAmountOfGainedFollowers();


	/** @return The last known amount of lost followers. */
	int getLastKnownAmountOfLostFollowers();


	/** @return A list of the most influential followers. */
	List<TwitterFollower> getMostInfluentialFollowers();


	/** @return The Twitter account's name. */
	String getName();


	/** @return The Twitter account's screenname. */
	String getScreenname();


	/**
	 * Post the tweets as a response to the original one, one after the other as
	 * a single tweet
	 *
	 * @param originalId
	 *            the id for the original tweet, which the next ones should be a
	 *            response to
	 * @param tweets
	 *            the tweets that form the response
	 * @return the status
	 */
	Status
	postTweetsAsSingleResponseTweet(long originalId, List<String> tweets);


	/**
	 * Post the given tweets as a single tweet.
	 * <p>
	 * In other words, the first tweet is posted as is, and the next ones are
	 * posted as retweets to the first.
	 * <p>
	 * Note: this operation does not break the given tweets, and just posts them
	 * as is.
	 *
	 * @param tweets
	 *            the tweets
	 *
	 * @return the status
	 */
	Status postTweetsAsSingleTweet(List<String> tweets);

}
