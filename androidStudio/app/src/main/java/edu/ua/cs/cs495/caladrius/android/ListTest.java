package edu.ua.cs.cs495.caladrius.android;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.Objects;

import edu.ua.cs.cs495.caladrius.android.graphData.GraphContract.GraphEntry;

/**
 * This just a test file for database to store graph settings.
 *
 * @author Hansheng Li
 */
public class ListTest extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {


    GraphCursorAdapter mCursorAdapter;
    private static final int GRAPH_LOADER = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_test);

        Toolbar myToolbar = findViewById(R.id.graph_list_toolbar);
        setSupportActionBar(myToolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Graph List");

        // Setup FAB to open EditorActivity
        FloatingActionButton fab = findViewById(R.id.add_graph);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListTest.this, GraphEditorActivity.class);
                startActivity(intent);
            }
        });

        ListView graphListView = findViewById(R.id.graph_list);

        mCursorAdapter = new GraphCursorAdapter(this, null);
        graphListView.setAdapter(mCursorAdapter);

        // Setup item click listener
        graphListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                Intent intent = new Intent(ListTest.this, GraphEditorActivity.class);

                Uri currentPetUri = ContentUris.withAppendedId(GraphEntry.CONTENT_URI, id);

                intent.setData(currentPetUri);

                startActivity(intent);
            }
        });

        getLoaderManager().initLoader(GRAPH_LOADER, null, this);
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
                        GraphEntry.COLUMN_GRAPH_TYPE};

                // Returns a new CursorLoader
                return new CursorLoader(
                        this,
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

    private void insertGraph() {
        ContentValues values = new ContentValues();
        values.put(GraphEntry.COLUMN_GRAPH_TIME_RANGE, GraphEntry.TIME_RANGE_TODAY);
        values.put(GraphEntry.COLUMN_GRAPH_TYPE, GraphEntry.BAR_GRAPH);
        values.put(GraphEntry.COLUMN_GRAPH_STATS, GraphEntry.STATS_BPM);
        values.put(GraphEntry.COLUMN_GRAPH_COLORS, GraphEntry.COLOR_BLACK);
        values.put(GraphEntry.COLUMN_GRAPH_TITLE, "Heart Rate");

        // Insert a new row for Toto into the provider using the ContentResolver.
        // Use the {@link GraphEntry#CONTENT_URI} to indicate that we want to insert
        // into the pets database table.
        // Receive the new content URI that will allow us to access Toto's data in the future.
        Uri newUri = getContentResolver().insert(GraphEntry.CONTENT_URI, values);
//                Intent intent = new Intent(ListTest.this, QueryEditor.class);
//                startActivity(intent);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_graph_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_insert_dummy_data:
                insertGraph();
                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_entries:
                getContentResolver().delete(
                        GraphEntry.CONTENT_URI, null, null);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
