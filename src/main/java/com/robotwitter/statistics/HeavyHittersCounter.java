/**
 *
 */

package com.robotwitter.statistics;


/**
 * @author Shmulik, Itay
 *
 */
public class HeavyHittersCounter
{
	HeavyHittersCounter()
	{
		isTaken = false;
		userID = -1;
		count = 0;
	}


	public boolean dec(long amount)
	{
		if (!isTaken) { return false; }
		count -= amount;
		if (count <= 0)
		{
			count = 0;
			userID = -1;
			isTaken = false;
		}
		return true;
	}
	
	
	/**
	 * @return the count
	 */
	public long getCount()
	{
		return count;
	}
	
	
	/**
	 * @return the isTaken
	 */
	public boolean getIsTaken()
	{
		return isTaken;
	}


	/**
	 * @return the userID
	 */
	public long getUserID()
	{
		return userID;
	}


	public boolean inc(long amount)
	{
		if (isTaken)
		{
			count += amount;
			return true;
		}
		return false;
	}


	public boolean takeCounter(long userID)
	{
		if (isTaken) { return false; }
		isTaken = true;
		this.userID = userID;
		count = 0;
		return true;
	}



	private boolean isTaken;

	private long userID;

	private long count;

}
