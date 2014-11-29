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
public class DatabaseType
{	
	/**
	 * The user name of the user
	 */
	String eMail;

	/**
	 * @param email The email of the user
	 */
	public DatabaseType(String email)
	{
		this.eMail = email;
	}

	/**
	 * @return the email
	 */
	public String getEMail()
	{
		return this.eMail;
	}

	/**
	 * @param email the Email to set
	 */
	public void setEMail(String email)
	{
		this.eMail = email;
	}

	/* (non-Javadoc) @see java.lang.Object#equals(java.lang.Object) */
	@SuppressWarnings("cast")
	@Override
	public boolean equals(Object obj)
	{
		return this.equals((String) obj);
	}

	/* (non-Javadoc) @see java.lang.Object#toString() */
	@Override
	public String toString()
	{
		return this.eMail;
	}
	
	
	
}
