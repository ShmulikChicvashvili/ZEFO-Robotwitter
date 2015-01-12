/**
 * 
 */
package com.robotwitter.database.interfaces;

import java.util.ArrayList;

import com.robotwitter.database.interfaces.returnValues.SqlError;

/**
 * @author Itay, Shmulik
 *
 */
public interface IDatabaseHeavyHitters
{	
	/**
	 * @param followedUserID The user which we computed his heavy hitters
	 * @return The heavy hitters list sorted
	 */
	public ArrayList<Long> get(Long followedUserID);
	
	/**
	 * @param followedUserID The user which we compute his heavy hitters
	 * @param heavyHittersIDs List of all heavy hitters IDs
	 * @return The state of the query
	 */
	public SqlError insert(Long followedUserID, ArrayList<Long> heavyHittersIDs);
}
