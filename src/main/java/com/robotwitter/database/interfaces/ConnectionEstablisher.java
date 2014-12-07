/**
 *
 */

package com.robotwitter.database.interfaces;


import java.sql.Connection;
import java.sql.SQLException;




/**
 * @author Eyal and Shmulik
 *
 */
public interface ConnectionEstablisher
{
	/**
	 * @return a valid SQL connection
	 * @throws SQLException
	 *             Indicating a connection could not be established
	 * @throws ClassNotFoundException
	 *             Indicating the JDBC driver for the connection could not be
	 *             found
	 */
	Connection getConnection() throws SQLException;
	
	
	/**
	 * @return the schema the connection is connected to
	 */
	String getSchema();
}
