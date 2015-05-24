/**
 *
 */

package com.robotwitter.webapp.util.tweeting;


import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.aliasi.util.Pair;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.VerticalLayout;

import com.robotwitter.webapp.messages.IMessagesContainer;
import com.robotwitter.webapp.util.RobotwitterCustomComponent;




/**
 * @author Eyal
 *
 */
public class RepeatChooser extends RobotwitterCustomComponent
{

	private enum RepeatType
	{
		ONE_TIME(0),
		WEEKLY(1);

		@SuppressWarnings("boxing")
		private RepeatType(int index)
		{
			this.index = index;
		}



		public Integer index;
	}



	/**
	 * @param messages
	 */
	public RepeatChooser(IMessagesContainer messages)
	{
		super(messages);

		initializeLayout();
	}


	public RepeatType getChosenRepeatType()
	{
		return RepeatType.ONE_TIME;
	}


	public Calendar getOneTimeDate()
	{
		final Calendar c = Calendar.getInstance();

		return c;
	}


	public Pair<List<DayOfWeek>, Calendar> getWeeklyChoice()
	{
		final List<DayOfWeek> list = new ArrayList<>();
		final Calendar hour = Calendar.getInstance();

		return new Pair<List<DayOfWeek>, Calendar>(list, hour);
	}


	private void changeRepeatType(ValueChangeEvent event)
	{

	}


	/**
	 * Initialize repeat type.
	 */
	private void initializeLayout()
	{
		repeatTypeRadio =
			new OptionGroup(
				messages.get("PostScheduledTweet.caption.repeat-type"));
		repeatTypeRadio.setMultiSelect(false);

		repeatTypeRadio.addItem(0);
		repeatTypeRadio.setItemCaption(
			RepeatType.ONE_TIME.index,
			messages.get("PostScheduledTweet.radio.one-time"));

		repeatTypeRadio.addItem(1);
		repeatTypeRadio.setItemCaption(
			RepeatType.WEEKLY.index,
			messages.get("PostScheduledTweet.radio.weekly"));

		repeatTypeRadio
			.addValueChangeListener(event -> changeRepeatType(event));

		repeatDatesLayout = new VerticalLayout();

		final VerticalLayout layout =
			new VerticalLayout(repeatTypeRadio, repeatDatesLayout);
		setCompositionRoot(layout);
	}



	private OptionGroup repeatTypeRadio;

	private VerticalLayout repeatDatesLayout;
}
