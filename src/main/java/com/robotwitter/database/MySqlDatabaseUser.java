/**
 *
 */

package com.robotwitter.database;


import java.io.File;
import java.util.ArrayList;

import com.google.inject.Inject;
import com.robotwitter.database.interfaces.ConnectionEstablisher;
import com.robotwitter.database.interfaces.IDatabaseUsers;
import com.robotwitter.database.primitives.DBUser;
import com.robotwitter.database.primitives.DatabaseType;




/**
 * @author Shmulik
 *
 *         The database that handles registring a user and fetching a user
 */
public class MySqlDatabaseUser extends MySqlDatabase implements IDatabaseUsers
{
	
	/**
	 * C'tor For MySql db of users
	 *
	 * @param conEstablisher
	 *            A connection establisher for the database
	 */
	@Inject
	public MySqlDatabaseUser(final ConnectionEstablisher conEstablisher)
	{
		super(conEstablisher);
		try
		{
			this.con = this.connectionEstablisher.getConnection();
			this.statement = this.con.createStatement();
				final String statementCreate = 
					"CREATE TABLE IF NOT EXISTS `yearlyproj_db`.`users` (" //$NON-NLS-1$
						+ "`email` VARCHAR(45) NOT NULL," //$NON-NLS-1$
						+ "`password` VARCHAR(45) NOT NULL," //$NON-NLS-1$
						+ "PRIMARY KEY (`email`));"; //$NON-NLS-1$
				this.statement.execute(statementCreate);
		} catch (final Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		finally {
			CloseConnection();
		}
	}
	
	
	/* (non-Javadoc) @see Database.IDatabase#get(java.lang.String) */
	@Override
	@SuppressWarnings("nls")
	public DBUser get(final String eMail)
	{
		DBUser $ = null;
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
				$ =
					new DBUser(
						this.resultSet.getString(this.eMailColumn),
						this.resultSet.getString(this.passwordColumn));
			}
		} catch (final Exception e)
		{
			e.printStackTrace();
		} finally
		{
			CloseConnection();
		}
		return $;
	}
	
	
	/* (non-Javadoc) @see
	 * Database.IDatabase#insert(DatabasePrimitives.DatabaseTypes) */
	@SuppressWarnings("nls")
	public void insert(DBUser user)
	{
		try
		{
			this.con = this.connectionEstablisher.getConnection();
			
			this.preparedStatement =
				this.con.prepareStatement("INSERT INTO "
					+ this.table
					+ " ("
					+ this.eMailColumn
					+ ","
					+ this.passwordColumn
					+ ") VALUES ( ?, ? );");
			this.preparedStatement.setString(1, user.getEMail());
			this.preparedStatement.setString(2, user.getPassword());
			this.preparedStatement.executeUpdate();
			
		} catch (final Exception e)
		{
			e.printStackTrace();
		} finally
		{
			CloseConnection();
		}
		
	}
	
	
	/**
	 * Create table of users statement
	 */
	
	/* (non-Javadoc) @see Database.IDatabase#isExists(java.lang.String) */
	@SuppressWarnings("nls")
	public boolean isExists(String eMail)
	{
		boolean $ = false;
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
			if (this.resultSet.first())
			{
				$ = true;
			}
		} catch (final Exception e)
		{
			e.printStackTrace();
		} finally
		{
			CloseConnection();
		}
		return $;
	}
	
	
	
	/**
	 * The table name
	 */
	final private String table = this.schema + "." + "users"; //$NON-NLS-1$ //$NON-NLS-2$
	
	/**
	 * Email column
	 */
	final private String eMailColumn = "email"; //$NON-NLS-1$
	
	/**
	 * Password column
	 */
	final private String passwordColumn = "password"; //$NON-NLS-1$
}
