/**
 * 
 */

package com.Robotwitter.Database;


import com.Robotwitter.DatabasePrimitives.DBUser;
import com.Robotwitter.DatabasePrimitives.DatabaseType;
import com.google.inject.Inject;




/**
 * @author Shmulik
 *
 */
public class MySqlDatabaseUser extends MySqlDatabase
{
	
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
	
	
	
	/**
	 * Create table of users statement
	 */
	
	/**
	 * C'tor For MySql db of users
	 * 
	 * @param conEstablisher
	 *            A connection establisher for the database
	 */
	@Inject
	public MySqlDatabaseUser(ConnectionEstablisher conEstablisher)
	{
		super(conEstablisher);
		try
		{
			this.con = this.connectionEstablisher.getConnection();
			this.statement = this.con.createStatement();
			String statementCreate =
				"CREATE TABLE IF NOT EXISTS `yearlyproj_db`.`users` (" //$NON-NLS-1$
					+ "`email` VARCHAR(45) NOT NULL," //$NON-NLS-1$
					+ "`password` VARCHAR(45) NOT NULL," //$NON-NLS-1$
					+ "PRIMARY KEY (`email`));"; //$NON-NLS-1$
			this.statement.execute(statementCreate);
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
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
	 * Database.IDatabase#insert(DatabasePrimitives.DatabaseTypes) */
	@SuppressWarnings("nls")
	public void insert(DatabaseType obj)
	{
		DBUser u = (DBUser) obj;
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
			this.preparedStatement.setString(1, u.getEMail());
			this.preparedStatement.setString(2, u.getPassword());
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
	
	
	/* (non-Javadoc) @see Database.IDatabase#get(java.lang.String) */
	@SuppressWarnings("nls")
	public DatabaseType get(String eMail)
	{
		DatabaseType $ = null;
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
