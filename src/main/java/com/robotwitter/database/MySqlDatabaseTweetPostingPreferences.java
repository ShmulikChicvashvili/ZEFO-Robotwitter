/**
 * 
 */

package com.robotwitter.database;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import com.google.inject.Inject;

import com.robotwitter.database.interfaces.ConnectionEstablisher;
import com.robotwitter.database.interfaces.IDatabaseTweetPostingPreferences;
import com.robotwitter.database.interfaces.returnValues.SqlError;
import com.robotwitter.database.primitives.DBTweetPostingPreferences;
import com.robotwitter.posting.TweetPostingPreferenceType;




/**
 * @author Shmulik
 *
 */
public class MySqlDatabaseTweetPostingPreferences extends AbstractMySqlDatabase
	implements
		IDatabaseTweetPostingPreferences
{
	
	private enum Columns
	{
		EMAIL,
		TYPE,
		PREFIX,
		POSTFIX
	}
	
	
	
	/**
	 * @param conEstablisher
	 *            The connection handler
	 * @throws SQLException
	 */
	@Inject
	public MySqlDatabaseTweetPostingPreferences(
		ConnectionEstablisher conEstablisher) throws SQLException
	{
		super(conEstablisher);
		initializeQueries();
		try (
			Connection conn = connectionEstablisher.getConnection();
			Statement statement = conn.createStatement())
		{
			statement.execute(creationQuery);
		}
	}
	
	
	/* (non-Javadoc) @see
	 * com.robotwitter.database.interfaces.IDatabaseTweetPostingPreferences
	 * #insert(com.robotwitter.database.primitives.DBTweetPostingPreferences) */
	@SuppressWarnings("boxing")
	@Override
	public SqlError insert(DBTweetPostingPreferences tweetPostingPreferences)
	{
		if (!areParametersValid(tweetPostingPreferences)) { return SqlError.INVALID_PARAMS; }
		try (
			Connection conn = connectionEstablisher.getConnection();
			PreparedStatement preparedStatement =
				conn.prepareStatement(insertQuery))
		{
			preparedStatement.setString(1, tweetPostingPreferences
				.getEMail()
				.toLowerCase());
			preparedStatement.setString(2, tweetPostingPreferences
				.getPostingPreference()
				.toString());
			preparedStatement.setString(3, tweetPostingPreferences.getPrefix());
			preparedStatement
				.setString(4, tweetPostingPreferences.getPostfix());
			preparedStatement.executeUpdate();
		} catch (SQLException e)
		{
			if (e.getErrorCode() == insertAlreadyExists) { return SqlError.ALREADY_EXIST; }
			e.printStackTrace();
			return SqlError.FAILURE;
		}
		return SqlError.SUCCESS;
	}

	
	/* (non-Javadoc) @see
	 * com.robotwitter.database.interfaces.IDatabaseTweetPostingPreferences
	 * #get(java.lang.String) */
	@Override
	public DBTweetPostingPreferences get(String email)
	{
		if (email == null) { return null; }
		DBTweetPostingPreferences $ = null;
		try (
			Connection conn = connectionEstablisher.getConnection();
			PreparedStatement preparedStatement =
				conn.prepareStatement(getQuery))
		{
			preparedStatement.setString(1, email.toLowerCase());
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next())
			{
				$ =
					new DBTweetPostingPreferences(
						resultSet.getString(Columns.EMAIL
							.toString()
							.toLowerCase()),
						TweetPostingPreferenceType.valueOf(resultSet
							.getString(Columns.TYPE.toString().toLowerCase())),
						resultSet.getString(Columns.PREFIX
							.toString()
							.toLowerCase()),
						resultSet.getString(Columns.POSTFIX
							.toString()
							.toLowerCase()));
			}
			resultSet.close();
		} catch (SQLException e)
		{
			e.printStackTrace();
			return null;
		}
		return $;
	}
	
	
	/* (non-Javadoc) @see
	 * com.robotwitter.database.interfaces.IDatabaseTweetPostingPreferences
	 * #update(com.robotwitter.database.primitives.DBTweetPostingPreferences) */
	@Override
	public SqlError update(DBTweetPostingPreferences tweetPostingPreferences)
	{
		if (!areParametersValid(tweetPostingPreferences)) { return SqlError.INVALID_PARAMS; }
		if (!isExists(tweetPostingPreferences.getEMail())) { return SqlError.DOES_NOT_EXIST; }
		try (
			Connection conn = connectionEstablisher.getConnection();
			PreparedStatement preparedStatement =
				conn.prepareStatement(updateQuery))
		{
			preparedStatement.setString(1, tweetPostingPreferences
				.getPostingPreference()
				.toString());
			preparedStatement.setString(2, tweetPostingPreferences.getPrefix());
			preparedStatement
				.setString(3, tweetPostingPreferences.getPostfix());
			preparedStatement.setString(4, tweetPostingPreferences.getEMail());
			preparedStatement.executeUpdate();
		} catch (SQLException e)
		{
			e.printStackTrace();
			return SqlError.FAILURE;
		}
		return SqlError.SUCCESS;
	}
	
	
	/* (non-Javadoc) @see
	 * com.robotwitter.database.interfaces.IDatabaseTweetPostingPreferences
	 * #isExists(java.lang.String) */
	@Override
	public boolean isExists(String email)
	{
		if (email == null) { return false; }
		boolean $ = false;
		try (
			Connection conn = connectionEstablisher.getConnection();
			PreparedStatement preparedStatement =
				conn.prepareStatement(getQuery))
		{
			preparedStatement.setString(1, email.toLowerCase());
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next())
			{
				$ = true;
			}
			resultSet.close();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return $;
	}
	
	
	private boolean areParametersValid(
		DBTweetPostingPreferences tweetPostingPreferences)
	{
		if (tweetPostingPreferences == null
			|| tweetPostingPreferences.getEMail() == null
			|| tweetPostingPreferences.getPostingPreference() == null) { return false; }
		if (tweetPostingPreferences.getPostingPreference() == TweetPostingPreferenceType.PREFIX)
		{
			if (tweetPostingPreferences.getPrefix() == null) { return false; }
		}
		if (tweetPostingPreferences.getPostingPreference() == TweetPostingPreferenceType.POSTFIX)
		{
			if (tweetPostingPreferences.getPostfix() == null) { return false; }
		}
		return true;
	}
	
	
	private void initializeQueries()
	{
		creationQuery =
			String
				.format(
					"CREATE TABLE IF NOT EXISTS %s (%s VARCHAR(255) NOT NULL, %s ENUM('%s','%s','%s','%s') NOT NULL, %s VARCHAR(255) NULL, %s VARCHAR(45) NULL, PRIMARY KEY (%s));",
					table,
					Columns.EMAIL.toString().toLowerCase(),
					Columns.TYPE.toString().toLowerCase(),
					TweetPostingPreferenceType.BASIC.toString(),
					TweetPostingPreferenceType.NUMBERED.toString(),
					TweetPostingPreferenceType.PREFIX.toString(),
					TweetPostingPreferenceType.POSTFIX.toString(),
					Columns.PREFIX.toString().toLowerCase(),
					Columns.POSTFIX.toString().toLowerCase(),
					Columns.EMAIL.toString().toLowerCase());
		
		insertQuery =
			String.format(
				"INSERT INTO %s (%s,%s,%s,%s) VALUES (?,?,?,?);",
				table,
				Columns.EMAIL.toString().toLowerCase(),
				Columns.TYPE.toString().toLowerCase(),
				Columns.PREFIX.toString().toLowerCase(),
				Columns.POSTFIX.toString().toLowerCase());
		
		getQuery =
			String.format("SELECT * FROM %s WHERE %s = ?", table, Columns.EMAIL
				.toString()
				.toLowerCase());
		
		updateQuery =
			String.format(
				"UPDATE %s SET %s = ?, %s = ?, %s = ? WHERE %s = ?",
				table,
				Columns.TYPE.toString().toLowerCase(),
				Columns.PREFIX.toString().toLowerCase(),
				Columns.POSTFIX.toString().toLowerCase(),
				Columns.EMAIL.toString().toLowerCase());
	}
	
	
	
	private String creationQuery;
	
	private String insertQuery;
	
	private String getQuery;
	
	private String updateQuery;
	
	private final String table = schema + "." + "`tweet_posting_preferences`";
	
}
