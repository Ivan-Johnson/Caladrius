package edu.ua.cs.cs495.caladrius.android;

import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import edu.ua.cs.cs495.caladrius.android.graphData.GraphContract.GraphEntry;

import java.util.Objects;

/**
 * The SummaryPage module represents the main View that is exposed upon logging into the application.
 * It contains FitbitGraphView instances as well as a button to view all of the data for a given user.
 */
public class SummaryPage extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>
{
//	public final FitbitGraphView.GraphViewGraph[][] defaultGraphTypes = {
//		{FitbitGraphView.GraphViewGraph.BarGraph},
//		{FitbitGraphView.GraphViewGraph.PointsGraph},
//		{FitbitGraphView.GraphViewGraph.PointsGraph, FitbitGraphView.GraphViewGraph.PointsGraph},
//		{FitbitGraphView.GraphViewGraph.BarGraph, FitbitGraphView.GraphViewGraph.BarGraph},
//		{FitbitGraphView.GraphViewGraph.BarGraph, FitbitGraphView.GraphViewGraph.LineGraph,
//			FitbitGraphView.GraphViewGraph.PointsGraph},
//	};
//	public final String[][] defaultGraphStats = {
//		{"calories"},
//		{"steps"},
//		{"caloriesBMR", "activityCalories"},
//		{"steps", "minutesSedentary"},
//		{"minutesLightlyActive", "minutesFairlyActive", "minutesVeryActive"},
//	};
//	public final Integer[][] defaultGraphColors = {
//		{Color.DKGRAY},
//		{Color.RED},
//		{Color.BLUE, Color.GREEN},
//		{Color.BLACK, Color.MAGENTA},
//		{Color.BLUE, Color.RED, Color.BLACK},
//	};
//	public final String[] defaultGraphTitles = {
//		"Calories",
//		"Steps",
//		"CaloriesBMR vs activityCalories",
//		"Steps vs Minutes Sedentary",
//		"Minutes of Activity",
//	};

	GraphCursorAdapter mCursorAdapter;
	private static final int GRAPH_LOADER = 0;
	public SummaryPage()
	{
		// Empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		View view = inflater.inflate(R.layout.list_test, container, false);


		FloatingActionButton fab = view.findViewById(R.id.add_graph);
		fab.setVisibility(View.INVISIBLE);

		Toolbar myToolbar = view.findViewById(R.id.graph_list_toolbar);
		myToolbar.setVisibility(View.GONE);
		ListView graphListView = view.findViewById(R.id.graph_list);

		mCursorAdapter = new GraphCursorAdapter(getContext(), null);
		graphListView.setAdapter(mCursorAdapter);

		// Setup item click listener
		graphListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

				Intent intent = new Intent(getContext(), QueryActivity.class);
				intent.putExtra("startDate", "N/A");
				intent.putExtra("endDate", "N/A");
				intent.putExtra("item_1", "N/A");
				intent.putExtra("item_2", "N/A");
				Objects.requireNonNull(getContext()).startActivity(intent);

//				Uri currentPetUri = ContentUris.withAppendedId(GraphContract.GraphEntry.CONTENT_URI, id);
//
//				intent.setData(currentPetUri);
//
//				startActivity(intent);
			}
		});

		getLoaderManager().initLoader(GRAPH_LOADER, null, this);

//		ArrayList queries = new ArrayList();
//
//		assert defaultGraphStats.length == defaultGraphTypes.length;
//		for (int c = defaultGraphStats.length - 1; c >= 0; c--) {
//			ArrayList<FitbitGraphView.GraphViewGraph> graphTypes =
//					new ArrayList<>(Arrays.asList(defaultGraphTypes[c]));
//
//			ArrayList<String> stats = new ArrayList<>(Arrays.asList(defaultGraphStats[c]));
//
//			ArrayList<Integer> color = new ArrayList<>(Arrays.asList(defaultGraphColors[c]));
//
//			String title = defaultGraphTitles[c];
//
//			queries.add(new Query(graphTypes, stats, color, title));
//		}
//
//		alldata = new Button(getContext());
//		alldata.setText(R.string.view_all_data);
//		alldata.setTextSize(TypedValue.COMPLEX_UNIT_SP, 28);
//
//		SummaryPageAdapter.FitbitGraphViewOnClickAdapter onClick = new SummaryPageAdapter.FitbitGraphViewOnClickAdapter() {
//			@Override
//			public void onClick(FitbitGraphView fgv) {
//				Intent submitPage = new Intent(getContext(), QueryActivity.class);
//				submitPage.putExtra("startDate", "N/A");
//				submitPage.putExtra("endDate", "N/A");
//				submitPage.putExtra("item_1", "N/A");
//				submitPage.putExtra("item_2", "N/A");
//				Objects.requireNonNull(getContext()).startActivity(submitPage);
//			}
//		};
//
//		ListView ll = new ListView(getContext());
//		try {
//			ll.setAdapter(new SummaryPageAdapter(ll.getContext(), queries, onClick, alldata));
//		} catch (JSONException | InterruptedException | IOException | ExecutionException e) {
//			TextView tv = new TextView(ll.getContext());
//			tv.setText("Caladrius failed to construct the summary page adapter; "+e.getLocalizedMessage());
//			ll.addView(tv);
//		}
//
		return view;
	}



	@Override
	public Loader<Cursor> onCreateLoader(int loaderID, Bundle bundle) {
		/*
		 * Takes action based on the ID of the Loader that's being created
		 */
		switch (loaderID) {
		case GRAPH_LOADER:
			// Define a projection that specifies which columns from the database
			// you will actually use after this query.
			String[] projection = {
				GraphEntry._ID,
				GraphEntry.COLUMN_GRAPH_TIME_RANGE,
				GraphEntry.COLUMN_GRAPH_COLORS,
				GraphEntry.COLUMN_GRAPH_STATS,
				GraphEntry.COLUMN_GRAPH_TITLE,
				GraphEntry.COLUMN_GRAPH_TYPE,
				GraphEntry.COLUMN_NUMBER_OF_GRAPH,
				GraphEntry.COLUMN_GRAPH2_TYPE,
				GraphEntry.COLUMN_GRAPH2_STATS,
				GraphEntry.COLUMN_GRAPH2_COLORS,
				GraphEntry.COLUMN_GRAPH_TIME_RANGE_TYPE,
				GraphEntry.COLUMN_GRAPH_START_TIME,
				GraphEntry.COLUMN_GRAPH_END_TIME
			};

			// Returns a new CursorLoader
			return new CursorLoader(
				Objects.requireNonNull(getContext()),
				GraphEntry.CONTENT_URI,   // The content URI of the words table
				projection,         // The columns to return for each row
				null,      // Selection criteria
				null,  // Selection criteria
				null);    // The sort order for the returned rows

		default:
			// An invalid id was passed in
			return null;
		}
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

		mCursorAdapter.swapCursor(data);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {

		/*
		 * Clears out the adapter's reference to the Cursor.
		 * This prevents memory leaks.
		 */
		mCursorAdapter.swapCursor(null);
	}

//	@Override
//	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//		super.onActivityCreated(savedInstanceState);
//		alldata.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View view) {
//				Intent nextScreen = new Intent(SummaryPage.this.getContext(), AllData.class);
//				startActivity(nextScreen);
//			}
//		});
//	}
}
