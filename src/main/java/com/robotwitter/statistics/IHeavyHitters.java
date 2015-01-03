/**
 *
 */

package com.robotwitter.statistics;


import java.util.ArrayList;




/**
 * @author Itay, Shmulik
 *
 */
public interface IHeavyHitters
{
	public ArrayList<Long> getCurrentHeavyHitters();


	public void onDirectMessage(Long userID);


	public void onFavorite(Long userID);


	public void onFollow(Long userID);


	public void onMentioned(Long userID);


	public void onRetweetedStatus(Long userID);

}
