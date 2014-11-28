/**
 * 
 */
package DatabasePrimitives;

/**
 * @author Shmulik
 *
 * Everything that's going to be kept on the database will be kept
 * for certain user
 */
public class DatabaseTypes
{	
	/**
	 * The user name of the user
	 */
	String userName;

	/**
	 * @param userName The user name of the user
	 */
	public DatabaseTypes(String userName)
	{
		this.userName = userName;
	}

	/**
	 * @return the userName
	 */
	public String getUserName()
	{
		return this.userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName)
	{
		this.userName = userName;
	}
	
	
	
}
