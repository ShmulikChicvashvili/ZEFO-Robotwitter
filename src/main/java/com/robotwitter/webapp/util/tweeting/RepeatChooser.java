/**
 *
 */

package com.robotwitter.webapp.util.tweeting;


import java.util.Calendar;
import java.util.Date;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Layout;
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

	public enum RepeatType
	{
		ONE_TIME(0),
		DAILY(1),
		WEEKLY(2);

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


	public Calendar getChosenDate()
	{
		Date date = dateField.getValue();
		Calendar c = Calendar.getInstance();
		c.setTime(date);

		return c;
	}


	public RepeatType getChosenRepeatType()
	{
		Integer chosen = (Integer) repeatTypeRadio.getValue();

		for (RepeatType r : RepeatType.values())
		{
			if (r.index.equals(chosen)) { return r; }
		}

		assert false;
		return RepeatType.ONE_TIME;
	}


	private void changeRepeatType(ValueChangeEvent event)
	{
		// repeatDatesPlaceHolder.removeAllComponents();
		// repeatDatesPlaceHolder.addComponent(repeatDatesLayout);
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

		repeatTypeRadio.addItem(RepeatType.ONE_TIME.index);
		repeatTypeRadio.setItemCaption(
			RepeatType.ONE_TIME.index,
			messages.get("PostScheduledTweet.radio.one-time"));

		repeatTypeRadio.addItem(RepeatType.DAILY.index);
		repeatTypeRadio.setItemCaption(
			RepeatType.DAILY.index,
			messages.get("PostScheduledTweet.radio.daily"));

		repeatTypeRadio.addItem(RepeatType.WEEKLY.index);
		repeatTypeRadio.setItemCaption(
			RepeatType.WEEKLY.index,
			messages.get("PostScheduledTweet.radio.weekly"));

		repeatTypeRadio
		.addValueChangeListener(event -> changeRepeatType(event));
		repeatTypeRadio.setValue(RepeatType.ONE_TIME.index);

		repeatDatesPlaceHolder = new VerticalLayout();
		initializeRepeatDatesLayout();

		final VerticalLayout layout =
			new VerticalLayout(repeatTypeRadio, repeatDatesPlaceHolder);
		setCompositionRoot(layout);
	}


	private void initializeRepeatDatesLayout()
	{
		dateField = new DateField();
		repeatDatesLayout = new VerticalLayout(dateField);

		// In the future might remove this line, and change 'placeHolder' with
		// radio button choice.
		repeatDatesPlaceHolder.addComponent(repeatDatesLayout);

		// set starting time to 1 minute ahead
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		cal.roll(Calendar.MINUTE, 1);
		dateField.setValue(cal.getTime());
		dateField.setResolution(Resolution.MINUTE);
	}



	private OptionGroup repeatTypeRadio;

	private VerticalLayout repeatDatesPlaceHolder;

	private Layout repeatDatesLayout;

	private DateField dateField;

}
