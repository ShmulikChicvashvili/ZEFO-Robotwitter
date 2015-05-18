/**
 * 
 */
package com.robotwitter.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * @author Eyal
 *
 */
@RunWith(Suite.class)
@SuiteClasses({
	DatabaseFollowersTest.class,
	EmailPasswordRetrieverTest.class,
	FollowerIdsBackfillerTest.class,
	FollowersNumberTest.class,
	GetFollowersPOC.class,
	HeavyHittersDatabaseTest.class,
	HeavyHittersStreamListenerTest.class,
	HeavyHittersTest.class,
	IntegratedTwitterAttacherTest.class,
	NaiveTwitterFollowerRetrieverTest.class,
	PostingTweetUtilsTest.class,
	PreferenceTest.class,
	RetrievalMailBuilderTest.class,
	SentimentPOC.class,
	TestDatabaseActualFollower.class,
	TestDatabaseTwitterAccounts.class,
	TestDatabaseUser.class,
	TestLoginAuthenticator.class,
	TestRegistration.class,
	TweetingPostingTest.class,
	TwitterAttacherTest.class })
public class AllTests
{	
	
}
