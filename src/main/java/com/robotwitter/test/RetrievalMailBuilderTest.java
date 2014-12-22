/**
 *
 */

package com.robotwitter.test;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;

import com.robotwitter.management.RetrievalMailBuilder;
import com.robotwitter.management.RetrievalMailBuilderModule;
import com.robotwitter.miscellaneous.EmailMessage;




/**
 * @author Itay
 *
 */
public class RetrievalMailBuilderTest
{
	
	/**
	 */
	@Before
	public void setUp()
	{
		Injector injector =
			Guice.createInjector(new RetrievalMailBuilderModule());
		builder = injector.getInstance(RetrievalMailBuilder.class);
		
	}
	
	
	
	/**
	 */
	@After
	public void tearDown()
	{
		builder = null;
	}
	
	
	@Test
	public void test()
	{
		EmailMessage mail =
			builder.buildRetrievalEmail(
				"systemMail",
				"userMail",
				"userPassword");
		if(mail == null) {
			fail("failed to read the xml file RetrievalMailTemplate.xml");
		}
		assertEquals(mail.getEmailAddressFrom(), "systemMail");
		assertEquals(mail.getEmailAddressTo(),"userMail");
		assertEquals(mail.getMsgSubject(), "Your Robotwitter account password");
		assertTrue(mail.getMsgText().contains("userPassword"));
		
		System.out.println(mail.getMsgText());
	}
	
	
	RetrievalMailBuilder builder;
	
}
