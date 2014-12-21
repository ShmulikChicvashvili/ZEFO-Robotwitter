/**
 *
 */

package com.robotwitter.test;


import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import twitter4j.TwitterFactory;

import com.robotwitter.twitter.NaiveTwitterFollowerRetriever;
import com.robotwitter.twitter.TwitterAppConfiguration;




/**
 * @author Itay
 *
 */
public class NaiveTwitterFollowerRetrieverTest
{

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		final TwitterAppConfiguration conf = new TwitterAppConfiguration();
		final TwitterFactory tf =
			new TwitterFactory(conf.getAppConfiguration());
		retriever = new NaiveTwitterFollowerRetriever(tf);

	}
	
	
	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception
	{
		retriever = null;
	}


	@SuppressWarnings("javadoc")
	@Test
	public void testExistingUser()
	{
		long yogiID = 47973104;
		long shmulikID = 248335762;

		assertEquals(retriever.retrieveFollowersAmount(yogiID), 50);
		assertEquals(retriever.retrieveFollowersAmount(shmulikID), 5);
	}


	@SuppressWarnings("javadoc")
	@Test
	public void testNonExistingUser()
	{
		assertEquals(retriever.retrieveFollowersAmount(-1), -1);
	}
	
	
	
	NaiveTwitterFollowerRetriever retriever;

}
