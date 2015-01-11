/**
 *
 */

package com.robotwitter.posting;


import java.util.ArrayList;




/**
 * @author Shmulik
 *
 */
public class BasicPreference extends AbstractPreference implements Preference
{

	/* (non-Javadoc) @see
	 * com.robotwitter.posting.Preference#generateTweet(java.lang.String) */
	@Override
	public ArrayList<String> generateTweet(String tweet)
	{
		return breakToTweets(tweet, tweetMaxLength);
	}

}
