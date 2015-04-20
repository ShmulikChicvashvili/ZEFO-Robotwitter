/**
 * 
 */

package com.robotwitter.database.interfaces;


import com.robotwitter.database.interfaces.returnValues.SqlError;
import com.robotwitter.database.primitives.DBTweetPostingPreferences;




/**
 * @author Shmulik
 *
 */
public interface IDatabaseTweetPostingPreferences
{
	/**
	 * @param tweetPostingPreferences
	 *            The tweet selected preference
	 * @return The error in case there was
	 */
	public SqlError insert(DBTweetPostingPreferences tweetPostingPreferences);
	
	
	/**
	 * @param email
	 *            The email of the user we want his preference
	 * @return The tweeting preference
	 */
	public DBTweetPostingPreferences get(String email);
	
	
	/**
	 * @param tweetPostingPreferences
	 *            The tweet new selected preference
	 * @return The error in case there was
	 */
	public SqlError update(DBTweetPostingPreferences tweetPostingPreferences);
	
	
	/**
	 * @param email
	 *            The user which we want to know if exists
	 * @return True incase of existence, false otherwise
	 */
	public boolean isExists(String email);
}
