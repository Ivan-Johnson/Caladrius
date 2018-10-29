package edu.ua.cs.cs495.caladrius.android;

import java.util.ArrayList;

public class Query
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

	// Constructor
	public Query(ArrayList<FitbitGraphView.GraphViewGraph> graphType,
	             ArrayList<String> statsToRetrieve,
	             ArrayList<Integer> seriesColors,
	             String graphTitle,
	             Boolean horizontalScroll, Boolean verticalScroll,
	             Boolean horizontalZoomAndScroll,
	             Boolean verticalZoomAndScroll)
	{
		this.graphType = graphType;
		this.statsToRetrieve = statsToRetrieve;
		this.seriesColors = seriesColors;
		this.graphTitle = graphTitle;
		this.horizontalScroll = horizontalScroll;
		this.verticalScroll = verticalScroll;
		this.horizontalZoomAndScroll = horizontalZoomAndScroll;
		this.verticalZoomAndScroll = verticalZoomAndScroll;
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

}
