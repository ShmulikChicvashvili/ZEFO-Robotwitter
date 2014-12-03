/**
 * 
 */
package com.robotwitter.management;

import javax.xml.crypto.dsig.keyinfo.RetrievalMethod;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import com.robotwitter.database.MySqlDatabaseUser;
import com.robotwitter.database.interfaces.IDatabaseUsers;
import com.robotwitter.miscellaneous.EmailMessage;
import com.robotwitter.miscellaneous.TemplateMailReader;
import com.robotwitter.test.RetrievelMailBuilderMock;

/**
 * @author Itay
 *
 */
public class EmailPasswordRetrieverModule extends AbstractModule
{	
	/* (non-Javadoc) @see com.google.inject.AbstractModule#configure() */
	@Override
	protected void configure()
	{
		bind(IEmailPasswordRetriever.class).to(EmailPasswordRetriever.class);
		bind(String.class).annotatedWith(Names.named("System Email")).toInstance("robotwitter.app@gmail.com");
		bind(IRetrievalMailBuilder.class).to(RetrievelMailBuilderMock.class);
		bind(IDatabaseUsers.class).to(MySqlDatabaseUser.class);
	}	
	
}
