
package com.robotwitter.webapp.view.analysis;


import com.google.inject.Inject;
import com.google.inject.name.Named;

import com.vaadin.ui.VerticalLayout;

import com.robotwitter.webapp.messages.IMessagesContainer;
import com.robotwitter.webapp.view.AbstractView;




/** Analysis view. */
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
		VerticalLayout temp = new VerticalLayout();
		temp.setSizeFull();
		
		// DataSeries dataSeries = new DataSeries().add(2, 6, 7, 10);
		//
		// SeriesDefaults seriesDefaults =
		// new SeriesDefaults().setRenderer(SeriesRenderers.BAR);
		//
		// Axes axes =
		// new Axes().addAxis(new XYaxis()
		// .setRenderer(AxisRenderers.CATEGORY)
		// .setTicks(new Ticks().add("a", "b", "c", "d")));
		//
		// Highlighter highlighter = new Highlighter().setShow(false);
		//
		// Options options =
		// new Options()
		// .setSeriesDefaults(seriesDefaults)
		// .setAxes(axes)
		// .setHighlighter(highlighter);
		//
		// DCharts chart =
		// new DCharts().setDataSeries(dataSeries).setOptions(options).show();
		//
		// setCompositionRoot(chart);
	}



	/** The view's name. */
	public static final String NAME = "analysis";

	/** Serialisation version unique ID. */
	private static final long serialVersionUID = 1L;
}
