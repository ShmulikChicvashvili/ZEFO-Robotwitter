/**
 *
 */

package com.robotwitter.webapp.view.scheduling;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.AbstractSelect;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.VerticalLayout;

import com.robotwitter.database.interfaces.IDatabaseTweetPostingPreferences;
import com.robotwitter.database.primitives.DBScheduledTweet;
import com.robotwitter.webapp.control.scheduling.IScheduledTweetsController;
import com.robotwitter.webapp.messages.IMessagesContainer;
import com.robotwitter.webapp.util.tweeting.TweetPreview;
import com.robotwitter.webapp.view.AbstractView;




/**
 * @author Amir Drutin
 *
 */
public class ScheduleView extends AbstractView
{
	@Inject
	public ScheduleView(
		@Named(NAME) IMessagesContainer messages,
		IScheduledTweetsController scheduledController,
		IDatabaseTweetPostingPreferences dbPreference)
	{
		super(messages, messages.get("ScheduleView.page.title"));
		this.dbPreference = dbPreference;
		this.scheduledController = scheduledController;

		getUserSession().observeActiveTwitterAccount(this);
	}


	/* (non-Javadoc) @see
	 * com.robotwitter.webapp.util.RobotwitterCustomComponent#
	 * activateTwitterAccount(long) */
	@Override
	public void activateTwitterAccount(long id)
	{
		updateListSelect();
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
		if (selectedTweet == null) { return; }
		scheduledController.removeScheduledTweet(selectedTweet);
		updateListSelect();
	}


	private void previewButtonClick()
	{
		if (selectedTweet == null) { return; }

		System.out.println(viewSelect.getValue());

		TweetPreview tweetPreview = new TweetPreview();
		List<String> tweets =
			scheduledController.previewTweet(selectedTweet.getTweetText());
		tweetPreview.updatePreview(tweets);
		getUI().addWindow(
			new SchedulePreviewWindow(
				messages,
				scheduledController,
				selectedTweet));
	}


	private void updateListSelect()
	{
		// scheduledTweets = dbScheduled.getScheduledTweets();
		// TODO get by id
		long userId =
			getUserSession()
			.getAccountController()
			.getActiveTwitterAccount()
			.getID();
		scheduledTweets = scheduledController.getAllScheduledTweets(userId);
		scheduledTweetsMap = new HashMap<>();
		for (DBScheduledTweet tweet : scheduledTweets)
		{
			scheduledTweetsMap.put(tweet.getKey(), tweet);
		}

		selectPanel.removeComponent(viewSelect);
		viewSelect = createListSelect();
		selectedTweet = null;
		selectPanel.addComponentAsFirst(viewSelect);
	}


	protected Button createDeleteButton()
	{
		final Button deleteButton =
			new Button(
				messages.get("ScheduleView.caption.delete-button"),
				event -> deleteButtonClick());
		deleteButton.setIcon(FontAwesome.MINUS);
		return deleteButton;
	}


	protected ListSelect createListSelect()
	{
		ListSelect select =
			new ListSelect(messages.get("ScheduleView.caption.select-list"));
		select.setNullSelectionAllowed(false);
		select.setMultiSelect(false);
		select.setImmediate(true);
		select.setRows(10);
		select.setItemCaptionMode(AbstractSelect.ITEM_CAPTION_MODE_EXPLICIT);

		for (DBScheduledTweet tweet : scheduledTweets)
		{
			select.addItem(tweet.getKey());
			select.setItemCaption(tweet.getKey(), tweet.getTweetName());
		}
		// selectedTweet = (DBScheduledTweet) select.getValue();
		select.addValueChangeListener(event -> {
			if (select.getValue() == null)
			{
				selectedTweet = null;
				return;
			}
			selectedTweet = scheduledTweetsMap.get(select.getValue());
			System.out.println(select.getValue());
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
						scheduledController,
						() -> updateListSelect())));
		newButton.setIcon(FontAwesome.PLUS);
		return newButton;
	}


	protected Button createPreviewButton()
	{
		Button previewButton =
			new Button(
				messages.get("ScheduleView.caption.preview-button"),
				event -> previewButtonClick());
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
		viewSelect = new ListSelect();
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

		updateListSelect();
	}



	/** The view's name. */
	public static final String NAME = "schedule";

	private List<DBScheduledTweet> scheduledTweets;

	private Map<Integer, DBScheduledTweet> scheduledTweetsMap;

	private VerticalLayout selectPanel;

	private ListSelect viewSelect;

	private DBScheduledTweet selectedTweet;

	private IScheduledTweetsController scheduledController;

	private IDatabaseTweetPostingPreferences dbPreference;
}
