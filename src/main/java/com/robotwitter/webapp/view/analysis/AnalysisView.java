
package com.robotwitter.webapp.view.analysis;


import com.google.inject.Inject;
import com.google.inject.name.Named;

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import com.robotwitter.webapp.messages.IMessagesContainer;
import com.robotwitter.webapp.view.AbstractView;




/**
 * Analysis view.
 *
 * @author Eyal Tolchinsky
 * @author Hagai Akibayov
 */
public class AnalysisView extends AbstractView
{

	/**
	 * Instantiates a new login view.
	 *
	 * @param messages
	 *            the container of messages to display
	 */
	@Inject
	public AnalysisView(@Named(NAME) IMessagesContainer messages)
	{
		super(messages, messages.get("AnalysisView.page.title"));
	}
	
	
	@Override
	public final boolean isSignedInProhibited()
	{
		return false;
	}


	@Override
	public final boolean isSignedInRequired()
	{
		return true;
	}
	
	
	@Override
	protected final void initialise()
	{

		Label header = new Label(messages.get("AnalysisView.label.header"));
		FollowersAmountOverview overview =
			new FollowersAmountOverview(messages);

		FollowersAmountOverTimeChart followersChart =
			new FollowersAmountOverTimeChart(messages);
		MostInfluentialFollowers influentialFollowers =
			new MostInfluentialFollowers(messages);
		HorizontalLayout followers =
			new HorizontalLayout(influentialFollowers, followersChart);
		followers.setSizeFull();
		
		VerticalLayout layout = new VerticalLayout(header, overview, followers);
		layout.setSizeFull();
		layout.setExpandRatio(followers, 1);

		header.addStyleName(HEADER_STYLENAME);
		addStyleName(STYLENAME);

		setCompositionRoot(layout);
	}



	/** The view's name. */
	public static final String NAME = "analysis";

	/** The CSS class name to apply to this component. */
	private static final String STYLENAME = "AnalysisView";

	/** The CSS class name to apply to this component. */
	private static final String HEADER_STYLENAME = "AnalysisView-header";
	
	/** Serialisation version unique ID. */
	private static final long serialVersionUID = 1L;
}
