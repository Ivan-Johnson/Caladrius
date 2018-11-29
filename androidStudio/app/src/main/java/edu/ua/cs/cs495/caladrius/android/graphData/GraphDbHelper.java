package edu.ua.cs.cs495.caladrius.android.graphData;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import edu.ua.cs.cs495.caladrius.android.graphData.GraphContract.GraphEntry;

/**
 * This class help to build sql database and crate a table for graph setting
 *
 * @author Hansheng Li
 */
public class GraphDbHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = GraphDbHelper.class.getSimpleName();

    /** Name of the database file */
    private static final String DATABASE_NAME = "caladrius.db";

    /**
     * Database version. If you change the database schema, you must increment the database version.
     */
    public static final int DATABASE_VERSION = 1;

    public GraphDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // Create a String that contains the SQL statement to create the pets table
        String SQL_CREATE_GRAPHS_TABLE = "CREATE TABLE " + GraphEntry.TABLE_NAME + " ("
                + GraphEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + GraphEntry.COLUMN_GRAPH_TIME_RANGE + " INTEGER NOT NULL, "
                + GraphEntry.COLUMN_GRAPH_TYPE + " INTEGER NOT NULL, "
                + GraphEntry.COLUMN_GRAPH_STATS + " INTEGER NOT NULL, "
                + GraphEntry.COLUMN_GRAPH_COLORS + " INTEGER NOT NULL, "
                + GraphEntry.COLUMN_GRAPH_TIME_RANGE_TYPE + " INTEGER NOT NULL, "
                + GraphEntry.COLUMN_NUMBER_OF_GRAPH + " INTEGER NOT NULL, "
                + GraphEntry.COLUMN_GRAPH2_COLORS + " INTEGER NOT NULL, "
                + GraphEntry.COLUMN_GRAPH2_STATS + " INTEGER NOT NULL, "
                + GraphEntry.COLUMN_GRAPH2_TYPE + " INTEGER NOT NULL, "
                + GraphEntry.COLUMN_GRAPH_START_TIME + " TEXT NOT NULL, "
                + GraphEntry.COLUMN_GRAPH_END_TIME + " TEXT NOT NULL, "
                + GraphEntry.COLUMN_GRAPH_TITLE + " TEXT);";

        // Execute the SQL statement
        db.execSQL(SQL_CREATE_GRAPHS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // db.execSQL(SQL_CREATE_PETS_TABLE);
    }
}
