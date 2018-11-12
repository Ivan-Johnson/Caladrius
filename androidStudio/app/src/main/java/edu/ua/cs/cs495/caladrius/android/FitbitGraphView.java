package edu.ua.cs.cs495.caladrius.android;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.provider.ContactsContract;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.PointsGraphSeries;
import com.jjoe64.graphview.series.Series;
import edu.ua.cs.cs495.caladrius.fitbit.FitbitAccount;
import edu.ua.cs.cs495.caladrius.fitbit.Point;

import java.util.ArrayList;

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
	public FitbitGraphView(final Context context, final Query query)
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

	private void setSecondaryScale(Series<DataPoint> s, Integer color, double maxY)
	{
		this.getSecondScale().addSeries(s);
		this.getSecondScale().setMinY(0);
		this.getSecondScale().setMaxY(10);
		this.getGridLabelRenderer().setVerticalLabelsSecondScaleColor(color);
	}

	private void makeGraphViewGraph()
	{
		double xMax = 0;
		double yMax = 0;

		double xMaxOfMax = 0;
		double yMaxOfMax = 0;

		this.setTitle(this.getGraphTitle());
		this.setTitleTextSize(75);

		for (int i = 0; i < this.graphType.size(); i++) {
			DataPoint[] points = dpsFromPoints(Caladrius.user.fAcc.getPoints(statsToRetrieve.get(i)));
			if (points.length > 0) {
				double tmpX = points[points.length - 1].getX();
				xMax = xMax > tmpX ? xMax : tmpX;
				if (xMax > xMaxOfMax)
				{
					xMaxOfMax = xMax;
				}

				double tmpY = points[points.length - 1].getY();
				yMax = yMax > tmpY ? yMax : tmpY;
				if (yMax > yMaxOfMax)
				{
					yMaxOfMax = yMax;
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
//				DataPoint points_bar[] = {
//					new DataPoint(0.5, 0),
//					new DataPoint(1.5, 2),
//					new DataPoint(2.5, 1),
//					new DataPoint(3.5, 4),
//					new DataPoint(4.5, 3),
//					new DataPoint(5.5, 5)
//				};
//				xMax = 6;
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
					setSecondaryScale(series, seriesColors.get(i-1), Math.round(yMaxOfMax + 1));
				}

				// Index 0 size 2
				else
				{
					this.addSeries(series);
					scrollHandler(this);
					legendHandler(this);
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
		getViewport().setMinX(0); //TODO find min x for negative x
		getViewport().setMaxX(Math.round(xMaxOfMax + 1));

		getViewport().setYAxisBoundsManual(true);
		getViewport().setMinY(0);
		getViewport().setMaxY(Math.round(yMaxOfMax + 1));
	}

	public enum GraphViewGraph
	{
		LineGraph, BarGraph, PointsGraph
	}
}
