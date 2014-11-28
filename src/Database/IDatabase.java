/**
 * 
 */
package Database;

import DatabasePrimitives.DatabaseTypes;

/**
 * @author Shmulik
 *
 */
public interface IDatabase
{	
	/**
	 * Controls connection to DB
	 */
	public void connect();
	
	/**
	 * @param dbType insert primitive to the database
	 */
	public void insert(DatabaseTypes dbType);
}
