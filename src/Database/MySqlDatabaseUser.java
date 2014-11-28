/**
 * 
 */

package Database;


import DatabasePrimitives.DatabaseTypes;
import DatabasePrimitives.User;




/**
 * @author Shmulik
 *
 */
public class MySqlDatabaseUser extends MySqlDatabase
{
	
	/**
	 * The table name
	 */
	final private String table = this.schema + "." + "user"; //$NON-NLS-1$ //$NON-NLS-2$
	
	/**
	 * username column
	 */
	final private String usernameColumn = "username"; //$NON-NLS-1$
	
	/**
	 * email column
	 */
	final private String eMailColumn = "email"; //$NON-NLS-1$
	
	/**
	 * password column
	 */
	final private String passwordColumn = "password"; //$NON-NLS-1$
	
	
	
	/**
	 * Create table of users statement
	 */
	
	/**
	 * C'tor for MySql db of users
	 */
	public MySqlDatabaseUser()
	{
		super();
		try
		{
			this.preparedStatement =
				this.connect
					.prepareStatement("CREATE TABLE IF NOT EXISTS" //$NON-NLS-1$
						+ " ? (? STRING NOT NULL, ? STRING NOT NULL, ? STRING NOT NULL)"); //$NON-NLS-1$
			this.preparedStatement.setString(1, this.table);
			this.preparedStatement.setString(2, this.usernameColumn);
			this.preparedStatement.setString(3, this.eMailColumn);
			this.preparedStatement.setString(4, this.passwordColumn);
			this.preparedStatement.execute();
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/* (non-Javadoc) @see
	 * Database.IDatabase#insert(DatabasePrimitives.DatabaseTypes) */
	@Override
	public void insert(DatabaseTypes obj) throws Exception
	{
		User u = (User) obj;
		this.preparedStatement =
			this.connect.prepareStatement("INSERT INTO ? VALUES ?, ?, ?"); //$NON-NLS-1$
		this.preparedStatement.setString(1, this.table);
		this.preparedStatement.setString(2, u.getUserName());
		this.preparedStatement.setString(3, u.geteMail());
		this.preparedStatement.setString(4, u.getPassword());
		this.preparedStatement.execute();
	}
	
}
