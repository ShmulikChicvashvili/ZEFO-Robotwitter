/**
 * 
 */
package Database;

import DatabasePrimitives.DatabaseType;


/**
 * @author Shmulik
 *
 */
public interface IDatabase
{	
	/**
	 * Controls connection to DB
	 * @param obj The object to insert
	 * @throws Exception e
	 */
	public void insert(DatabaseType obj);
	
	/**
	 * @param eMail The email which you check whether exists or not
	 * @return If exists true else false
	 * @throws Exception e
	 */
	public boolean isExists(String eMail);
	
	/**
	 * @param eMail The email which you want to get is DatabaseType
	 * @return The query result
	 * @throws Exception SqlExpression
	 */
	public DatabaseType get(String eMail);
}
