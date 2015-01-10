/**
 *
 */

package com.robotwitter.posting;


import java.util.ArrayList;




/**
 * @author Shmulik
 *
 */
public class NumberedPreference extends AbstractPreference
	implements
		Preference
{
	
	/* (non-Javadoc) @see
	 * com.robotwitter.posting.Preference#generateTweet(java.lang.String) */
	@SuppressWarnings("nls")
	@Override
	public ArrayList<String> generateTweet(String tweet)
	{
		if (tweet == null) { return null; }
		
		final ArrayList<String> $ = new ArrayList<>();
		
		final ArrayList<String> tweets =
			breakToTweets(tweet, tweetMaxLength - maximumNumberPrefixSize - 1);
		int prefix = 1;
		for (final String breakedTweet : tweets)
		{
			$.add(generateNumberedPrefix(prefix, tweets.size())
				+ " "
				+ breakedTweet);
			prefix++;
		}
		
		return $;
	}
	
	
	private String generateNumberedPrefix(int current, int max)
	{
		return "(" + current + "/" + max + ")";
	}
	
	
	
	/**
	 * We allow maximum of 9999 tweets, that means that the prefix is of size 4
	 */
	private final int maximumNumberPrefixSize = "(10/10)".length();
}
