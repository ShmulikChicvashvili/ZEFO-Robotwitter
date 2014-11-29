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
			String statementCreate =
				"CREATE TABLE IF NOT EXISTS `yearlyproj_db`.`user` (" //$NON-NLS-1$
					+ "`username` VARCHAR(45) NOT NULL," //$NON-NLS-1$
					+ "`email` VARCHAR(45) NOT NULL," //$NON-NLS-1$
					+ "`password` VARCHAR(45) NOT NULL);"; //$NON-NLS-1$
			this.statement
				.execute(statementCreate);
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/* (non-Javadoc) @see
	 * Database.IDatabase#insert(DatabasePrimitives.DatabaseTypes) */
	public void insert(DatabaseTypes obj) throws Exception
	{
		User u = (User) obj;
		this.preparedStatement =
			this.connect.prepareStatement("insert into ? ( ?, ?, ? ) values ( ?, ?, ? );"); //$NON-NLS-1$
		this.preparedStatement.setString(1, this.table);
		this.preparedStatement.setString(2, this.usernameColumn);
		this.preparedStatement.setString(3, this.eMailColumn);
		this.preparedStatement.setString(4, this.passwordColumn);
		this.preparedStatement.setString(5, u.getUserName());
		this.preparedStatement.setString(6, u.geteMail());
		this.preparedStatement.setString(7, u.getPassword());
		this.preparedStatement.executeUpdate();
	}
	
}
