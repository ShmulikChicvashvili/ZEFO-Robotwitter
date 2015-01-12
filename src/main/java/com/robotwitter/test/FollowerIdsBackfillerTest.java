/**
 * 
 */

package com.robotwitter.test;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;

import com.robotwitter.database.MySqlDBModule;
import com.robotwitter.statistics.UserListenerModule;
import com.robotwitter.twitter.FollowerIdsBackfiller;
import com.robotwitter.twitter.IUserBackfiller;
import com.robotwitter.twitter.UserTrackerModule;




/**
 * @author Itay
 *
 */
public class FollowerIdsBackfillerTest
{
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		Injector injector =
			Guice.createInjector(
				new DatabaseTestModule(),
				new MySqlDBModule(),
				new UserTrackerModule(),
				new UserListenerModule());
		trackedUser = (long) 248335762;
		
		backfiller = injector.getInstance(FollowerIdsBackfiller.class);
		backfiller.setUser(trackedUser);
	}
	
	
	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception
	{
		backfiller.stop();
		backfiller = null;
		trackedUser = (long) 0;
	}
	
	
	@Test
	public void test()
	{
		backfiller.start();
		try
		{
			Thread.sleep(1000 * 10);
		} catch (InterruptedException e)
		{}
	}
	
	
	
	private IUserBackfiller backfiller;
	
	private Long trackedUser;
	
}
