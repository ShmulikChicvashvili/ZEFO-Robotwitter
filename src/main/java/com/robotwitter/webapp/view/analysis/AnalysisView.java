
package com.robotwitter.webapp.view.analysis;


import com.google.inject.Inject;
import com.google.inject.name.Named;

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
			new FollowersAmountOverview(messages, !isMobile());
		AnalysisTabs tabs = new AnalysisTabs(messages);
		
		VerticalLayout layout = new VerticalLayout(header, overview, tabs);
		
		header.addStyleName(HEADER_STYLENAME);
		tabs.addStyleName(TABS_STYLENAME);
		addStyleName(STYLENAME);
		
		if (isMobile())
		{
			addStyleName(MOBILE_STYLENAME);
		}
		
		setCompositionRoot(layout);
	}
	
	
	
	/** The view's name. */
	public static final String NAME = "analysis";
	
	/** The CSS class name to apply to this component. */
	private static final String STYLENAME = "AnalysisView";
	
	/** The CSS class name to apply to this component in mobile browsers. */
	private static final String MOBILE_STYLENAME = "AnalysisView-mobile";

	/** The CSS class name to apply to the header component. */
	private static final String HEADER_STYLENAME = "AnalysisView-header";

	/** The CSS class name to apply to the header component. */
	private static final String TABS_STYLENAME = "AnalysisView-tabs";

	/** Serialisation version unique ID. */
	private static final long serialVersionUID = 1L;
}
