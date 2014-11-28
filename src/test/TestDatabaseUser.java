/**
 * 
 */
package test;

import static org.junit.Assert.*;

import org.junit.Test;

import Database.DatabaseUserFactory;
import Database.IDatabase;

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
	public static void test()
	{
		try
		{
			IDatabase db = DatabaseUserFactory.class.newInstance().getDatabase();
		} catch (InstantiationException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
