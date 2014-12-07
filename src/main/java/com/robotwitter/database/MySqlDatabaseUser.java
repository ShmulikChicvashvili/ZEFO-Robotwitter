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
	private enum Columns
	{
		EMAIL,
		PASSWORD
	}
	
	
	
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
		try (Connection con = connectionEstablisher.getConnection())
		{
			statement = con.createStatement();
			final String statementCreate =
				String.format("CREATE TABLE IF NOT EXISTS %s (" //$NON-NLS-1$
					+ "`%s` VARCHAR(45) NOT NULL," //$NON-NLS-1$
					+ "`%s` VARCHAR(45) NOT NULL," //$NON-NLS-1$
					+ "PRIMARY KEY (`%s`));", //$NON-NLS-1$
					table,
					Columns.EMAIL.toString().toLowerCase(),
					Columns.PASSWORD.toString().toLowerCase(),
					Columns.EMAIL.toString().toLowerCase());
			statement.execute(statementCreate);
		} catch (SQLException e)
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
		try (Connection con = connectionEstablisher.getConnection())
		{
			preparedStatement = con.prepareStatement("" //$NON-NLS-1$
				+ "SELECT * FROM " //$NON-NLS-1$
				+ table
				+ " WHERE " //$NON-NLS-1$
				+ eMailColumn
				+ "=?;"); //$NON-NLS-1$
			preparedStatement.setString(1, eMail);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next())
			{
				$ =
					new DBUser(
						resultSet.getString(eMailColumn),
						resultSet.getString(passwordColumn));
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
		try (Connection con = connectionEstablisher.getConnection())
		{
			preparedStatement = con.prepareStatement("INSERT INTO " //$NON-NLS-1$
				+ table
				+ " (" //$NON-NLS-1$
				+ eMailColumn
				+ "," //$NON-NLS-1$
				+ passwordColumn
				+ ") VALUES ( ?, ? );"); //$NON-NLS-1$
			preparedStatement.setString(1, user.getEMail().toLowerCase());
			preparedStatement.setString(2, user.getPassword());
			preparedStatement.executeUpdate();
			
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
		try (Connection con = connectionEstablisher.getConnection())
		{
			preparedStatement = con.prepareStatement("" //$NON-NLS-1$
				+ "SELECT * FROM " //$NON-NLS-1$
				+ table
				+ " WHERE " //$NON-NLS-1$
				+ eMailColumn
				+ "=?;"); //$NON-NLS-1$
			preparedStatement.setString(1, eMail);
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
	
	
	
	/**
	 * The table name
	 */
	final private String table = schema + "." + "`users`"; //$NON-NLS-1$ //$NON-NLS-2$ 
	
	/**
	 * Email column
	 */
	final private String eMailColumn = "email"; //$NON-NLS-1$
	
	/**
	 * Password column
	 */
	final private String passwordColumn = "password"; //$NON-NLS-1$
}
