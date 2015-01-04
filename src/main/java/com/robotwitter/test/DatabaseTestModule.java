/**
 *
 */

package com.robotwitter.test;


import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

import com.robotwitter.database.DBCPConnectionPool;
import com.robotwitter.database.MySQLConEstablisher;
import com.robotwitter.database.interfaces.ConnectionEstablisher;
import com.robotwitter.database.interfaces.ConnectionPool;




/**
 * Module for creating a database for testing.
 *
 * @author Eyal and Shmulik
 *
 */
public class DatabaseTestModule extends AbstractModule
{

	/**
	 * Instantiates a new database test module.
	 */
	public DatabaseTestModule()
	{}


	/* (non-Javadoc) @see com.google.inject.AbstractModule#configure() */
	@SuppressWarnings("nls")
	@Override
	protected void configure()
	{
		bind(String.class).annotatedWith(Names.named("DB Schema")).toInstance(
			"yearlyproj_db");

		// Stuff for connection pool
		bind(String.class).annotatedWith(Names.named("DB Driver")).toInstance(
			"com.mysql.jdbc.Driver");
		bind(String.class)
			.annotatedWith(Names.named("DB Username"))
			.toInstance("root");
		bind(String.class)
			.annotatedWith(Names.named("DB Password"))
			.toInstance("root");
		bind(String.class).annotatedWith(Names.named("DB URL")).toInstance(
			"jdbc:mysql://localhost/");

		bind(ConnectionPool.class).to(DBCPConnectionPool.class);

		bind(ConnectionEstablisher.class).to(MySQLConEstablisher.class);
	}

}
