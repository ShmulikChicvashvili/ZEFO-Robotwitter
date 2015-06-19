package com.robotwitter.test;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.robotwitter.database.MySQLConEstablisher;
import com.robotwitter.database.MySqlDatabaseFollowers;
import com.robotwitter.database.MySqlDatabaseScheduledTweets;
import com.robotwitter.database.interfaces.IDatabaseScheduledTweets;
import com.robotwitter.database.primitives.DBScheduledTweet;
import com.robotwitter.posting.AutomateTweetPostingPeriod;

public class DatabaseSchedulingTweetTest {
	@Before
	public final void before() {
		final Injector injector = Guice
				.createInjector(new DatabaseTestModule());

		try (Connection con = injector.getInstance(MySQLConEstablisher.class)
				.getConnection(); Statement statement = con.createStatement()) {
			final String dropSchema = "DROP DATABASE `test`";
			statement.executeUpdate(dropSchema);
		} catch (final SQLException e) {
			System.out.println(e.getErrorCode());
		}
		db = injector.getInstance(MySqlDatabaseScheduledTweets.class);
	}

	@Test
	public void test() {
		DBScheduledTweet st = new DBScheduledTweet("", 1, "", "",
				new Timestamp(new Date().getTime()),
				AutomateTweetPostingPeriod.WEEKLY);
		db.insertScheduledTweet(st);
		ArrayList<DBScheduledTweet> arr = (ArrayList<DBScheduledTweet>) db.getScheduledTweets();
		for(DBScheduledTweet sc : arr) {
			System.out.println(sc.toString());
		}
		arr = (ArrayList<DBScheduledTweet>) db.getScheduledTweetsForInitialization();
		for(DBScheduledTweet sc : arr) {
			System.out.println(sc.toString());
		}
	}

	IDatabaseScheduledTweets db;
}
