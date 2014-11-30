/**
 *
 */

package com.robotwitter.database;


import java.util.ArrayList;

import com.robotwitter.database.primitives.DatabaseType;




/**
 * @author Shmulik
 *
 */
public interface IDatabase
{
	/**
	 * @param eMail
	 *            The email which you want to get is DatabaseType
	 * @return The query result
	 */
	public ArrayList<DatabaseType> get(String eMail);
	
	
	/**
	 * Controls connection to DB
	 * 
	 * @param obj
	 *            The object to insert
	 *
	 *            Please PAY ATTENTION: do not perform insert before you check
	 *            whether the object you are trying to insert is already exists.
	 *            This could trigger an exception. TODO: Make an Enum that will
	 *            contain the result of the insert action. INSERT_SUCCESS,
	 *            INSERT_ALREADY_EXISTS, INSERT_INVALID_PARAMETERS
	 */
	public void insert(DatabaseType obj);
	
	
	/**
	 * @param eMail
	 *            The email which you check whether exists or not
	 * @return If exists true else false
	 */
	public boolean isExists(DatabaseType obj);
}
