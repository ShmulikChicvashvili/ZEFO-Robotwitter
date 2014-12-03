/**
 *
 */

package com.robotwitter.database;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.robotwitter.database.interfaces.ConnectionEstablisher;




/**
 * @author Shmulik
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
		this.connectionEstablisher = conEstablisher;
		this.schema = this.connectionEstablisher.getSchema();
	}

	/**
	 * statement handler
	 */
	protected Statement statement = null;

	/**
	 * preparedStatement handler
	 */
	protected PreparedStatement preparedStatement = null;

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
	
}
