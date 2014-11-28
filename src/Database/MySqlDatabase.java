/**
 * 
 */

package Database;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;




/**
 * @author Shmulik
 *
 */
public class MySqlDatabase implements IDatabase
{
	private Connection connect = null;
	
	
	
	/* (non-Javadoc) @see Database.IDatabase#connect() */
	@Override
	public void connect() throws Exception
	{
		try
		{
			// this will load the MySQL driver
			Class.forName("com.mysql.jdbc.Driver"); //$NON-NLS-1$
			this.connect =
				DriverManager.getConnection("jdbc:mysql://localhost/feedback?" //$NON-NLS-1$
					+ "user=sqluser&password=sqluserpw"); //$NON-NLS-1$
		} catch (Exception e)
		{
			throw e;
		}
	}
	
}
