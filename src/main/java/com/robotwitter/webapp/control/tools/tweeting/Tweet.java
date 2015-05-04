/**
 *
 */

package com.robotwitter.webapp.control.tools.tweeting;


/**
 * @author Eyal
 *
 */
public class Tweet
{

	/**
	 * @param id
	 * @param text
	 * @param name
	 * @param screenName
	 * @param picture
	 */
	public Tweet(
		long id,
		String text,
		String name,
		String screenName,
		String picture)
	{
		super();
		this.id = id;
		this.text = text;
		this.name = name;
		this.screenName = screenName;
		this.picture = picture;
	}


	/**
	 * @return the id
	 */
	public long getId()
	{
		return id;
	}


	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}


	/**
	 * @return the picture
	 */
	public String getPicture()
	{
		return picture;
	}


	/**
	 * @return the screenName
	 */
	public String getScreenName()
	{
		return screenName;
	}


	/**
	 * @return the text
	 */
	public String getText()
	{
		return text;
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
	 * @param name
	 *            the name to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}


	/**
	 * @param picture
	 *            the picture to set
	 */
	public void setPicture(String picture)
	{
		this.picture = picture;
	}


	/**
	 * @param screenName
	 *            the screenName to set
	 */
	public void setScreenName(String screenName)
	{
		this.screenName = screenName;
	}


	/**
	 * @param text
	 *            the text to set
	 */
	public void setText(String text)
	{
		this.text = text;
	}



	/** The tweet's id. */
	private long id;

	/** The tweet's text. */
	private String text;

	/** The tweeter's name. */
	private String name;

	/** The tweeter's screen name. */
	private String screenName;

	/** The tweeter's picture. */
	private String picture;
}
