/**
 * 
 */
package com.robotwitter.webapp.view.dashboard;

import java.sql.Timestamp;

/**
 * @author Itay
 *
 */
public class ConnectedAccountInfo
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
	public ConnectedAccountInfo(
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
		final String picture,
		final int unansweredMesseges,
		final int gainedFollowers,
		final int lostFollowers)
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
		this.unansweredMesseges = unansweredMesseges;
		followersGained = gainedFollowers;
		followersLost = lostFollowers;
	}


	/* (non-Javadoc) @see java.lang.Object#equals(java.lang.Object) */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		ConnectedAccountInfo other = (ConnectedAccountInfo) obj;
		if (description == null)
		{
			if (other.description != null) return false;
		} else if (!description.equals(other.description)) return false;
		if (followerId != other.followerId) return false;
		if (followersGained != other.followersGained) return false;
		if (followersLost != other.followersLost) return false;
		if (isCelebrity != other.isCelebrity) return false;
		if (joined == null)
		{
			if (other.joined != null) return false;
		} else if (!joined.equals(other.joined)) return false;
		if (language == null)
		{
			if (other.language != null) return false;
		} else if (!language.equals(other.language)) return false;
		if (location == null)
		{
			if (other.location != null) return false;
		} else if (!location.equals(other.location)) return false;
		if (name == null)
		{
			if (other.name != null) return false;
		} else if (!name.equals(other.name)) return false;
		if (numFavorites != other.numFavorites) return false;
		if (numFollowers != other.numFollowers) return false;
		if (numFollowing != other.numFollowing) return false;
		if (picture == null)
		{
			if (other.picture != null) return false;
		} else if (!picture.equals(other.picture)) return false;
		if (screenName == null)
		{
			if (other.screenName != null) return false;
		} else if (!screenName.equals(other.screenName)) return false;
		if (unansweredMesseges != other.unansweredMesseges) return false;
		return true;
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
	 * @return the followersGained
	 */
	public int getFollowersGained()
	{
		return followersGained;
	}


	/**
	 * @return the followersLost
	 */
	public int getFollowersLost()
	{
		return followersLost;
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
	 * @return the unansweredMesseges
	 */
	public int getUnansweredMesseges()
	{
		return unansweredMesseges;
	}


	/* (non-Javadoc) @see java.lang.Object#hashCode() */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result =
			prime
				* result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + (int) (followerId ^ (followerId >>> 32));
		result = prime * result + followersGained;
		result = prime * result + followersLost;
		result = prime * result + (isCelebrity ? 1231 : 1237);
		result = prime * result + ((joined == null) ? 0 : joined.hashCode());
		result =
			prime * result + ((language == null) ? 0 : language.hashCode());
		result =
			prime * result + ((location == null) ? 0 : location.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + numFavorites;
		result = prime * result + numFollowers;
		result = prime * result + numFollowing;
		result = prime * result + ((picture == null) ? 0 : picture.hashCode());
		result =
			prime * result + ((screenName == null) ? 0 : screenName.hashCode());
		result = prime * result + unansweredMesseges;
		return result;
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
	 * @param followersGained the followersGained to set
	 */
	public void setFollowersGained(int followersGained)
	{
		this.followersGained = followersGained;
	}


	/**
	 * @param followersLost the followersLost to set
	 */
	public void setFollowersLost(int followersLost)
	{
		this.followersLost = followersLost;
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


	/**
	 * @param unansweredMesseges the unansweredMesseges to set
	 */
	public void setUnansweredMesseges(int unansweredMesseges)
	{
		this.unansweredMesseges = unansweredMesseges;
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
	
	private int unansweredMesseges;
	
	private int followersGained;
	
	private int followersLost;
}
