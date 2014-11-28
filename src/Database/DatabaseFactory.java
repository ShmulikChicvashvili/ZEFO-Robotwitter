/**
 * 
 */
package Database;

/**
 * @author Shmulik
 *
 */
public interface DatabaseFactory
{	
	/**
	 * @return the current database created
	 */
	IDatabase getDatabase();
}
