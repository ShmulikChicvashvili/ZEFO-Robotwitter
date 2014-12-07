/**
 *
 */

package com.robotwitter.database;


import java.sql.Connection;
import java.sql.SQLException;

import com.google.inject.Inject;
import com.robotwitter.database.interfaces.ConnectionEstablisher;
import com.robotwitter.database.interfaces.IDatabaseUsers;
import com.robotwitter.database.interfaces.returnValues.InsertError;
import com.robotwitter.database.primitives.DBUser;




/**
 * @author Shmulik and Eyal
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
		try (Connection con = this.connectionEstablisher.getConnection())
		{
			this.statement = con.createStatement();
			final String statementCreate =
				"CREATE TABLE IF NOT EXISTS " + this.table + " (" //$NON-NLS-1$//$NON-NLS-2$
					+ "`email` VARCHAR(45) NOT NULL," //$NON-NLS-1$
					+ "`password` VARCHAR(45) NOT NULL," //$NON-NLS-1$
					+ "PRIMARY KEY (`email`));"; //$NON-NLS-1$
			this.statement.execute(statementCreate);
		} catch (final Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/* (non-Javadoc) @see Database.IDatabase#get(java.lang.String) */
	@Override
	@SuppressWarnings("nls")
	public DBUser get(final String eMailUser)
	{
		if (eMailUser == null) { return null; }
		String eMail = eMailUser.toLowerCase();
		DBUser $ = null;
		try (Connection con = this.connectionEstablisher.getConnection())
		{
			this.preparedStatement = con.prepareStatement("" //$NON-NLS-1$
				+ "SELECT * FROM " //$NON-NLS-1$
				+ this.table
				+ " WHERE " //$NON-NLS-1$
				+ this.eMailColumn
				+ "=?;"); //$NON-NLS-1$
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
		}
		return $;
	}
	
	
	/* (non-Javadoc) @see
	 * Database.IDatabase#insert(DatabasePrimitives.DatabaseTypes) */
	@Override
	@SuppressWarnings("nls")
	public InsertError insert(DBUser user)
	{
		if (user == null) { return InsertError.INVALID_PARAMS; }
		try (Connection con = this.connectionEstablisher.getConnection())
		{
			this.preparedStatement = con.prepareStatement("INSERT INTO " //$NON-NLS-1$
				+ this.table
				+ " (" //$NON-NLS-1$
				+ this.eMailColumn
				+ "," //$NON-NLS-1$
				+ this.passwordColumn
				+ ") VALUES ( ?, ? );"); //$NON-NLS-1$
			this.preparedStatement.setString(1, user.getEMail().toLowerCase());
			this.preparedStatement.setString(2, user.getPassword());
			this.preparedStatement.executeUpdate();
			
		} catch (ClassNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return InsertError.SUCCESS;
	}
	
	
	/**
	 * Create table of users statement
	 */
	
	@Override
	/* (non-Javadoc) @see Database.IDatabase#isExists(java.lang.String) */
	@SuppressWarnings("nls")
	public boolean isExists(String eMailUser)
	{
		if (eMailUser == null) { return false; }
		String eMail = eMailUser.toLowerCase();
		boolean $ = false;
		try (Connection con = this.connectionEstablisher.getConnection())
		{
			this.preparedStatement = con.prepareStatement("" //$NON-NLS-1$
				+ "SELECT * FROM " //$NON-NLS-1$
				+ this.table
				+ " WHERE " //$NON-NLS-1$
				+ this.eMailColumn
				+ "=?;"); //$NON-NLS-1$
			this.preparedStatement.setString(1, eMail);
			this.resultSet = this.preparedStatement.executeQuery();
			if (this.resultSet.first())
			{
				$ = true;
			}
		} catch (final Exception e)
		{
			e.printStackTrace();
		}
		return $;
	}
	
	
	
	/**
	 * The table name
	 */
	final private String table = this.schema + "." + "`users`"; //$NON-NLS-1$ //$NON-NLS-2$ 
	
	/**
	 * Email column
	 */
	final private String eMailColumn = "email"; //$NON-NLS-1$
	
	/**
	 * Password column
	 */
	final private String passwordColumn = "password"; //$NON-NLS-1$
}
