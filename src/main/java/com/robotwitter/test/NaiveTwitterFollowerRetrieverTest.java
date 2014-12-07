/**
 * 
 */

package com.robotwitter.test;


import static org.junit.Assert.*;

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
	
	NaiveTwitterFollowerRetriever retriever;
	
	
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		final TwitterAppConfiguration conf = new TwitterAppConfiguration();
		final TwitterFactory tf = new TwitterFactory(conf.getConfiguration());
		this.retriever = new NaiveTwitterFollowerRetriever(tf);
		
	}
	
	
	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception
	{
		this.retriever = null;
	}
	
	
	@Test
	public void testExistingUser()
	{
		long yogiID = 47973104;
		long shmulikID = 248335762;
		
		assertEquals(this.retriever.retrieveFollowersAmount(yogiID), 50);
		assertEquals(this.retriever.retrieveFollowersAmount(shmulikID), 5);
	}
	
	
	@Test
	public void testNonExistingUser()
	{
		assertEquals(this.retriever.retrieveFollowersAmount(-1), -1);
	}
	
}