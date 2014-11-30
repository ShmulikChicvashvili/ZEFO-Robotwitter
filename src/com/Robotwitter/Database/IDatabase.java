/**
 * 
 */
package com.Robotwitter.Database;

import java.util.ArrayList;

import com.Robotwitter.DatabasePrimitives.DatabaseType;


/**
 * @author Shmulik
 *
 */
public interface IDatabase
{	
	/**
	 * Controls connection to DB
	 * @param obj The object to insert
	 */
	public void insert(DatabaseType obj);
	
	/**
	 * @param eMail The email which you check whether exists or not
	 * @return If exists true else false
	 */
	public boolean isExists(String eMail);
	
	/**
	 * @param eMail The email which you want to get is DatabaseType
	 * @return The query result
	 */
	public ArrayList<DatabaseType> get(String eMail);
}
