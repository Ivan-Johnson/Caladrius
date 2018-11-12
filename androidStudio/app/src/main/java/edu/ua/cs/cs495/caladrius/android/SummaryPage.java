package edu.ua.cs.cs495.caladrius.android;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * The SummaryPage module represents the main View that is exposed upon logging into the application.
 * It contains FitbitGraphView instances as well as a button to view all of the data for a given user.
 */
public class SummaryPage extends Fragment
{
	public final FitbitGraphView.GraphViewGraph[][] defaultGraphTypes = {
		{FitbitGraphView.GraphViewGraph.BarGraph},
		{FitbitGraphView.GraphViewGraph.PointsGraph},
		{FitbitGraphView.GraphViewGraph.PointsGraph},
		{FitbitGraphView.GraphViewGraph.BarGraph, FitbitGraphView.GraphViewGraph.LineGraph},
			{FitbitGraphView.GraphViewGraph.BarGraph, FitbitGraphView.GraphViewGraph.LineGraph, FitbitGraphView.GraphViewGraph.PointsGraph},
	};
	public final String[][] defaultGraphStats = {
		{"Heart Rate"},
		{"Caloric Burn"},
		{"Caloric Basal"},
		{"BPM", "Weight"},
			{"Test1", "Test2", "Test3"},
	};
	public final Integer[][] defaultGraphColors = {
		{Color.CYAN},
		{Color.RED},
		{Color.BLUE},
		{Color.GREEN, Color.GRAY},
			{Color.BLUE, Color.RED, Color.YELLOW},
	};
	public final String[] defaultGraphTitles = {
		"Heart Rate",
		"Caloric Burn",
		"Caloric BASAL",
		"BPM",
			"Test 3 Charts",
	};

	public SummaryPage()
	{
		// Empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		View rootView = inflater.inflate(R.layout.query_activity, container, false);

		LinearLayout ll = rootView.findViewById(R.id.list);
		assert defaultGraphStats.length == defaultGraphTypes.length;
		for (int c = defaultGraphStats.length - 1; c >= 0; c--) {
			ArrayList<FitbitGraphView.GraphViewGraph> graphTypes =
					new ArrayList<>(Arrays.asList(defaultGraphTypes[c]));

			ArrayList<String> stats = new ArrayList<>(Arrays.asList(defaultGraphStats[c]));

			ArrayList<Integer> color = new ArrayList<>(Arrays.asList(defaultGraphColors[c]));

			String title = defaultGraphTitles[c];

			Query query = new Query(graphTypes,
									stats,
									color,
									title);

			FitbitGraphView fgv = new FitbitGraphView(getContext(),
				query
			);

			ll.addView(fgv, 0);
		}

		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);

		final Button viewAllDataButton = getView().findViewById(R.id.viewAllData);
		viewAllDataButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Intent nextScreen = new Intent(v.getContext(), AllData.class);
				startActivityForResult(nextScreen, 0);
			}
		});
	}
}
