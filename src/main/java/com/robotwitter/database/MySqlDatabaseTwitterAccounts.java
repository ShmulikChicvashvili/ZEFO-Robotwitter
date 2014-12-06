/**
 *
 */

package com.robotwitter.database;


import java.sql.Connection;
import java.util.ArrayList;

import com.google.inject.Inject;

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
	
	/**
	 * @param conEstablisher
	 *            The connection handler
	 */
	@Inject
	public MySqlDatabaseTwitterAccounts(
		final ConnectionEstablisher conEstablisher)
	{
		super(conEstablisher);
		try (Connection con = connectionEstablisher.getConnection())
		{
			statement = con.createStatement();
			final String statementCreate =
				"CREATE TABLE IF NOT EXISTS " + table + "(" //$NON-NLS-1$ //$NON-NLS-2$
					+ "`user_id` BIGINT NOT NULL," //$NON-NLS-1$
					+ "`email` VARCHAR(255) NOT NULL," //$NON-NLS-1$
					+ "`token` VARCHAR(255) NOT NULL," //$NON-NLS-1$
					+ "`private_token` VARCHAR(255) NOT NULL," //$NON-NLS-1$
					+ "PRIMARY KEY (`user_id`)) DEFAULT CHARSET=utf8;"; //$NON-NLS-1$
			statement.execute(statementCreate);
		} catch (final Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		try (Connection con = connectionEstablisher.getConnection())
		{
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
				$ = new ArrayList<DBTwitterAccount>();
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
		}
		return $;
	}
	
	
	/* (non-Javadoc) @see
	 * com.Robotwitter.Database.IDatabase#insert(com.Robotwitter
	 * .DatabasePrimitives.DatabaseType) */
	@SuppressWarnings({ "nls", "boxing" })
	public InsertError insert(final DBTwitterAccount twitterAccount)
	{
		if(twitterAccount == null) {
			return InsertError.INVALID_PARAMS;
		}
		try (Connection con = connectionEstablisher.getConnection())
		{
			
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
		}
		return InsertError.SUCCESS;
	}
	
	
	/* (non-Javadoc) @see
	 * com.Robotwitter.Database.IDatabase#isExists(java.lang.String) */
	@SuppressWarnings({ "nls", "boxing" })
	public boolean isExists(final Long userId)
	{
		if(userId == null) {
			return false;
		}
		boolean $ = false;
		try (Connection con = connectionEstablisher.getConnection())
		{
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
		}
		return $;
	}
	
	
	
	@SuppressWarnings("nls")
	final private String table = schema + "." + "`user_twitter_accounts`"; //$NON-NLS-1$ //$NON-NLS-2$
	
	final private String userIdColumn = "user_id"; //$NON-NLS-1$
	
	@SuppressWarnings("nls")
	final private String eMailColumn = "email"; //$NON-NLS-1$
	
	@SuppressWarnings("nls")
	final private String tokenColumn = "token"; //$NON-NLS-1$
	
	@SuppressWarnings("nls")
	final private String privateTokenColumn = "private_token"; //$NON-NLS-1$
}
