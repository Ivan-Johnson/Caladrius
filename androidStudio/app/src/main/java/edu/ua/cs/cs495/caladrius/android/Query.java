package edu.ua.cs.cs495.caladrius.android;

import android.graphics.Color;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

/**
 * The Query module concentrates the parameters for the FitbitGraphView module.
 * This object is usually then passed into the FitbitGraphView module.
 * @author Ian Braudaway
 */
public class Query implements Serializable
{
	// Supplied graphType array list needs to be one of three enums,
	// there needs to be the same number of elements as the statsToRetrieve
	// ArrayList:
	// 1. "LineGraph"
	// 2. "BarGraph"
	// 3. "PointsGraph"
	ArrayList<FitbitGraphView.GraphViewGraph> graphType;

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

	/**
	 *
	 * @param graphType see below
	 * @param statsToRetrieve see below
	 * @param seriesColors see below
	 * @param graphTitle see below
	 */
	public Query(ArrayList<FitbitGraphView.GraphViewGraph> graphType,
				 ArrayList<String> statsToRetrieve,
				 ArrayList<Integer> seriesColors,
				 String graphTitle)
	{
		this(graphType, statsToRetrieve, seriesColors,
				graphTitle, false, false,
				false, false, true);
	}

	/**
	 *
	 * @param graphType see below
	 * @param statsToRetrieve see below
	 * @param graphTitle see below
	 */
	public Query(ArrayList<FitbitGraphView.GraphViewGraph> graphType,
				 ArrayList<String> statsToRetrieve,
				 String graphTitle)
	{
		this(graphType, statsToRetrieve, new ArrayList<Integer> (Collections.nCopies(graphType.size(), Color.BLUE)),
				graphTitle);
	}

	/**
	 *
	 * @param graphType use one of the enumerated graph types:
	 *                  FitbitGraphView.GraphViewGraph.BarGraph,
	 *                  FitbitGraphView.GraphViewGraph.LineGraph,
	 *                  FitbitGraphView.GraphViewGraph.PointsGraph
	 * @param statsToRetrieve strings representing statistics to retrieve from Fitbit, must follow Fitbit standards
	 *                        Examples:
	 *                        "calories", "steps", "caloriesBMR", "activityCalories", "steps", "minutesSedentary",
	 * 						  "minutesLightlyActive", "minutesFairlyActive", "minutesVeryActive"
	 * @param seriesColors colors from Color module
	 *                     Examples:
	 *                     Color.DKGRAY, Color.RED, Color.BLUE, Color.GREEN, Color.BLACK, Color.MAGENTA
	 * @param graphTitle string representing title of graph
	 * @param horizontalScroll boolean turning horizontal scrolling on the graph on or off
	 * @param verticalScroll boolean turning vertical scrolling on the graph on or off
	 * @param horizontalZoomAndScroll boolean turning horizontal scrolling and zooming on the graph on or off
	 * @param verticalZoomAndScroll boolean turning vertical scrolling and zooming on the graph on or off
	 * @param legend boolean turning legend feature on or off
	 */
	public Query(ArrayList<FitbitGraphView.GraphViewGraph> graphType,
	             ArrayList<String> statsToRetrieve,
	             ArrayList<Integer> seriesColors,
	             String graphTitle,
	             Boolean horizontalScroll, Boolean verticalScroll,
	             Boolean horizontalZoomAndScroll,
	             Boolean verticalZoomAndScroll,
	             Boolean legend)
	{
		this.graphType = graphType;
		this.statsToRetrieve = statsToRetrieve;
		this.seriesColors = seriesColors;
		this.graphTitle = graphTitle;
		this.horizontalScroll = horizontalScroll;
		this.verticalScroll = verticalScroll;
		this.horizontalZoomAndScroll = horizontalZoomAndScroll;
		this.verticalZoomAndScroll = verticalZoomAndScroll;
		this.legend = legend;
	}

	public ArrayList<FitbitGraphView.GraphViewGraph> getGraphType()
	{
		return this.graphType;
	}

	public void setGraphType(ArrayList<FitbitGraphView.GraphViewGraph> gType)
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

}
