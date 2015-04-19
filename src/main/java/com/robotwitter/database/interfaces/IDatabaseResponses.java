/**
 * 
 */

package com.robotwitter.database.interfaces;


import java.util.ArrayList;

import com.robotwitter.database.interfaces.returnValues.SqlError;
import com.robotwitter.database.primitives.DBResponse;




/**
 * @author Itay
 *
 */
public interface IDatabaseResponses
{
	
	/**
	 * Delete the information of a response from the database
	 * 
	 * @param responseId
	 *            The response to delete
	 * @return Return the status code. It could be either SUCCESS,
	 *         DOES_NOT_EXIST, INVALID_PARAMS
	 */
	public SqlError deleteResponse(long responseId);
	
	
	/**
	 * Retrive the response primitive with the given id from the database
	 * 
	 * @param responseId
	 *            The id of the response to get
	 * @return The response associated with this Id, or null if it does not exist
	 */
	public DBResponse get(long responseId);
	
	
	/**
	 * returns all bad responses attached to this user (meaning, posted on his
	 * timeline)
	 * 
	 * @param userId
	 *            The id of the user you want to get it's responses
	 * @return The DBResponse type of the responses posted to this user
	 */
	public ArrayList<DBResponse> getBadResponsesOfUser(long userId);
	
	
	/**
	 * returns all good responses attached to this user (meaning, posted on his
	 * timeline)
	 * 
	 * @param userId
	 *            The id of the user you want to get it's responses
	 * @return The DBResponse type of the responses posted to this user
	 */
	public ArrayList<DBResponse> getGoodResponsesOfUser(long userId);
	
	
	/**
	 * returns all responses attached to this user (meaning, posted on his
	 * timeline)
	 * 
	 * @param userId
	 *            The id of the user you want to get it's responses
	 * @return The DBResponse type of the responses posted to this user
	 */
	public ArrayList<DBResponse> getResponsesOfUser(long userId);
	
	
	/**
	 * @param response
	 *            The response you want to insert into the database
	 * @return whether the insert was successful. It could be either SUCCESS,
	 *         ALREADY_EXIST, INVALID_PARAMS
	 */
	public SqlError insert(DBResponse response);
	
	
	/**
	 * @param followerId
	 *            The id of the user to check
	 * @return whether a follower with this id exists
	 */
	public boolean isExists(long followerId);
	
	
	/**
	 * @param followedId
	 *            The id of the followed in the connection
	 * @param followerId
	 *            The id of the follower in the connection
	 * @return whether a follower with this id exists
	 */
	public boolean isExists(long followedId, long followerId);
	
	
	/**
	 * @param response
	 *            The response to update
	 * @return Returns the status code. It could be either SUCCESS,
	 *         DOES_NOT_EXIST, INVALID_PARAMS
	 */
	public SqlError update(DBResponse response);
}
