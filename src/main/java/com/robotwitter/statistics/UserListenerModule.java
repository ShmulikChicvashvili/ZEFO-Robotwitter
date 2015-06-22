/**
 * 
 */
package com.robotwitter.statistics;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

import com.robotwitter.classification.ITweetClassifier;
<<<<<<< HEAD
import com.robotwitter.classification.SentimentClassifier;
=======
import com.robotwitter.classification.SimpleTweetClassifier;
>>>>>>> refs/heads/develop

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
		
		//Binding the TweetClassifierListener
		bind(String.class)
			.annotatedWith(Names.named("Classifier Filepath"))
			.toInstance(
				"C:\\Users\\Itay\\Desktop\\TechnionDocs\\Semester8\\YearlyProject2\\classifier.txt");
<<<<<<< HEAD
		bind(ITweetClassifier.class).to(SentimentClassifier.class);
=======
		bind(ITweetClassifier.class).to(SimpleTweetClassifier.class);
>>>>>>> refs/heads/develop
	}	
	
}
