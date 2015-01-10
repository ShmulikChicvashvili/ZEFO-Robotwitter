/**
 *
 */

package com.robotwitter.webapp.util;


import java.util.List;

import org.dussan.vaadin.dcharts.DCharts;
import org.dussan.vaadin.dcharts.base.elements.PointLabels;
import org.dussan.vaadin.dcharts.base.elements.XYaxis;
import org.dussan.vaadin.dcharts.data.DataSeries;
import org.dussan.vaadin.dcharts.data.Ticks;
import org.dussan.vaadin.dcharts.metadata.SeriesToggles;
import org.dussan.vaadin.dcharts.metadata.TooltipAxes;
import org.dussan.vaadin.dcharts.metadata.renderers.AxisRenderers;
import org.dussan.vaadin.dcharts.metadata.renderers.LegendRenderers;
import org.dussan.vaadin.dcharts.metadata.renderers.SeriesRenderers;
import org.dussan.vaadin.dcharts.options.Axes;
import org.dussan.vaadin.dcharts.options.Highlighter;
import org.dussan.vaadin.dcharts.options.Legend;
import org.dussan.vaadin.dcharts.options.Options;
import org.dussan.vaadin.dcharts.options.SeriesDefaults;
import org.dussan.vaadin.dcharts.renderers.legend.EnhancedLegendRenderer;

import com.robotwitter.webapp.messages.IMessagesContainer;




/**
 * @author Eyal
 *
 */
public abstract class AbstractBarCharComponent
extends
RobotwitterCustomComponent
{

	/**
	 * Instantiates a new abstract bar char component.
	 *
	 * @param messages
	 *            the messages
	 */
	public AbstractBarCharComponent(IMessagesContainer messages)
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

		barChart = new DCharts().setOptions(options);

		setCompositionRoot(barChart);
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
		.setKeepTooltipInsideChart(true)
		.setTooltipAxes(TooltipAxes.XY_BAR);
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
		.setRenderer(SeriesRenderers.BAR)
		.setPointLabels(new PointLabels().setShow(true));
		return seriesDefaults;
	}


	/**
	 * Sets the data for the chart.
	 *
	 * @param data
	 *            the data
	 */
	protected final void set(List<String> ticks, List<Integer> amounts)
	{
		DataSeries dataSeries = new DataSeries().newSeries();
		for (Integer amount : amounts)
		{
			dataSeries.add(amount);
		}

		Axes axes = new Axes();
		axes.addAxis(new XYaxis().setRenderer(AxisRenderers.CATEGORY).setTicks(
			new Ticks().add("23", "45", "67")));

		options.setAxes(axes);
		barChart.setDataSeries(dataSeries);

		barChart.show();
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
		barChart.setCaption(label);
	}



	private Options options;

	private DCharts barChart;

}
