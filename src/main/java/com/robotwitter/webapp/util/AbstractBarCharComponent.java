/**
 *
 */

package com.robotwitter.webapp.util;


import java.util.ArrayList;
import java.util.List;

import org.dussan.vaadin.dcharts.DCharts;
import org.dussan.vaadin.dcharts.base.elements.PointLabels;
import org.dussan.vaadin.dcharts.base.elements.XYaxis;
import org.dussan.vaadin.dcharts.data.DataSeries;
import org.dussan.vaadin.dcharts.data.Ticks;
import org.dussan.vaadin.dcharts.metadata.TooltipAxes;
import org.dussan.vaadin.dcharts.metadata.renderers.AxisRenderers;
import org.dussan.vaadin.dcharts.metadata.renderers.SeriesRenderers;
import org.dussan.vaadin.dcharts.options.Axes;
import org.dussan.vaadin.dcharts.options.Highlighter;
import org.dussan.vaadin.dcharts.options.Legend;
import org.dussan.vaadin.dcharts.options.Options;
import org.dussan.vaadin.dcharts.options.SeriesDefaults;

import com.vaadin.ui.Label;

import com.robotwitter.webapp.messages.IMessagesContainer;




/**
 * @author Eyal
 * @author Hagai
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
	public AbstractBarCharComponent(
		IMessagesContainer messages,
		String noDataMessage)
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

		barChart = new DCharts();

		noDataLabel = new Label(noDataMessage);

		// setCompositionRoot(barChart);
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
		Legend legend = new Legend().setShow(false);
		// .setShow(true)
		// .setRenderer(LegendRenderers.ENHANCED)
		// .setRendererOptions(
		// new EnhancedLegendRenderer().setSeriesToggle(
		// SeriesToggles.SLOW).setSeriesToggleReplot(true));
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
	 * @param ticks
	 *            the axes ticks
	 * @param amounts
	 *            the amounts
	 */
	protected final void set(List<String> ticks, List<Integer> amounts)
	{

		Axes axes = new Axes();
		XYaxis axis = new XYaxis().setRenderer(AxisRenderers.CATEGORY);
		// if there are ticks set them, else leave it empty
		if (ticks.size() > 0)
		{
			axis.setTicks(new Ticks().add(ticks.toArray()));
		}
		axes.addAxis(axis);
		options.setAxes(axes);

		DataSeries dataSeries = new DataSeries();
		// if there is data set it, else leave it empty
		if (amounts.size() > 0)
		{
			dataSeries.add(amounts.toArray());
		}
		barChart.setDataSeries(dataSeries);
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
	 * Sets the data according to the amounts list, and the ticks according to
	 * the separators.
	 *
	 * @param separators
	 *            the separators
	 * @param amounts
	 *            the amounts
	 */
	protected final void setBySeperators(
		List<Integer> separators,
		List<Integer> amounts)
	{
		assert amounts.size() == 0
			&& separators.size() == 0
			|| amounts.size() == separators.size() + 1;

		List<String> ticks = new ArrayList<>();

		if (separators.size() > 0)
		{
			// set ticks to show the ranges
			ticks.add("<" + separators.get(0));
			for (int i = 0; i < separators.size() - 1; i++)
			{
				ticks.add(separators.get(i) + "-" + separators.get(i + 1));
			}
			ticks.add("&ge;" + separators.get(separators.size() - 1));
			assert ticks.size() == amounts.size();
		}
		// set the ticks and data. they both might be empty (if there is no
		// data).
		// 'set' should handle this.

		set(ticks, amounts);
	}


	/**
	 * Updates the options and shows the bar chart.
	 */
	protected final void show()
	{
		if (barChart.getDataSeries().isEmpty())
		{
			setCompositionRoot(noDataLabel);
		} else
		{
			setCompositionRoot(barChart);
			barChart.setOptions(options);
			barChart.show();
		}
	}



	/** The bar chart options. */
	private Options options;

	/** The bar chart. */
	private DCharts barChart;

	/** The no data label. */
	private Label noDataLabel;

}
