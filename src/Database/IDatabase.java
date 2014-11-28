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
public interface IDatabase
{	
	/**
	 * Controls connection to DB
	 * @param obj The object to insert
	 * @throws Exception e
	 */
	public void insert(DatabaseTypes obj) throws Exception;

	
//	/**
//	 * @return The query result
//	 * @throws Exception SqlExpression
//	 */
//	public DatabaseTypes get() throws Exception;
}
