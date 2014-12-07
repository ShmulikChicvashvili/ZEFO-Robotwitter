/**
 * 
 */

package com.robotwitter.database;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

import com.robotwitter.database.interfaces.ConnectionEstablisher;
import com.robotwitter.database.interfaces.IDatabaseNumFollowers;
import com.robotwitter.database.interfaces.returnValues.InsertError;
import com.robotwitter.database.primitives.DBFollowersNumber;




/**
 * Handles the connection to number followers table.
 * 
 * @author Eyal
 *
 */
public final class MySqlDatabaseNumFollowers extends MySqlDatabase
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
			Statement statement = (Statement) con.createStatement())
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
				(PreparedStatement) con.prepareStatement(""
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
				final DBFollowersNumber statistic =
					new DBFollowersNumber(resultSet.getLong(Columns.TWITTER_ID
						.name()
						.toLowerCase()), resultSet.getDate(Columns.DATE
						.name()
						.toLowerCase()), resultSet.getInt(Columns.NUM_FOLLOWERS
						.name()
						.toLowerCase()));
				$.add(statistic);
			}
			resultSet.close();
		} catch (SQLException e)
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
	@Override
	public InsertError insert(DBFollowersNumber statistic)
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	/**
	 * The table name.
	 */
	private final String table = schema + ".`followers_number`"; //$NON-NLS-1$
	
}
