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
	 * The email of the user
	 */
	String eMail;
	
	/**
	 * The password of the user
	 */
	String password;
	
	/**
	 * @param userName The user name of the user
	 * @param eMail The email of the user
	 * @param password The password of the user
	 */
	public User(String userName, String eMail, String password)
	{
		super(userName);
		this.eMail = eMail;
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
	public String geteMail()
	{
		return this.eMail;
	}

	/**
	 * @param eMail the eMail to set
	 */
	public void seteMail(String eMail)
	{
		this.eMail = eMail;
	}	
}
