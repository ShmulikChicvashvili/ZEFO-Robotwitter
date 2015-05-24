/**
 *
 */
package com.robotwitter.webapp.control.scheduling;

import java.util.List;

/**
 * @author Eyal
 *
 */
public interface IScheduledTweetsController
{

	/**
	 * @param text
	 * @return
	 */
	List<String> previewTweet(String text);

}
