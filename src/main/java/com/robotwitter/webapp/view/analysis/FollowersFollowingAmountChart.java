/**
 *
 */

package com.robotwitter.webapp.view.analysis;


import java.util.ArrayList;
import java.util.List;

import com.robotwitter.webapp.control.account.ITwitterAccountController;
import com.robotwitter.webapp.messages.IMessagesContainer;
import com.robotwitter.webapp.util.AbstractBarCharComponent;




/**
 * Represents a chart showing followers amount by the number of accounts they
 * are following.
 *
 * @author Eyal
 * @author Hagai
 *
 */
public class FollowersFollowingAmountChart extends AbstractBarCharComponent
{
	
	/**
	 * Instantiates a new followers following amount chart.
	 *
	 * @param messages
	 *            the messages
	 */
	public FollowersFollowingAmountChart(IMessagesContainer messages)
	{
		
		super(messages, messages
			.get("FollowersFollowingAmountChart.error.no-data"));
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
	
	
	/**
	 * Initialise layout.
	 */
	private void initialiseLayout()
	{
		updateChart();
	}
	
	
	/**
	 * Update chart.
	 */
	private void updateChart()
	{
		ITwitterAccountController controller =
			getUserSession().getAccountController().getActiveTwitterAccount();
		
		List<Integer> separators = new ArrayList<>();
		List<Integer> amounts = new ArrayList<>();
		controller.getFollowersAmountByTheirFollowingAmount(
			5,
			amounts,
			separators);
		
		setBySeperators(separators, amounts);
		show();
	}
}
