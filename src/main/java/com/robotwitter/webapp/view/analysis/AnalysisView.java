
package com.robotwitter.webapp.view.analysis;


import com.google.inject.Inject;
import com.google.inject.name.Named;

import com.vaadin.ui.Component;
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
	private Component wrapInPanel(
		Component component,
		String title,
		String tooltip)
	{
		Label titleLabel = new Label(title);
		titleLabel.setDescription(tooltip);
		VerticalLayout panel = new VerticalLayout(titleLabel, component);
		panel.setSizeFull();
		panel.setExpandRatio(component, 1);
		titleLabel.addStyleName(PANEL_TITLE_STYLENAME);
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

		FollowersDisplayedLanguageChart followersDisplayedLanguageChart =
			new FollowersDisplayedLanguageChart(messages);
		FollowersFollowersAmountChart followersFollowersAmountChart =
			new FollowersFollowersAmountChart(messages);
		FollowersFollowingAmountChart followersFollowingAmountChart =
			new FollowersFollowingAmountChart(messages);

		followersChart.setSizeFull();
		influentialFollowers.setSizeFull();
		followersDisplayedLanguageChart.setSizeFull();
		followersFollowersAmountChart.setSizeFull();
		followersFollowingAmountChart.setSizeFull();

		Component followersChartPanel =
			wrapInPanel(
				followersChart,
				messages.get("AnalysisView.caption.followers-amount-over-time"),
				messages.get("AnalysisView.tooltip.followers-amount-over-time"));
		Component influentialFollowersPanel =
			wrapInPanel(
				influentialFollowers,
				messages.get("AnalysisView.caption.most-influential-followers"),
				messages.get("AnalysisView.tooltip.most-influential-followers"));
		Component followersDisplayedLanguageChartPanel =
			wrapInPanel(
				followersDisplayedLanguageChart,
				messages
				.get("AnalysisView.caption.followers-displayed-language"),
				messages
				.get("AnalysisView.tooltip.followers-displayed-language"));
		Component followersFollowersAmountChartPanel =
			wrapInPanel(
				followersFollowersAmountChart,
				messages.get("AnalysisView.caption.followers-followers-amount"),
				messages.get("AnalysisView.tooltip.followers-followers-amount"));
		Component followersFollowingAmountChartPanel =
			wrapInPanel(
				followersFollowingAmountChart,
				messages.get("AnalysisView.caption.followers-following-amount"),
				messages.get("AnalysisView.tooltip.followers-following-amount"));

		// TODO this should be in CSS
		influentialFollowersPanel.setWidth("700px");

		HorizontalLayout firstRow = new HorizontalLayout(header, overview);
		firstRow.setWidth("100%");
		firstRow.setSpacing(true);

		HorizontalLayout secondRow =
			new HorizontalLayout(influentialFollowersPanel, followersChartPanel);
		secondRow.setSizeFull();
		secondRow.setSpacing(true);
		secondRow.setExpandRatio(followersChartPanel, 1);

		HorizontalLayout thirdRow =
			new HorizontalLayout(
				followersDisplayedLanguageChartPanel,
				followersFollowersAmountChartPanel,
				followersFollowingAmountChartPanel);
		thirdRow.setSizeFull();
		thirdRow.setSpacing(true);

		VerticalLayout layout =
			new VerticalLayout(firstRow, secondRow, thirdRow);
		layout.setSizeFull();
		layout.setSpacing(true);
		layout.setExpandRatio(secondRow, 1);
		layout.setExpandRatio(thirdRow, 1);

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

	/** The CSS class name to apply to each panel title label. */
	private static final String PANEL_TITLE_STYLENAME =
		"AnalysisView-panel-title";

	/** The CSS class name to apply to the header component. */
	private static final String HEADER_STYLENAME = "AnalysisView-header";

	/** Serialisation version unique ID. */
	private static final long serialVersionUID = 1L;
}
