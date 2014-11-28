/**
 * 
 */

package Database;


import java.sql.Connection;
import java.sql.DriverManager;
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
	protected Connection connect = null;
	
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
	protected final String schema = "yearly_proj_db"; //$NON-NLS-1$
	
	/**
	 * Creating the database statement
	 */
	final private String createSchemaStatement = "CREATE SCHEMA IF NOT EXISTS " //$NON-NLS-1$
		+ this.schema;
	
	
	
	/**
	 * C'tor of general settings
	 */
	public MySqlDatabase()
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver"); //$NON-NLS-1$
			this.connect =
				DriverManager.getConnection("jdbc:mysql://localhost/yearly_proj_db?" //$NON-NLS-1$
					+ "user=root&password=root"); //$NON-NLS-1$
			this.statement = this.connect.createStatement();
			this.statement.executeQuery(this.createSchemaStatement);
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
