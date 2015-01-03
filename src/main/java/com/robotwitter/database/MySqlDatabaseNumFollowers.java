/**
 *
 */

package com.robotwitter.database;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;

import com.robotwitter.database.interfaces.ConnectionEstablisher;
import com.robotwitter.database.interfaces.IDatabaseNumFollowers;
import com.robotwitter.database.interfaces.returnValues.SqlError;
import com.robotwitter.database.primitives.DBFollowersNumber;




/**
 * Handles the connection to number followers table.
 *
 * @author Eyal and Shmulik
 *
 */
public final class MySqlDatabaseNumFollowers extends AbstractMySqlDatabase
	implements
		IDatabaseNumFollowers
{
	/**
	 * The columns for the table.
	 *
	 * @author Eyal
	 *
	 */
	private enum Columns
	{
		/**
		 * Twitter account id.
		 */
		TWITTER_ID,
		/**
		 * The date for the statistic.
		 */
		DATE,
		/**
		 * Number of followers.
		 */
		NUM_FOLLOWERS
	}



	/**
	 * C'tor for MySqlDB for the number followers table.
	 *
	 * @param conEsatblisher
	 *            An object to create connections to the database.
	 * @throws SQLException
	 *             Could not create the table.
	 */
	@Inject
	public MySqlDatabaseNumFollowers(ConnectionEstablisher conEsatblisher)
		throws SQLException
	{
		super(conEsatblisher);
		try (
			Connection con = connectionEstablisher.getConnection();
			Statement statement = con.createStatement())
		{

			final String statementCreate =
				String.format("CREATE TABLE IF NOT EXISTS %s (" //$NON-NLS-1$
					+ "`%s` BIGINT NOT NULL," //$NON-NLS-1$
					+ "`%s` TIMESTAMP NOT NULL," //$NON-NLS-1$
					+ "`%s` INT NOT NULL," //$NON-NLS-1$
					+ "PRIMARY KEY (`%s`, `%s`))", //$NON-NLS-1$
					table,
					Columns.TWITTER_ID.toString().toLowerCase(),
					Columns.DATE.toString().toLowerCase(),
					Columns.NUM_FOLLOWERS.toString().toLowerCase(),
					Columns.TWITTER_ID.toString().toLowerCase(),
					Columns.DATE.toString().toLowerCase());

			statement.execute(statementCreate);
		}
	}


	/* (non-Javadoc) @see
	 * com.robotwitter.database.interfaces.IDatabaseNumFollowers
	 * #get(java.lang.String) */
	@SuppressWarnings({ "boxing", "nls" })
	@Override
	public List<DBFollowersNumber> get(Long twitterId)
	{
		if (twitterId == null) { return null; }
		ArrayList<DBFollowersNumber> $ = null;
		try (
			Connection con = connectionEstablisher.getConnection();
			PreparedStatement preparedStatement =
				con.prepareStatement(""
					+ "SELECT * FROM "
					+ table
					+ " WHERE "
					+ Columns.TWITTER_ID.name().toLowerCase()
					+ "=?;"))
		{
			preparedStatement.setLong(1, twitterId);
			resultSet = preparedStatement.executeQuery();
			$ = new ArrayList<>();
			while (resultSet.next())
			{
				final Timestamp date =
					resultSet.getTimestamp(Columns.DATE
						.toString()
						.toLowerCase());
				final DBFollowersNumber statistic =
					new DBFollowersNumber(resultSet.getLong(Columns.TWITTER_ID
						.toString()
						.toLowerCase()), resultSet.getTimestamp(Columns.DATE
						.toString()
						.toLowerCase()), resultSet.getInt(Columns.NUM_FOLLOWERS
						.toString()
						.toLowerCase()));
				$.add(statistic);
			}
			resultSet.close();
		} catch (final SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if ($ == null || $.isEmpty()) { return null; }
		return $;
	}


	/* (non-Javadoc) @see
	 * com.robotwitter.database.interfaces.IDatabaseNumFollowers
	 * #insert(com.robotwitter.database.primitives.DBFollowersNumber) */
	@SuppressWarnings({ "boxing", "nls" })
	@Override
	public SqlError insert(DBFollowersNumber statistic)
	{
		if (statistic == null
			|| statistic.getTwitterId() == null
			|| statistic.getDate() == null
			|| statistic.getNumFollowers() < 0) { return SqlError.INVALID_PARAMS; }
		try (
			Connection con = connectionEstablisher.getConnection();
			PreparedStatement preparedStatement =
				con.prepareStatement("INSERT INTO "
					+ table
					+ " ("
					+ Columns.TWITTER_ID.toString().toLowerCase()
					+ ","
					+ Columns.DATE.toString().toLowerCase()
					+ ","
					+ Columns.NUM_FOLLOWERS.toString().toLowerCase()
					+ ") VALUES (?,?,?);"))
		{
			final Timestamp date = statistic.getDate();
			preparedStatement.setLong(1, statistic.getTwitterId());
			preparedStatement.setTimestamp(2, statistic.getDate());
			preparedStatement.setInt(3, statistic.getNumFollowers());
			preparedStatement.executeUpdate();
		} catch (final SQLException e)
		{
			if (e.getErrorCode() == insertAlreadyExists) { return SqlError.ALREADY_EXIST; }
			// TODO what to do if not this error code
			e.printStackTrace();
		}
		return SqlError.SUCCESS;
	}



	/**
	 * The table name.
	 */
	private final String table = schema + ".`followers_number`"; //$NON-NLS-1$

}
