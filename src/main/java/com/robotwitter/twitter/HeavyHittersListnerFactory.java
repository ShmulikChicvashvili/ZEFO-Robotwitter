/**
 *
 */

package com.robotwitter.twitter;


import com.google.inject.Inject;

import com.robotwitter.statistics.IHeavyHitters;




/**
 * @author Shmulik
 *
 */
public class HeavyHittersListnerFactory
{
	@Inject
	public HeavyHittersListnerFactory(IHeavyHitters heavyHitters)
	{
		this.heavyHitters = heavyHitters;
	}
	
	
	public IHeavyHitters getInstance()
	{
		return heavyHitters;
	}
	
	
	
	IHeavyHitters heavyHitters;
}
