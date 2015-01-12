
package com.robotwitter.webapp.control.tools;


import java.util.LinkedList;
import java.util.List;




/**
 * Implementation of the tweeting controller interface.
 *
 * @author Doron Hogery
 * @author Amir Drutin
 */
public class TweetingController implements ITweetingController
{
	
	@Override
	public final List<String> breakTweet(String tweet)
	{
		List<String> tweets = new LinkedList<>();
		while (tweet.length() > 25)
		{
			tweets.add(tweet.substring(0, 25) + " >>>");
			tweet = tweet.substring(25);
		}
		if (!tweet.isEmpty())
		{
			tweets.add(tweet);
		}
		
		return tweets;
	}
	
	
	@Override
	public final List<String> previewTweet(String tweet)
	{
		List<String> tweets = new LinkedList<>();
		while (tweet.length() > 100)
		{
			tweets.add(tweet.substring(0, 100) + " >>>");
			tweet = tweet.substring(100);
		}
		if (!tweet.isEmpty())
		{
			tweets.add(tweet);
		}
		
		return tweets;
	}
	
	
	
	/** Serialisation version unique ID. */
	private static final long serialVersionUID = 1L;
}
