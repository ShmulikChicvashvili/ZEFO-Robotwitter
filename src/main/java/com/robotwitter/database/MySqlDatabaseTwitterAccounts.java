/**
 *
 */

package com.robotwitter.database;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.google.inject.Inject;

import com.robotwitter.database.interfaces.ConnectionEstablisher;
import com.robotwitter.database.interfaces.IDatabaseTwitterAccounts;
import com.robotwitter.database.interfaces.returnValues.SqlError;
import com.robotwitter.database.primitives.DBTwitterAccount;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;




/**
 * The database handles saving twitter account connection details.
 *
 * @author Shmulik and Eyal
 *
 */
@SuppressWarnings("nls")
public class MySqlDatabaseTwitterAccounts extends AbstractMySqlDatabase
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
	 * Instantiates a new my sql database twitter accounts.
	 *
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
			Statement statement = con.createStatement())
		{
			@SuppressFBWarnings("DM_CONVERT_CASE")
			final String statementCreate =
				String.format(
					"CREATE TABLE IF NOT EXISTS %s ("
						+ "`%s` BIGINT NOT NULL,"
						+ "`%s` VARCHAR(255) NOT NULL,"
						+ "`%s` VARCHAR(255) NOT NULL,"
						+ "`%s` VARCHAR(255) NOT NULL,"
						+ "PRIMARY KEY (`%s`)) DEFAULT CHARSET=utf8;",
					table,
					Columns.USER_ID.toString().toLowerCase(),
					Columns.EMAIL.toString().toLowerCase(),
					Columns.TOKEN.toString().toLowerCase(),
					Columns.PRIVATE_TOKEN.toString().toLowerCase(),
					Columns.USER_ID.toString().toLowerCase());
			statement.execute(statementCreate);
		}
	}


	@SuppressWarnings("boxing")
	@Override
	public final SqlError delete(Long userID)
	{
		if (userID == null) { return SqlError.INVALID_PARAMS; }

		if (!isExists(userID)) { return SqlError.DOES_NOT_EXIST; }

		try (
			Connection con = connectionEstablisher.getConnection();
			PreparedStatement preparedStatement =
				con.prepareStatement(""
					+ "DELETE FROM "
					+ table
					+ " WHERE "
					+ Columns.USER_ID.toString().toLowerCase()
					+ "=?;"))
		{
			preparedStatement.setLong(1, userID);
			preparedStatement.executeUpdate();
		} catch (final SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return SqlError.SUCCESS;
	}


	/* (non-Javadoc) @see com.robotwitter.database.interfaces.IDatabaseTwitterAccounts#get(java.lang.Long) */
	@Override
	public DBTwitterAccount get(long userID)
	{
		DBTwitterAccount $ = null;
		try (
			Connection con = connectionEstablisher.getConnection();
			PreparedStatement preparedStatement =
				con.prepareStatement(""
					+ "SELECT * FROM "
					+ table
					+ " WHERE "
					+ Columns.USER_ID.toString().toLowerCase()
					+ "=?;"))
		{
			preparedStatement.setLong(1, userID);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next())
			{
				$ =
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
			}
			resultSet.close();
		} catch (final Exception e)
		{
			// TODO understand what the hell we should do?!
			e.printStackTrace();
		}
		if ($ == null) { return null; }
		return $;
	}


	/* (non-Javadoc) @see
	 * com.Robotwitter.Database.IDatabase#get(java.lang.String) */
	@Override
	@SuppressWarnings("boxing")
	public final ArrayList<DBTwitterAccount> get(String eMail)
	{
		if (eMail == null) { return null; }
		ArrayList<DBTwitterAccount> $ = null;
		try (
			Connection con = connectionEstablisher.getConnection();
			PreparedStatement preparedStatement =
				con.prepareStatement(""
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
	@SuppressWarnings("boxing")
	public final SqlError insert(final DBTwitterAccount twitterAccount)
	{
		if (twitterAccount == null
			|| twitterAccount.getUserId() == null
			|| twitterAccount.getEMail() == null
			|| twitterAccount.getToken() == null
			|| twitterAccount.getPrivateToken() == null) { return SqlError.INVALID_PARAMS; }
		try (
			Connection con = connectionEstablisher.getConnection();

			PreparedStatement preparedStatement =
				con.prepareStatement("INSERT INTO "
					+ table
					+ " ("
					+ Columns.USER_ID.toString().toLowerCase()
					+ ","
					+ Columns.EMAIL.toString().toLowerCase()
					+ ","
					+ Columns.TOKEN.toString().toLowerCase()
					+ ","
					+ Columns.PRIVATE_TOKEN.toString().toLowerCase()
					+ ") VALUES ( ?, ?, ?, ? );"))
		{
			preparedStatement.setLong(1, twitterAccount.getUserId());
			preparedStatement.setString(2, twitterAccount.getEMail());
			preparedStatement.setString(3, twitterAccount.getToken());
			preparedStatement.setString(4, twitterAccount.getPrivateToken());
			preparedStatement.executeUpdate();
		} catch (final SQLException e)
		{
			if (e.getErrorCode() == insertAlreadyExists) { return SqlError.ALREADY_EXIST; }
			e.printStackTrace();
		}
		return SqlError.SUCCESS;
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
				con.prepareStatement(""
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



	@SuppressWarnings("boxing")
	@Override
	public final SqlError update(DBTwitterAccount twitterAccount)
	{
		if (twitterAccount == null
			|| twitterAccount.getUserId() == null
			|| twitterAccount.getEMail() == null
			|| twitterAccount.getToken() == null
			|| twitterAccount.getPrivateToken() == null) { return SqlError.INVALID_PARAMS; }

		if (!isExists(twitterAccount.getUserId())) { return SqlError.DOES_NOT_EXIST; }

		try (
			Connection con = connectionEstablisher.getConnection();
			@SuppressWarnings("nls")
			PreparedStatement preparedStatement =
				con.prepareStatement(String.format(
					"UPDATE %s SET %s = ?, %s = ?, %s = ? WHERE %s = ?",
					table,
					Columns.EMAIL.toString().toLowerCase(),
					Columns.PRIVATE_TOKEN.toString().toLowerCase(),
					Columns.TOKEN.toString().toLowerCase(),
					Columns.USER_ID.toString().toLowerCase())))
		{
			preparedStatement.setString(1, twitterAccount.getEMail());
			preparedStatement.setString(2, twitterAccount.getToken());
			preparedStatement.setString(3, twitterAccount.getPrivateToken());
			preparedStatement.setLong(4, twitterAccount.getUserId());
			preparedStatement.executeUpdate();
		} catch (final SQLException e)
		{
			e.printStackTrace();
		}

		return SqlError.SUCCESS;
	}



	/** The table's name. */
	private final String table = schema + "." + "`user_twitter_accounts`";

}
