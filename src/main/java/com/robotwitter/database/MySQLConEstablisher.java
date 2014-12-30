/**
 *
 */

package com.robotwitter.database;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.mysql.jdbc.Statement;

import com.robotwitter.database.interfaces.ConnectionEstablisher;




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
		@Named("DB Server") final String serverName,
		@Named("DB Schema") final String schema) throws SQLException
	{
		this.serverName = serverName;
		this.schema = schema;

		createSchema();
	}


	/* (non-Javadoc) @see Database.ConnectionEstablisher#getConnection() */
	@Override
	@SuppressWarnings("nls")
	public final Connection getConnection() throws SQLException
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
		} catch (final ClassNotFoundException e)
		{
			e.printStackTrace();
			throw new RuntimeException("Can't create mysql.jdbc driver");
		}

		final Connection $ =
			DriverManager.getConnection("jdbc:mysql://" + serverName + "/"
			// + schema
				+ "?user=root&password=root");

		return $;

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
			Connection con = getConnection();
			Statement statement = (Statement) con.createStatement())
		{
			statement.executeUpdate("CREATE SCHEMA IF NOT EXISTS " + schema); //$NON-NLS-1$
		}
	}



	/**
	 * The server name.
	 */
	private final String serverName;

	/**
	 * The name of the schema.
	 */
	private final String schema;

}
