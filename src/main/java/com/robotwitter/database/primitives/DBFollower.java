package com.robotwitter.database.primitives;

import java.sql.Timestamp;

import org.apache.commons.lang3.StringEscapeUtils;

/**
 * @author Amir and Shmulik
 */

public class DBFollower {
	public DBFollower(final long followerId, final String name,
			final String screenName, final String description,
			final int followers, final int following, final String location,
			final int favorites, final String language,
			final boolean isCelebrity, final Timestamp joined, final String picture) {
		this.followerId = followerId;
		this.name = name;
		this.screenName = screenName;
		this.description = description;
		this.followers = followers;
		this.following = following;
		this.location = location;
		this.favorites = favorites;
		this.language = language;
		this.isCelebrity = isCelebrity;
		this.joined = joined;
		this.picture = picture;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof DBFollower)) {
			return false;
		}
		final DBFollower follower = (DBFollower) obj;
		return followerId == follower.followerId && name.equals(follower.name)
				&& screenName.equals(follower.screenName)
				&& description.equals(follower.description)
				&& followers == follower.followers
				&& following == follower.following
				&& location.equals(follower.location)
				&& favorites == follower.favorites
				&& language.equals(follower.language)
				&& isCelebrity == follower.isCelebrity
				&& joined.equals(follower.joined)
				&& picture.equals(follower.picture);
	}

	/**
	 * @return the description of the follower
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return the number of tweets favorited by the follower
	 */
	public int getFavorites() {
		return favorites;
	}

	/**
	 * @return the id of the follower
	 */
	public long getFollowerId() {
		return followerId;
	}

	/**
	 * @return the number of followers of the follower
	 */
	public int getFollowers() {
		return followers;
	}

	/**
	 * @return the number being followed by the follower
	 */
	public int getFollowing() {
		return following;
	}

	/**
	 * @return is the follower a celebrity
	 */
	public boolean getIsCelebrity() {
		return isCelebrity;
	}

	/**
	 * @return the date of joining Twitter
	 */
	public Timestamp getJoined() {
		return joined;
	}

	/**
	 * @return the language setting of the follower
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * @return the location of the follower
	 */
	public String getLocation() {
		return location;
	}
	
	/**
	 * @return the name of the follower
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the picture URL of the follower
	 */
	public String getPicture() {
		return picture;
	}
	
	/**
	 * @return the screen name of the follower
	 */
	public String getScreenName() {
		return screenName;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @param favorites
	 *            the favorites to set
	 */
	public void setFavorites(int favorites) {
		this.favorites = favorites;
	}

	/**
	 * @param followers
	 *            the followers to set
	 */
	public void setFollowers(int followers) {
		this.followers = followers;
	}

	/**
	 * @param following
	 *            the following to set
	 */
	public void setFollowing(int following) {
		this.following = following;
	}

	/**
	 * @param isCelebrity
	 *            the isCelebrity to set
	 */
	public void setIsCelebrity(boolean isCelebrity) {
		this.isCelebrity = isCelebrity;
	}

	/**
	 * @param joined
	 *            the joined to set
	 */
	public void setJoined(Timestamp joined) {
		this.joined = joined;
	}

	/**
	 * @param language
	 *            the language to set
	 */
	public void setLanguage(String language) {
		this.language = language;
	}
	
	/**
	 * @param location
	 *            the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}
	

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param picture
	 *            the picture URL to set
	 */
	public void setPicture(String picture) {
		this.picture = picture;
	}
	
	/**
	 * @param screenName
	 *            the screenName to set
	 */
	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}

	/* (non-Javadoc) @see java.lang.Object#toString() */
	@Override
	public String toString() {
		return StringEscapeUtils.escapeCsv(((Long) followerId).toString()) + "," +
			StringEscapeUtils.escapeCsv(name) + "," + 
			StringEscapeUtils.escapeCsv(screenName) + "," + 
			StringEscapeUtils.escapeCsv(description) + "," + 
			StringEscapeUtils.escapeCsv(((Integer) followers).toString()) + "," + 
			StringEscapeUtils.escapeCsv(((Integer) following).toString()) + "," + 
			StringEscapeUtils.escapeCsv(location) + "," + 
			StringEscapeUtils.escapeCsv(((Integer) favorites).toString()) + "," +
			StringEscapeUtils.escapeCsv(language) + "," + 
			StringEscapeUtils.escapeCsv(((Boolean) isCelebrity).toString()) + "," + 
			StringEscapeUtils.escapeCsv(joined.toString()) + "," + 
			StringEscapeUtils.escapeCsv(picture);
	}

	private long followerId;
	private String name;
	private String screenName;
	private String description;
	private int followers;
	private int following;
	private String location;
	private int favorites;
	private String language;
	private boolean isCelebrity;
	private Timestamp joined;
	private String picture;
}
