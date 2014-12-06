/**
 *
 */

package com.robotwitter.database;


import java.sql.Connection;

import com.google.inject.Inject;

import com.robotwitter.database.interfaces.ConnectionEstablisher;
import com.robotwitter.database.interfaces.IDatabaseUsers;
import com.robotwitter.database.interfaces.ReturnValues.InsertErrors;
import com.robotwitter.database.primitives.DBUser;




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
		try(Connection con = connectionEstablisher.getConnection())
		{
			statement = con.createStatement();
				final String statementCreate =
					"CREATE TABLE IF NOT EXISTS `yearlyproj_db`.`users` (" //$NON-NLS-1$
						+ "`email` VARCHAR(45) NOT NULL," //$NON-NLS-1$
						+ "`password` VARCHAR(45) NOT NULL," //$NON-NLS-1$
						+ "PRIMARY KEY (`email`));"; //$NON-NLS-1$
				statement.execute(statementCreate);
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
		if (eMailUser == null) {
			return null;
		}
		final String eMail = eMailUser.toLowerCase();
		DBUser $ = null;
		try(Connection con = connectionEstablisher.getConnection())
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
	public InsertErrors insert(DBUser user)
	{
		if (user == null) {
			return InsertErrors.INVALID_PARMS;
		}
		try(Connection con = connectionEstablisher.getConnection())
		{
			preparedStatement =
				con.prepareStatement("INSERT INTO "
					+ table
					+ " ("
					+ eMailColumn
					+ ","
					+ passwordColumn
					+ ") VALUES ( ?, ? );");
			preparedStatement.setString(1, user.getEMail().toLowerCase());
			preparedStatement.setString(2, user.getPassword());
			preparedStatement.executeUpdate();

		} catch (final Exception e)
		{
			e.printStackTrace();
		}
		return InsertErrors.SUCCESS;
	}


	/**
	 * Create table of users statement
	 */

	@Override
	/* (non-Javadoc) @see Database.IDatabase#isExists(java.lang.String) */
	@SuppressWarnings("nls")
	public boolean isExists(String eMailUser)
	{
		if(eMailUser == null) {
			return false;
		}
		final String eMail = eMailUser.toLowerCase();
		boolean $ = false;
		try(Connection con = connectionEstablisher.getConnection())
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
	final private String table = schema + "." + "users"; //$NON-NLS-1$ //$NON-NLS-2$

	/**
	 * Email column
	 */
	final private String eMailColumn = "email"; //$NON-NLS-1$

	/**
	 * Password column
	 */
	final private String passwordColumn = "password"; //$NON-NLS-1$
}
