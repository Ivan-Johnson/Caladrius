package edu.ua.cs.cs495.caladrius.android.graphData;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import edu.ua.cs.cs495.caladrius.android.graphData.GraphContract.GraphEntry;

/**
 * This class is for insert, edit, delete function for graph setting table,
 * and make sure all the input was valid.
 *
 * @author Hansheng Li
 */
public class GraphProvider extends ContentProvider {

    /** Tag for the log messages */
    public static final String LOG_TAG = GraphEntry.class.getSimpleName();

    private GraphDbHelper mDbHelper;

    private static final int GRAPHS = 100;
    private static final int GRAPH_ID = 101;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(GraphContract.CONTENT_AUTHORITY, GraphContract.PATH_GRAPHS, GRAPHS);
        sUriMatcher.addURI(GraphContract.CONTENT_AUTHORITY, GraphContract.PATH_GRAPHS+"/#", GRAPH_ID);

    }

    /**
     * Initialize the provider and the database helper object.
     */
    @Override
    public boolean onCreate() {

        mDbHelper = new GraphDbHelper(getContext());
        // Make sure the variable is a global variable, so it can be referenced from other
        // ContentProvider methods.
        return true;
    }

    /**
     * Perform the query for the given URI. Use the given projection, selection, selection arguments, and sort order.
     */
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        // Get readable database
        SQLiteDatabase database = mDbHelper.getReadableDatabase();

        // This cursor will hold the result of the query
        Cursor cursor;

        // Figure out if the URI matcher can match the URI to a specific code
        int match = sUriMatcher.match(uri);
        switch (match) {
            case GRAPHS:
                // For the GRAPHS code, query the GRAPHS table directly with the given
                // projection, selection, selection arguments, and sort order. The cursor
                // could contain multiple rows of the GRAPHS table.
                cursor = database.query(GraphEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);

                break;
            case GRAPH_ID:
                // For the GRAPH_ID code, extract out the ID from the URI.
                // For an example URI such as "content://com.example.android.GRAPHS/GRAPHS/3",
                // the selection will be "_id=?" and the selection argument will be a
                // String array containing the actual ID of 3 in this case.
                //
                // For every "?" in the selection, we need to have an element in the selection
                // arguments that will fill in the "?". Since we have 1 question mark in the
                // selection, we have 1 String in the selection arguments' String array.
                selection = GraphEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };

                // This will perform a query on the GRAPHS table where the _id equals 3 to return a
                // Cursor containing that row of the table.
                cursor = database.query(GraphEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    /**
     * Insert new data into the provider with the given ContentValues.
     */
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case GRAPHS:
                return insertGraph(uri, contentValues);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    /**
     * Insert a graph into the database with the given content values. Return the new content URI
     * for that specific row in the database.
     */
    private Uri insertGraph(Uri uri, ContentValues values) {

        // Check that the type is not null
        String timeRange = values.getAsString(GraphEntry.COLUMN_GRAPH_TIME_RANGE);
        if (timeRange == null) {
            throw new IllegalArgumentException("Graph requires time range");
        }

        // Check that the type is not null
        String type = values.getAsString(GraphEntry.COLUMN_GRAPH_TYPE);
        if (type == null) {
            throw new IllegalArgumentException("Graph requires a type");
        }

        // Check that the stats is not null
        String stats = values.getAsString(GraphEntry.COLUMN_GRAPH_STATS);
        if (stats == null) {
            throw new IllegalArgumentException("Graph requires a stats");
        }

        // Check that the color is not null
        String color = values.getAsString(GraphEntry.COLUMN_GRAPH_COLORS);
        if (color == null) {
            throw new IllegalArgumentException("Graph requires a color");
        }

        // Check that the title is not null
        String title = values.getAsString(GraphEntry.COLUMN_GRAPH_TITLE);
        if (title == null) {
            throw new IllegalArgumentException("Graph requires a title");
        }

        // No need to check the breed, any value is valid (including null).

        // Get writeable database
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        // Insert the new graph with the given values
        long id = database.insert(GraphEntry.TABLE_NAME, null, values);
        // If the ID is -1, then the insertion failed. Log an error and return null.
        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }

        getContext().getContentResolver().notifyChange(uri, null);

        // Once we know the ID of the new row in the table,
        // return the new URI with the ID appended to the end of it
        return ContentUris.withAppendedId(uri, id);
    }


    /**
     * Updates the data at the given selection and selection arguments, with the new ContentValues.
     */
    @Override
    public int update(Uri uri, ContentValues contentValues, String selection,
                      String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case GRAPHS:
                return updateGraph(uri, contentValues, selection, selectionArgs);
            case GRAPH_ID:
                // For the GRAPH_ID code, extract out the ID from the URI,
                // so we know which row to update. Selection will be "_id=?" and selection
                // arguments will be a String array containing the actual ID.
                selection = GraphEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                return updateGraph(uri, contentValues, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }

    /**
     * Update GRAPHS in the database with the given content values. Apply the changes to the rows
     * specified in the selection and selection arguments (which could be 0 or 1 or more GRAPHS).
     * Return the number of rows that were successfully updated.
     */
    private int updateGraph(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        // check that the name type is not null.
        if (values.containsKey(GraphEntry.COLUMN_GRAPH_TIME_RANGE)) {
            String timeRange = values.getAsString(GraphEntry.COLUMN_GRAPH_TIME_RANGE);
            if (timeRange == null) {
                throw new IllegalArgumentException("Graph requires a timeRange");
            }
        }

        // check that the name type is not null.
        if (values.containsKey(GraphEntry.COLUMN_GRAPH_TYPE)) {
            String type = values.getAsString(GraphEntry.COLUMN_GRAPH_TYPE);
            if (type == null) {
                throw new IllegalArgumentException("Graph requires a type");
            }
        }

        // check that the name value is not null.
        if (values.containsKey(GraphEntry.COLUMN_GRAPH_STATS)) {
            String stats = values.getAsString(GraphEntry.COLUMN_GRAPH_STATS);
            if (stats == null) {
                throw new IllegalArgumentException("Graph requires a stats");
            }
        }

        // check that the colors value is not null.
        if (values.containsKey(GraphEntry.COLUMN_GRAPH_COLORS)) {
            String colors = values.getAsString(GraphEntry.COLUMN_GRAPH_COLORS);
            if (colors == null) {
                throw new IllegalArgumentException("Graph requires a colors");
            }
        }

        // check that the title value is not null.
        if (values.containsKey(GraphEntry.COLUMN_GRAPH_TITLE)) {
            String title = values.getAsString(GraphEntry.COLUMN_GRAPH_TITLE);
            if (title == null) {
                throw new IllegalArgumentException("Graph requires a title");
            }
        }
        // If there are no values to update, then don't try to update the database
        if (values.size() == 0) {
            return 0;
        }

        // Otherwise, get writeable database to update the data
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        // Perform the update on the database and get the number of rows affected
        int rowsUpdated = database.update(GraphEntry.TABLE_NAME, values, selection, selectionArgs);

        // If 1 or more rows were updated, then notify all listeners that the data at the
        // given URI has changed
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        // Return the number of rows updated
        return rowsUpdated;
    }


    /**
     * Delete the data at the given selection and selection arguments.
     */
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Get writeable database
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        // Track the number of rows that were deleted
        int rowsDeleted;

        final int match = sUriMatcher.match(uri);
        switch (match) {
            case GRAPHS:
                // Delete all rows that match the selection and selection args
                rowsDeleted = database.delete(GraphEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case GRAPH_ID:
                // Delete a single row given by the ID in the URI
                selection = GraphEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                rowsDeleted = database.delete(GraphEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }


        // If 1 or more rows were deleted, then notify all listeners that the data at the
        // given URI has changed
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        // Return the number of rows deleted
        return rowsDeleted;
    }

    /**
     * Returns the MIME type of data for the content URI.
     */
    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case GRAPHS:
                return GraphEntry.CONTENT_LIST_TYPE;
            case GRAPH_ID:
                return GraphEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
    }

}