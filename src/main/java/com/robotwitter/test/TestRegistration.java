
package com.robotwitter.test;


import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.robotwitter.database.interfaces.IDatabaseUsers;
import com.robotwitter.database.interfaces.returnValues.SqlError;
import com.robotwitter.database.primitives.DBUser;
import com.robotwitter.webapp.control.registration.IRegistrationController.Status;
import com.robotwitter.webapp.control.registration.RegistrationController;




/**
 * @author AmirDrutin
 *
 */

public class TestRegistration
{
	
	@Before
	public void before()
	{
		final IDatabaseUsers db = Mockito.mock(IDatabaseUsers.class);
		final DBUser user = new DBUser("amir.drutin@gmail.com", "Amir1234");
		Mockito.when(db.get("amir.drutin@gmail.com")).thenReturn(user);
		final DBUser dbuser;
		Mockito.when(db.insert(any(DBUser.class))).thenReturn(SqlError.SUCCESS);
		registration = new RegistrationController(db);
	}
	
	
	/**
	 * Testing registration
	 */
	@SuppressWarnings("nls")
	@Test
	public void test()
	{
		final String existingEmail = "amir.drutin@gmail.com", goodEmail =
			"yossi@walla.co.il", goodPassword = "Aa1234567";
		
		/** Testing case of success */
		assertEquals(
			Status.SUCCESS,
			registration.register(goodEmail, goodPassword));
		
		/** Testing all cases of failure in the database */
		assertEquals(Status.FAILURE, registration.register(null, goodPassword));
		assertEquals(Status.FAILURE, registration.register(goodEmail, null));
		assertEquals(Status.FAILURE, registration.register(null, null));
		
		/** Testing all the cases of bad email address */
		// assertEquals(Status.BAD_EMAIL,
		// this.registration.register(badEmail1, goodPassword));
		// assertEquals(Status.BAD_EMAIL,
		// this.registration.register(badEmail2, goodPassword));
		// assertEquals(Status.BAD_EMAIL,
		// this.registration.register(badEmail3, goodPassword));
		// assertEquals(Status.BAD_EMAIL,
		// this.registration.register(badEmail4, goodPassword));
		// assertEquals(Status.BAD_EMAIL,
		// this.registration.register(badEmail5, goodPassword));
		
		/** Testing all the cases of bad passwords */
		// assertEquals(Status.LOWER_CASE_PASSWORD,
		// this.registration.register(goodEmail, lowerPassword));
		// assertEquals(Status.UPPER_CASE_PASSWORD,
		// this.registration.register(goodEmail, upperPassword));
		// assertEquals(Status.SHORT_PASSWORD,
		// this.registration.register(goodEmail, shortPassword));
		
		/** Testing precedence of error messages */
		// assertEquals(Status.BAD_EMAIL,
		// this.registration.register(badEmail1, shortPassword));
		// assertEquals(Status.BAD_EMAIL,
		// this.registration.register(badEmail1, upperPassword));
		// assertEquals(Status.BAD_EMAIL,
		// this.registration.register(badEmail1, lowerPassword));
		
		/**
		 * Testing case of existing email address, and the precedences of error
		 * messages with it
		 */
		assertEquals(
			Status.USER_ALREADY_EXISTS,
			registration.register(existingEmail, goodPassword));
		
		/* assertEquals(Status.LOWER_CASE_PASSWORD,
		 * this.registration.register(existingEmail, lowerPassword));
		 * assertEquals(Status.UPPER_CASE_PASSWORD,
		 * this.registration.register(existingEmail, upperPassword));
		 * assertEquals(Status.SHORT_PASSWORD,
		 * this.registration.register(existingEmail, shortPassword)); */
	}
	
	
	
	RegistrationController registration;
}
