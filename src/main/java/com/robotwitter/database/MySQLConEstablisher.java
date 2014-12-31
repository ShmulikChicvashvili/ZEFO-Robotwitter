/**
 *
 */

package com.robotwitter.database;


import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import com.robotwitter.database.interfaces.ConnectionEstablisher;
import com.robotwitter.database.interfaces.ConnectionPool;




/**
 * @author Shmulik and Eyal
 *
 */
public class MySQLConEstablisher implements ConnectionEstablisher
{
	/**
	 * @param serverName
	 *            The server we should connect to
	 * @param schema
	 *            The schema on the DB server
	 * @throws SQLException
	 *             There was a problem creating the schema.
	 *
	 */
	@Inject
	public MySQLConEstablisher(
		final ConnectionPool connectionPool,
		@Named("DB Schema") final String schema) throws SQLException
	{
		this.connectionPool = connectionPool;
		this.schema = schema;

		createSchema();
	}


	/* (non-Javadoc) @see Database.ConnectionEstablisher#getConnection() */
	@Override
	@SuppressWarnings("nls")
	public final Connection getConnection() throws SQLException
	{
		return connectionPool.getConnection();

	}


	/* (non-Javadoc) @see Database.ConnectionEstablisher#getSchema() */
	@Override
	public final String getSchema()
	{
		return schema;
	}


	private void createSchema() throws SQLException
	{
		try (
			final Connection con = getConnection();
			Statement statement = con.createStatement())
		{
			statement.executeUpdate("CREATE SCHEMA IF NOT EXISTS " + schema); //$NON-NLS-1$
		}
	}
	
	
	
	private final ConnectionPool connectionPool;

	/**
	 * The name of the schema.
	 */
	private final String schema;

}
