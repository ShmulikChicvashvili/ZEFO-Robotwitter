
package com.robotwitter.webapp.view.analysis;


import java.util.Map;

import com.robotwitter.webapp.control.account.ITwitterAccountController;
import com.robotwitter.webapp.messages.IMessagesContainer;
import com.robotwitter.webapp.util.AbstractPieChartComponent;




/**
 * Represents a chart of a Twitter account's followers language distribution.
 *
 * @author Hagai Akibayov
 * @author Eyal Tolchinsky
 */
public class FollowersDisplayedLanguageChart extends AbstractPieChartComponent
{
	
	/**
	 * Instantiates a new followers displayed language chart.
	 *
	 * @param messages
	 *            the container of messages to display
	 */
	public FollowersDisplayedLanguageChart(IMessagesContainer messages)
	{
		super(messages);
		initialiseLayout();
		getUserSession().observeActiveTwitterAccount(this);
	}
	
	
	/* (non-Javadoc) @see
	 * com.robotwitter.webapp.util.RobotwitterCustomComponent#
	 * activateTwitterAccount(long) */
	@Override
	public final void activateTwitterAccount(long id)
	{
		updateChart();
	}
	
	
	private void initialiseLayout()
	{
		set("Label");
		updateChart();
	}
	
	
	private final void updateChart()
	{
		ITwitterAccountController controller =
			getUserSession().getAccountController().getActiveTwitterAccount();
		
		Map<String, Integer> amountPerLanguage =
			controller.getFollowersAmountByDisplayedLanguage();
		
		set(amountPerLanguage);
		show();
	}
	
}
