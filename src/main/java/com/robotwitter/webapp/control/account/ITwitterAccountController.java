
package com.robotwitter.webapp.control.account;


import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;




/**
 * Controller of a single Twitter account of a user.
 *
 * @author Hagai Akibayov
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
	 * Gets the followers amount by displayed language.
	 *
	 * @return a map from a displayed language to the amount of followers our
	 *         twitter account has with this displayed language.
	 */
	Map<String, Integer> getFollowersAmountByDisplayedLanguage();


	/**
	 * Gets a list with the amount of followers who have an amount of followers
	 * in the corresponding range.
	 * <p>
	 * For example: given we have 3 followers: <br>
	 * - one who has 5 followers<br>
	 * - one who has 9 followers <br>
	 * - one who has 15 followers.
	 * <p>
	 * - the list of separators [] will return [3] <br>
	 * - the list of separators [5] will return [0,3] (right end is exclusive) <br>
	 * - the list of separators [3,9,20] will return [0,2,1,0]
	 *
	 * @param separators
	 *            a sorted list of separators for the ranges. each two
	 *            consecutive (except the end points) determine a range from the
	 *            left (inclusive) to the right (exclusive).
	 * @return a list with the followers amount whose number of accounts they
	 *         are following is inside the given range. the ranges are
	 *         determined by the list of separators. the list size should be one
	 *         more the the separators list size.
	 */
	List<Integer> getFollowersAmountByTheirFollowersAmount(
		List<Integer> separators);


	/**
	 * Gets a list with the amount of followers who have an amount of accounts
	 * their following after in the corresponding range.
	 * <p>
	 * For example: given we have 3 followers: <br>
	 * - one who has follows 5 accounts<br>
	 * - one who has follows 9 accounts <br>
	 * - one who has follows 15 accounts.
	 * <p>
	 * - the list of separators [] will return [3] <br>
	 * - the list of separators [5] will return [0,3] (right end is exclusive) <br>
	 * - the list of separators [3,9,20] will return [0,2,1,0]
	 *
	 * @param separators
	 *            a sorted list of separators for the ranges. each two
	 *            consecutive (except the end points) determine a range from the
	 *            left (inclusive) to the right (exclusive).
	 * @return a list with the followers amount who have a number of followers
	 *         in the given range. the ranges are determined by the list of
	 *         separators. the list size should be one more the the separators
	 *         list size.
	 */
	List<Integer> getFollowersAmountByTheirFollowingAmount(
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

}
