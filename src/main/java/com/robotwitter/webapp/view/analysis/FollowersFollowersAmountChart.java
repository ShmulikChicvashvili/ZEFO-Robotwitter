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
 * @author Eyal
 *
 */
public class FollowersFollowersAmountChart extends AbstractBarCharComponent
{
	/**
	 * Instantiates a new followers displayed language chart.
	 *
	 * @param messages
	 *            the container of messages to display
	 */
	public FollowersFollowersAmountChart(IMessagesContainer messages)
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

		List<Integer> separators = new ArrayList<>();
		separators.add(1);
		separators.add(5);

		controller.getFollowersAmountByTheirFollowersAmount(separators);

		List<String> ticks = new ArrayList<>();
		List<Integer> amounts = new ArrayList<>();

		ticks.add("First");
		amounts.add(3);
		ticks.add("Second");
		amounts.add(0);
		ticks.add("Third");
		amounts.add(10);

		set(ticks, amounts);
	}
}
