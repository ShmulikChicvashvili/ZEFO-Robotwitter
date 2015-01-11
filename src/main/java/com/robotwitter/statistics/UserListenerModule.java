/**
 * 
 */
package com.robotwitter.statistics;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

/**
 * @author Itay, Shmulik
 *
 */
public class UserListenerModule extends AbstractModule
{

	/* (non-Javadoc) @see com.google.inject.AbstractModule#configure() */
	@Override
	protected void configure()
	{
		//Binding the HeavyHittersListener
		bind(Integer.class).annotatedWith(Names.named("Heavy Hitters Counter Number")).toInstance(new Integer(200));
		bind(Integer.class).annotatedWith(Names.named("Heavy Hitters Number")).toInstance(new Integer(10));
		bind(IHeavyHitters.class).to(HeavyHitters.class);
	}	
	
}
