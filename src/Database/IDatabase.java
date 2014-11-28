/**
 * 
 */
package Database;


/**
 * @author Shmulik
 *
 */
public interface IDatabase
{	
	/**
	 * Controls connection to DB
	 * @throws Exception SQLexception
	 */
	public void connect() throws Exception;
}
