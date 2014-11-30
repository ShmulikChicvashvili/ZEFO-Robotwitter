/**
 * 
 */
package com.Robotwitter.Database;

import java.util.ArrayList;

import com.Robotwitter.DatabasePrimitives.DatabaseType;

/**
 * @author Shmulik
 *
 * The database handles saving twitter account connection details
 */
public class MySqlDatabaseTwitterAcounts extends MySqlDatabase
{

	/**
	 * @param conEstablisher The connection handler
	 */
	public MySqlDatabaseTwitterAcounts(ConnectionEstablisher conEstablisher)
	{
		super(conEstablisher);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc) @see com.Robotwitter.Database.IDatabase#insert(com.Robotwitter.DatabasePrimitives.DatabaseType) */
	public void insert(DatabaseType obj)
	{
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc) @see com.Robotwitter.Database.IDatabase#isExists(java.lang.String) */
	public boolean isExists(String eMail)
	{
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc) @see com.Robotwitter.Database.IDatabase#get(java.lang.String) */
	public ArrayList<DatabaseType> get(String eMail)
	{
		// TODO Auto-generated method stub
		return null;
	}	
	
}
