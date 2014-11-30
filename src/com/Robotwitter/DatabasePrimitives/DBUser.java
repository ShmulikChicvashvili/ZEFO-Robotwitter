/**
 * 
 */

package com.Robotwitter.DatabasePrimitives;


/**
 * @author Shmulik
 * 
 *         User primitive type
 */
public class DBUser extends DatabaseType
{
	
	/**
	 * The password of the user
	 */
	String password;
	
	
	
	/**
	 * @param email
	 *            The email of the user
	 * @param password
	 *            The password of the user
	 */
	public DBUser(String email, String password)
	{
		super(email);
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
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password)
	{
		this.password = password;
	}
	
	
	/* (non-Javadoc) @see
	 * DatabasePrimitives.DatabaseType#equals(java.lang.Object) */
	@Override
	public boolean equals(Object obj)
	{
		if (obj != null)
		{
			DBUser u = (DBUser) obj;
			return (this.eMail.equals(u.getEMail()) && this.password.equals(u
				.getPassword()));
		}
		return false;
	}
	
	
	/* (non-Javadoc) @see DatabasePrimitives.DatabaseType#toString() */
	@SuppressWarnings("nls")
	@Override
	public String toString()
	{
		return "Email: " + this.getEMail() + " Password: " + this.getPassword();
	}
	
}
