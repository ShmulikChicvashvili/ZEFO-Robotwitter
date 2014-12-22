/**
 *
 */

package com.robotwitter.database.primitives;


import java.sql.Timestamp;




/**
 * @author Eyal and Shmulik
 *
 */
public class DBFollowersNumber
{
	public DBFollowersNumber(Long twitterId, Timestamp date, int numFollowers)
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
	}
	
	
	/* (non-Javadoc) @see
	 * com.robotwitter.database.primitives.DatabaseType#equals(java.lang.Object) */
	@Override
	public boolean equals(Object obj)
	{
		if (obj == null || !(obj instanceof DBFollowersNumber)) { return false; }
		final DBFollowersNumber other = (DBFollowersNumber) obj;
		return twitterId.equals(other.twitterId)
			&& date.equals(other.date)
			&& numFollowers == other.numFollowers;
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
	 * @return the twitterId
	 */
	public Long getTwitterId()
	{
		return twitterId;
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
	 * @param twitterId
	 *            the twitterId to set
	 */
	public void setTwitterId(Long twitterId)
	{
		this.twitterId = twitterId;
	}
	
	
	/* (non-Javadoc) @see java.lang.Object#toString() */
	@Override
	public String toString()
	{
		return "DBFollowersNumber [date=" //$NON-NLS-1$
			+ date
			+ ", numFollowers=" //$NON-NLS-1$
			+ numFollowers
			+ "]"; //$NON-NLS-1$
	}
	
	
	
	private Timestamp date;
	
	private int numFollowers;
	
	private Long twitterId;
}
