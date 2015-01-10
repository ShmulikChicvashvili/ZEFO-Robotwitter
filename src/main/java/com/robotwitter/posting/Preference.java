/**
 *
 */

package com.robotwitter.posting;


import java.util.ArrayList;




/**
 * @author Shmulik
 *
 *         The interface will be the base interface for generating tweets by
 *         preference
 */
public interface Preference
{
	/**
	 * @param tweet
	 *            The tweet to generate by preference
	 * @return The generated tweet
	 */
	ArrayList<String> generateTweet(String tweet);
}
