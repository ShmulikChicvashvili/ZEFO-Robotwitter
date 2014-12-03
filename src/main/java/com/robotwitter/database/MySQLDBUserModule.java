/**
 *
 */

package com.robotwitter.database;


import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import com.robotwitter.database.interfaces.ConnectionEstablisher;




/**
 * @author Shmulik
 *
 *         The class handles the default configuration for the factory of
 *         IDatabase
 */
public class MySQLDBUserModule extends AbstractModule
{

	/**
	 *
	 */
	public MySQLDBUserModule()
	{}


	/* (non-Javadoc) @see com.google.inject.AbstractModule#configure() */
	@SuppressWarnings("nls")
	@Override
	protected void configure()
	{
		bind(String.class).annotatedWith(Names.named("DB Server")).toInstance(
			"localhost");
		bind(String.class).annotatedWith(Names.named("DB Schema")).toInstance(
			"yearlyproj_db");

		bind(ConnectionEstablisher.class).to(MySQLConEstablisher.class);
	}

}
