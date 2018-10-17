package edu.ua.cs.cs495.caladrius.caladrius;

import android.app.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.util.ArrayList;


import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.PointsGraphSeries;

public class Graph
{
    // Instance Variables

    // Pass in the view where the graphs are instantiated
    View view;

    // Pass in graph identifier in xml
    int id;

    // Supplied graphType array list needs to be one of three strings,
    // there needs to be the same number of elements as the statsToRetrieve
    // ArrayList:
    // 1. "Line Graph"
    // 2. "Bar Graph"
    // 3. "Points Graph"
    ArrayList<String> graphType;

    // Supplied statsToRetrieve ArrayList must contain relevant statistics
    // that can be retrieved via Fitbit API. Ex: BPM, BasalCaloricBurn, etc.
    ArrayList<String> statsToRetrieve;

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
    public Graph(View view, int id, ArrayList<String> graphType, ArrayList<String> statsToRetrieve,
                 Boolean horizontalScroll, Boolean verticalScroll,
                 Boolean horizontalZoomAndScroll,
                 Boolean verticalZoomAndScroll)
    {
        this.view = view;
        this.id = id;
        this.graphType = graphType;
        this.statsToRetrieve = statsToRetrieve;
        this.horizontalScroll = horizontalScroll;
        this.verticalScroll = verticalScroll;
        this.horizontalZoomAndScroll = horizontalZoomAndScroll;
        this.verticalZoomAndScroll = verticalZoomAndScroll;

        makeGraphViewGraph();
    }

    // Getter Methods
    public View getView()
    {
        return this.view;
    }

    public int getId()
    {
        return this.id;
    }

    public ArrayList<String> getGraphType()
    {
        return this.graphType;
    }

    public ArrayList<String> getStatsToRetrieve()
    {
        return this.statsToRetrieve;
    }

    public Boolean getHorizontalScroll()
    {
        return this.horizontalScroll;
    }

    public Boolean getVerticalScroll()
    {
        return this.verticalScroll;
    }

    public Boolean getHorizontalZoomAndScroll()
    {
        return this.horizontalZoomAndScroll;
    }

    public Boolean getVerticalZoomAndScroll()
    {
        return this.verticalZoomAndScroll;
    }

    // Setter Methods
    public void setGraphType(ArrayList<String> gType)
    {
        this.graphType = gType;
    }

    public void setStatsToRetrieve(ArrayList<String> sToRetrieve)
    {
        this.statsToRetrieve = sToRetrieve;
    }

    public void setHorizontalScroll(Boolean hScroll)
    {
        this.horizontalScroll = hScroll;
    }

    public void setVerticalScroll(Boolean vScroll)
    {
        this.verticalScroll = vScroll;
    }

    public void setHorizontalZoomAndScroll(Boolean hZScroll)
    {
        this.horizontalZoomAndScroll = hZScroll;
    }

    public void setVerticalZoomAndScroll(Boolean vZScroll)
    {
        this.verticalZoomAndScroll = vZScroll;
    }

    private void scrollHandler(GraphView g)
    {
        if (getHorizontalScroll() == true)
        {
            g.getViewport().setScrollable(true);
        }

        if (getVerticalScroll() == true)
        {
            g.getViewport().setScrollableY(true);
        }

        if (getHorizontalZoomAndScroll() == true)
        {
            g.getViewport().setScalable(true);
        }

        if (getVerticalZoomAndScroll() == true)
        {
            g.getViewport().setScalableY(true);
        }
    }

    private void makeGraphViewGraph()
    {
        GraphView graph = this.getView().findViewById(this.getId());

        for (int i=0; i<this.graphType.size(); i++)
        {
            if (this.graphType.get(i) == "Line Graph")
            {
                LineGraphSeries<DataPoint> series = new LineGraphSeries<>(
                        new DataPoint[] {
                                // Some sort of loop creating DataPoint objects from
                                // whatever you want to plot, getting information from
                                // FitBit API.
                                new DataPoint(0, 0)
                                // Use this.statsToRetrieve[i] to find out what we are
                                // plotting at this point in the loop. Send that
                                // string to the fitbit API.
                        });
                graph.addSeries(series);
            }

            else if (this.graphType.get(i) == "Bar Graph")
            {
                BarGraphSeries<DataPoint> series = new BarGraphSeries<>(
                        new DataPoint[] {
                                // Some sort of loop creating DataPoint objects from
                                // whatever you want to plot, getting information from
                                // FitBit API.
                                new DataPoint(0, 0)
                                // Use this.statsToRetrieve[i] to find out what we are
                                // plotting at this point in the loop. Send that
                                // string to the fitbit API.
                        });
                graph.addSeries(series);
            }

            // "Points Graph"
            else
            {
                PointsGraphSeries<DataPoint> series = new PointsGraphSeries<>(
                        new DataPoint[] {
                                // Some sort of loop creating DataPoint objects from
                                // whatever you want to plot, getting information from
                                // FitBit API.
                                new DataPoint(0, 0)
                                // Use this.statsToRetrieve[i] to find out what we are
                                // plotting at this point in the loop. Send that
                                // string to the fitbit API.
                        });
                graph.addSeries(series);
            }

            scrollHandler(graph);
        }
    }
}
