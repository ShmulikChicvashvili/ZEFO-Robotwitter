package com.robotwitter.database.interfaces;

import java.util.ArrayList;

import com.robotwitter.database.interfaces.returnValues.SqlError;
import com.robotwitter.database.primitives.DBFollower;

public interface IDatabaseFollowers {

	/**
	 * @param followerId
	 * 				The id of the follower to get
	 * @return The follower associated with this Id
	 */
	public DBFollower get(long followerId);
	
	/**
	 * @param userId
	 * 			The id of the user you want to get it's followers ids
	 * @return The ids of the followers of this user
	 */
	public ArrayList<Long> getFollowersId(String userId);
	
	/**
	 * @param name
	 *            The actual name of the followers you want to get
	 * @return The followers associated with this specific name
	 */
	public ArrayList<DBFollower> getByName(String name);

	/**
	 * @param screenName
	 *            The screen name of the followers you want to get
	 * @return The followers associated with this specific screen name
	 */
	public ArrayList<DBFollower> getByScreenName(String screenName);

	/**
	 * @param follower
	 *            The follower you want to insert into the database
	 * @return whether the insert was successful. It could be either SUCCESS,
	 *         DOES_NOT_EXIST, INVALID_PARAMS
	 */
	public SqlError insert(DBFollower follower);
	
	/**
	 * @param userId
	 * 			The id of the user being followed
	 * @param followerId
	 * 			The id of the follower
	 * @return whether the insert was successful. It could be either SUCCESS,
	 *         DOES_NOT_EXIST, INVALID_PARAMS
	 */
	public SqlError insert(long userId, long followerId);

	/**
	 * @param name
	 *            the actual name of the followers to check
	 * @return whether a follower with this name exists
	 */
	public boolean isExistsByName(String name);

	/**
	 * @param ScreenName
	 *            The screen name of the follower to check
	 * @return whether a follower with this screen name exists
	 */
	public boolean isExistsByScreenName(String ScreenName);

	/**
	 * @param follower
	 *            The follower to update
	 * @return Returns the status code. It could be either SUCCESS,
	 *         DOES_NOT_EXIST, INVALID_PARAMS
	 */
	public SqlError update(DBFollower follower);
}
