/**
 *
 */

package com.robotwitter.database;


import java.util.ArrayList;

import com.google.inject.Inject;

import com.robotwitter.database.primitives.DBTwitterAccount;
import com.robotwitter.database.primitives.DatabaseType;




/**
 * @author Shmulik
 *
 *         The database handles saving twitter account connection details
 */
public class MySqlDatabaseTwitterAccounts extends MySqlDatabase
{

	/**
	 * @param conEstablisher
	 *            The connection handler
	 */
	@Inject
	public MySqlDatabaseTwitterAccounts(
		final ConnectionEstablisher conEstablisher)
	{
		super(conEstablisher);
		try
		{
			con = connectionEstablisher.getConnection();
			statement = con.createStatement();
			final String statementCreate =
				"CREATE TABLE IF NOT EXISTS `yearlyproj_db`.`user_twitter_accounts` (" //$NON-NLS-1$
				+ "`user_id` BIGINT NOT NULL," //$NON-NLS-1$
				+ "`email` VARCHAR(45) NOT NULL," //$NON-NLS-1$
				+ "`token` VARCHAR(45) NOT NULL," //$NON-NLS-1$
				+ "`private_token` VARCHAR(45) NOT NULL," //$NON-NLS-1$
				+ "PRIMARY KEY (`user_id`));"; //$NON-NLS-1$
			statement.execute(statementCreate);
		} catch (final Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally
		{
			try
			{
				statement.close();
				con.close();
			} catch (final Exception e)
			{
				// DO NOTHING! Throwing something can make close get into
				// undefined behaviour.
			}
		}
	}
	
	
	/* (non-Javadoc) @see
	 * com.Robotwitter.Database.IDatabase#get(java.lang.String) */
	@Override
	@SuppressWarnings({ "boxing", "nls" })
	public ArrayList<DatabaseType> get(final String eMail)
	{
		ArrayList<DatabaseType> $ = null;
		try
		{
			con = connectionEstablisher.getConnection();
			preparedStatement =
				con.prepareStatement(""
					+ "SELECT * FROM "
					+ table
					+ " WHERE "
					+ eMailColumn
					+ "=?;");
			preparedStatement.setString(1, eMail);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next())
			{
				$ = new ArrayList<DatabaseType>();
				final DBTwitterAccount twitterAccount =
					new DBTwitterAccount(
						resultSet.getString(eMailColumn),
						resultSet.getString(tokenColumn),
						resultSet.getString(privateTokenColumn),
						resultSet.getLong(userIdColumn));
				$.add(twitterAccount);
			}
		} catch (final Exception e)
		{
			e.printStackTrace();
		} finally
		{
			try
			{
				resultSet.close();
				preparedStatement.close();
				con.close();
			} catch (final Exception e)
			{
				// DO NOTHING! Throwing something can make close get into
				// undefined behaviour.
			}
		}
		return $;
	}
	
	
	/* (non-Javadoc) @see
	 * com.Robotwitter.Database.IDatabase#insert(com.Robotwitter
	 * .DatabasePrimitives.DatabaseType) */
	@SuppressWarnings({ "nls", "boxing" })
	public void insert(final DatabaseType obj)
	{
		final DBTwitterAccount twitterAccount = (DBTwitterAccount) obj;
		try
		{
			con = connectionEstablisher.getConnection();

			preparedStatement = con.prepareStatement("INSERT INTO " //$NON-NLS-1$
				+ table
				+ " (" //$NON-NLS-1$
				+ userIdColumn
				+ "," //$NON-NLS-1$
				+ eMailColumn
				+ "," //$NON-NLS-1$
				+ tokenColumn
				+ "," //$NON-NLS-1$
				+ privateTokenColumn
				+ ") VALUES ( ?, ?, ?, ? );"); //$NON-NLS-1$
			preparedStatement.setLong(1, twitterAccount.getUserId());
			preparedStatement.setString(2, twitterAccount.getEMail());
			preparedStatement.setString(3, twitterAccount.getToken());
			preparedStatement.setString(4, twitterAccount.getPrivateToken());
			preparedStatement.executeUpdate();

		} catch (final Exception e)
		{
			e.printStackTrace();
		} finally
		{
			try
			{
				preparedStatement.close();
				con.close();
			} catch (final Exception e)
			{
				// DO NOTHING! Throwing something can make close get into
				// undefined behaviour.
			}
		}
	}
	
	
	/* (non-Javadoc) @see
	 * com.Robotwitter.Database.IDatabase#isExists(java.lang.String) */
	@SuppressWarnings({ "nls", "boxing" })
	public boolean isExists(final DatabaseType obj)
	{
		final DBTwitterAccount temp = (DBTwitterAccount) obj;
		final Long userId = temp.getUserId();
		boolean $ = false;
		try
		{
			con = connectionEstablisher.getConnection();
			preparedStatement =
				con.prepareStatement(""
					+ "SELECT * FROM "
					+ table
					+ " WHERE "
					+ userIdColumn
					+ "=?;");
			preparedStatement.setLong(1, userId);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.first())
			{
				$ = true;
			}
		} catch (final Exception e)
		{
			e.printStackTrace();
		} finally
		{
			try
			{
				resultSet.close();
				preparedStatement.close();
				con.close();
			} catch (final Exception e)
			{
				// DO NOTHING! Throwing something can make close get into
				// undefined behaviour.
			}
		}
		return $;
	}
	
	
	
	@SuppressWarnings("nls")
	final private String table = schema + "." + "user_twitter_accounts"; //$NON-NLS-1$ //$NON-NLS-2$
	
	final private String userIdColumn = "user_id"; //$NON-NLS-1$
	
	@SuppressWarnings("nls")
	final private String eMailColumn = "email"; //$NON-NLS-1$
	
	@SuppressWarnings("nls")
	final private String tokenColumn = "token"; //$NON-NLS-1$
	
	@SuppressWarnings("nls")
	final private String privateTokenColumn = "private_token"; //$NON-NLS-1$
}
