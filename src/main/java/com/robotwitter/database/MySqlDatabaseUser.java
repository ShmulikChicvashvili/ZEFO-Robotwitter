/**
 *
 *
 */

package com.robotwitter.database;


import java.sql.Connection;
import java.sql.SQLException;

import com.google.inject.Inject;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

import com.robotwitter.database.interfaces.ConnectionEstablisher;
import com.robotwitter.database.interfaces.IDatabaseUsers;
import com.robotwitter.database.interfaces.returnValues.SqlError;
import com.robotwitter.database.primitives.DBUser;




/**
 * Here we maintain functions over the users database.
 *
 * @author Shmulik and Eyal
 *
 *         The database that handles registring a user and fetching a user
 */
public class MySqlDatabaseUser extends AbstractMySqlDatabase implements IDatabaseUsers
{
	/**
	 * The columns this table has.
	 *
	 * @author Eyal
	 */
	private enum Columns
	{
		/**
		 * Email column.
		 */
		EMAIL,
		/**
		 * Password column.
		 */
		PASSWORD
	}
	
	
	
	/**
	 * C'tor For MySql db of users.
	 *
	 * @param conEstablisher
	 *            A connection establisher for the database
	 * @throws SQLException
	 *             Could not create the table
	 */
	@Inject
	public MySqlDatabaseUser(final ConnectionEstablisher conEstablisher)
		throws SQLException
	{
		super(conEstablisher);
		try (
			Connection con = connectionEstablisher.getConnection();
			Statement statement = (Statement) con.createStatement())
		{
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
		}
	}
	
	
	/* (non-Javadoc) @see Database.IDatabase#get(java.lang.String) */
	@Override
	public final DBUser get(final String eMailUser)
	{
		if (eMailUser == null) { return null; }
		final String eMail = eMailUser.toLowerCase();
		DBUser $ = null;
		try (
			Connection con = connectionEstablisher.getConnection();
			PreparedStatement preparedStatement =
				(PreparedStatement) con.prepareStatement("SELECT * FROM " //$NON-NLS-1$
					+ table
					+ " WHERE " //$NON-NLS-1$
					+ Columns.EMAIL.toString().toLowerCase()
					+ "=?;")) //$NON-NLS-1$)
		
		{
			
			preparedStatement.setString(1, eMail);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next())
			{
				$ =
					new DBUser(resultSet.getString(Columns.EMAIL
						.toString()
						.toLowerCase()), resultSet.getString(Columns.PASSWORD
						.toString()
						.toLowerCase()));
			}
			resultSet.close();
		} catch (final SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return $;
	}
	
	
	/* (non-Javadoc) @see
	 * Database.IDatabase#insert(DatabasePrimitives.DatabaseTypes) */
	@SuppressWarnings("boxing")
	@Override
	public final SqlError insert(DBUser user)
	{
		if (user == null
			|| user.getEMail() == null
			|| user.getPassword() == null) { return SqlError.INVALID_PARAMS; }
		try (
			Connection con = connectionEstablisher.getConnection();
			@SuppressWarnings("nls")
			PreparedStatement preparedStatement =
				(PreparedStatement) con.prepareStatement("INSERT INTO "
					+ table
					+ " ("
					+ Columns.EMAIL.toString().toLowerCase()
					+ ","
					+ Columns.PASSWORD.toString().toLowerCase()
					+ ") VALUES ( ?, ? );"))
		{
			preparedStatement.setString(1, user.getEMail().toLowerCase());
			preparedStatement.setString(2, user.getPassword());
			preparedStatement.executeUpdate();
			
		} catch (final SQLException e)
		{
			if (e.getErrorCode() == insertAlreadyExists) { return SqlError.ALREADY_EXIST; }
			// TODO what to do if not this error code
			e.printStackTrace();
		}
		return SqlError.SUCCESS;
	}
	
	
	/**
	 * Create table of users statement.
	 */
	
	@Override
	/* (non-Javadoc) @see Database.IDatabase#isExists(java.lang.String) */
	@SuppressWarnings("nls")
	public final boolean isExists(String eMailUser)
	{
		if (eMailUser == null) { return false; }
		final String eMail = eMailUser.toLowerCase();
		boolean $ = false;
		try (
			Connection con = connectionEstablisher.getConnection();
			PreparedStatement preparedStatement =
				(PreparedStatement) con.prepareStatement("" //$NON-NLS-1$
					+ "SELECT * FROM " //$NON-NLS-1$
					+ table
					+ " WHERE " //$NON-NLS-1$
					+ Columns.EMAIL.toString().toLowerCase()
					+ "=?;"))
		{
			preparedStatement.setString(1, eMail);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.first())
			{
				$ = true;
			}
			resultSet.close();
		} catch (final SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return $;
	}
	
	
	/**
	 * @param user
	 *            User to update
	 * @return sqlError
	 */
	public SqlError update(DBUser user)
	{
		if (user == null
			|| user.getEMail() == null
			|| user.getPassword() == null) { return SqlError.INVALID_PARAMS; }
		
		if (!isExists(user.getEMail())) { return SqlError.DOES_NOT_EXIST; }
		
		try (
			Connection con = connectionEstablisher.getConnection();
			@SuppressWarnings("nls")
			PreparedStatement preparedStatement =
				(PreparedStatement) con.prepareStatement(String.format(
					"UPDATE %s SET %s = ?, %s = ?, %s = ? WHERE %s = ?",
					table,
					Columns.EMAIL.toString().toLowerCase(),
					Columns.PASSWORD.toString().toLowerCase())))
		{
			preparedStatement.setString(1, user.getEMail());
			preparedStatement.setString(2, user.getPassword());
			preparedStatement.executeUpdate();
		} catch (final SQLException e)
		{
			e.printStackTrace();
		}
		
		return SqlError.SUCCESS;
	}
	
	
	
	/**
	 * The table name.
	 */
	private final String table = schema + "." + "`users`"; //$NON-NLS-1$ //$NON-NLS-2$
}
