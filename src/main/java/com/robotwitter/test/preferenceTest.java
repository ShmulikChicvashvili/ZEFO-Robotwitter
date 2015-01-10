/**
 * 
 */

package com.robotwitter.test;


import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.robotwitter.posting.BasicPreference;
import com.robotwitter.posting.NumberedPreference;
import com.robotwitter.posting.PostfixPreference;
import com.robotwitter.posting.PrefixPreference;




/**
 * @author Itay
 * 
 *         a unit test for the preferences classes.
 *
 */
public class preferenceTest
{
	
	/**
	 * @throws java.lang.Exception
	 */
	@SuppressWarnings("nls")
	@Before
	public void setUp() throws Exception
	{
		basicPref = new BasicPreference();
		prefixPref = new PrefixPreference("(Continued) >>");
		postfixPref = new PostfixPreference(">>>");
		numberedPref = new NumberedPreference();
		
		shortTweet = "This tweet is really short, way less then 140 chars!";
		longTweet =
			"This tweet is Really Really long, wanna see how long? "
				+ "thiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiis"
				+ " long! Haha just kidding, its way longer then that! its"
				+ " actually this long!";
	}
	
	
	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception
	{
		basicPref = null;
		prefixPref = null;
		postfixPref = null;
		numberedPref = null;
		shortTweet = null;
		longTweet = null;
	}
	
	
	@Test
	public void testLongTweet()
	{
		ArrayList<String> basicPrefBreakdown =
			basicPref.generateTweet(longTweet);
		assertNotNull(basicPrefBreakdown);
		assertEquals(basicPrefBreakdown.size(), 2);
		assertEquals(
			basicPrefBreakdown.get(0),
			"This tweet is Really Really"
				+ " long, wanna see how long? thiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii"
				+ "iiiiiiiiiiiiiiiiiiiiiis long! Haha just kidding,");
		assertEquals(
			basicPrefBreakdown.get(1),
			"its way longer then that! its actually this long!");
		
		ArrayList<String> prefixPrefBreakdown =
			prefixPref.generateTweet(longTweet);
		assertNotNull(prefixPrefBreakdown);
		assertEquals(prefixPrefBreakdown.size(), 2);
		assertEquals(
			prefixPrefBreakdown.get(0),
			"This tweet is Really Really long, wanna see how long?"
				+ " thiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiis long! Haha");
		assertEquals(
			prefixPrefBreakdown.get(1),
			"(Continued) >> just kidding, its way longer then that! its actually this long!");
		
		ArrayList<String> postfixPrefBreakdown =
			postfixPref.generateTweet(longTweet);
		assertNotNull(postfixPrefBreakdown);
		assertEquals(postfixPrefBreakdown.size(), 2);
		assertEquals(
			postfixPrefBreakdown.get(0),
			"This tweet is Really Really long, wanna see how long? thiiiiiiiiiii"
				+ "iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiis long! Haha just >>>");
		assertEquals(
			postfixPrefBreakdown.get(1),
			"kidding, its way longer then that! its actually this long!");
		
		ArrayList<String> numberedPrefBreakdown =
			numberedPref.generateTweet(longTweet);
		assertNotNull(numberedPrefBreakdown);
		assertEquals(numberedPrefBreakdown.size(), 2);
		assertEquals(
			numberedPrefBreakdown.get(0),
			"(1/2) This tweet is Really Really long, wanna see how long? thiiiii"
			+ "iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiis long! Haha just");
		assertEquals(
			numberedPrefBreakdown.get(1),
			"(2/2) kidding, its way longer then that! its actually this long!");
	}
	
	
	@Test
	public void testNullTweet()
	{
		assertNull(basicPref.generateTweet(null));
		
		assertNull(prefixPref.generateTweet(null));
		
		assertNull(postfixPref.generateTweet(null));
		
		assertNull(numberedPref.generateTweet(null));
	}
	
	
	@Test
	public void testShortTweet()
	{
		ArrayList<String> basicPrefBreakdown =
			basicPref.generateTweet(shortTweet);
		assertNotNull(basicPrefBreakdown);
		assertEquals(basicPrefBreakdown.size(), 1);
		assertEquals(basicPrefBreakdown.get(0), shortTweet);
		
		ArrayList<String> prefixPrefBreakdown =
			prefixPref.generateTweet(shortTweet);
		assertNotNull(prefixPrefBreakdown);
		assertEquals(prefixPrefBreakdown.size(), 1);
		assertEquals(prefixPrefBreakdown.get(0), shortTweet);
		
		ArrayList<String> postfixPrefBreakdown =
			postfixPref.generateTweet(shortTweet);
		assertNotNull(postfixPrefBreakdown);
		assertEquals(postfixPrefBreakdown.size(), 1);
		assertEquals(postfixPrefBreakdown.get(0), shortTweet);
		
		ArrayList<String> numberedPrefBreakdown =
			numberedPref.generateTweet(shortTweet);
		assertNotNull(numberedPrefBreakdown);
		assertEquals(numberedPrefBreakdown.size(), 1);
		assertEquals(numberedPrefBreakdown.get(0), shortTweet);
	}
	
	
	
	private String shortTweet;
	
	private String longTweet;
	
	private BasicPreference basicPref;
	
	private PrefixPreference prefixPref;
	
	private PostfixPreference postfixPref;
	
	private NumberedPreference numberedPref;
	
}
