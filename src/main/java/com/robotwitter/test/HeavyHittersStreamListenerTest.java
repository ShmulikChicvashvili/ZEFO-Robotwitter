/**
 * 
 */

package com.robotwitter.test;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import twitter4j.TwitterStreamFactory;

import com.robotwitter.database.interfaces.IDatabaseTwitterAccounts;
import com.robotwitter.database.primitives.DBTwitterAccount;
import com.robotwitter.statistics.HeavyHitters;
import com.robotwitter.twitter.HeavyHittersListnerFactory;
import com.robotwitter.twitter.TwitterAppConfiguration;
import com.robotwitter.twitter.UserTracker;
import com.robotwitter.twitter.HeavyHittersListener;




/**
 * @author Itay
 *
 */
public class HeavyHittersStreamListenerTest
{
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		TwitterAppConfiguration conf = new TwitterAppConfiguration();
		TwitterStreamFactory factory =
			new TwitterStreamFactory(conf.getUserConfiguration());
		
		trackedUser = 248335762;
		
		IDatabaseTwitterAccounts accountsDB =
			Mockito.mock(IDatabaseTwitterAccounts.class);
		Mockito.when(accountsDB.get(trackedUser)).thenReturn(
			new DBTwitterAccount(
				"shmulikjkech@gmail.com",
				"248335762-hzlfNjWvIn1OJgV2d6szoVQxVFVfdlAcR36eB6Pa",
				"3PnhpehlVKN7o7RDSekE8tOW35fEAz22AARUoFsQToKyo",
				trackedUser));
		tracker = new UserTracker(factory, accountsDB, trackedUser);
		
		listener =
			new HeavyHittersListener(new HeavyHittersListnerFactory(
				new HeavyHitters(200, 10)), trackedUser);
	}
	
	
	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception
	{
		trackedUser = -1;
		tracker.stopTrack();
		listener = null;
		tracker = null;
	}
	
	
	@Test
	public void testTrack()
	{
		System.out
			.println("Every 10 seconds, we will print the ids of the 10 biggest users using heavy hitters algorithm, for 2 minutes");
		tracker.addListener(listener);
		tracker.beginTrack();
		
		for(int i=0;i<12;i++) {
			System.out.println(listener.getHeavyHitters().toString());
			try
			{
				Thread.sleep(1000*10);
			} catch (InterruptedException e)
			{
				// All is fine, this wakes us up!
			}
		}
	}
	
	
	
	private long trackedUser;
	
	private UserTracker tracker;
	
	private HeavyHittersListener listener;
	
}
