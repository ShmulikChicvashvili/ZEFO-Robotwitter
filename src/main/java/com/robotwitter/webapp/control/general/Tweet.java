
package com.robotwitter.webapp.control.general;


/**
 * Represents a Tweet.
 *
 * @author Eyal
 */
public class Tweet
{

	/**
	 * Instantiate a new Tweet.
	 *
	 * @param id
	 *            the tweet's ID
	 * @param text
	 *            the tweet's content (text)
	 * @param name
	 *            the tweet's author's name
	 * @param screenname
	 *            the tweet's author's screenname
	 * @param picture
	 *            the tweet's author's picture
	 */
	public Tweet(
		long id,
		String text,
		String name,
		String screenname,
		String picture)
	{
		super();
		this.id = id;
		this.text = text;
		this.name = name;
		this.screenname = screenname;
		this.picture = picture;
	}


	/** @return The tweet's ID. */
	public final long getID()
	{
		return id;
	}


	/** @return the tweet's author's name. */
	public final String getName()
	{
		return name;
	}


	/**
	 * @return the tweet's author's picture.
	 */
	public final String getPicture()
	{
		return picture;
	}


	/** @return the tweet's author's screenname. */
	public final String getScreenName()
	{
		return screenname;
	}
	
	
	/** @return the tweet's text. */
	public final String getText()
	{
		return text;
	}


	/**
	 * Set the tweet's ID.
	 *
	 * @param id
	 *            the ID to set
	 */
	public final void setID(long id)
	{
		this.id = id;
	}


	/**
	 * Set the tweet's author's name.
	 *
	 * @param name
	 *            the name to set
	 */
	public final void setName(String name)
	{
		this.name = name;
	}


	/**
	 * Set the tweet's author's picture.
	 *
	 * @param picture
	 *            the picture to set
	 */
	public final void setPicture(String picture)
	{
		this.picture = picture;
	}


	/**
	 * Set the tweet's author's screenname.
	 *
	 * @param screenname
	 *            the screenname to set
	 */
	public final void setScreenName(String screenname)
	{
		this.screenname = screenname;
	}
	
	
	/**
	 * Set the tweet's text.
	 *
	 * @param text
	 *            the text to set
	 */
	public final void setText(String text)
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
	private String screenname;

	/** The tweeter's picture. */
	private String picture;
}
