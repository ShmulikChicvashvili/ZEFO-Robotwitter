/**
 * 
 */

package com.robotwitter.classification;


import twitter4j.FilterQuery;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;




/**
 * This class is meant to listen to random tweets from the internet (using
 * Twitter API) and save them for hand classification later.
 * 
 * @author Itay
 */
public class TweetGatherer
{
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		TweetGatherer gatherer = new TweetGatherer();
		gatherer.run();
		
	}
	
	
	public TweetGatherer()
	{
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true);
		cb.setOAuthConsumerKey("wTlnDZNxIsFU65JnBnCyxlEDH");
		cb
			.setOAuthConsumerSecret("soeJE6y7YiYRH9LRqoAf9r1sVgCAokXG3gIC1KYEu9g4cIWmt0");
		cb
			.setOAuthAccessToken("2910240756-G9s6Qepm6ra9A44tnlGSTIszTkUjgiFEoGZpJTc");
		cb
			.setOAuthAccessTokenSecret("1Nag7L1T9kehKzqMRP5U8ZXdUUizTPR0jYw3UUPSvqxYR");
		
		stream = new TwitterStreamFactory(cb.build()).getInstance();
		stream
			.addListener(new TweetStorer(
				"C:\\Users\\Itay\\Desktop\\TechnionDocs\\Semester8\\YearlyProject2\\tweets",
				10000));
	}
	
	
	public void run()
	{
		FilterQuery fq = new FilterQuery();
		
		String keywords[] = { "Microsoft", "Lenovo" };
		
		fq.track(keywords);
		stream.sample();
	}
	
	
	
	private TwitterStream stream;
	
}
