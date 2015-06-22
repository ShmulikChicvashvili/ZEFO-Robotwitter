package com.robotwitter.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;

import com.robotwitter.database.interfaces.ConnectionEstablisher;
import com.robotwitter.database.interfaces.IDatabaseScheduledTweets;
import com.robotwitter.database.interfaces.returnValues.SqlError;
import com.robotwitter.database.primitives.DBScheduledTweet;
import com.robotwitter.posting.AutomateTweetPostingPeriod;

public class MySqlDatabaseScheduledTweets extends AbstractMySqlDatabase
		implements IDatabaseScheduledTweets {

	private enum Columns {
		EMAIL, USER_ID, TWEET_NAME, TWEET_TEXT, STARTING_DATE, REPEAT_RATE;
	}

	@Inject
	public MySqlDatabaseScheduledTweets(ConnectionEstablisher conEstablisher)
			throws SQLException {
		super(conEstablisher);

		initQueries();

		try (Connection conn = conEstablisher.getConnection();
				Statement statement = conn.createStatement()) {
			statement.executeUpdate(creationScheduledQuery);
			statement.executeUpdate(creationFlushingQuery);
		}
	}

	@Override
	public List<DBScheduledTweet> getScheduledTweets() {
		ArrayList<DBScheduledTweet> $ = new ArrayList<>();

		try (Connection conn = connectionEstablisher.getConnection();
				Statement statement = conn.createStatement()) {
			getFromTable(gettingScheduledQuery, $, statement);
		} catch (SQLException e) {
			e.printStackTrace();
			return $;
		}

//		try {
//			flush($);
//		} catch (SQLException e) {
//			e.printStackTrace();
//			System.err.println("Undefined behaviour");
//		}
		return $;
	}

	@Override
	public List<DBScheduledTweet> getScheduledTweetsForInitialization() {
		ArrayList<DBScheduledTweet> $ = new ArrayList<>();

		try (Connection conn = connectionEstablisher.getConnection();
				Statement statement = conn.createStatement()) {
			getFromTable(gettingScheduledQuery, $, statement);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

		return $;
	}

	@Override
	public SqlError insertScheduledTweet(DBScheduledTweet scheduledTweet) {
		if (scheduledTweet == null || scheduledTweet.getEMail() == null
				|| scheduledTweet.getUserId() < 0
				|| scheduledTweet.getTweetName() == null
				|| scheduledTweet.getTweetText() == null
				|| scheduledTweet.getStartingDate() == null
				|| scheduledTweet.getPostingPeriod() == null) {
			return SqlError.INVALID_PARAMS;
		}

		try (Connection conn = connectionEstablisher.getConnection();
				PreparedStatement prpdStmt = conn
						.prepareStatement(insertionScheduledQuery)) {
			insertToTable(scheduledTweet, prpdStmt);
		} catch (SQLException e) {
			if (e.getErrorCode() == insertAlreadyExists) {
				return SqlError.ALREADY_EXIST;
			}
			e.printStackTrace();
			return SqlError.FAILURE;
		}
		return SqlError.SUCCESS;
	}
	
	@Override
	public SqlError removeScheduledTweet(DBScheduledTweet scheduledTweet) {
		// TODO Auto-generated method stub
		return SqlError.SUCCESS;
	}

	private void flush(List<DBScheduledTweet> scheduledTweets)
			throws SQLException {
		try (Connection conn = connectionEstablisher.getConnection();
				PreparedStatement insertStmt = conn
						.prepareStatement(insertionScheduledQuery);
				Statement statement = conn.createStatement();
				Statement stmt = conn.createStatement()) {

			for (DBScheduledTweet scheduledTweet : scheduledTweets) {
				insertToTable(scheduledTweet, insertStmt);
			}

			stmt.execute(droppingQuery);
			statement.executeUpdate(creationFlushingQuery);
		} 
	}

	private void getFromTable(String gettingQuery,
			ArrayList<DBScheduledTweet> $, Statement statement)
			throws SQLException {
		resultSet = statement.executeQuery(gettingQuery);
		while (resultSet.next()) {
			$.add(new DBScheduledTweet(resultSet.getString(Columns.EMAIL
					.toString().toLowerCase()), resultSet
					.getLong(Columns.USER_ID.toString().toLowerCase()),
					resultSet.getString(Columns.TWEET_NAME.toString()
							.toLowerCase()), resultSet
							.getString(Columns.TWEET_TEXT.toString()
									.toLowerCase()), resultSet
							.getTimestamp(Columns.STARTING_DATE.toString()
									.toLowerCase()), AutomateTweetPostingPeriod
							.valueOf(resultSet.getString(Columns.REPEAT_RATE
									.toString().toLowerCase()))));
		}
		resultSet.close();
	}

	private void initQueries() {
		creationScheduledQuery = String
				.format("CREATE TABLE IF NOT EXISTS %s (%s VARCHAR(255) NOT NULL, %s BIGINT NOT NULL, %s VARCHAR(255) NOT NULL, %s TEXT NOT NULL,"
						+ " %s TIMESTAMP NOT NULL, %s ENUM('%s','%s','%s','%s') NOT NULL);",
						scheduledTweetsTable, Columns.EMAIL.toString()
								.toLowerCase(), Columns.USER_ID.toString()
								.toLowerCase(), Columns.TWEET_NAME.toString()
								.toLowerCase(), Columns.TWEET_TEXT.toString()
								.toLowerCase(), Columns.STARTING_DATE
								.toString().toLowerCase(), Columns.REPEAT_RATE
								.toString().toLowerCase(),
						AutomateTweetPostingPeriod.SINGLE.toString(),
						AutomateTweetPostingPeriod.DAILY.toString(),
						AutomateTweetPostingPeriod.WEEKLY.toString(),
						AutomateTweetPostingPeriod.MONTHLY.toString());

		creationFlushingQuery = String
				.format("CREATE TABLE IF NOT EXISTS %s (%s VARCHAR(255) NOT NULL, %s BIGINT NOT NULL, %s VARCHAR(255) NOT NULL, %s TEXT NOT NULL,"
						+ " %s TIMESTAMP NOT NULL, %s ENUM('%s','%s','%s','%s') NOT NULL);",
						flushingTweetsTable, Columns.EMAIL.toString()
								.toLowerCase(), Columns.USER_ID.toString()
								.toLowerCase(), Columns.TWEET_NAME.toString()
								.toLowerCase(), Columns.TWEET_TEXT.toString()
								.toLowerCase(), Columns.STARTING_DATE
								.toString().toLowerCase(), Columns.REPEAT_RATE
								.toString().toLowerCase(),
						AutomateTweetPostingPeriod.SINGLE.toString(),
						AutomateTweetPostingPeriod.DAILY.toString(),
						AutomateTweetPostingPeriod.WEEKLY.toString(),
						AutomateTweetPostingPeriod.MONTHLY.toString());

		droppingQuery = String.format("DROP TABLE %s", flushingTweetsTable);

		insertionScheduledQuery = String
				.format("INSERT INTO %s (%s, %s, %s, %s, %s , %s) VALUES (?, ?, ?, ?, ? ,?)",
						scheduledTweetsTable, Columns.EMAIL.toString()
								.toLowerCase(), Columns.USER_ID.toString()
								.toLowerCase(), Columns.TWEET_NAME.toString()
								.toLowerCase(), Columns.TWEET_TEXT.toString()
								.toLowerCase(), Columns.STARTING_DATE
								.toString().toLowerCase(), Columns.REPEAT_RATE
								.toString().toLowerCase());

		insertionFlushingQuery = String
				.format("INSERT INTO %s (%s, %s, %s, %s, %s , %s) VALUES (?, ?, ?, ?, ? ,?)",
						flushingTweetsTable, Columns.EMAIL.toString()
								.toLowerCase(), Columns.USER_ID.toString()
								.toLowerCase(), Columns.TWEET_NAME.toString()
								.toLowerCase(), Columns.TWEET_TEXT.toString()
								.toLowerCase(), Columns.STARTING_DATE
								.toString().toLowerCase(), Columns.REPEAT_RATE
								.toString().toLowerCase());

		gettingScheduledQuery = String.format("SELECT * FROM %s",
				scheduledTweetsTable);

		gettingFlushingQuery = String.format("SELECT * FROM %s",
				flushingTweetsTable);
	}

	private void insertToTable(DBScheduledTweet scheduledTweet,
			PreparedStatement prpdStmt) throws SQLException {
		prpdStmt.setString(1, scheduledTweet.getEMail());
		prpdStmt.setLong(2, scheduledTweet.getUserId());
		prpdStmt.setString(3, scheduledTweet.getTweetName());
		prpdStmt.setString(4, scheduledTweet.getTweetText());
		prpdStmt.setTimestamp(5, scheduledTweet.getStartingDate());
		prpdStmt.setString(6, scheduledTweet.getPostingPeriod().toString());
		prpdStmt.executeUpdate();
	}

	/*
	 * The table will hold all the scheduled tweets in the system
	 */
	private final String scheduledTweetsTable = schema + "."
			+ "`scheduled_tweets`";

	/*
	 * The table will hold the schedule tweets which we got in the current
	 * session
	 */
	private final String flushingTweetsTable = schema + "."
			+ "`scheduled_tweets_temp`";

	private String creationScheduledQuery;

	private String creationFlushingQuery;

	private String droppingQuery;

	private String insertionScheduledQuery;

	private String insertionFlushingQuery;

	private String gettingScheduledQuery;

	private String gettingFlushingQuery;
}
