/**
 *
 */

package com.robotwitter.database.interfaces;


import java.sql.Connection;




/**
 * @author Shmulik
 *
 */
public interface ConnectionPool
{
	Connection getConnection();
}
