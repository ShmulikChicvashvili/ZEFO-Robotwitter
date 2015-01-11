/**
 *
 */

package com.robotwitter.webapp.util;


import java.util.Map;
import java.util.Map.Entry;

import org.dussan.vaadin.dcharts.DCharts;
import org.dussan.vaadin.dcharts.data.DataSeries;
import org.dussan.vaadin.dcharts.metadata.SeriesToggles;
import org.dussan.vaadin.dcharts.metadata.renderers.LegendRenderers;
import org.dussan.vaadin.dcharts.metadata.renderers.SeriesRenderers;
import org.dussan.vaadin.dcharts.options.Highlighter;
import org.dussan.vaadin.dcharts.options.Legend;
import org.dussan.vaadin.dcharts.options.Options;
import org.dussan.vaadin.dcharts.options.SeriesDefaults;
import org.dussan.vaadin.dcharts.renderers.legend.EnhancedLegendRenderer;
import org.dussan.vaadin.dcharts.renderers.series.PieRenderer;

import com.robotwitter.webapp.messages.IMessagesContainer;




/**
 * Represents an abstract pie chart component.
 *
 *
 * A pie chart is divided into sectors that each represents a proportion of the
 * whole.
 *
 * @author Eyal
 * @author Hagai
 */
public abstract class AbstractPieChartComponent
extends
RobotwitterCustomComponent
{

	/**
	 * Instantiates a new abstract pie chart component.
	 *
	 * @param messages
	 *            the messages
	 */
	public AbstractPieChartComponent(IMessagesContainer messages)
	{
		super(messages);

		SeriesDefaults seriesDefaults = initialiseSeriesDefaults();

		Legend legend = initialiseLegend();

		Highlighter highlighter = initialiseHighlighter();

		options =
			new Options()
		.setSeriesDefaults(seriesDefaults)
		.setHighlighter(highlighter)
		.setLegend(legend);

		pieChart = new DCharts();
		setCompositionRoot(pieChart);
	}


	/**
	 * Initialise highlighter.
	 *
	 * @return a highlighter for the chart.
	 */
	private Highlighter initialiseHighlighter()
	{
		Highlighter highlighter =
			new Highlighter()
		.setShow(true)
		.setShowTooltip(true)
		.setTooltipAlwaysVisible(true)
		.setKeepTooltipInsideChart(true);
		return highlighter;
	}


	/**
	 * Initialise legend.
	 *
	 * @return a legend for the chart.
	 */
	private Legend initialiseLegend()
	{
		Legend legend =
			new Legend()
		.setShow(true)
		.setRenderer(LegendRenderers.ENHANCED)
		.setRendererOptions(
			new EnhancedLegendRenderer().setSeriesToggle(
				SeriesToggles.SLOW).setSeriesToggleReplot(true));
		return legend;
	}


	/**
	 * Initialise series defaults.
	 *
	 * @return the series defaults options for the chart.
	 */
	private SeriesDefaults initialiseSeriesDefaults()
	{
		SeriesDefaults seriesDefaults =
			new SeriesDefaults()
		.setRenderer(SeriesRenderers.PIE)
		.setRendererOptions(new PieRenderer().setShowDataLabels(true));
		return seriesDefaults;
	}


	/**
	 * Sets the data for the chart.
	 *
	 * @param data
	 *            the data
	 */
	protected final void set(Map<String, Integer> data)
	{
		DataSeries dataSeries = new DataSeries();
		for (Entry<String, Integer> entry : data.entrySet())
		{
			dataSeries.newSeries().add(entry.getKey(), entry.getValue());
		}
		if (dataSeries.isEmpty())
		{
			options.getLegend().setShow(false);
			Object[] empty = { null, null };
			dataSeries.newSeries().add(empty);
		} else
		{
			options.getLegend().setShow(true);
		}
		pieChart.setDataSeries(dataSeries);
	}


	/**
	 * Sets the label for the chart.
	 *
	 * @param label
	 *            the label
	 */
	protected final void set(String label)
	{
		options.setTitle(label);
	}


	/**
	 * Updates the options and shows the pie chart.
	 */
	protected final void show()
	{
		pieChart.setOptions(options);
		pieChart.show();
	}



	/** The pie chart options. */
	private Options options;

	/** The pie chart. */
	private DCharts pieChart;
}
