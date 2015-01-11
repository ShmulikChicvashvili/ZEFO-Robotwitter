
package com.robotwitter.webapp.control;


import com.google.inject.AbstractModule;

import com.robotwitter.twitter.ITwitterAttacher;
import com.robotwitter.twitter.TwitterAppConfiguration;
import com.robotwitter.twitter.TwitterAttacher;
import com.robotwitter.webapp.control.tools.ITweetingController;
import com.robotwitter.webapp.control.tools.TweetingController;




/**
 * Resolve controller-related dependencies.
 *
 * @author Itay Khazon
 */
public class ControllerModule extends AbstractModule
{
	@Override
	protected final void configure()
	{
		bind(ITwitterAttacher.class).to(TwitterAttacher.class);
		bind(TwitterAppConfiguration.class).toInstance(
			new TwitterAppConfiguration());
		bind(ITweetingController.class).to(TweetingController.class);
	}
}
