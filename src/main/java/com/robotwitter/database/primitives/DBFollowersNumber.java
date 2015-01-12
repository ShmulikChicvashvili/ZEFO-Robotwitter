/**
 *
 */

package com.robotwitter.database.primitives;


import java.sql.Timestamp;




/**
 * @author Eyal and Shmulik
 *
 * @author Itay
 * Edited by Itay in 9.1.15 to add the number of followers joined and left.
 */
public class DBFollowersNumber
{
	public DBFollowersNumber(Long twitterId, Timestamp date, int numFollowers, int numJoined, int numLeft)
	{
		this.twitterId = twitterId;
		if (date != null)
		{
			this.date = (Timestamp) date.clone();
		} else
		{
			this.date = null;
		}
		this.numFollowers = numFollowers;
		this.numJoined = numJoined;
		this.numLeft = numLeft;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj == null || !(obj instanceof DBFollowersNumber)) { return false; }
		final DBFollowersNumber other = (DBFollowersNumber) obj;
		return twitterId.equals(other.twitterId)
			&& date.equals(other.date)
			&& numFollowers == other.numFollowers
			&& numJoined == other.numJoined
			&& numLeft == other.numLeft;
	}

	/**
	 * @return the date
	 */
	public Timestamp getDate()
	{
		return date;
	}

	/**
	 * @return the numFollowers
	 */
	public int getNumFollowers()
	{
		return numFollowers;
	}

	/**
	 * @return the numJoined
	 */
	public int getNumJoined()
	{
		return numJoined;
	}

	/**
	 * @return the numLeft
	 */
	public int getNumLeft()
	{
		return numLeft;
	}

	/**
	 * @return the twitterId
	 */
	public Long getTwitterId()
	{
		return twitterId;
	}

	/* (non-Javadoc) @see java.lang.Object#hashCode() */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + numFollowers;
		result = prime * result + numJoined;
		result = prime * result + numLeft;
		result =
			prime * result + ((twitterId == null) ? 0 : twitterId.hashCode());
		return result;
	}
	
	
	/**
	 * @param date
	 *            the date to set
	 */
	public void setDate(Timestamp date)
	{
		this.date = (Timestamp) date.clone();
	}
	
	
	/**
	 * @param numFollowers
	 *            the numFollowers to set
	 */
	public void setNumFollowers(int numFollowers)
	{
		this.numFollowers = numFollowers;
	}
	
	
	/**
	 * @param numJoined the numJoined to set
	 */
	public void setNumJoined(int numJoined)
	{
		this.numJoined = numJoined;
	}
	
	
	/**
	 * @param numLeft the numLeft to set
	 */
	public void setNumLeft(int numLeft)
	{
		this.numLeft = numLeft;
	}
	
	
	/**
	 * @param twitterId
	 *            the twitterId to set
	 */
	public void setTwitterId(Long twitterId)
	{
		this.twitterId = twitterId;
	}
	
	
	
	
	private int numJoined;
	
	private int numLeft;
	
	private Timestamp date;
	
	private int numFollowers;
	
	private Long twitterId;
}
