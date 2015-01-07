
package com.robotwitter.webapp.view.analysis;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.dussan.vaadin.dcharts.DCharts;
import org.dussan.vaadin.dcharts.base.elements.XYaxis;
import org.dussan.vaadin.dcharts.data.DataSeries;
import org.dussan.vaadin.dcharts.metadata.TooltipAxes;
import org.dussan.vaadin.dcharts.metadata.XYaxes;
import org.dussan.vaadin.dcharts.metadata.locations.TooltipLocations;
import org.dussan.vaadin.dcharts.metadata.renderers.AxisRenderers;
import org.dussan.vaadin.dcharts.metadata.renderers.LabelRenderers;
import org.dussan.vaadin.dcharts.options.Axes;
import org.dussan.vaadin.dcharts.options.Highlighter;
import org.dussan.vaadin.dcharts.options.Options;
import org.dussan.vaadin.dcharts.renderers.tick.AxisTickRenderer;
import org.dussan.vaadin.dcharts.renderers.tick.CanvasAxisTickRenderer;

import com.vaadin.ui.UI;

import com.robotwitter.webapp.control.account.ITwitterAccountController;
import com.robotwitter.webapp.messages.IMessagesContainer;
import com.robotwitter.webapp.util.RobotwitterCustomComponent;




/**
 * Represents a graph of the amount of Twitter followers of the active Twitter
 * account over time.
 *
 * @author Hagai Akibayov
 * @author Eyal Tolchinsky
 */
public class FollowersAmountOverTimeChart extends RobotwitterCustomComponent
{

	/**
	 * Instantiates a new followers over time chart.
	 *
	 * @param messages
	 *            the container of messages to display
	 */
	public FollowersAmountOverTimeChart(IMessagesContainer messages)
	{
		super(messages);
		initialiseLayout();
		getUserSession().observeActiveTwitterAccount(this);
	}
	
	
	@Override
	public final void activateTwitterAccount(long id)
	{
		updateChart();
	}


	/** Initialises the layout. */
	private void initialiseLayout()
	{
		Axes axes = new Axes();
		axes.addAxis(new XYaxis()
		.setRenderer(AxisRenderers.DATE)
		.setTickOptions(
			new CanvasAxisTickRenderer().setAngle(-30).setFormatString(
				"%#d %b, %Y")));
		axes.addAxis(new XYaxis(XYaxes.Y)
			.setLabel(messages.get("AnalysisView.chart.label.followers"))
			.setLabelRenderer(LabelRenderers.CANVAS)
			.setTickOptions(new AxisTickRenderer().setFormatString("%d")));
		
		Highlighter highlighter =
			new Highlighter()
				.setShow(true)
				.setSizeAdjust(10)
				.setTooltipLocation(TooltipLocations.NORTH)
				.setTooltipAxes(TooltipAxes.YX)
				.setUseAxesFormatters(true)
				.setFormatString("%s followers on %s");
		
		Options options =
			new Options()
		.addOption(axes)
		.addOption(highlighter)
		.setAnimate(true);
		
		chart = new DCharts().setOptions(options);
		updateChart();
		
		// FIXME this should be done on the client-side
		UI.getCurrent().getPage().addBrowserWindowResizeListener(event -> {
			chart.setSizeFull();
		});

		addStyleName(STYLENAME);
		
		setCompositionRoot(chart);
	}


	/** Update the followers over time chart. */
	private void updateChart()
	{
		ITwitterAccountController controller =
			getUserSession().getAccountController().getActiveTwitterAccount();

		Map<Date, Integer> followers =
			controller.getAmountOfFollowers(null, null);

		DataSeries dataSeries = new DataSeries().newSeries();

		DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

		for (Map.Entry<Date, Integer> entry : followers.entrySet())
		{
			dataSeries.add(df.format(entry.getKey()), entry.getValue());
		}

		chart.setDataSeries(dataSeries).show();
	}



	/** The followers over time chart. */
	DCharts chart;

	/** The CSS class name to apply to this component. */
	private static final String STYLENAME = "FollowersOverTimeChart";

	/** Serialisation version unique ID. */
	private static final long serialVersionUID = 1L;
}
