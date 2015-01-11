/**
 * 
 */

package com.robotwitter.test;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

import com.robotwitter.posting.NumberedPreference;
import com.robotwitter.posting.Preference;
import com.robotwitter.posting.TweetPostService;
import com.robotwitter.posting.TweetPostService.ReturnStatus;
import com.robotwitter.twitter.TwitterAccount;
import com.robotwitter.twitter.TwitterAppConfiguration;




/**
 * @author Itay, Shmulik
 *
 */
public class TweetingPostingTest
{
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		Preference pref = new NumberedPreference();
		twitterPoster = new TweetPostService(pref);
		
		TwitterFactory tf =
			new TwitterFactory(
				new TwitterAppConfiguration().getUserConfiguration());
		Twitter connector = tf.getInstance();
		
		connector.setOAuthAccessToken(new AccessToken(
			"248335762-hzlfNjWvIn1OJgV2d6szoVQxVFVfdlAcR36eB6Pa",
			"3PnhpehlVKN7o7RDSekE8tOW35fEAz22AARUoFsQToKyo"));
		
		shmulikAccout = new TwitterAccount(tf);
		shmulikAccout.setTwitter(connector);
		shmulikAccout.setAttached(true);
	}
	
	
	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception
	{
		twitterPoster = null;
		shmulikAccout = null;
	}
	
	
	@Test
	public void testSuccess()
	{
		String tweet =
			"Hi my name is Merabi Shmulik Chicvashvili, currently working for a "
				+ "Robotwitter ltd. We are developing the future of tweeting and shit."
				+ "McGonogol ya' wrinkley bitch ill gnaw your arm off harry yr a wizzard.";
		twitterPoster.setTweet(tweet);
		twitterPoster.setTwitterAccount(shmulikAccout);
		assertEquals(twitterPoster.post(), ReturnStatus.SUCCESS);
	}
	
	
	
	private TwitterAccount shmulikAccout;
	
	private TweetPostService twitterPoster;
	
}
