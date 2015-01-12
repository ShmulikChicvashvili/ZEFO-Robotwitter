/**
 * 
 */

package com.robotwitter.database;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.TreeMap;

import com.google.inject.Inject;

import com.robotwitter.database.interfaces.ConnectionEstablisher;
import com.robotwitter.database.interfaces.IDatabaseHeavyHitters;
import com.robotwitter.database.interfaces.returnValues.SqlError;




/**
 * @author Itay, Shmulik
 *
 */
public class MySqlDatabaseHeavyHitters extends AbstractMySqlDatabase
	implements
		IDatabaseHeavyHitters
{
	
	private enum Columns
	{
		FOLLOWED_ID,
		
		RANK,
		
		FOLLOWER_ID
	}
	
	
	
	/**
	 * @param conEstablisher
	 * @throws SQLException
	 */
	@Inject
	public MySqlDatabaseHeavyHitters(ConnectionEstablisher conEstablisher)
		throws SQLException
	{
		super(conEstablisher);
		try (
			Connection con = connectionEstablisher.getConnection();
			Statement statement = con.createStatement())
		{
			final String statementCreate =
				String.format("CREATE TABLE IF NOT EXISTS %s (" //$NON-NLS-1$
					+ "`%s` BIGINT NOT NULL," //$NON-NLS-1$
					+ "`%s` INT NOT NULL," //$NON-NLS-1$
					+ "`%s` BIGINT NOT NULL);", //$NON-NLS-1$
					table,
					Columns.FOLLOWED_ID.toString().toLowerCase(),
					Columns.RANK.toString().toLowerCase(),
					Columns.FOLLOWER_ID.toString().toLowerCase());
			statement.execute(statementCreate);
		}
	}
	
	
	/* (non-Javadoc) @see
	 * com.robotwitter.database.interfaces.IDatabaseHeavyHitters
	 * #get(java.lang.Long) */
	@SuppressWarnings("boxing")
	@Override
	public ArrayList<Long> get(Long followedUserID)
	{
		if(followedUserID == null) {
			return null;
		}
		ArrayList<Long> $ = new ArrayList<>();
		
		try (
			Connection con = connectionEstablisher.getConnection();
			PreparedStatement preparedStatement =
				con.prepareStatement(""
					+ "SELECT * FROM "
					+ table
					+ " WHERE "
					+ Columns.FOLLOWED_ID.toString().toLowerCase()
					+ "=?;"))
		{
			preparedStatement.setLong(1, followedUserID);
			resultSet = preparedStatement.executeQuery();
			TreeMap<Integer, Long> sortedHeavyHitters = new TreeMap<>();
			while (resultSet.next())
			{
				sortedHeavyHitters.put(resultSet.getInt(Columns.RANK
					.toString()
					.toLowerCase()), resultSet.getLong(Columns.FOLLOWER_ID
					.toString()
					.toLowerCase()));
			}
			resultSet.close();
			for(Long entry : sortedHeavyHitters.values()) {
				$.add(entry);
			}
		} catch (final Exception e)
		{
			// TODO understand what the hell we should do?!
			e.printStackTrace();
		}
		
		return $;
	}
	
	
	/* (non-Javadoc) @see
	 * com.robotwitter.database.interfaces.IDatabaseHeavyHitters
	 * #insert(java.lang.Long, java.util.ArrayList) */
	@SuppressWarnings({ "boxing", "nls" })
	@Override
	public
		SqlError
		insert(Long followedUserID, ArrayList<Long> heavyHittersIDs)
	{
		if (followedUserID == null || heavyHittersIDs == null) { return SqlError.INVALID_PARAMS; }
		if(heavyHittersIDs.contains(null)) { return SqlError.INVALID_PARAMS; }
		try (
			Connection con = connectionEstablisher.getConnection();
			@SuppressWarnings("nls")
			PreparedStatement insertStatement =
				con.prepareStatement("INSERT INTO "
					+ table
					+ " ("
					+ Columns.FOLLOWED_ID.toString().toLowerCase()
					+ ","
					+ Columns.RANK.toString().toLowerCase()
					+ ","
					+ Columns.FOLLOWER_ID.toString().toLowerCase()
					+ ") VALUES ( ?, ?, ? );");
			PreparedStatement deleteStatement =
				con.prepareStatement("DELETE FROM "
					+ table
					+ " WHERE "
					+ Columns.FOLLOWED_ID.toString().toLowerCase()
					+ " = ?;"))
		{
			deleteStatement.setLong(1, followedUserID);
			deleteStatement.executeUpdate();
			
			for (int i = 0; i < heavyHittersIDs.size(); i++)
			{
				insertStatement.setLong(1, followedUserID);
				insertStatement.setInt(2, i);
				insertStatement.setLong(3, heavyHittersIDs.get(i));
				insertStatement.executeUpdate();
			}
			
		} catch (final SQLException e)
		{
			e.printStackTrace();
			return SqlError.FAILURE;
		}
		return SqlError.SUCCESS;
	}
	
	
	
	private final String table = schema + "." + "`heavy_hitters`";
	
}
