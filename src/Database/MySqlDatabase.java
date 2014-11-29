/**
 * 
 */

package Database;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;




/**
 * @author Shmulik
 *
 */
public abstract class MySqlDatabase implements IDatabase
{
	/**
	 * connection handler
	 */
	protected Connection con = null;
	
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
	 * C'tor of general settings
	 * @param conEstablisher A connection establisher for the database
	 */
	public MySqlDatabase(ConnectionEstablisher conEstablisher)
	{
		this.schema = conEstablisher.getSchema();
		try
		{
			this.con = conEstablisher.getConnection();
			this.statement = this.con.createStatement();
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
