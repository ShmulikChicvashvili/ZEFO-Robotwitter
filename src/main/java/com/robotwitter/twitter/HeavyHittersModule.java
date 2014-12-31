/**
 *
 */

package com.robotwitter.twitter;


import com.google.inject.AbstractModule;

import com.robotwitter.statistics.HeavyHitters;
import com.robotwitter.statistics.IHeavyHitters;




/**
 * @author Shmulik
 *
 */
public class HeavyHittersModule extends AbstractModule
{
	
	/* (non-Javadoc) @see com.google.inject.AbstractModule#configure() */
	@Override
	protected void configure()
	{
		bind(IHeavyHitters.class).to(HeavyHitters.class);
	}
	
}
