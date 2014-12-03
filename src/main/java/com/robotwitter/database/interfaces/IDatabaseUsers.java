/**
 * 
 */
package com.robotwitter.database.interfaces;

import java.util.ArrayList;

import com.robotwitter.database.primitives.DBUser;
import com.robotwitter.database.primitives.DatabaseType;

/**
 * @author Shmulik
 *
 * The interface for users DB
 * 
 * Created at : 12:53 AM, 2.12.14
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
	public void insert(DBUser user);
	
	
	/**
	 * @param eMail
	 *            The email which you check whether exists or not
	 * @return If exists true else false
	 */
	public boolean isExists(String eMail);
}