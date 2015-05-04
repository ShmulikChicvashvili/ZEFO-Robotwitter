/**
 * 
 */

package com.robotwitter.classification;


import java.io.IOException;
import java.util.ArrayList;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;




/**
 * This class is meant to be a POC to test the classifier, without ANY use in
 * the actual app. The class purpose is to listen to random tweets containing a
 * string and print them with their classification.
 * 
 * @author Itay
 */
public class TwitterManager
{
	public TwitterManager()
	{
		cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true);
		cb.setOAuthConsumerKey("wTlnDZNxIsFU65JnBnCyxlEDH");
		cb
			.setOAuthConsumerSecret("soeJE6y7YiYRH9LRqoAf9r1sVgCAokXG3gIC1KYEu9g4cIWmt0");
		cb
			.setOAuthAccessToken("2910240756-G9s6Qepm6ra9A44tnlGSTIszTkUjgiFEoGZpJTc");
		cb
			.setOAuthAccessTokenSecret("1Nag7L1T9kehKzqMRP5U8ZXdUUizTPR0jYw3UUPSvqxYR");
		twitter = new TwitterFactory(cb.build()).getInstance();
		sentClassifier =
			new SentimentClassifier(
				"C:\\Users\\Itay\\Desktop\\TechnionDocs\\Semester8\\YearlyProject2\\classifier.txt");
	}
	
	
	public void performQuery(String inQuery)
		throws InterruptedException,
		IOException
	{
		Query query = new Query(inQuery);
		query.setCount(100);
		try
		{
			int count = 0;
			QueryResult r;
			do
			{
				r = twitter.search(query);
				ArrayList ts = (ArrayList) r.getTweets();
				for (int i = 0; i < ts.size() && count < LIMIT; i++)
				{
					count++;
					Status t = (Status) ts.get(i);
					String text = t.getText();
					System.out.println("Text: " + text);
					String name = t.getUser().getScreenName();
					System.out.println("User: " + name);
					String sent = sentClassifier.classify(t.getText());
					System.out.println("Sentiment: " + sent);
					System.out.println("");
				}
			} while ((query = r.nextQuery()) != null && count < LIMIT);
		} catch (TwitterException te)
		{
			System.out.println("Couldn't connect: " + te);
		}
	}
	
	
	
	SentimentClassifier sentClassifier;
	
	int LIMIT = 500; // the number of retrieved tweets
	
	ConfigurationBuilder cb;
	
	Twitter twitter;
}
