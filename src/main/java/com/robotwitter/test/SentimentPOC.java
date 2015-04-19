/**
 * 
 */

package com.robotwitter.test;


import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.robotwitter.classification.TwitterManager;




/**
 * An actual use of the twitterManager class to test that its working. NOT a
 * JUnit, a POC!
 * 
 * @author Itay
 *
 */
public class SentimentPOC
{
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{}
	
	
	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception
	{}
	
	
	@Test
	public void test() throws InterruptedException, IOException
	{
		TwitterManager twitterManager = new TwitterManager();
		twitterManager.performQuery("Israel");
	}
	
}
