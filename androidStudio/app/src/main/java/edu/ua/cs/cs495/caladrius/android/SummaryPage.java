package edu.ua.cs.cs495.caladrius.android;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import edu.ua.cs.cs495.caladrius.android.graphData.GraphContract.GraphEntry;

import java.util.Objects;

/**
 * The SummaryPage module represents the main View that is exposed upon logging into the application.
 * It contains FitbitGraphView instances as well as a button to view all of the data for a given user.
 */
public class SummaryPage extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, NavigationView.OnNavigationItemSelectedListener
{

	private GraphCursorAdapter mCursorAdapter;
	private static final int GRAPH_LOADER = 0;
	public SummaryPage()
	{
		// Empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		View view = inflater.inflate(R.layout.summary_page, container, false);

		Toolbar toolbar = view.findViewById(R.id.summary_page_toolbar);
		((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(toolbar);


		DrawerLayout drawer = view.findViewById(R.id.drawer_layout);


		ActionBarDrawerToggle toggle =
			new ActionBarDrawerToggle(getActivity(), drawer, toolbar,
				R.string.navigation_drawer_open,
				R.string.navigation_drawer_close);
		drawer.addDrawerListener(toggle);
		toggle.syncState();

		NavigationView navigationView = view.findViewById(R.id.nav_view);
		navigationView.setNavigationItemSelectedListener(this);


		ListView graphListView = view.findViewById(R.id.graph_list);

		mCursorAdapter = new GraphCursorAdapter(getContext(), null, 1);
		graphListView.setAdapter(mCursorAdapter);

		getLoaderManager().initLoader(GRAPH_LOADER, null, this);

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


	// Menu icons are inflated just as they were with actionbar
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.menu_main, menu);
		super.onCreateOptionsMenu(menu,inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.calender:
			Intent queryIntent = new Intent(getContext(),
				GraphEditorActivity.class);
			queryIntent.putExtra("query_flag", 1);
			startActivity(queryIntent);
			return true;
		case R.id.edit:
			Intent editIntent = new Intent(getContext(),
				ListTest.class);
			startActivity(editIntent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}


	@SuppressWarnings("StatementWithEmptyBody")
	@Override
	public boolean onNavigationItemSelected(MenuItem item) {
		// Handle navigation view item clicks here.
		int id = item.getItemId();

		if (id == R.id.nav_alldata) {
			Intent editIntent = new Intent(getContext(),
				AllData.class);
			startActivity(editIntent);
		} else if (id == R.id.nav_logout) {

		} else if (id == R.id.nav_menu_info) {

		}

		DrawerLayout drawer = getActivity().findViewById(R.id.drawer_layout);
		drawer.closeDrawer(GravityCompat.START);
		return true;
	}
}
