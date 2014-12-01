/**
 *
 */

package com.robotwitter.database;


import java.util.ArrayList;

import com.google.inject.Inject;

import com.robotwitter.database.primitives.DBUser;
import com.robotwitter.database.primitives.DatabaseType;




/**
 * @author Shmulik
 *
 *         The database that handles registring a user and fetching a user
 */
public class MySqlDatabaseUser extends MySqlDatabase
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
			con = connectionEstablisher.getConnection();
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
	
	
	/* (non-Javadoc) @see Database.IDatabase#get(java.lang.String) */
	@Override
	@SuppressWarnings("nls")
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
				final DBUser u =
					new DBUser(
						resultSet.getString(eMailColumn),
						resultSet.getString(passwordColumn));
				$.add(u);
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
	 * Database.IDatabase#insert(DatabasePrimitives.DatabaseTypes) */
	@SuppressWarnings("nls")
	public void insert(final DatabaseType obj)
	{
		final DBUser u = (DBUser) obj;
		try
		{
			con = connectionEstablisher.getConnection();

			preparedStatement =
				con.prepareStatement("INSERT INTO "
					+ table
					+ " ("
					+ eMailColumn
					+ ","
					+ passwordColumn
					+ ") VALUES ( ?, ? );");
			preparedStatement.setString(1, u.getEMail());
			preparedStatement.setString(2, u.getPassword());
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
	
	
	/**
	 * Create table of users statement
	 */

	/* (non-Javadoc) @see Database.IDatabase#isExists(java.lang.String) */
	@SuppressWarnings("nls")
	public boolean isExists(final DatabaseType obj)
	{
		final DBUser temp = (DBUser) obj;
		final String eMail = temp.getEMail();
		boolean $ = false;
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