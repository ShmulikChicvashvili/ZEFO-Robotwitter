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
 *         added that tweet generators only add postfixes if there is more then
 *         one tweet to post, and doesn't add postfix to last tweet.
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
		if($.size() > 1) {
			String lastTweet = $.get($.size()-1);
			$ = attachPostfixToEachTweet($.subList(0, $.size()-1), postfix);
			$.add(lastTweet);
		}
		return $;
	}



	private final String postfix;
}
