/**
 * 
 */
package DatabasePrimitives;

/**
 * @author Shmulik
 * 
 * User primitive type
 */
public class DBUser extends DatabaseType
{	
	
	/**
	 * The email of the user
	 */
	String email;
	
	/**
	 * The password of the user
	 */
	String password;
	
	/**
	 * @param userName The user name of the user
	 * @param email The email of the user
	 * @param password The password of the user
	 */
	public DBUser(String userName, String email, String password)
	{
		super(userName);
		this.email = email;
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

	/**
	 * @return the eMail
	 */
	public String getEmail()
	{
		return this.email;
	}

	/**
	 * @param eMail the eMail to set
	 */
	public void setEmail(String eMail)
	{
		this.email = eMail;
	}	
}
