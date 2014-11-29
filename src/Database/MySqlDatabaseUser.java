/**
 * 
 */

package Database;


import DatabasePrimitives.DBUser;
import DatabasePrimitives.DatabaseType;

import com.google.inject.Inject;




/**
 * @author Shmulik
 *
 */
public class MySqlDatabaseUser extends MySqlDatabase
{
	
	/**
	 * The table name
	 */
	final private String table = this.schema + "." + "users"; //$NON-NLS-1$ //$NON-NLS-2$
	
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
	 * 
	 * @param conEstablisher
	 *            A connection establisher for the database
	 */
	@Inject
	public MySqlDatabaseUser(ConnectionEstablisher conEstablisher)
	{
		super(conEstablisher);
		try
		{
			String statementCreate =
				"CREATE TABLE IF NOT EXISTS `yearlyproj_db`.`users` (" //$NON-NLS-1$
					+ "`username` VARCHAR(45) NOT NULL," //$NON-NLS-1$
					+ "`email` VARCHAR(45) NOT NULL," //$NON-NLS-1$
					+ "`password` VARCHAR(45) NOT NULL," //$NON-NLS-1$
					+ "PRIMARY KEY (`username`));"; //$NON-NLS-1$
			this.statement.execute(statementCreate);
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/* (non-Javadoc) @see
	 * Database.IDatabase#insert(DatabasePrimitives.DatabaseTypes) */
	@SuppressWarnings("nls")
	public void insert(DatabaseType obj) throws Exception
	{
		DBUser u = (DBUser) obj;
		this.preparedStatement =
			this.con.prepareStatement("INSERT INTO "
				+ this.table
				+ " ("
				+ this.usernameColumn
				+ ","
				+ this.eMailColumn
				+ ","
				+ this.passwordColumn
				+ ") VALUES ( ?, ?, ? );"); 
		this.preparedStatement.setString(1, u.getUserName());
		this.preparedStatement.setString(2, u.getEmail());
		this.preparedStatement.setString(3, u.getPassword());
		this.preparedStatement.executeUpdate();
	}
}
