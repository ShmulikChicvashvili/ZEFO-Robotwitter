/**
 * 
 */

package com.robotwitter.test;


import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.robotwitter.twitter.TwitterAppConfiguration;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;




/**
 * @author Itay
 *
 *         a proof of concept for displaying the new followers of an account,
 *         assuming you have the most recent 5000 followers from yesterday.
 */
public class GetFollowersPOC
{
	/**
	 * 
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		idfUserID = 18576537;
		
		final TwitterAppConfiguration conf = new TwitterAppConfiguration();
		twitterConnctor =
			(new TwitterFactory(conf.getAppConfiguration())).getInstance();
		
		todayFollowers =
			twitterConnctor
				.getFollowersIDs(idfUserID, -1, 5000)
				.getIDs();
		
		File file = new File("yesterdayFollowers.txt"); // this file was taken
														// at date 8.12 at 9:30
		BufferedReader reader = null;
		
		List<Long> list = new ArrayList<>();
		reader = new BufferedReader(new FileReader(file));
		String text = null;
		while ((text = reader.readLine()) != null)
		{
			list.add(Long.parseLong(text));
		}
		yesterdayFollowers = new long[list.size()];
		for (int i = 0; i < list.size(); i++)
		{
			yesterdayFollowers[i] = list.get(i);
		}
	}
	
	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception
	{
		idfUserID = 0;
		todayFollowers = null;
		yesterdayFollowers = null;
		twitterConnctor = null;
	}
	
	@SuppressWarnings("nls")
	@Test
	public void test()
	{
		int newFollowersAmount = findNewFollowers();
		if (newFollowersAmount < 5000)
		{
			System.out.println("There are likely "
				+ newFollowersAmount
				+ " new followers");
		} else
		{
			System.out.println("need to load more pages!");
			fail();
		}
	}
	
	/**
	 * 
	 */
	private int findNewFollowers()
	{
		for (long yesterdayFollower : yesterdayFollowers)
		{
			for (int j = 0; j < todayFollowers.length; j++)
			{
				if (yesterdayFollower == todayFollowers[j]) { return j; }
			}
		}
		return 5001;
	}
	
	
	
	/**
	 * idf's user id, this will be our study case since it has a large-ish
	 * account (408k followers) and doesn't change drastically.
	 */
	private long idfUserID;
	
	
	/**
	 * our connector to twitter.
	 */
	Twitter twitterConnctor;
	
	
	/**
	 * Yesterday followers, will be read from a file.
	 */
	long[] yesterdayFollowers;
	
	
	/**
	 * Today's followers, will be gathered from twitter via REST API
	 */
	long[] todayFollowers;
	
}
