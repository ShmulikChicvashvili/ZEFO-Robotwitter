/**
 *
 */

package com.robotwitter.webapp.control.scheduling;


import java.util.List;

import com.ibm.icu.util.Calendar;

import com.robotwitter.posting.AutomateTweetPostingPeriod;




/**
 * @author Eyal
 *
 */
public interface IScheduledTweetsController
{

	void addScheduledTweet(
		String name,
		String text,
		long userId,
		Calendar c,
		AutomateTweetPostingPeriod period);


	/**
	 * @param text
	 * @return
	 */
	List<String> previewTweet(String text);

}
