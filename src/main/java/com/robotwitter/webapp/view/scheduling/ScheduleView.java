/**
 *
 */

package com.robotwitter.webapp.view.scheduling;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.AbstractSelect;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.VerticalLayout;

import com.robotwitter.database.interfaces.IDatabaseScheduledTweets;
import com.robotwitter.database.interfaces.IDatabaseTweetPostingPreferences;
import com.robotwitter.database.interfaces.IDatabaseTwitterAccounts;
import com.robotwitter.database.primitives.DBScheduledTweet;
import com.robotwitter.webapp.control.scheduling.ScheduledTweetsController;
import com.robotwitter.webapp.messages.IMessagesContainer;
import com.robotwitter.webapp.view.AbstractView;




/**
 * @author Amir Drutin
 *
 */
public class ScheduleView extends AbstractView
{
	/**
	 * Wrap the given component in a panel.
	 *
	 * @param component
	 *            the component to wrap
	 * @param title
	 *            the panel's title
	 *
	 * @return a newly created panel wrapper, wrapping the given component
	 */
	private static Component wrapInPanel(Component component, String title)
	{
		Label titleLabel = new Label(title);
		VerticalLayout panel = new VerticalLayout(titleLabel, component);
		panel.setWidthUndefined();
		return panel;
	}


	@Inject
	public ScheduleView(
		@Named(NAME) IMessagesContainer messages,
		IDatabaseScheduledTweets dbScheduled,
		IDatabaseTwitterAccounts dbAccounts,
		IDatabaseTweetPostingPreferences dbPreference)
	{
		super(messages, messages.get("ScheduleView.page.title"));
		this.dbAccounts = dbAccounts;
		this.dbPreference = dbPreference;
		this.dbScheduled = dbScheduled;
		scheduledController =
			new ScheduledTweetsController(dbScheduled, dbAccounts, dbPreference);
	}


	/* (non-Javadoc) @see
	 * com.robotwitter.webapp.view.AbstractView#isSignedInProhibited() */
	@Override
	public boolean isSignedInProhibited()
	{
		return false;
	}


	/* (non-Javadoc) @see
	 * com.robotwitter.webapp.view.AbstractView#isSignedInRequired() */
	@Override
	public boolean isSignedInRequired()
	{
		return true;
	}


	private void deleteButtonClick()
	{
		scheduledController.removeScheduledTweet(selectedTweet);
		updateListSelect();
	}


	private void updateListSelect()
	{
		scheduledTweets = dbScheduled.getScheduledTweets();

		selectPanel.removeComponent(viewSelect);
		viewSelect = createListSelect();
		selectPanel.addComponentAsFirst(viewSelect);
	}


	protected Button createDeleteButton()
	{
		final Button deleteButton =
			new Button(
				messages.get("ScheduleView.caption.new-button"),
				event -> deleteButtonClick());
		deleteButton.setIcon(FontAwesome.MINUS);
		return deleteButton;
	}


	protected ListSelect createListSelect()
	{
		HashMap<DBScheduledTweet, String> hashedTweets =
			new HashMap<DBScheduledTweet, String>();

		for (DBScheduledTweet tweet : scheduledTweets)
		{
			hashedTweets.put(tweet, tweet.getTweetName());
		}

		ListSelect select =
			new ListSelect(messages.get("ScheduleView.caption.select-list"));
		select.setNullSelectionAllowed(true);
		select.setMultiSelect(false);
		select.setImmediate(true);
		select.setRows(10);
		select.setItemCaptionMode(AbstractSelect.ITEM_CAPTION_MODE_EXPLICIT);

		for (DBScheduledTweet key : hashedTweets.keySet())
		{
			select.addItem(key);
			select.setItemCaption(key, hashedTweets.get(key));
		}
		// selectedTweet = (DBScheduledTweet) select.getValue();
		select
		.addValueChangeListener(event -> {
			selectedTweet = new ArrayList<>();
			selectedTweet.add(
				/* (List<DBScheduledTweet>) */(DBScheduledTweet) select
				.getValue());
		});
		return select;
	}


	protected Button createNewButton()
	{
		final Button newButton =
			new Button(
				messages.get("ScheduleView.caption.new-button"),
				event -> getUI().addWindow(
					new PostScheduledTweetWindow(
						messages,
						new ScheduledTweetsController(
							dbScheduled,
							dbAccounts,
							dbPreference), null)));
		newButton.setIcon(FontAwesome.PLUS);
		return newButton;
	}


	protected Button createPreviewButton()
	{
		Button previewButton =
			new Button(
				messages.get("ScheduleView.caption.preview-button"),
				event -> getUI().addWindow(new SchedulePreviewWindow(messages)));
		previewButton.setIcon(FontAwesome.EYE);
		return previewButton;
	}


	protected Button createRefreshButton()
	{
		Button refreshButton =
			new Button(
				messages.get("ScheduleView.caption.refresh-button"),
				event -> updateListSelect());
		refreshButton.setIcon(FontAwesome.REFRESH);
		return refreshButton;
	}


	/* (non-Javadoc) @see com.robotwitter.webapp.view.AbstractView#initialise() */
	@Override
	protected void initialise()
	{
		Label header = new Label(messages.get("ScheduleView.label.header"));
		scheduledTweets = scheduledController.getInitializedScheduledTweets();
		viewSelect = createListSelect();
		Label selectCaption =
			new Label(messages.get("ScheduleView.caption.select-list"));
		selectPanel = new VerticalLayout(viewSelect, selectCaption);
		HorizontalLayout buttons =
			new HorizontalLayout(
				createDeleteButton(),
				createPreviewButton(),
				createNewButton(),
				createRefreshButton());
		VerticalLayout layout =
			new VerticalLayout(header, selectPanel, buttons);
		setCompositionRoot(layout);
		/**
		 * final Button button = new Button("Post scheduled", event -> getUI()
		 * .addWindow( new PostScheduledTweetWindow(messages, new
		 * ScheduledTweetsController(dbScheduled, dbAccounts, dbPreference),
		 * null)));
		 *
		 * setCompositionRoot(button);
		 */
	}



	/** The view's name. */
	public static final String NAME = "schedule";

	private List<DBScheduledTweet> scheduledTweets;

	private VerticalLayout selectPanel;

	private ListSelect viewSelect;

	private List<DBScheduledTweet> selectedTweet;

	private ScheduledTweetsController scheduledController;

	private IDatabaseScheduledTweets dbScheduled;

	private IDatabaseTwitterAccounts dbAccounts;

	private IDatabaseTweetPostingPreferences dbPreference;
}
