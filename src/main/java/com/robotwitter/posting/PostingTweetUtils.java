/**
 *
 */

package com.robotwitter.posting;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;




/**
 * @author Shmulik
 *
 */
public class PostingTweetUtils
{
	/**
	 * @param tweet
	 *            The tweet we want to break into several tweets
	 * @param tweetMaxLength
	 *            The length of each tweet we want to generate
	 * @return The tweets divided
	 */
	@SuppressWarnings("nls")
	public static ArrayList<String> breakToTweets(
		String tweet,
		int tweetMaxLength)
	{
		if (tweet == null) { return null; }

		final ArrayList<String> $ = new ArrayList<>();

		ArrayList<String> tmp = new ArrayList<>();
		final String[] tweetSplitedByWhiteSpaces = tweet.split(" ");
		for (String s : tweetSplitedByWhiteSpaces)
		{
			if (s.length() > tweetMaxLength)
			{
				s = s.substring(0, tweetMaxLength - 1);
			}
			if ((join(tmp, " ") + s).length() <= tweetMaxLength)
			{
				tmp.add(s);
			} else
			{
				$.add(join(tmp, " "));
				tmp = new ArrayList<>();
			}
		}
		
		$.add(join(tmp, " "));

		return $;
	}
	
	
	/**
	 * @param longUrl
	 *            The url which want to compress
	 * @return The compressed url
	 */
	@SuppressWarnings("nls")
	public static String getTinyUrl(String longUrl)
	{
		if (longUrl == null) { return null; }

		String $ = "";

		final String tinyUrlServiceUri =
			"http://tinyurl.com/api-create.php?url=";
		try
		{
			final URL url = new URL(tinyUrlServiceUri + longUrl);
			final BufferedReader in =
				new BufferedReader(new InputStreamReader(url.openStream()));

			String str;
			while ((str = in.readLine()) != null)
			{
				$ += str;
			}

			in.close();

		} catch (final MalformedURLException e)
		{
			return null;
		} catch (final IOException e)
		{
			return null;
		}
		
		return $;
	}
	
	
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
}
