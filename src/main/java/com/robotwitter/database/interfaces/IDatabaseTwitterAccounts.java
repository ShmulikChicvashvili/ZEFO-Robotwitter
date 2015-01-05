/**
 *
 */

package com.robotwitter.database.interfaces;


import java.util.ArrayList;

import com.robotwitter.database.interfaces.returnValues.SqlError;
import com.robotwitter.database.primitives.DBTwitterAccount;




/**
 * @author Shmulik and Eyal
 *
 *         The interface for the DB of twitter accounts
 *
 *         Created at : 1 AM, Monday (Or is Tuesday already?!), 2.12.14
 */
public interface IDatabaseTwitterAccounts
{
	/**
	 * @param userID
	 *            The user to delete
	 * @return Return the status code. It could be either SUCCESS,
	 *         DOES_NOT_EXIST, INVALID_PARAMS
	 */
	public SqlError delete(Long userID);
	
	
	/**
	 * @param userID The user's id
	 * @return The user
	 */
	public DBTwitterAccount get(long userID);
	
	/**
	 * @param eMail
	 *            The email which you want to get is twitter accounts
	 * @return The twitter accounts associated with this specific email
	 */
	public ArrayList<DBTwitterAccount> get(String eMail);
	
	
	/**
	 * Controls connection to DB
	 *
	 * @param twitterAccount
	 *            The twitter account to insert
	 *
	 *            Please PAY ATTENTION: do not perform insert before you check
	 *            whether the object you are trying to insert is already exists.
	 *            This could trigger an exception. TODO: Make an Enum that will
	 *            contain the result of the insert action. INSERT_SUCCESS,
	 *            INSERT_ALREADY_EXISTS, INSERT_INVALID_PARAMETERS
	 */
	public SqlError insert(DBTwitterAccount twitterAccount);


	/**
	 * @param userId
	 *            The user id which you check whether exists or not
	 * @return If exists true else false
	 */
	public boolean isExists(Long userId);


	/**
	 * @param twitterAccount
	 *            The twitter account to update
	 * @return Returns the status code. It could be either SUCCESS,
	 *         DOES_NOT_EXIST, INVALID_PARAMS
	 */
	public SqlError update(DBTwitterAccount twitterAccount);
}
