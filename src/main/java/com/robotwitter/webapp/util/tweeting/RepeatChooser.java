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


	public Calendar getChosenDate()
	{
		Date date = dateField.getValue();
		Calendar c = Calendar.getInstance();
		c.setTime(date);

		return c;
	}


	public RepeatType getChosenRepeatType()
	{
		@SuppressWarnings("boxing")
		Integer chosen = (Integer) repeatTypeRadio.getValue();
		if (chosen.equals(RepeatType.ONE_TIME.index)) { return RepeatType.ONE_TIME; }
		if (chosen.equals(RepeatType.WEEKLY.index)) { return RepeatType.WEEKLY; }
		
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

		dateField.setValue(new Date());
		dateField.setResolution(Resolution.MINUTE);
	}



	private OptionGroup repeatTypeRadio;

	private VerticalLayout repeatDatesPlaceHolder;

	private Layout repeatDatesLayout;

	private DateField dateField;

}
