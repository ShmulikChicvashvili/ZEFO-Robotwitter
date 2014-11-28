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
	@Override
	public IDatabase getDatabase()
	{
		return new MySqlDatabaseUser();
	}
}
