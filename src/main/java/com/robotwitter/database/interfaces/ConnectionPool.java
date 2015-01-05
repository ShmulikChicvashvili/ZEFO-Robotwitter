/**
 *
 */

package com.robotwitter.database.interfaces;


import java.sql.Connection;
import java.sql.SQLException;




/**
 * @author Shmulik and Amir
 *
 */
public interface ConnectionPool
{
	Connection getConnection() throws SQLException;
}
