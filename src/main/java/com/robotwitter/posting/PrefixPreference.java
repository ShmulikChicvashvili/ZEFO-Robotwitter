/**
 *
 */

package com.robotwitter.posting;


import java.util.ArrayList;




/**
 * @author Shmulik
 *
 */
public class PrefixPreference extends AbstractPreference implements Preference
{
	
	/**
	 * @param prefix
	 *            The prefix to set to each tweet
	 */
	public PrefixPreference(String prefix)
	{
		this.prefix = prefix;
	}


	/* (non-Javadoc) @see
	 * com.robotwitter.posting.Preference#generateTweet(java.lang.String) */
	@Override
	public ArrayList<String> generateTweet(String tweet)
	{
		if (tweet == null) { return null; }
		ArrayList<String> $ = new ArrayList<>();
		$ = breakToTweets(tweet, tweetMaxLength - prefix.length() - 1);
		$ = attachPrefixToEachTweet($, prefix);
		return $;
	}



	/**
	 *
	 */
	private final String prefix;
}
