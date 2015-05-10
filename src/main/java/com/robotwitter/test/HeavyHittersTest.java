/**
 *
 */

package com.robotwitter.test;


import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.robotwitter.statistics.HeavyHitters;




/**
 * @author Itay, Shmulik
 *
 */
public class HeavyHittersTest
{
	public enum EventType
	{
		FOLLOW,
		FAVORITE,
		RETWEET,
		MESSAGE,
		MENTION
	}
	
	
	
	private class StreamGenerator
	{
		public StreamGenerator(
			long seed,
			int userNumber,
			long bigUser,
			long secondBiggestUser)
		{
			rand = new Random(seed);
			
			this.userNumber = userNumber;
			this.bigUser = bigUser;
			secondBiggest = secondBiggestUser;
			userTable = new ArrayList<Long>(userNumber * 1000);
			eventTable = new ArrayList<EventType>();
			for (int i = 0; i < userNumber * 1000; i++)
			{
				final Long user = (long) rand.nextInt(userNumber);
				userTable.add(i, user + 1);
			}
			
			for (final EventType event : EventType.values())
			{
				final int eventProb = rand.nextInt(100);
				for (int i = 0; i < eventProb; i++)
				{
					eventTable.add(event);
				}
			}
			
			while (eventTable.size() < 500)
			{
				eventTable.add(EventType.MENTION);
			}
		}


		public EventType generateEventType()
		{
			final int eventIndex = rand.nextInt(500);
			return eventTable.get(eventIndex);
		}


		public Long generateUserEvent()
		{
			if (rand.nextBoolean() == true) { return bigUser; }
			
			if (rand.nextBoolean() == true) { return secondBiggest; }
			final int userIndex = rand.nextInt(userNumber * 1000);
			return userTable.get(userIndex);
		}



		private final long secondBiggest;

		private final long bigUser;
		
		private final ArrayList<EventType> eventTable;
		
		private final Random rand;
		
		private final int userNumber;
		
		private final ArrayList<Long> userTable;
	}



	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		hh = new HeavyHitters(COUNTERS_NUMBER, HH_NUMBER);
		stream =
			new StreamGenerator(
				SEED,
				USER_NUMBER,
				BIGGEST_USER,
				SECOND_BIGGEST_USER);
	}


	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception
	{
		hh = null;
		stream = null;
	}


	@Test
	public void testEmptyStream()
	{
		assertEquals(0, hh.getCurrentHeavyHitters().size());
	}
	
	
	@Test
	public void testLargeAmountOfFollowers()
	{
		for (long i = 1; i < COUNTERS_NUMBER + 1; i++)
		{
			for (int j = 0; j < i; j++)
			{
				hh.onRetweetedStatus(i);
			}
		}
		
		// Add another one to the hh, meaning we will decrease now
		final Long newUser = (long) (COUNTERS_NUMBER + 1);
		hh.onFollow(newUser);
		// After this action, HH_NUMBER should replace user 1, we add some
		// action to our new user, so we can see it.
		for (int i = 0; i < COUNTERS_NUMBER; i++)
		{
			hh.onRetweetedStatus(newUser);
		}
		
		assertEquals(HH_NUMBER, hh.getCurrentHeavyHitters().size());
		final ArrayList<Long> heavyHitters = hh.getCurrentHeavyHitters();
		
		for (int i = 0; i < HH_NUMBER; i++)
		{
			if (i == 0)
			{
				assertEquals(
					heavyHitters.get(i).longValue(),
					newUser.longValue());
			} else
			{
				assertEquals(heavyHitters.get(i).longValue(), COUNTERS_NUMBER
					+ 1
					- i);
			}
		}
	}
	
	
	@Test
	public void testOneBigOneSmall()
	{
		final Long bigUser = (long) 10;
		final Long smallUser = (long) 20;
		
		for (int i = 0; i < 10; i++)
		{
			hh.onRetweetedStatus(bigUser);
		}
		
		hh.onRetweetedStatus(smallUser);
		
		assertEquals(2, hh.getCurrentHeavyHitters().size());
		assertEquals(bigUser, hh.getCurrentHeavyHitters().get(0));
		assertEquals(smallUser, hh.getCurrentHeavyHitters().get(1));
	}
	
	
	@Test
	public void testOneEvent()
	{
		final Long user = (long) 17;
		hh.onFollow(user);
		
		assertEquals(1, hh.getCurrentHeavyHitters().size());
		assertEquals(user.longValue(), hh
			.getCurrentHeavyHitters()
			.get(0)
			.longValue());
	}
	
	
	@Test
	public void testOnlyBiggest()
	{
		for (long i = 1; i < HH_NUMBER + 10; i++)
		{
			for (int j = 0; j < i; j++)
			{
				hh.onFavorite(i);
			}
		}
		assertEquals(HH_NUMBER, hh.getCurrentHeavyHitters().size());
		final ArrayList<Long> heavyHitters = hh.getCurrentHeavyHitters();
		
		for (int i = 0; i < HH_NUMBER; i++)
		{
			assertEquals(heavyHitters.get(i).longValue(), HH_NUMBER + 9 - i);
		}
	}
	
	
	@Test
	public void testOnStream()
	{
		for (int i = 0; i < 10000; i++)
		{
			switch (stream.generateEventType())
			{
				case MENTION:
					hh.onMentioned(stream.generateUserEvent());
					break;
				case MESSAGE:
					hh.onDirectMessage(stream.generateUserEvent());
					break;
				case RETWEET:
					hh.onRetweetedStatus(stream.generateUserEvent());
					break;
				case FAVORITE:
					hh.onFavorite(stream.generateUserEvent());
					break;
				case FOLLOW:
					hh.onFollow(stream.generateUserEvent());
					break;
			}
		}
		final ArrayList<Long> heavyHitters = hh.getCurrentHeavyHitters();
		assertEquals(HH_NUMBER, heavyHitters.size());
		assertEquals(BIGGEST_USER, heavyHitters.get(0).longValue());
		assertEquals(SECOND_BIGGEST_USER, heavyHitters.get(1).longValue());
		
	}



	private static final long SECOND_BIGGEST_USER = 50;
	
	private static final long BIGGEST_USER = 100;
	
	private StreamGenerator stream;
	
	private static final long SEED = 0;
	
	private static final int USER_NUMBER = 1000;
	
	public static int COUNTERS_NUMBER = 200;
	
	public static int HH_NUMBER = 10;
	
	HeavyHitters hh;
	
}
