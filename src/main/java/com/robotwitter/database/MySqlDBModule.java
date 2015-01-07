/**
 *
 */

package com.robotwitter.database;


import com.google.inject.AbstractModule;

import com.robotwitter.database.interfaces.IDatabaseFollowers;
import com.robotwitter.database.interfaces.IDatabaseHeavyHitters;
import com.robotwitter.database.interfaces.IDatabaseNumFollowers;
import com.robotwitter.database.interfaces.IDatabaseTwitterAccounts;
import com.robotwitter.database.interfaces.IDatabaseUsers;




/**
 * @author Itay, Shmulik
 *
 */
public class MySqlDBModule extends AbstractModule
{
	
	/* (non-Javadoc) @see com.google.inject.AbstractModule#configure() */
	@Override
	protected final void configure()
	{
		bind(IDatabaseUsers.class).to(MySqlDatabaseUser.class);
		bind(IDatabaseTwitterAccounts.class).to(
			MySqlDatabaseTwitterAccounts.class);
		bind(IDatabaseNumFollowers.class).to(MySqlDatabaseNumFollowers.class);
		bind(IDatabaseFollowers.class).to(MySqlDatabaseFollowers.class);
		bind(IDatabaseHeavyHitters.class).to(MySqlDatabaseHeavyHitters.class);
	}
	
}
