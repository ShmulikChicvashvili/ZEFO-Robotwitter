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
			PreparedStatement prpdStmt = conn
				.prepareStatement(creationQuery)) {
			prpdStmt.setString(1, scheduledTweetsTable);
			prpdStmt.executeUpdate();
			prpdStmt.setString(1, flushingTweetsTable);
			prpdStmt.executeUpdate();
		}
	}
	
	@Override
	public List<DBScheduledTweet> getScheduledTweets() {
		ArrayList<DBScheduledTweet> $ = new ArrayList<>();
		
		try (Connection conn = connectionEstablisher.getConnection();
			PreparedStatement prpdStmt = conn
				.prepareStatement(gettingQuery)) {
			getFromTable(flushingTweetsTable, $, prpdStmt);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
		try {
			flush($);
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("Undefined behaviour");
		}
		return $;
	}
	
	@Override
	public List<DBScheduledTweet> getScheduledTweetsForInitialization() {
		ArrayList<DBScheduledTweet> $ = new ArrayList<>();
		
		try (Connection conn = connectionEstablisher.getConnection();
			PreparedStatement prpdStmt = conn
				.prepareStatement(gettingQuery)) {
			getFromTable(scheduledTweetsTable, $, prpdStmt);
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
				.prepareStatement(insertionQuery)) {
			insertToTable(flushingTweetsTable, scheduledTweet, prpdStmt);
		} catch (SQLException e) {
			if (e.getErrorCode() == insertAlreadyExists) {
				return SqlError.ALREADY_EXIST;
			}
			e.printStackTrace();
			return SqlError.FAILURE;
		}
		return SqlError.SUCCESS;
	}
	
	private void flush(List<DBScheduledTweet> scheduledTweets)
		throws SQLException {
		try (Connection conn = connectionEstablisher.getConnection();
			PreparedStatement insertStmt = conn
				.prepareStatement(insertionQuery);
			PreparedStatement createStmt = conn
				.prepareStatement(creationQuery);
			Statement stmt = conn.createStatement()) {
			
			for (DBScheduledTweet scheduledTweet : scheduledTweets) {
				insertToTable(scheduledTweetsTable, scheduledTweet, insertStmt);
			}
			
			stmt.execute(droppingQuery);
			createStmt.setString(1, flushingTweetsTable);
			createStmt.executeUpdate();
		}
	}
	
	private void getFromTable(String table, ArrayList<DBScheduledTweet> $,
		PreparedStatement prpdStmt) throws SQLException {
		prpdStmt.setString(1, table);
		resultSet = prpdStmt.executeQuery();
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
		creationQuery = String
			.format("CREATE TABLE ? (%s VARCHAR(255) NOT NULL, %s BIGINT NOT NULL, %s VARCHAR(255) NOT NULL, %s TEXT NOT NULL,"
				+ " %s TIMESTAMP NOT NULL, %s ENUM('%s','%s','%s','%s') NOT NULL);",
				Columns.EMAIL.toString().toLowerCase(), Columns.USER_ID
				.toString().toLowerCase(), Columns.TWEET_NAME
				.toString().toLowerCase(), Columns.TWEET_TEXT
				.toString().toLowerCase(),
				Columns.STARTING_DATE.toString().toLowerCase(),
				Columns.REPEAT_RATE.toString().toLowerCase(),
				AutomateTweetPostingPeriod.SINGLE.toString(),
				AutomateTweetPostingPeriod.DAILY.toString(),
				AutomateTweetPostingPeriod.WEEKLY.toString(),
				AutomateTweetPostingPeriod.MONTHLY.toString());
		
		droppingQuery = String.format("DROP TABLE %s", flushingTweetsTable);
		
		insertionQuery = String
			.format("INSERT INTO ? (%s, %s, %s, %s, %s , %s) VALUES (?, ?, ?, ?, ? ,?)",
				Columns.EMAIL.toString().toLowerCase(), Columns.USER_ID
				.toString().toLowerCase(), Columns.TWEET_NAME
				.toString().toLowerCase(), Columns.TWEET_TEXT
				.toString().toLowerCase(),
				Columns.STARTING_DATE.toString().toLowerCase(),
				Columns.REPEAT_RATE.toString().toLowerCase());
		
		gettingQuery = "SELECT * FROM ?";
	}
	
	private void insertToTable(String table, DBScheduledTweet scheduledTweet,
		PreparedStatement prpdStmt) throws SQLException {
		prpdStmt.setString(1, table);
		prpdStmt.setString(2, scheduledTweet.getEMail());
		prpdStmt.setLong(3, scheduledTweet.getUserId());
		prpdStmt.setString(4, scheduledTweet.getTweetName());
		prpdStmt.setString(5, scheduledTweet.getTweetText());
		prpdStmt.setTimestamp(6, scheduledTweet.getStartingDate());
		prpdStmt.setString(7, scheduledTweet.getPostingPeriod().toString());
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
	
	private String creationQuery;
	
	private String droppingQuery;
	
	private String insertionQuery;
	
	private String gettingQuery;
	
}
