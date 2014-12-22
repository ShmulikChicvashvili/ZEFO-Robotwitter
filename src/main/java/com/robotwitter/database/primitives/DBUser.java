/**
 *
 */

package com.robotwitter.database.primitives;


/**
 * @author Shmulik
 *
 *         User primitive type
 */
public class DBUser extends DatabaseType
{
	
	/**
	 * @param email
	 *            The email of the user
	 * @param password
	 *            The password of the user
	 */
	public DBUser(final String email, final String password)
	{
		super(email);
		this.password = password;
	}


	/* (non-Javadoc) @see
	 * DatabasePrimitives.DatabaseType#equals(java.lang.Object) */
	@Override
	public boolean equals(final Object obj)
	{
		if (obj != null)
		{
			if (obj instanceof DBUser)
			{
				final DBUser u = (DBUser) obj;
				return eMail.toLowerCase().equals(u.getEMail().toLowerCase())
					&& password.equals(u.getPassword());
			}
		}
		return false;
	}
	
	
	/**
	 * @return the password
	 */
	public String getPassword()
	{
		return password;
	}
	
	
	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(final String password)
	{
		this.password = password;
	}
	
	
	/* (non-Javadoc) @see DatabasePrimitives.DatabaseType#toString() */
	@SuppressWarnings("nls")
	@Override
	public String toString()
	{
		return "Email: " + getEMail() + " Password: " + getPassword();
	}



	/**
	 * The password of the user
	 */
	String password;
	
}
