/**
 *
 */

package com.robotwitter.posting;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;




/**
 * @author Shmulik
 *
 */
public abstract class AbstractPreference
{
	
	/**
	 * @param charAt
	 * @return
	 */
	private static boolean isWhitespace(char ch)
	{
		return ch == ' ' || ch == '\t' || ch == '\r' || ch == '\n';
	}
	
	
	/**
	 * @param s
	 *            The collection contains the strings
	 * @param delimiter
	 *            The string to put between to string
	 * @return The joined string
	 */
	private static String join(Collection<?> s, String delimiter)
	{
		final StringBuilder builder = new StringBuilder();
		final Iterator<?> iter = s.iterator();
		while (iter.hasNext())
		{
			builder.append(iter.next());
			if (!iter.hasNext())
			{
				break;
			}
			builder.append(delimiter);
		}
		return builder.toString();
	}
	
	
	/**
	 * @param tweets
	 *            The tweets list
	 * @param postfix
	 *            The postfix to set
	 * @return The postfixed tweets
	 */
	@SuppressWarnings("nls")
	protected static ArrayList<String> attachPostfixToEachTweet(
		List<String> tweets,
		String postfix)
	{
		if (tweets == null || tweets.contains(null) || postfix == null) { return null; }
		
		final ArrayList<String> $ = new ArrayList<>();
		
		for (final String tweet : tweets)
		{
			final String postfixedTweet = tweet + " " + postfix;
			$.add(postfixedTweet);
		}
		
		return $;
	}
	
	
	/**
	 * @param list
	 *            The tweet list
	 * @param prefix
	 *            The prefix to set
	 * @return The prefixed tweets
	 */
	@SuppressWarnings("nls")
	protected static ArrayList<String> attachPrefixToEachTweet(
		List<String> list,
		String prefix)
	{
		if (list == null || list.contains(null) || prefix == null) { return null; }
		
		final ArrayList<String> $ = new ArrayList<>();
		
		for (final String tweet : list)
		{
			final String prefixedTweet = prefix + " " + tweet;
			$.add(prefixedTweet);
		}
		
		return $;
	}
	
	
	/**
	 * @param tweet
	 *            The tweet we want to break into several tweets
	 * @param tweetMaxLength
	 *            The length of each tweet we want to generate
	 * @return The tweets divided
	 */
	@SuppressWarnings("nls")
	protected static ArrayList<String> breakToTweets(
		String tweet,
		int tweetMaxLength)
	{
		if (tweet == null) { return null; }
		
		final ArrayList<String> $ = new ArrayList<String>();
		if (tweetMaxLength >= tweet.length())
		{
			$.add(tweet);
			return $;
		}
		
		int endIndex = tweetMaxLength;
		while (endIndex >= 0 && !isWhitespace(tweet.charAt(endIndex)))
		{
			endIndex--;
		}
		$.add(tweet.substring(0, endIndex));
		$.addAll(breakToTweets(
			tweet.substring(endIndex + 1, tweet.length()),
			tweetMaxLength));
		return $;
	}
	
	
	
	// This is the old version, DO NOT DELETE!
	// protected static ArrayList<String> breakToTweets(
	// String tweet,
	// int tweetMaxLength)
	// {
	// if (tweet == null) { return null; }
	//
	// final ArrayList<String> $ = new ArrayList<>();
	//
	// ArrayList<String> tmp = new ArrayList<>();
	// final String[] tweetSplitedByWhiteSpaces = tweet.split("\\s+");
	// for (final String s : tweetSplitedByWhiteSpaces)
	// {
	// if (s.length() > tweetMaxLength)
	// {
	// if (!tmp.isEmpty())
	// {
	// $.add(join(tmp, " "));
	// }
	// $.add(s.substring(0, tweetMaxLength));
	// tmp = new ArrayList<>();
	// continue;
	// }
	// tmp.add(s);
	// if (join(tmp, " ").length() <= tweetMaxLength)
	// { /* We already added s */} else
	// {
	// tmp.remove(s);
	// $.add(join(tmp, " "));
	// tmp = new ArrayList<>();
	// tmp.add(s);
	// }
	// }
	//
	// if (!tmp.isEmpty())
	// {
	// $.add(join(tmp, " "));
	// }
	//
	// return $;
	// }
	
	/**
	 * Twitter's maximum tweet length
	 */
	protected final int tweetMaxLength = 140;
}
