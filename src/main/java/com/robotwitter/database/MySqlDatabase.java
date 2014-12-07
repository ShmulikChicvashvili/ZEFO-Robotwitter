/**
 *
 */

package com.robotwitter.database;


import java.sql.ResultSet;

import com.robotwitter.database.interfaces.ConnectionEstablisher;




/**
 * @author Shmulik and Eyal
 *
 */
public abstract class MySqlDatabase
{
	/**
	 * C'tor of general settings
	 *
	 * @param conEstablisher
	 *            A connection establisher for the database
	 */
	public MySqlDatabase(final ConnectionEstablisher conEstablisher)
	{
		connectionEstablisher = conEstablisher;
		if (connectionEstablisher.getSchema().startsWith("`")) //$NON-NLS-1$
		{
			schema = conEstablisher.getSchema();
		} else
		{
			schema = "`" + connectionEstablisher.getSchema() + "`"; //$NON-NLS-1$ //$NON-NLS-2$
		}
	}
	
	
	
	/**
	 * resultSet handler
	 */
	protected ResultSet resultSet = null;
	
	/**
	 * The database name
	 */
	protected final String schema;
	
	/**
	 * The connection establisher with the database
	 */
	protected final ConnectionEstablisher connectionEstablisher;
	
	@SuppressWarnings("boxing")
	protected final Integer insertErrorCode = 1062;
	
}
