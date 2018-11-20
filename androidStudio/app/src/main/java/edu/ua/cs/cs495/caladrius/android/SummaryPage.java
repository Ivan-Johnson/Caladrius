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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

/**
 * The SummaryPage module represents the main View that is exposed upon logging into the application.
 * It contains FitbitGraphView instances as well as a button to view all of the data for a given user.
 */
public class SummaryPage extends Fragment
{
	public final FitbitGraphView.GraphViewGraph[][] defaultGraphTypes = {
		{FitbitGraphView.GraphViewGraph.BarGraph},
		{FitbitGraphView.GraphViewGraph.PointsGraph},
		{FitbitGraphView.GraphViewGraph.PointsGraph, FitbitGraphView.GraphViewGraph.PointsGraph},
		{FitbitGraphView.GraphViewGraph.BarGraph, FitbitGraphView.GraphViewGraph.LineGraph},
		{FitbitGraphView.GraphViewGraph.BarGraph, FitbitGraphView.GraphViewGraph.LineGraph,
			FitbitGraphView.GraphViewGraph.PointsGraph},
	};
	public final String[][] defaultGraphStats = {
		{"calories"},
		{"steps"},
		{"caloriesBMR", "activityCalories"},
		{"steps", "minutesSedentary"},
		{"minutesLightlyActive", "minutesFairlyActive", "minutesVeryActive"},
	};
	public final Integer[][] defaultGraphColors = {
		{Color.DKGRAY},
		{Color.RED},
		{Color.BLUE, Color.GREEN},
		{Color.BLACK, Color.MAGENTA},
		{Color.BLUE, Color.RED, Color.BLACK},
	};
	public final String[] defaultGraphTitles = {
		"Calories",
		"Steps",
		"CaloriesBMR vs activityCalories",
		"Steps vs Minutes Sedentary",
		"Minutes of Activity",
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

			try {
				FitbitGraphView fgv = new FitbitGraphView(getContext(),query);
				// Navigate to the split graph/data view
				fgv.setOnClickListener(new View.OnClickListener()
				{
					public void onClick(View v)
					{
						Intent submitPage = new Intent(getContext(), QueryActivity.class);
						submitPage.putExtra("startDate", "N/A");
						submitPage.putExtra("endDate", "N/A");
						submitPage.putExtra("item_1", "N/A");
						submitPage.putExtra("item_2", "N/A");
						Objects.requireNonNull(getContext()).startActivity(submitPage);
					}
				});
				ll.addView(fgv, 0);
			}
			catch (Exception e) {
				Toast.makeText(this.getContext(), e.toString(), Toast.LENGTH_LONG).show();
				e.printStackTrace();
			}
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
