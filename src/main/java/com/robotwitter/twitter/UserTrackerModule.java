/**
 * 
 */

package com.robotwitter.twitter;


import twitter4j.TwitterFactory;
import twitter4j.TwitterStreamFactory;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

import com.robotwitter.management.ITwitterTracker;
import com.robotwitter.management.TwitterTracker;




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
		
		bind(TwitterFactory.class).annotatedWith(
			Names.named("User based factory")).toInstance(
			new TwitterFactory(new TwitterAppConfiguration()
				.getUserConfiguration()));
		
		bind(IUserTracker.class).to(UserTracker.class);
		
		bind(ITwitterTracker.class).to(TwitterTracker.class);
	}
	
}
