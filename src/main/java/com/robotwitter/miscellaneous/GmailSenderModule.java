/**
 * 
 */

package com.robotwitter.miscellaneous;


import com.google.inject.AbstractModule;
import com.google.inject.name.Names;




/**
 * @author Itay
 *
 */
public class GmailSenderModule extends AbstractModule
{
	
	/* (non-Javadoc) @see com.google.inject.AbstractModule#configure() */
	@Override
	protected void configure()
	{
		bind(IEmailSender.class).to(EmailSender.class);
		bind(IEmailSession.class).to(GmailSession.class);
		bind(String.class)
			.annotatedWith(Names.named("System Email Username"))
			.toInstance("robotwitter.app");
		bind(String.class)
			.annotatedWith(Names.named("System Email Password"))
			.toInstance("robotwitter123");
	}
	
}
