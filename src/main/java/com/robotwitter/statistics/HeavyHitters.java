/**
 *
 */

package com.robotwitter.statistics;


import java.util.ArrayList;
import java.util.Collections;

import com.google.inject.Inject;
import com.google.inject.name.Named;




/**
 * @author Shmulik, Itay
 *
 */
public class HeavyHitters implements IHeavyHitters
{
	/**
	 * @param numOfCounters
	 * @param numOfHeavyHitters
	 */
	@Inject
	public HeavyHitters(
		@Named("Heavy Hitters Counter Number")Integer numOfCounters,@Named("Heavy Hitters Number") Integer numOfHeavyHitters)
	{
		this.numOfCounters = numOfCounters;
		this.numOfHeavyHitters = numOfHeavyHitters;
		
		if (numOfHeavyHitters > numOfCounters)
		{
			this.numOfHeavyHitters = numOfCounters;
		}
		
		counters = new ArrayList<HeavyHittersCounter>();
		for (int i = 0; i < numOfCounters; i++)
		{
			counters.add(new HeavyHittersCounter());
		}
	}
	
	
	/* (non-Javadoc) @see
	 * com.robotwitter.statistics.IHeavyHitters#getCurrentHeavyHitters() */
	@SuppressWarnings("boxing")
	@Override
	public ArrayList<Long> getCurrentHeavyHitters()
	{
		Collections.sort(
			counters,
			(o1, o2) -> Long.signum(o2.getCount() - o1.getCount()));
		
		final ArrayList<Long> $ = new ArrayList<>();
		for (int i = 0; i < numOfHeavyHitters; i++)
		{
			if (counters.get(i).getIsTaken() == false) { return $; }
			$.add(counters.get(i).getUserID());
		}
		return $;
	}
	
	
	/* (non-Javadoc) @see
	 * com.robotwitter.statistics.IHeavyHitters#onDirectMessage(java.lang.Long) */
	@Override
	public void onDirectMessage(Long userID)
	{
		handleEvent(userID, messageAmount, decreasionAmount);
	}
	
	
	/* (non-Javadoc) @see
	 * com.robotwitter.statistics.IHeavyHitters#onFavorite(java.lang.Long) */
	@Override
	public void onFavorite(Long userID)
	{
		handleEvent(userID, favoriteAmount, decreasionAmount);
	}
	
	
	/* (non-Javadoc) @see
	 * com.robotwitter.statistics.IHeavyHitters#onFollow(java.lang.Long) */
	@Override
	public void onFollow(Long userID)
	{
		handleEvent(userID, followAmount, decreasionAmount);
	}
	
	
	/* (non-Javadoc) @see
	 * com.robotwitter.statistics.IHeavyHitters#onMentioned(java.lang.Long) */
	@Override
	public void onMentioned(Long userID)
	{
		handleEvent(userID, mentionAmount, decreasionAmount);
	}
	
	
	/* (non-Javadoc) @see
	 * com.robotwitter.statistics.IHeavyHitters#onRetweetedStatus
	 * (java.lang.Long) */
	@Override
	public void onRetweetedStatus(Long userID)
	{
		handleEvent(userID, retweetAmount, decreasionAmount);
	}
	
	
	private void decreaseCounter(int amount)
	{
		for (final HeavyHittersCounter counter : counters)
		{
			counter.dec(amount);
		}
	}
	
	
	private HeavyHittersCounter getUserCounter(Long userID)
	{
		HeavyHittersCounter $ = null;
		for (final HeavyHittersCounter counter : counters)
		{
			if (counter.getUserID() == userID)
			{
				$ = counter;
			}
		}
		return $;
	}
	
	
	private
		void
		handleEvent(Long userID, int increaseAmount, int decreaseAmount)
	{
		if (increaseCounter(userID, increaseAmount)) { return; }
		decreaseCounter(decreaseAmount);
		increaseCounter(userID, increaseAmount);
	}
	
	
	private boolean increaseCounter(Long userID, int amount)
	{
		final HeavyHittersCounter userCounter = getUserCounter(userID);
		if (userCounter != null)
		{
			userCounter.inc(amount);
			return true;
		}
		for (final HeavyHittersCounter counter : counters)
		{
			if (counter.takeCounter(userID))
			{
				counter.inc(amount);
				return true;
			}
		}
		return false;
	}
	
	
	
	private final static int followAmount = 7;
	
	private final static int favoriteAmount = 5;
	
	private final static int retweetAmount = 4;
	
	private final static int messageAmount = 3;
	
	private final static int mentionAmount = 1;
	
	private final static int decreasionAmount = 4;
	
	int numOfCounters;
	
	int numOfHeavyHitters;
	
	ArrayList<HeavyHittersCounter> counters;
	
}
