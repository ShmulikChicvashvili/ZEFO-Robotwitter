/**
 *
 */

package com.robotwitter.database.interfaces;


import com.robotwitter.database.interfaces.returnValues.SqlError;
import com.robotwitter.database.primitives.DBUser;




/**
 * @author Shmulik and Eyal
 *
 *         The interface for users DB
 *
 *         Created at : 12:53 AM, 2.12.14
 */
public interface IDatabaseUsers
{
	/**
	 * @param eMail
	 *            The email which you want to get is associated user
	 * @return The user associated with the specific email
	 */
	public DBUser get(String eMail);
	
	
	/**
	 * Controls connection to DB
	 *
	 * @param user
	 *            The user to insert
	 *
	 *            Please PAY ATTENTION: do not perform insert before you check
	 *            whether the object you are trying to insert is already exists.
	 *            This could trigger an exception. TODO: Make an Enum that will
	 *            contain the result of the insert action. INSERT_SUCCESS,
	 *            INSERT_ALREADY_EXISTS, INSERT_INVALID_PARAMETERS
	 */
	public SqlError insert(DBUser user);
	
	
	/**
	 * @param eMail
	 *            The email which you check whether exists or not
	 * @return If exists true else false
	 */
	public boolean isExists(String eMail);


	/**
	 * @param user
	 *            The user to update
	 * @return Returns the status code. It could be either SUCCESS,
	 *         DOES_NOT_EXIST, INVALID_PARAMS
	 */
	public SqlError update(DBUser user);
}
