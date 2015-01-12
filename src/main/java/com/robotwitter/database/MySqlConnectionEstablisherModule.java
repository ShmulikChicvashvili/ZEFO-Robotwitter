/**
 *
 */

package com.robotwitter.database;


import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

import com.robotwitter.database.interfaces.ConnectionEstablisher;
import com.robotwitter.database.interfaces.ConnectionPool;




/**
 * @author Shmulik and Eyal
 *
 *         The class handles the default configuration for the factory of the
 *         IDatabase
 * 
 *         Changed the name : 7/1/15 10:16 By Shmulik and Itay
 */
public class MySqlConnectionEstablisherModule extends AbstractModule
{
	
	/**
	 *
	 */
	public MySqlConnectionEstablisherModule()
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
