
package com.robotwitter.webapp.control.account;


import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.robotwitter.twitter.GainedLostNumFollowers;
import com.robotwitter.twitter.TwitterFollower;




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
	 * Gets the followers for the twitter account.
	 *
	 * @return A list containing all the followers for the account.
	 */
	List<TwitterFollower> getAccountFollowers();


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


	/** @return The Twitter account's ID. */
	long getID();


	/** @return The Twitter account's image. */
	String getImage();


	/**
	 * Gets the last known numbers of total, gained and lost followers.
	 *
	 * @return An object containing the numbers of total, gained and lost
	 *         followers, as recent as possible.
	 */
	GainedLostNumFollowers getLastGainedAndLostNumFollowers();


	/** @return The Twitter account's name. */
	String getName();


	/** @return The Twitter account's screenname. */
	String getScreenname();

}
