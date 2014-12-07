/**
 *
 */

package com.robotwitter.database;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import com.google.inject.Inject;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

import com.robotwitter.database.interfaces.ConnectionEstablisher;
import com.robotwitter.database.interfaces.IDatabaseTwitterAccounts;
import com.robotwitter.database.interfaces.returnValues.InsertError;
import com.robotwitter.database.primitives.DBTwitterAccount;




/**
 * @author Shmulik and Eyal
 *
 *         The database handles saving twitter account connection details
 */
public class MySqlDatabaseTwitterAccounts extends MySqlDatabase
	implements
		IDatabaseTwitterAccounts
{
	
	private enum Columns
	{
		USER_ID,
		EMAIL,
		TOKEN,
		PRIVATE_TOKEN
	}
	
	
	
	/**
	 * @param conEstablisher
	 *            The connection handler
	 * @throws SQLException
	 */
	@Inject
	public MySqlDatabaseTwitterAccounts(
		final ConnectionEstablisher conEstablisher) throws SQLException
	{
		super(conEstablisher);
		try (
			Connection con = connectionEstablisher.getConnection();
			Statement statement = (Statement) con.createStatement())
		{
			final String statementCreate =
				String.format("CREATE TABLE IF NOT EXISTS %s (" //$NON-NLS-1$
					+ "`%s` BIGINT NOT NULL," //$NON-NLS-1$
					+ "`%s` VARCHAR(255) NOT NULL," //$NON-NLS-1$
					+ "`%s` VARCHAR(255) NOT NULL," //$NON-NLS-1$
					+ "`%s` VARCHAR(255) NOT NULL," //$NON-NLS-1$
					+ "PRIMARY KEY (`%s`)) DEFAULT CHARSET=utf8;", //$NON-NLS-1$
					table,
					Columns.USER_ID.name().toLowerCase(),
					Columns.EMAIL.name().toLowerCase(),
					Columns.TOKEN.name().toLowerCase(),
					Columns.PRIVATE_TOKEN.name().toLowerCase(),
					Columns.USER_ID.name().toLowerCase());
			statement.execute(statementCreate);
		}
	}
	
	
	/* (non-Javadoc) @see
	 * com.Robotwitter.Database.IDatabase#get(java.lang.String) */
	@Override
	@SuppressWarnings({ "boxing", "nls" })
	public ArrayList<DBTwitterAccount> get(String eMail)
	{
		if (eMail == null) { return null; }
		ArrayList<DBTwitterAccount> $ = null;
		try (
			Connection con = connectionEstablisher.getConnection();
			PreparedStatement preparedStatement =
				(PreparedStatement) con.prepareStatement(""
					+ "SELECT * FROM "
					+ table
					+ " WHERE "
					+ Columns.EMAIL.name().toLowerCase()
					+ "=?;"))
		{
			preparedStatement.setString(1, eMail);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next())
			{
				$ = new ArrayList<>();
				final DBTwitterAccount twitterAccount =
					new DBTwitterAccount(
						resultSet.getString(Columns.EMAIL.name().toLowerCase()),
						resultSet.getString(Columns.TOKEN.name().toLowerCase()),
						resultSet.getString(Columns.PRIVATE_TOKEN
							.name()
							.toLowerCase()), resultSet.getLong(Columns.USER_ID
							.name()
							.toLowerCase()));
				$.add(twitterAccount);
				preparedStatement.close();
				resultSet.close();
			}
		} catch (final Exception e)
		{
			e.printStackTrace();
		}
		return $;
	}
	
	
	/* (non-Javadoc) @see
	 * com.Robotwitter.Database.IDatabase#insert(com.Robotwitter
	 * .DatabasePrimitives.DatabaseType) */
	@Override
	@SuppressWarnings({ "boxing" })
	public InsertError insert(final DBTwitterAccount twitterAccount)
	{
		if (twitterAccount == null) { return InsertError.INVALID_PARAMS; }
		try (
			Connection con = connectionEstablisher.getConnection();
			
			PreparedStatement preparedStatement =
				(PreparedStatement) con.prepareStatement("INSERT INTO " //$NON-NLS-1$
					+ table
					+ " (" //$NON-NLS-1$
					+ Columns.USER_ID.name().toLowerCase()
					+ "," //$NON-NLS-1$
					+ Columns.EMAIL.name().toLowerCase()
					+ "," //$NON-NLS-1$
					+ Columns.TOKEN.name().toLowerCase()
					+ "," //$NON-NLS-1$
					+ Columns.PRIVATE_TOKEN.name().toLowerCase()
					+ ") VALUES ( ?, ?, ?, ? );")) //$NON-NLS-1$
		{
			preparedStatement.setLong(1, twitterAccount.getUserId());
			preparedStatement.setString(2, twitterAccount.getEMail());
			preparedStatement.setString(3, twitterAccount.getToken());
			preparedStatement.setString(4, twitterAccount.getPrivateToken());
			preparedStatement.executeUpdate();
		} catch (final SQLException e)
		{
			if (e.getErrorCode() == insertErrorCode) { return InsertError.ALREADY_EXIST; }
		}
		return InsertError.SUCCESS;
	}
	
	
	/* (non-Javadoc) @see
	 * com.Robotwitter.Database.IDatabase#isExists(java.lang.String) */
	@Override
	@SuppressWarnings({ "nls", "boxing" })
	public boolean isExists(final Long userId)
	{
		if (userId == null) { return false; }
		boolean $ = false;
		try (
			Connection con = connectionEstablisher.getConnection();
			PreparedStatement preparedStatement =
				(PreparedStatement) con.prepareStatement(""
					+ "SELECT * FROM "
					+ table
					+ " WHERE "
					+ Columns.USER_ID.name().toLowerCase()
					+ "=?;"))
		{
			preparedStatement.setLong(1, userId);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.first())
			{
				$ = true;
			}
		} catch (final Exception e)
		{
			e.printStackTrace();
		}
		return $;
	}
	
	
	
	@SuppressWarnings("nls")
	final private String table = schema + "." + "`user_twitter_accounts`"; //$NON-NLS-1$ //$NON-NLS-2$
	
}
