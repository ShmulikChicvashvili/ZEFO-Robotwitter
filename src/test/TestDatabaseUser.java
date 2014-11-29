/**
 * 
 */

package test;


import static org.junit.Assert.*;

import org.junit.Test;

import Database.DatabaseUserFactory;
import Database.IDatabase;
import DatabasePrimitives.User;




/**
 * @author Shmulik
 *
 */
public class TestDatabaseUser
{
	
	/**
	 * Testing the user database table features
	 */
	@Test
	public void test()
	{
		try
		{
			IDatabase db =
				DatabaseUserFactory.class.newInstance().getDatabase();
			db.insert(new User("shmulik", "shmulikjkech@gmail.com", "sh")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
