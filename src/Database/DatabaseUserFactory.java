/**
 * 
 */
package Database;

/**
 * @author Shmulik
 *
 */
public class DatabaseUserFactory implements DatabaseFactory
{

	/* (non-Javadoc) @see Database.DatabaseFactory#getDatabase() */
	public IDatabase getDatabase()
	{
		// TODO Auto-generated method stub
		return new MySqlDatabaseUser();
	}
}
