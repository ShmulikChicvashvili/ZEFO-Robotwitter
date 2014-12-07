/**
 * 
 */

package com.robotwitter.test;


import static org.junit.Assert.*;

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
	
	RetrievalMailBuilder builder;
	
	
	
	/**
	 */
	@Before
	public void setUp()
	{
		Injector injector =
			Guice.createInjector(new RetrievalMailBuilderModule());
		this.builder = injector.getInstance(RetrievalMailBuilder.class);
		
	}
	
	
	/**
	 */
	@After
	public void tearDown()
	{
		this.builder = null;
	}
	
	
	@Test
	public void test()
	{
		EmailMessage mail =
			this.builder.buildRetrievalEmail(
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
	
}
