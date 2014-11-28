/**
 * 
 */
package DatabasePrimitives;

/**
 * @author Shmulik
 * 
 * User primitive type
 */
public class User extends DatabaseTypes
{	
	
	/**
	 * The password of the user
	 */
	String password;
	
	/**
	 * @param userName The user name of the user
	 * @param password The password of the user
	 */
	public User(String userName, String password)
	{
		super(userName);
		this.password = password;
	}
	
	/**
	 * @return the password
	 */
	public String getPassword()
	{
		return this.password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password)
	{
		this.password = password;
	}
	
	
}
