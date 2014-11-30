/**
 * 
 */

package com.Robotwitter.Database;


import java.util.ArrayList;

import com.Robotwitter.DatabasePrimitives.DBTwitterAccount;
import com.Robotwitter.DatabasePrimitives.DatabaseType;
import com.google.inject.Inject;




/**
 * @author Shmulik
 *
 *         The database handles saving twitter account connection details
 */
public class MySqlDatabaseTwitterAccounts extends MySqlDatabase
{
	
	@SuppressWarnings("nls")
	final private String table = this.schema + "." + "user_twitter_accounts"; //$NON-NLS-1$ //$NON-NLS-2$
	
	final private String userIdColumn = "user_id"; //$NON-NLS-1$
	
	@SuppressWarnings("nls")
	final private String eMailColumn = "email"; //$NON-NLS-1$
	
	@SuppressWarnings("nls")
	final private String tokenColumn = "token"; //$NON-NLS-1$
	
	@SuppressWarnings("nls")
	final private String privateTokenColumn = "private_token"; //$NON-NLS-1$
	
	
	
	/**
	 * @param conEstablisher
	 *            The connection handler
	 */
	@Inject
	public MySqlDatabaseTwitterAccounts(ConnectionEstablisher conEstablisher)
	{
		super(conEstablisher);
		try
		{
			this.con = this.connectionEstablisher.getConnection();
			this.statement = this.con.createStatement();
			String statementCreate =
				"CREATE TABLE IF NOT EXISTS `yearlyproj_db`.`user_twitter_accounts` (" //$NON-NLS-1$
					+ "`user_id` BIGINT NOT NULL," //$NON-NLS-1$
					+ "`email` VARCHAR(45) NOT NULL," //$NON-NLS-1$
					+ "`token` VARCHAR(45) NOT NULL," //$NON-NLS-1$
					+ "`private_token` VARCHAR(45) NOT NULL," //$NON-NLS-1$
					+ "PRIMARY KEY (`user_id`));"; //$NON-NLS-1$
			this.statement.execute(statementCreate);
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally
		{
			try
			{
				this.statement.close();
				this.con.close();
			} catch (Exception e)
			{
				// DO NOTHING! Throwing something can make close get into
				// undefined behaviour.
			}
		}
	}
	
	
	/* (non-Javadoc) @see
	 * com.Robotwitter.Database.IDatabase#insert(com.Robotwitter
	 * .DatabasePrimitives.DatabaseType) */
	@SuppressWarnings({ "nls", "boxing" })
	public void insert(DatabaseType obj)
	{
		DBTwitterAccount twitterAccount = (DBTwitterAccount) obj;
		try
		{
			this.con = this.connectionEstablisher.getConnection();
			
			this.preparedStatement = this.con.prepareStatement("INSERT INTO " //$NON-NLS-1$
				+ this.table
				+ " (" //$NON-NLS-1$
				+ this.userIdColumn
				+ "," //$NON-NLS-1$
				+ this.eMailColumn
				+ "," //$NON-NLS-1$
				+ this.tokenColumn
				+ "," //$NON-NLS-1$
				+ this.privateTokenColumn
				+ ") VALUES ( ?, ?, ?, ? );"); //$NON-NLS-1$
			this.preparedStatement.setLong(1, twitterAccount.getUserId());
			this.preparedStatement.setString(2, twitterAccount.getEMail());
			this.preparedStatement.setString(3, twitterAccount.getToken());
			this.preparedStatement.setString(
				4,
				twitterAccount.getPrivateToken());
			this.preparedStatement.executeUpdate();
			
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			try
			{
				this.preparedStatement.close();
				this.con.close();
			} catch (Exception e)
			{
				// DO NOTHING! Throwing something can make close get into
				// undefined behaviour.
			}
		}
	}
	
	
	/* (non-Javadoc) @see
	 * com.Robotwitter.Database.IDatabase#isExists(java.lang.String) */
	@SuppressWarnings({ "nls", "boxing" })
	public boolean isExists(DatabaseType obj)
	{
		DBTwitterAccount temp = (DBTwitterAccount) obj;
		Long userId = temp.getUserId();
		boolean $ = false;
		try
		{
			this.con = this.connectionEstablisher.getConnection();
			this.preparedStatement =
				this.con.prepareStatement(""
					+ "SELECT * FROM "
					+ this.table
					+ " WHERE "
					+ this.userIdColumn
					+ "=?;");
			this.preparedStatement.setLong(1, userId);
			this.resultSet = this.preparedStatement.executeQuery();
			if (this.resultSet.first())
			{
				$ = true;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			try
			{
				this.resultSet.close();
				this.preparedStatement.close();
				this.con.close();
			} catch (Exception e)
			{
				// DO NOTHING! Throwing something can make close get into
				// undefined behaviour.
			}
		}
		return $;
	}
	
	
	/* (non-Javadoc) @see
	 * com.Robotwitter.Database.IDatabase#get(java.lang.String) */
	@SuppressWarnings({ "boxing", "nls" })
	public ArrayList<DatabaseType> get(String eMail)
	{
		ArrayList<DatabaseType> $ = null;
		try
		{
			this.con = this.connectionEstablisher.getConnection();
			this.preparedStatement =
				this.con.prepareStatement(""
					+ "SELECT * FROM "
					+ this.table
					+ " WHERE "
					+ this.eMailColumn
					+ "=?;");
			this.preparedStatement.setString(1, eMail);
			this.resultSet = this.preparedStatement.executeQuery();
			if (this.resultSet.next())
			{
				$ = new ArrayList<DatabaseType>();
				DBTwitterAccount twitterAccount =
					new DBTwitterAccount(
						this.resultSet.getString(this.eMailColumn),
						this.resultSet.getString(this.tokenColumn),
						this.resultSet.getString(this.privateTokenColumn),
						this.resultSet.getLong(this.userIdColumn));
				$.add(twitterAccount);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			try
			{
				this.resultSet.close();
				this.preparedStatement.close();
				this.con.close();
			} catch (Exception e)
			{
				// DO NOTHING! Throwing something can make close get into
				// undefined behaviour.
			}
		}
		return $;
	}
}
