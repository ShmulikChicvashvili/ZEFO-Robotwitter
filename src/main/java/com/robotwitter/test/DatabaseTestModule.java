/**
 * 
 */

package com.robotwitter.test;


import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

import com.robotwitter.database.MySQLConEstablisher;
import com.robotwitter.database.interfaces.ConnectionEstablisher;





/**
 * Module for creating a database for testing.
 * @author Eyal
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
		bind(String.class).annotatedWith(Names.named("DB Server")).toInstance(
			"localhost");
		bind(String.class).annotatedWith(Names.named("DB Schema")).toInstance(
			"test");
		
		bind(ConnectionEstablisher.class).to(MySQLConEstablisher.class);
	}
	
}
