/**
 *
 */

package com.robotwitter.webapp.view.scheduling;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.VerticalLayout;
import com.robotwitter.database.interfaces.IDatabaseScheduledTweets;
import com.robotwitter.database.interfaces.IDatabaseTweetPostingPreferences;
import com.robotwitter.database.interfaces.IDatabaseTwitterAccounts;
import com.robotwitter.webapp.control.scheduling.ScheduledTweetsController;
import com.robotwitter.webapp.messages.IMessagesContainer;
import com.robotwitter.webapp.view.AbstractView;

/**
 * @author Amir Drutin
 *
 */
public class ScheduleView extends AbstractView {
	@Inject
	public ScheduleView(@Named(NAME) IMessagesContainer messages,
			IDatabaseScheduledTweets dbScheduled,
			IDatabaseTwitterAccounts dbAccounts,
			IDatabaseTweetPostingPreferences dbPreference) {
		super(messages, messages.get("ScheduleView.page.title"));
		this.dbAccounts = dbAccounts;
		this.dbPreference = dbPreference;
		this.dbScheduled = dbScheduled;
	}
	
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

	/*
	 * (non-Javadoc) @see
	 * com.robotwitter.webapp.view.AbstractView#isSignedInProhibited()
	 */
	@Override
	public boolean isSignedInProhibited() {
		return false;
	}

	/*
	 * (non-Javadoc) @see
	 * com.robotwitter.webapp.view.AbstractView#isSignedInRequired()
	 */
	@Override
	public boolean isSignedInRequired() {
		return true;
	}
	
	protected ListSelect createListSelect(){
		ListSelect s = new ListSelect("ScheduleView.caption.select-list");
		s.setNullSelectionAllowed(true);
		s.setMultiSelect(true);
		s.setImmediate(true);
		
		return s;
	}

	/* (non-Javadoc) @see com.robotwitter.webapp.view.AbstractView#initialise() */
	@Override
	protected void initialise() {
		Label header = new Label(messages.get("ScheduleView.label.header"));
		
		

		final Button button = new Button("Post scheduled", event -> getUI()
				.addWindow(
						new PostScheduledTweetWindow(messages,
								new ScheduledTweetsController(dbScheduled, dbAccounts, dbPreference), null)));

		setCompositionRoot(button);
	}

	/** The view's name. */
	public static final String NAME = "Schedule";

	private IDatabaseScheduledTweets dbScheduled;
	
	private IDatabaseTwitterAccounts dbAccounts;
	
	private IDatabaseTweetPostingPreferences dbPreference;
}
