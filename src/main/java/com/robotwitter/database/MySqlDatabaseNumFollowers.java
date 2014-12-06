/**
 * 
 */

package com.robotwitter.database;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;

import com.robotwitter.database.interfaces.ConnectionEstablisher;
import com.robotwitter.database.interfaces.IDatabaseNumFollowers;
import com.robotwitter.database.interfaces.returnValues.InsertError;
import com.robotwitter.database.primitives.DBFollowersNumber;




/**
 * @author Eyal
 *
 */
public final class MySqlDatabaseNumFollowers extends MySqlDatabase
	implements
		IDatabaseNumFollowers
{
	private enum Columns
	{
		TWITTER_ID,
		DATE,
		NUM_FOLLOWERS
	}
	
	
	
	/**
	 * 
	 */
	@Inject
	public MySqlDatabaseNumFollowers(ConnectionEstablisher conEsatblisher)
	{
		super(conEsatblisher);
		try (Connection con = connectionEstablisher.getConnection())
		{
			statement = con.createStatement();
			final String statementCreate =
				"CREATE TABLE IF NOT EXISTS " + table + "(" //$NON-NLS-1$ //$NON-NLS-2$
					+ "`twitter_id` BIGINT NOT NULL," //$NON-NLS-1$
					+ "`date` TIMESTAMP NOT NULL," //$NON-NLS-1$
					+ "`num_followers` INT NOT NULL," //$NON-NLS-1$
					+ "PRIMARY KEY (`twitter_id`, `date`))"; //$NON-NLS-1$
			
			statement.execute(statementCreate);
		} catch (final Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/* (non-Javadoc) @see
	 * com.robotwitter.database.interfaces.IDatabaseNumFollowers
	 * #get(java.lang.String) */
	@SuppressWarnings("boxing")
	@Override
	public List<DBFollowersNumber> get(Long twitterId)
	{
		if (twitterId == null) { return null; }
		ArrayList<DBFollowersNumber> $ = null;
		try (Connection con = connectionEstablisher.getConnection())
		{
			preparedStatement =
				con.prepareStatement(""
					+ "SELECT * FROM "
					+ table
					+ " WHERE "
					+ Columns.TWITTER_ID.name().toLowerCase()
					+ "=?;");
			preparedStatement.setLong(1, twitterId);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next())
			{
				$ = new ArrayList<>();
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
		} catch (ClassNotFoundException | SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	
	
	
	private final String table = schema + ".`followers_number`"; //$NON-NLS-1$
	
}
