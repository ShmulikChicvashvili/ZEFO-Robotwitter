
package com.robotwitter.management;


import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

import com.robotwitter.database.MySqlDatabaseUser;
import com.robotwitter.database.interfaces.IDatabaseUsers;




/**
 * @author Itay Khazon
 *
 */
public class EmailPasswordRetrieverModule extends AbstractModule
{
	/* (non-Javadoc) @see com.google.inject.AbstractModule#configure() */
	@Override
	protected final void configure()
	{
		bind(IEmailPasswordRetriever.class).to(EmailPasswordRetriever.class);
		bind(String.class)
			.annotatedWith(Names.named("System Email")).toInstance("robotwitter.app@gmail.com"); //$NON-NLS-1$ //$NON-NLS-2$
		bind(IRetrievalMailBuilder.class).to(RetrievalMailBuilder.class);
		bind(IDatabaseUsers.class).to(MySqlDatabaseUser.class);
	}
	
}
