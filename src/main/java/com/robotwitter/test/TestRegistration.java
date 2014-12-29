package com.robotwitter.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import static org.mockito.Matchers.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.robotwitter.database.MySQLDBUserModule;
import com.robotwitter.database.MySqlDatabaseUser;
import com.robotwitter.database.interfaces.returnValues.SqlError;
import com.robotwitter.database.primitives.DBUser;
import com.robotwitter.webapp.control.registration.IRegistrationController.Status;
import com.robotwitter.webapp.control.registration.RegistrationController;

/**
 * @author AmirDrutin
 *
 */

public class TestRegistration {

	@Before
	public void before() {
		final MySqlDatabaseUser db = Mockito.mock(MySqlDatabaseUser.class);
		final DBUser user = new DBUser("amir.drutin@gmail.com", "Amir1234");
		Mockito.when(db.get("amir.drutin@gmail.com")).thenReturn(user);
		final DBUser dbuser;
		Mockito.when(db.insert(any(DBUser.class))).thenReturn(SqlError.SUCCESS);
		this.registration = new RegistrationController(db);
	}

	/**
	 * Testing registration
	 */
	@SuppressWarnings("nls")
	@Test
	public void test() {
		String existingEmail = "amir.drutin@gmail.com", goodEmail = "yossi@walla.co.il", badEmail1 = "yossi", badEmail2 = "yossi@walla", badEmail3 = "@walla.com", badEmail4 = "yossi@walla.", badEmail5 = "yossi@.com";
		String goodPassword = "Amir1234", lowerPassword = "amir1234", upperPassword = "AMIR1234", shortPassword = "Ab123";

		/** Testing case of success */
		assertEquals(Status.SUCCESS,
				this.registration.register(goodEmail, goodPassword));

		/** Testing all the cases of bad email address */
		assertEquals(Status.BAD_EMAIL,
				this.registration.register(badEmail1, goodPassword));
		assertEquals(Status.BAD_EMAIL,
				this.registration.register(badEmail2, goodPassword));
		assertEquals(Status.BAD_EMAIL,
				this.registration.register(badEmail3, goodPassword));
		assertEquals(Status.BAD_EMAIL,
				this.registration.register(badEmail4, goodPassword));
		assertEquals(Status.BAD_EMAIL,
				this.registration.register(badEmail5, goodPassword));

		/** Testing all the cases of bad passwords */
		assertEquals(Status.LOWER_CASE_PASSWORD,
				this.registration.register(goodEmail, lowerPassword));
		assertEquals(Status.UPPER_CASE_PASSWORD,
				this.registration.register(goodEmail, upperPassword));
		assertEquals(Status.SHORT_PASSWORD,
				this.registration.register(goodEmail, shortPassword));

		/** Testing precedence of error messages */
		assertEquals(Status.BAD_EMAIL,
				this.registration.register(badEmail1, shortPassword));
		assertEquals(Status.BAD_EMAIL,
				this.registration.register(badEmail1, upperPassword));
		assertEquals(Status.BAD_EMAIL,
				this.registration.register(badEmail1, lowerPassword));

		/**
		 * Testing case of existing email adress, and the precedences of error
		 * messages with it
		 */
		assertEquals(Status.USER_ALREADY_EXISTS,
				this.registration.register(existingEmail, goodPassword));
		assertEquals(Status.LOWER_CASE_PASSWORD,
				this.registration.register(existingEmail, lowerPassword));
		assertEquals(Status.UPPER_CASE_PASSWORD,
				this.registration.register(existingEmail, upperPassword));
		assertEquals(Status.SHORT_PASSWORD,
				this.registration.register(existingEmail, shortPassword));
	}

	RegistrationController registration;
}
