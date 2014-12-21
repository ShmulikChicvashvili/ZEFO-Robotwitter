/**
 *
 */

package com.robotwitter.twitter;


import com.google.inject.AbstractModule;

import com.robotwitter.database.MySqlDatabaseTwitterAccounts;
import com.robotwitter.database.interfaces.IDatabaseTwitterAccounts;




/**
 * @author Itay
 *
 */
public class TwitterAttacherModule extends AbstractModule
{
	
	/* (non-Javadoc) @see com.google.inject.AbstractModule#configure() */
	@Override
	protected final void configure()
	{
		bind(IDatabaseTwitterAccounts.class).to(
			MySqlDatabaseTwitterAccounts.class);
	}
	
}
