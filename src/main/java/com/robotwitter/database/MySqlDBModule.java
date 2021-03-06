/**
 *
 */

package com.robotwitter.database;

import com.google.inject.AbstractModule;
import com.robotwitter.database.interfaces.IDatabaseFollowers;
import com.robotwitter.database.interfaces.IDatabaseHeavyHitters;
import com.robotwitter.database.interfaces.IDatabaseNumFollowers;
import com.robotwitter.database.interfaces.IDatabaseResponses;
import com.robotwitter.database.interfaces.IDatabaseScheduledTweets;
import com.robotwitter.database.interfaces.IDatabaseTweetPostingPreferences;
import com.robotwitter.database.interfaces.IDatabaseTwitterAccounts;
import com.robotwitter.database.interfaces.IDatabaseUsers;

/**
 * @author Itay, Shmulik
 *
 */
public class MySqlDBModule extends AbstractModule {

	/* (non-Javadoc) @see com.google.inject.AbstractModule#configure() */
	@Override
	protected final void configure() {
		bind(IDatabaseUsers.class).to(MySqlDatabaseUser.class)
				.asEagerSingleton();
		bind(IDatabaseTwitterAccounts.class).to(
				MySqlDatabaseTwitterAccounts.class).asEagerSingleton();
		bind(IDatabaseNumFollowers.class).to(MySqlDatabaseNumFollowers.class)
				.asEagerSingleton();
		bind(IDatabaseFollowers.class).to(MySqlDatabaseFollowers.class)
				.asEagerSingleton();
		bind(IDatabaseHeavyHitters.class).to(MySqlDatabaseHeavyHitters.class)
				.asEagerSingleton();
		bind(IDatabaseTweetPostingPreferences.class).to(
				MySqlDatabaseTweetPostingPreferences.class).asEagerSingleton();
		bind(IDatabaseResponses.class).to(MySqlDatabaseResponses.class)
				.asEagerSingleton();
		bind(IDatabaseScheduledTweets.class).to(
				MySqlDatabaseScheduledTweets.class).asEagerSingleton();
	}
}
