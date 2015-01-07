/**
 * 
 */

package com.robotwitter.twitter;


import twitter4j.TwitterStreamFactory;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;




/**
 * @author Itay, Shmulik
 *
 */
public class UserTrackerModule extends AbstractModule
{
	
	/* (non-Javadoc) @see com.google.inject.AbstractModule#configure() */
	@Override
	protected void configure()
	{
		
		bind(TwitterStreamFactory.class).annotatedWith(
			Names.named("User based factory")).toInstance(
			new TwitterStreamFactory(new TwitterAppConfiguration()
				.getUserConfiguration()));
		
		bind(IUserTracker.class).to(UserTracker.class);
	}
	
}
