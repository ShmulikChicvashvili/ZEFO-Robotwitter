/**
 * 
 */

package com.robotwitter.test;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.google.inject.Guice;
import com.google.inject.Injector;

import com.robotwitter.database.MySqlConnectionEstablisherModule;
import com.robotwitter.database.MySqlDBModule;
import com.robotwitter.statistics.UserListenerModule;
import com.robotwitter.twitter.HeavyHittersListener;
import com.robotwitter.twitter.UserTracker;
import com.robotwitter.twitter.UserTrackerModule;




/**
 * @author Itay, Shmulik
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
		Injector injector =
			Guice.createInjector(
				new MySqlConnectionEstablisherModule(),
				new MySqlDBModule(),
				new UserTrackerModule(),
				new UserListenerModule());
		trackedUser = 248335762;
		
		tracker = injector.getInstance(UserTracker.class);
		tracker.setUser(trackedUser);
		
		listener = injector.getInstance(HeavyHittersListener.class);
		listener.setUser(trackedUser);
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
		
		for (int i = 0; i < 12; i++)
		{
			System.out.println(listener.getHeavyHitters().toString());
			try
			{
				Thread.sleep(1000 * 10);
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
