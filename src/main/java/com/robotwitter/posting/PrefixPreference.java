/**
 *
 */

package com.robotwitter.posting;


import java.util.ArrayList;




/**
 * @author Shmulik
 *
 * @author Itay
 * 
 *         added that tweet generators only add prefixes if there is more then
 *         one tweet to post. and doesnt add the prefix to the first post.
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
		if ($.size() > 1)
		{
			String firstTweet = $.get(0);
			$=attachPrefixToEachTweet($.subList(1, $.size()), prefix);
			$.add(0, firstTweet);
		}
		return $;
	}
	
	
	
	/**
	 *
	 */
	private final String prefix;
}
