
package com.robotwitter.webapp.view.analysis;


import com.google.inject.Inject;
import com.google.inject.name.Named;

import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;
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
	
	
	/**
	 * Wrap the given component in a panel.
	 *
	 * @param component
	 *            the component to wrap
	 *
	 * @return a newly created panel wrapper, wrapping the given component
	 */
	private Component wrapInPanel(Component component)
	{
		VerticalLayout panel = new VerticalLayout(component);
		panel.setSizeFull();
		panel.addStyleName(PANEL_STYLENAME);
		return panel;
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
		// FollowersAmountOverTimeChart followersChart2 =
		// new FollowersAmountOverTimeChart(messages);
		// MostInfluentialFollowers influentialFollowers2 =
		// new MostInfluentialFollowers(messages);
		
		Button pie1 = new Button("Pie 1");
		Button pie2 = new Button("Pie 1");
		Button pie3 = new Button("Pie 1");
		
		followersChart.setSizeFull();
		influentialFollowers.setSizeFull();
		pie1.setSizeFull();
		pie2.setSizeFull();
		pie3.setSizeFull();

		GridLayout layout = new GridLayout(6, 4);
		layout.setSizeFull();
		layout.setSpacing(true);

		layout.addComponent(header, 0, 0, 5, 0);
		layout.addComponent(overview, 0, 1, 5, 1);
		layout.addComponent(wrapInPanel(influentialFollowers), 0, 2, 3, 2);
		layout.addComponent(wrapInPanel(followersChart), 4, 2, 5, 2);
		layout.addComponent(wrapInPanel(pie1), 0, 3, 1, 3);
		layout.addComponent(wrapInPanel(pie2), 2, 3, 3, 3);
		layout.addComponent(wrapInPanel(pie3), 4, 3, 5, 3);
		
		layout.setRowExpandRatio(2, 2);
		layout.setRowExpandRatio(3, 3);

		layout.setColumnExpandRatio(0, 1);
		layout.setColumnExpandRatio(1, 1);
		layout.setColumnExpandRatio(2, 1);
		layout.setColumnExpandRatio(3, 1);
		layout.setColumnExpandRatio(4, 1);
		layout.setColumnExpandRatio(5, 1);

		followersChart.chart.setWidth("50%");
		
		header.addStyleName(HEADER_STYLENAME);
		addStyleName(STYLENAME);

		setCompositionRoot(layout);
	}



	/** The view's name. */
	public static final String NAME = "analysis";

	/** The CSS class name to apply to this component. */
	private static final String STYLENAME = "AnalysisView";
	
	/** The CSS class name to apply to each panel component. */
	private static final String PANEL_STYLENAME = "AnalysisView-panel";
	
	/** The CSS class name to apply to this component. */
	private static final String HEADER_STYLENAME = "AnalysisView-header";
	
	/** Serialisation version unique ID. */
	private static final long serialVersionUID = 1L;
}
