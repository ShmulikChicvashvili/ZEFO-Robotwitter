/**
 * 
 */

package com.robotwitter.database.primitives;


import java.sql.Timestamp;




/**
 * @author Itay
 *
 */
public class DBResponse
{
	
	/**
	 * @param userID
	 * @param id
	 * @param timestamp
	 * @param text
	 * @param classify
	 */
	public DBResponse(
		long userID,
		long id,
		Timestamp timestamp,
		String text,
		String classify,
		Boolean answered)
	{
		this.userID = userID;
		this.id = id;
		this.timestamp = timestamp;
		this.text = text;
		this.classify = classify;
		this.answered = answered;
	}
	
	
	/* (non-Javadoc) @see java.lang.Object#equals(java.lang.Object) */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		DBResponse other = (DBResponse) obj;
		if (answered == null)
		{
			if (other.answered != null) return false;
		} else if (!answered.equals(other.answered)) return false;
		if (classify == null)
		{
			if (other.classify != null) return false;
		} else if (!classify.equals(other.classify)) return false;
		if (id != other.id) return false;
		if (text == null)
		{
			if (other.text != null) return false;
		} else if (!text.equals(other.text)) return false;
		if (timestamp == null)
		{
			if (other.timestamp != null) return false;
		} else if (!timestamp.equals(other.timestamp)) return false;
		if (userID != other.userID) return false;
		return true;
	}
	
	
	/**
	 * @return the answered
	 */
	public Boolean getAnswered()
	{
		return answered;
	}
	
	
	/**
	 * @return the classify
	 */
	public String getClassify()
	{
		return classify;
	}
	
	
	/**
	 * @return the id
	 */
	public long getId()
	{
		return id;
	}
	
	
	/**
	 * @return the text
	 */
	public String getText()
	{
		return text;
	}
	
	
	/**
	 * @return the timestamp
	 */
	public Timestamp getTimestamp()
	{
		return timestamp;
	}
	
	
	/**
	 * @return the userID
	 */
	public Long getUserID()
	{
		return userID;
	}
	
	
	/* (non-Javadoc) @see java.lang.Object#hashCode() */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result =
			prime * result + ((answered == null) ? 0 : answered.hashCode());
		result =
			prime * result + ((classify == null) ? 0 : classify.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((text == null) ? 0 : text.hashCode());
		result =
			prime * result + ((timestamp == null) ? 0 : timestamp.hashCode());
		result = prime * result + (int) (userID ^ (userID >>> 32));;
		return result;
	}
	
	
	/**
	 * @param answered
	 *            the answered to set
	 */
	public void setAnswered(Boolean answered)
	{
		this.answered = answered;
	}
	
	
	/**
	 * @param classify
	 *            the classify to set
	 */
	public void setClassify(String classify)
	{
		this.classify = classify;
	}
	
	
	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(long id)
	{
		this.id = id;
	}
	
	
	/**
	 * @param text
	 *            the text to set
	 */
	public void setText(String text)
	{
		this.text = text;
	}
	
	
	/**
	 * @param timestamp
	 *            the timestamp to set
	 */
	public void setTimestamp(Timestamp timestamp)
	{
		this.timestamp = timestamp;
	}
	
	
	/**
	 * @param userID
	 *            the userID to set
	 */
	public void setUserID(Long userID)
	{
		this.userID = userID;
	}
	
	
	
	private long userID;
	
	private long id;
	
	private Timestamp timestamp;
	
	private String text;
	
	private String classify;
	
	private Boolean answered;
	
}
