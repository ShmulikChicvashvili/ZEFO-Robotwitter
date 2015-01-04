
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

import com.google.inject.Inject;
import com.google.inject.name.Named;

import com.robotwitter.webapp.control.account.ITwitterAccountController;
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

		Axes axes =
			new Axes().addAxis(
				new XYaxis().setRenderer(AxisRenderers.DATE).setTickOptions(
					new CanvasAxisTickRenderer().setAngle(-30).setFormatString(
						"%#d %b, %Y"))).addAxis(
							new XYaxis(XYaxes.Y)
							.setLabel(
								messages.get("AnalysisView.chart.label.followers"))
								.setLabelRenderer(LabelRenderers.CANVAS)
								.setTickOptions(
									new AxisTickRenderer().setFormatString("%d")));

		Highlighter highlighter =
			new Highlighter()
		.setShow(true)
		.setSizeAdjust(10)
		.setTooltipLocation(TooltipLocations.NORTH)
		.setTooltipAxes(TooltipAxes.YX)
		.setUseAxesFormatters(true)
		.setFormatString("%s followers on %s");

		// Cursor cursor = new Cursor().setShow(true);

		Options options = new Options().addOption(axes).addOption(highlighter);
		// .addOption(cursor);

		DCharts chart =
			new DCharts().setDataSeries(dataSeries).setOptions(options).show();

		setCompositionRoot(chart);
	}



	/** The view's name. */
	public static final String NAME = "analysis";

	/** Serialisation version unique ID. */
	private static final long serialVersionUID = 1L;
}
