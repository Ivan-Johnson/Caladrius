package edu.ua.cs.cs495.caladrius.android;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.PointsGraphSeries;
import com.jjoe64.graphview.series.Series;
import edu.ua.cs.cs495.caladrius.fitbit.*;
import org.json.JSONArray;
import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutionException;

/**
 * The FitbitGraphView module generates an instance of a GraphView graph with the supplied Query parameter class.
 * It interacts with both the Graphview API and the Fitbit API.
 * @author Ian Braudaway
 */
public class FitbitGraphView extends GraphView
{
	// Supplied graphType array list needs to be one of three enums,
	// there needs to be the same number of elements as the statsToRetrieve
	// ArrayList:
	// 1. "LineGraph"
	// 2. "BarGraph"
	// 3. "PointsGraph"
	ArrayList<GraphViewGraph> graphType;

	// Instance Variables
	// Supplied statsToRetrieve ArrayList must contain relevant statistics
	// that can be retrieved via Fitbit API. Ex: BPM, BasalCaloricBurn, etc.
	// Must have same number of elements as graphType list.
	ArrayList<String> statsToRetrieve;

	// Supplied seriesColors ArrayList must contain colors for each series
	ArrayList<Integer> seriesColors;

	// Supplied graphTitle is a string that is the title of the graph
	String graphTitle;

	// Supplied horizontalScroll Boolean enables/disables horizontal
	// scrolling through the graph
	Boolean horizontalScroll;

	// Supplied verticalScroll Boolean enables/disables vertical
	// scrolling through the graph
	Boolean verticalScroll;

	// Supplied horizontalZoomAndScroll Boolean enables/disables horizontal
	// scrolling through the graph and horizontal zooming
	Boolean horizontalZoomAndScroll;

	// Supplied verticalZoomAndScroll Boolean enables/disables vertical
	// scrolling through the graph and vertical zooming
	Boolean verticalZoomAndScroll;

	// Supplied Legend Boolean enables/disables legend detailing series
	// for the GraphView graph
	Boolean legend;

	// Constructor
	public FitbitGraphView(final Context context, final Query query) throws JSONException, InterruptedException, ExecutionException
	{
		super(context);

		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
			RelativeLayout.LayoutParams.WRAP_CONTENT,
			RelativeLayout.LayoutParams.WRAP_CONTENT
		);
		//int pxPadding = pxFromDip(30);
		//params.setMargins(pxPadding, pxPadding, pxPadding, pxPadding);
		setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, pxFromDip(300)));
		//setLayoutParams(params);
		//setPaddingRelative(pxPadding, pxPadding, pxPadding, pxPadding);

		this.graphType = query.getGraphType();
		this.statsToRetrieve = query.getStatsToRetrieve();
		this.seriesColors = query.getSeriesColors();
		this.graphTitle = query.getGraphTitle();
		this.horizontalScroll = query.getHorizontalScroll();
		this.verticalScroll = query.getVerticalScroll();
		this.horizontalZoomAndScroll = query.getHorizontalZoomAndScroll();
		this.verticalZoomAndScroll = query.getVerticalZoomAndScroll();
		this.legend = query.getLegend();

		GridLabelRenderer glr = this.getGridLabelRenderer();
		glr.setPadding(32);

		makeGraphViewGraph();

		// Navigate to the split graph/data view
		this.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				Intent submitPage = new Intent(context, QueryActivity.class);
				submitPage.putExtra("startDate", "N/A");
				submitPage.putExtra("endDate", "N/A");
				submitPage.putExtra("item_1", "N/A");
				submitPage.putExtra("item_2", "N/A");
				context.startActivity(submitPage);
			}
		});
	}

	private static final DataPoint dpFromPoint(Point point)
	{
		if (point.isXDate()) {
			return new DataPoint(point.getXAsDate(), point.getY());
		} else {
			return new DataPoint(point.getX(), point.getY());
		}
	}

	protected static final DataPoint[] dpsFromPoints(Point points[])
	{
		DataPoint dps[] = new DataPoint[points.length];
		for (int c = 0; c < points.length; c++) {
			dps[c] = dpFromPoint(points[c]);
		}
		return dps;
	}

	// converts from density independent pixels to pixels
	private int pxFromDip(int dip)
	{
		Resources r = getResources();
		float px = TypedValue.applyDimension(
			TypedValue.COMPLEX_UNIT_DIP,
			dip,
			r.getDisplayMetrics()
		);

		return Math.round(px);
	}

	public ArrayList<GraphViewGraph> getGraphType()
	{
		return this.graphType;
	}

	public void setGraphType(ArrayList<GraphViewGraph> gType)
	{
		this.graphType = gType;
	}

	public ArrayList<String> getStatsToRetrieve()
	{
		return this.statsToRetrieve;
	}

	public void setStatsToRetrieve(ArrayList<String> sToRetrieve)
	{
		this.statsToRetrieve = sToRetrieve;
	}

	public ArrayList<Integer> getSeriesColors()
	{
		return this.seriesColors;
	}

	public void setSeriesColors(ArrayList<Integer> sColors)
	{
		this.seriesColors = sColors;
	}

	public String getGraphTitle()
	{
		return this.graphTitle;
	}

	public void setGraphTitle(String gTitle)
	{
		this.graphTitle = gTitle;
	}

	public Boolean getHorizontalScroll()
	{
		return this.horizontalScroll;
	}

	public void setHorizontalScroll(Boolean hScroll)
	{
		this.horizontalScroll = hScroll;
	}

	public Boolean getVerticalScroll()
	{
		return this.verticalScroll;
	}

	public void setVerticalScroll(Boolean vScroll)
	{
		this.verticalScroll = vScroll;
	}

	public Boolean getHorizontalZoomAndScroll()
	{
		return this.horizontalZoomAndScroll;
	}

	public void setHorizontalZoomAndScroll(Boolean hZScroll)
	{
		this.horizontalZoomAndScroll = hZScroll;
	}

	public Boolean getVerticalZoomAndScroll()
	{
		return this.verticalZoomAndScroll;
	}

	public void setVerticalZoomAndScroll(Boolean vZScroll)
	{
		this.verticalZoomAndScroll = vZScroll;
	}

	public Boolean getLegend()
	{
		return this.legend;
	}

	public void setLegend(Boolean leg)
	{
		this.legend = leg;
	}

	private void scrollHandler(GraphView g)
	{
		if (getHorizontalScroll() == true) {
			g.getViewport()
			 .setScrollable(true);
		}

		if (getVerticalScroll() == true) {
			g.getViewport()
			 .setScrollableY(true);
		}

		if (getHorizontalZoomAndScroll() == true) {
			g.getViewport()
			 .setScalable(true);
		}

		if (getVerticalZoomAndScroll() == true) {
			g.getViewport()
			 .setScalableY(true);
		}
	}

	private void legendHandler(GraphView g)
	{
		if (getLegend() == true)
		{
			this.getLegendRenderer().setVisible(true);
			this.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
		}
	}

	private void setSecondaryScale(Series<DataPoint> s, Integer color, Integer firstColor, double maxY)
	{
		this.getSecondScale().addSeries(s);
		this.getSecondScale().setMinY(0);
		this.getSecondScale().setMaxY(maxY);
		this.getGridLabelRenderer().setVerticalLabelsSecondScaleColor(color);
		this.getGridLabelRenderer().setVerticalLabelsColor(firstColor);

		this.getGridLabelRenderer().setHorizontalLabelsAngle(30);
	}

	private DataPoint[] makePointsFromFitbit(String statToRetrieve) throws JSONException, InterruptedException, ExecutionException
	{
		Fitbit fitbit = new Fitbit();
		JSONArray arr = fitbit.getFitbitData(statToRetrieve);
		int numPoints = arr.length();

		DataPoint[] points = new DataPoint[numPoints];
		try
		{
			for (int x = 0; x < numPoints; x++) {
				String dt = arr.getJSONObject(x).getString("dateTime");
				SimpleDateFormat ISO8601DATEFORMAT = new SimpleDateFormat("yyyy-MM-dd");
				Date date = ISO8601DATEFORMAT.parse(dt);
				DataPoint dp = new DataPoint(date, arr.getJSONObject(x).getInt("value"));

				points[x] = dp;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return points;
	}

	private void makeGraphViewGraph() throws JSONException, InterruptedException, ExecutionException
	{
		double xMax = Double.MIN_VALUE;
		double xMin = Double.MAX_VALUE;
		double yMax = 0;

		this.setTitle(this.getGraphTitle());
		this.setTitleTextSize(75);

		for (int i = 0; i < this.graphType.size(); i++) {
			DataPoint[] points = makePointsFromFitbit(statsToRetrieve.get(i));
			if (points.length > 0) {
				double tmpX;
				tmpX = points[points.length - 1].getX();
				xMax = xMax > tmpX ? xMax : tmpX;
				tmpX = points[0].getX();
				xMin = xMin < tmpX ? xMin : tmpX;

				for (DataPoint dp : points) {
					double tmpY = dp.getY();
					yMax = yMax > tmpY ? yMax : tmpY;
				}
			}

			Series<DataPoint> series;
			int c = seriesColors.get(i);
			// LineGraph
			if (this.graphType.get(i)
			                  .equals(GraphViewGraph.LineGraph)) {
				series = new LineGraphSeries<>(points);
				((LineGraphSeries<DataPoint>) series).setColor(c);
				this.getGridLabelRenderer().setVerticalLabelsColor(c);
				((LineGraphSeries<DataPoint>) series).setTitle(statsToRetrieve.get(i));
			}

			// BarGraph
			else if (this.graphType.get(i)
			                       .equals(GraphViewGraph.BarGraph)) {
				series = new BarGraphSeries<>(points);
				((BarGraphSeries<DataPoint>) series).setColor(c);
				this.getGridLabelRenderer().setVerticalLabelsColor(c);
				((BarGraphSeries<DataPoint>) series).setTitle(statsToRetrieve.get(i));
			}

			// PointsGraph
			else if (this.graphType.get(i)
					               .equals(GraphViewGraph.PointsGraph)){
				series = new PointsGraphSeries<>(points);
				((PointsGraphSeries<DataPoint>) series).setColor(c);
				this.getGridLabelRenderer().setVerticalLabelsColor(c);
				((PointsGraphSeries<DataPoint>) series).setTitle(statsToRetrieve.get(i));
			}

			else
			{
				series = null;
			}

			if (this.graphType.size() == 2)
			{
				if (i == 1)
				{
					setSecondaryScale(series, seriesColors.get(i), seriesColors.get(i-1), Math.round(yMax + 1));
				}

				// Index 0 size 2
				else
				{
					this.addSeries(series);
					scrollHandler(this);
					legendHandler(this);

					getViewport().setYAxisBoundsManual(true);
					getViewport().setMinY(0);
					getViewport().setMaxY(yMax * 1.05);
					yMax = Double.MIN_VALUE;
				}

			}

			if (this.graphType.size() != 2)
			{
				this.addSeries(series);
				scrollHandler(this);
				legendHandler(this);
			}
		}
		getViewport().setXAxisBoundsManual(true);
		double xBorder = (xMax - xMin) * 0.05;
		getViewport().setMinX(xMin - xBorder); //TODO find min x for negative x
		getViewport().setMaxX(xMax + xBorder);
		getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getContext()));

		if (graphType.size() == 2) {
			return;
		}

		getViewport().setYAxisBoundsManual(true);
		getViewport().setMinY(0);
		getViewport().setMaxY(yMax * 1.05);

		getGridLabelRenderer().setHorizontalLabelsAngle(30);
	}

	public enum GraphViewGraph
	{
		LineGraph, BarGraph, PointsGraph
	}
}
