/**
 *
 */

package com.robotwitter.twitter;


import java.sql.Timestamp;




// TODO: Auto-generated Javadoc
/**
 * The Class TwitterFollower.
 *
 * @author Eyal
 */
public class TwitterFollower
{

	/**
	 * Instantiates a new twitter follower.
	 *
	 * @param followerId
	 *            the follower id
	 * @param name
	 *            the name
	 * @param screenName
	 *            the screen name
	 * @param description
	 *            the description
	 * @param numFollowers
	 *            the num followers
	 * @param numFollowing
	 *            the num following
	 * @param location
	 *            the location
	 * @param numFavorites
	 *            the num favorites
	 * @param language
	 *            the language
	 * @param isCelebrity
	 *            the is celebrity
	 * @param joined
	 *            the joined
	 * @param picture
	 *            the picture
	 */
	public TwitterFollower(
		final long followerId,
		final String name,
		final String screenName,
		final String description,
		final int numFollowers,
		final int numFollowing,
		final String location,
		final int numFavorites,
		final String language,
		final boolean isCelebrity,
		final Timestamp joined,
		final String picture)
	{
		this.followerId = followerId;
		this.name = name;
		this.screenName = screenName;
		this.description = description;
		this.numFollowers = numFollowers;
		this.numFollowing = numFollowing;
		this.location = location;
		this.numFavorites = numFavorites;
		this.language = language;
		this.isCelebrity = isCelebrity;
		this.joined = joined;
		this.picture = picture;
	}


	/* (non-Javadoc) @see java.lang.Object#equals(java.lang.Object) */
	@Override
	public final boolean equals(Object obj)
	{
		if (obj == null || !(obj instanceof TwitterFollower)) { return false; }
		final TwitterFollower follower = (TwitterFollower) obj;
		return followerId == follower.followerId
			&& name.equals(follower.name)
			&& screenName.equals(follower.screenName)
			&& description.equals(follower.description)
			&& numFollowers == follower.numFollowers
			&& numFollowing == follower.numFollowing
			&& location.equals(follower.location)
			&& numFavorites == follower.numFavorites
			&& language.equals(follower.language)
			&& isCelebrity == follower.isCelebrity
			&& joined.equals(follower.joined)
			&& picture.equals(follower.picture);
	}


	/**
	 * Gets the description.
	 *
	 * @return the description of the follower
	 */
	public final String getDescription()
	{
		return description;
	}


	/**
	 * Gets the follower id.
	 *
	 * @return the id of the follower
	 */
	public final long getFollowerId()
	{
		return followerId;
	}


	/**
	 * Gets the checks if is celebrity.
	 *
	 * @return is the follower a celebrity
	 */
	public final boolean getIsCelebrity()
	{
		return isCelebrity;
	}


	/**
	 * Gets the joined.
	 *
	 * @return the date of joining Twitter
	 */
	public final Timestamp getJoined()
	{
		return joined;
	}


	/**
	 * Gets the language.
	 *
	 * @return the language setting of the follower
	 */
	public final String getLanguage()
	{
		return language;
	}


	/**
	 * Gets the location.
	 *
	 * @return the location of the follower
	 */
	public final String getLocation()
	{
		return location;
	}


	/**
	 * Gets the name.
	 *
	 * @return the name of the follower
	 */
	public final String getName()
	{
		return name;
	}


	/**
	 * Gets the num favorites.
	 *
	 * @return the number of tweets favorited by the follower
	 */
	public final int getNumFavorites()
	{
		return numFavorites;
	}


	/**
	 * Gets the num followers.
	 *
	 * @return the number of followers of the follower
	 */
	public final int getNumFollowers()
	{
		return numFollowers;
	}


	/**
	 * Gets the num following.
	 *
	 * @return the number being followed by the follower
	 */
	public final int getNumFollowing()
	{
		return numFollowing;
	}


	/**
	 * Gets the picture.
	 *
	 * @return the picture URL of the follower
	 */
	public final String getPicture()
	{
		return picture;
	}


	/**
	 * Gets the screen name.
	 *
	 * @return the screen name of the follower
	 */
	public final String getScreenName()
	{
		return screenName;
	}


	/**
	 * Sets the description.
	 *
	 * @param description
	 *            the description to set
	 */
	public final void setDescription(String description)
	{
		this.description = description;
	}


	/**
	 * Sets the checks if is celebrity.
	 *
	 * @param isCelebrity
	 *            the isCelebrity to set
	 */
	public final void setIsCelebrity(boolean isCelebrity)
	{
		this.isCelebrity = isCelebrity;
	}


	/**
	 * Sets the joined.
	 *
	 * @param joined
	 *            the joined to set
	 */
	public final void setJoined(Timestamp joined)
	{
		this.joined = joined;
	}


	/**
	 * Sets the language.
	 *
	 * @param language
	 *            the language to set
	 */
	public final void setLanguage(String language)
	{
		this.language = language;
	}


	/**
	 * Sets the location.
	 *
	 * @param location
	 *            the location to set
	 */
	public final void setLocation(String location)
	{
		this.location = location;
	}


	/**
	 * Sets the name.
	 *
	 * @param name
	 *            the name to set
	 */
	public final void setName(String name)
	{
		this.name = name;
	}


	/**
	 * Sets the num favorites.
	 *
	 * @param favorites
	 *            the favorites to set
	 */
	public final void setNumFavorites(int favorites)
	{
		numFavorites = favorites;
	}


	/**
	 * Sets the num followers.
	 *
	 * @param followers
	 *            the followers to set
	 */
	public final void setNumFollowers(int followers)
	{
		numFollowers = followers;
	}


	/**
	 * Sets the num following.
	 *
	 * @param following
	 *            the following to set
	 */
	public final void setNumFollowing(int following)
	{
		numFollowing = following;
	}


	/**
	 * Sets the picture.
	 *
	 * @param picture
	 *            the picture URL to set
	 */
	public final void setPicture(String picture)
	{
		this.picture = picture;
	}


	/**
	 * Sets the screen name.
	 *
	 * @param screenName
	 *            the screenName to set
	 */
	public final void setScreenName(String screenName)
	{
		this.screenName = screenName;
	}



	/** The follower id. */
	private long followerId;

	/** The name. */
	private String name;

	/** The screen name. */
	private String screenName;

	/** The description. */
	private String description;

	/** The num followers. */
	private int numFollowers;

	/** The num following. */
	private int numFollowing;

	/** The location. */
	private String location;

	/** The num favorites. */
	private int numFavorites;

	/** The language. */
	private String language;

	/** The is celebrity. */
	private boolean isCelebrity;

	/** The joined. */
	private Timestamp joined;

	/** The picture. */
	private String picture;
}
