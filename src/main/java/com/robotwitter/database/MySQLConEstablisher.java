/**
 *
 */

package com.robotwitter.database;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.robotwitter.database.interfaces.ConnectionEstablisher;




/**
 * @author Eyal
 *
 */
public class MySQLConEstablisher implements ConnectionEstablisher
{
	/**
	 * @param serverName
	 *            the server we should connect to
	 * @param schema
	 *            the schema on the DB server
	 *
	 */
	@Inject
	public MySQLConEstablisher(
		@Named("DB Server") final String serverName,
		@Named("DB Schema") final String schema)
	{
		this.serverName = serverName;
		this.schema = schema;
	}
	
	
	/* (non-Javadoc) @see Database.ConnectionEstablisher#getConnection() */
	@Override
	@SuppressWarnings("nls")
	public Connection getConnection()
		throws SQLException,
		ClassNotFoundException
	{
		Class.forName("com.mysql.jdbc.Driver");
		final Connection $ =
			DriverManager.getConnection("jdbc:mysql://"
				+ serverName
				+ "/"
				+ schema
				+ "?user=root&password=root");

		// Create the schema if it doesn't exist
		final java.sql.Statement statement = $.createStatement();
		statement.executeUpdate(createSchemaStatement + schema);
		statement.close();

		return $;

	}
	
	
	/* (non-Javadoc) @see Database.ConnectionEstablisher#getSchema() */
	@Override
	public String getSchema()
	{
		return schema;
	}



	/**
	 *
	 */
	private final String serverName;
	
	/**
	 *
	 */
	private final String schema;
	
	/**
	 *
	 */
	@SuppressWarnings("nls")
	final private String createSchemaStatement = "CREATE SCHEMA IF NOT EXISTS ";

}
