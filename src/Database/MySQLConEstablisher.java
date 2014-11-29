/**
 * 
 */

package Database;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.google.inject.Inject;
import com.google.inject.name.Named;




/**
 * @author Eyal
 *
 */
public class MySQLConEstablisher implements ConnectionEstablisher
{
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
	
	
	
	/**
	 * @param serverName
	 *            the server we should connect to
	 * @param schema
	 *            the schema on the DB server
	 * 
	 */
	@Inject
	public MySQLConEstablisher(
		@Named("DB Server") String serverName,
		@Named("DB Schema") String schema)
	{
		this.serverName = serverName;
		this.schema = schema;
	}
	
	
	/* (non-Javadoc) @see Database.ConnectionEstablisher#getConnection() */
	@SuppressWarnings("nls")
	public Connection getConnection()
		throws SQLException,
		ClassNotFoundException
	{
		Class.forName("com.mysql.jdbc.Driver");
		Connection $ =
			DriverManager.getConnection("jdbc:mysql://"
				+ this.serverName
				+ "/"
				+ this.schema
				+ "?user=root&password=root");
		
		// Create the schema if it doesn't exist
		java.sql.Statement statement = $.createStatement();
		statement.executeUpdate(this.createSchemaStatement + this.schema);
		statement.close();
		
		return $;
		
	}
	
	
	/* (non-Javadoc) @see Database.ConnectionEstablisher#getSchema() */
	public String getSchema()
	{
		return this.schema;
	}
	
}
