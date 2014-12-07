/**
 * 
 */

package com.robotwitter.database.interfaces;

import java.util.List;

import com.robotwitter.database.interfaces.returnValues.InsertError;
import com.robotwitter.database.primitives.DBFollowersNumber;

/**
 * @author Eyal and Shmulik
 *
 */
public interface IDatabaseNumFollowers
{	
	
	public List<DBFollowersNumber> get(Long twitterId);
	
	public InsertError insert(DBFollowersNumber statistic);
	
}
