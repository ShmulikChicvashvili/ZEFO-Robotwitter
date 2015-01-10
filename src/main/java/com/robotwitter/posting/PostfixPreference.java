/**
 *
 */

package com.robotwitter.posting;


import java.util.ArrayList;




/**
 * @author Shmulik
 *
 */
public class PostfixPreference extends AbstractPreference implements Preference
{
	
	/**
	 * @param postfix
	 *            The postfix to set to each tweet
	 */
	public PostfixPreference(String postfix)
	{
		this.postfix = postfix;
	}
	
	
	/* (non-Javadoc) @see
	 * com.robotwitter.posting.Preference#generateTweet(java.lang.String) */
	@Override
	public ArrayList<String> generateTweet(String tweet)
	{
		if (tweet == null) { return null; }
		ArrayList<String> $ = new ArrayList<>();
		$ = breakToTweets(tweet, tweetMaxLength - postfix.length() - 1);
		$ = attachPostfixToEachTweet($, postfix);
		return $;
	}



	private final String postfix;
}
