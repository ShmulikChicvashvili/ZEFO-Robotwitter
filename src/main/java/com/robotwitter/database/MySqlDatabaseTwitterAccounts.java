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
 * The database handles saving twitter account connection details.
 *
 * @author Shmulik and Eyal
 *
 */
public class MySqlDatabaseTwitterAccounts extends MySqlDatabase
	implements
		IDatabaseTwitterAccounts
{

	/**
	 * @author Shmulik An enum for the columns of the table.
	 */
	private enum Columns
	{
		/**
		 * User id column.
		 */
		USER_ID,
		/**
		 * Email column.
		 */
		EMAIL,
		/**
		 * Token column.
		 */
		TOKEN,
		/**
		 * Private token column.
		 */
		PRIVATE_TOKEN
	}



	/**
	 * @param conEstablisher
	 *            The connection handler
	 * @throws SQLException
	 *             exception
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
					Columns.USER_ID.toString().toLowerCase(),
					Columns.EMAIL.toString().toLowerCase(),
					Columns.TOKEN.toString().toLowerCase(),
					Columns.PRIVATE_TOKEN.toString().toLowerCase(),
					Columns.USER_ID.toString().toLowerCase());
			statement.execute(statementCreate);
		}
	}


	/* (non-Javadoc) @see
	 * com.Robotwitter.Database.IDatabase#get(java.lang.String) */
	@Override
	@SuppressWarnings({ "boxing", "nls" })
	public final ArrayList<DBTwitterAccount> get(String eMail)
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
					+ Columns.EMAIL.toString().toLowerCase()
					+ "=?;"))
		{
			preparedStatement.setString(1, eMail);
			resultSet = preparedStatement.executeQuery();
			$ = new ArrayList<>();
			while (resultSet.next())
			{
				final DBTwitterAccount twitterAccount =
					new DBTwitterAccount(
						resultSet.getString(Columns.EMAIL
							.toString()
							.toLowerCase()),
						resultSet.getString(Columns.TOKEN
							.toString()
							.toLowerCase()),
						resultSet.getString(Columns.PRIVATE_TOKEN
							.toString()
							.toLowerCase()),
						resultSet.getLong(Columns.USER_ID
							.toString()
							.toLowerCase()));
				$.add(twitterAccount);
			}
			resultSet.close();
		} catch (final Exception e)
		{
			// TODO understand what the hell we should do?!
			e.printStackTrace();
		}
		if ($ == null || $.isEmpty()) { return null; }
		return $;
	}


	/* (non-Javadoc) @see
	 * com.Robotwitter.Database.IDatabase#insert(com.Robotwitter
	 * .DatabasePrimitives.DatabaseType) */
	@Override
	@SuppressWarnings({ "boxing" })
	public final InsertError insert(final DBTwitterAccount twitterAccount)
	{
		if (twitterAccount == null) { return InsertError.INVALID_PARAMS; }
		try (
			Connection con = connectionEstablisher.getConnection();

			PreparedStatement preparedStatement =
				(PreparedStatement) con.prepareStatement("INSERT INTO " //$NON-NLS-1$
					+ table
					+ " (" //$NON-NLS-1$
					+ Columns.USER_ID.toString().toLowerCase()
					+ "," //$NON-NLS-1$
					+ Columns.EMAIL.toString().toLowerCase()
					+ "," //$NON-NLS-1$
					+ Columns.TOKEN.toString().toLowerCase()
					+ "," //$NON-NLS-1$
					+ Columns.PRIVATE_TOKEN.toString().toLowerCase()
					+ ") VALUES ( ?, ?, ?, ? );")) //$NON-NLS-1$
		{
			preparedStatement.setLong(1, twitterAccount.getUserId());
			preparedStatement.setString(2, twitterAccount.getEMail());
			preparedStatement.setString(3, twitterAccount.getToken());
			preparedStatement.setString(4, twitterAccount.getPrivateToken());
			preparedStatement.executeUpdate();
		} catch (final SQLException e)
		{
			if (e.getErrorCode() == insertAlreadyExists) { return InsertError.ALREADY_EXIST; }
			e.printStackTrace();
		}
		return InsertError.SUCCESS;
	}


	/* (non-Javadoc) @see
	 * com.Robotwitter.Database.IDatabase#isExists(java.lang.String) */
	@Override
	@SuppressWarnings({ "nls", "boxing" })
	public final boolean isExists(final Long userId)
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
					+ Columns.USER_ID.toString().toLowerCase()
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
			// TODO understand what the hell we should do?!
			e.printStackTrace();
		}
		return $;
	}



	/**
	 * The table's name
	 */
	@SuppressWarnings("nls")
	final private String table = schema + "." + "`user_twitter_accounts`"; //$NON-NLS-1$ //$NON-NLS-2$

}
