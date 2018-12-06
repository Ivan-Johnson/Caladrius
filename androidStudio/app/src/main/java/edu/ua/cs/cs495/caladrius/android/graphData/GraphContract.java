package edu.ua.cs.cs495.caladrius.android.graphData;

import android.content.ContentResolver;
import android.graphics.Color;
import android.net.Uri;
import android.provider.BaseColumns;

import edu.ua.cs.cs495.caladrius.android.FitbitGraphView;

/**
 * This class store all the String, URI and attributes values.
 *
 * @author Hansheng Li
 */
public final class GraphContract {

    private GraphContract(){}

    /**
     * The "Content authority" is a name for the entire content provider, similar to the
     * relationship between a domain name and its website.  A convenient string to use for the
     * content authority is the package name for the app, which is guaranteed to be unique on the
     * device.
     */
    public static final String CONTENT_AUTHORITY = "edu.ua.cs.cs495.caladrius.android";

    /**
     * Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
     * the content provider.
     */
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    /**
     * Possible path (appended to base content URI for possible URI's)
     * For instance, content://edu.ua.cs.cs495.caladrius.android/graphs/ is a valid path for
     * looking at graph data. content://edu.ua.cs.cs495.caladrius.android/staff/ will fail,
     * as the ContentProvider hasn't been given any information on what to do with "staff".
     */
    public static final String PATH_GRAPHS = "graphs";

    public static final class GraphEntry implements BaseColumns{

        /** The content URI to access the graph data in the provider */
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_GRAPHS);

        /**
         * The MIME type of the {@link #CONTENT_URI} for a list of graphs.
         */
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_GRAPHS;

        /**
         * The MIME type of the {@link #CONTENT_URI} for a single graph.
         */
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_GRAPHS;


        /** Name of database table for graphs */
        public final static String TABLE_NAME = "graphs";


        public final static String _ID = BaseColumns._ID;
        /**
         * Type: TEXT
         */
        public final static String COLUMN_GRAPH_TIME_RANGE ="time";
        public final static String COLUMN_GRAPH_TYPE ="type";
        public final static String COLUMN_GRAPH_STATS ="stats";
        public final static String COLUMN_GRAPH_COLORS ="colors";
        public final static String COLUMN_GRAPH_TITLE ="title";
        public final static String COLUMN_GRAPH_TIME_RANGE_TYPE ="timeRangeType";
        public final static String COLUMN_GRAPH2_TYPE ="type2";
        public final static String COLUMN_GRAPH2_STATS ="stats2";
        public final static String COLUMN_GRAPH2_COLORS ="colors2";
        public final static String COLUMN_NUMBER_OF_GRAPH ="graphNumber";
        public final static String COLUMN_GRAPH_START_TIME ="start";
        public final static String COLUMN_GRAPH_END_TIME ="end";

        public static final int BAR_GRAPH = 0;
        public static final int POINTS_GRAPH = 1;
        public static final int LINE_GRAPH = 2;

        public static final int POINTS_GRAPH_2 = 0;
        public static final int LINE_GRAPH_2 = 1;


        public static final int TIME_RANGE_TYPE_SINGLE = 0;
        public static final int TIME_RANGE_TYPE_SEVERAL = 1;
        public static final int TIME_RANGE_TYPE_RELATIVE = 2;



        public static final int GRAPH_NUMBER_ONE = 0;
        public static final int GRAPH_NUMBER_TWO = 1;


        public static final int TIME_RANGE_TODAY= 0;
        public static final int TIME_RANGE_WEEK= 1;
        public static final int TIME_RANGE_MONTH= 2;
        public static final int TIME_RANGE_YEAR= 3;

        public static final int STATS_MINUTESSEDENTARY = 0;
        public static final int STATS_STEPS = 1;
        public static final int STATS_CALORIC = 2;
        public static final int STATS_CALORIESBMR = 3;
        public static final int STATS_DISTANCE = 4;
        public static final int STATS_MINUTESLIGHTLYACTIVE = 5;
        public static final int STATS_MINUTESFAIRLYACTIVE = 6;
        public static final int STATS_MINUTESVERYACTIVE= 7;
        public static final int STATS_ACTIVITYCALORIES = 8;

        public static final int COLOR_BLACK = 0;
        public static final int COLOR_BLUE = 1;
        public static final int COLOR_CYAN = 2;
        public static final int COLOR_GRAY = 3;
        public static final int COLOR_GREEN = 4;
        public static final int COLOR_RED = 5;
        public static final int COLOR_YELLOW = 6;

        public static FitbitGraphView.GraphViewGraph getGraphType(int graphType){
            if (graphType == GraphEntry.BAR_GRAPH){
                return FitbitGraphView.GraphViewGraph.BarGraph;
            } else if (graphType == GraphEntry.LINE_GRAPH){
                return FitbitGraphView.GraphViewGraph.LineGraph;
            } else if (graphType == GraphEntry.POINTS_GRAPH){
                return FitbitGraphView.GraphViewGraph.PointsGraph;
            }
            return FitbitGraphView.GraphViewGraph.BarGraph;
        }

        public static int GetColour(Integer selection){
            if (selection == GraphEntry.COLOR_BLACK) {
                return Color.parseColor("#1e272e");
            } else if (selection == GraphEntry.COLOR_BLUE) {
                return Color.parseColor("#3498db");
            } else if (selection == GraphEntry.COLOR_CYAN) {
                return Color.parseColor("#00BCD4");
            } else if (selection == GraphEntry.COLOR_GRAY) {
                return Color.parseColor("#808e9b");
            } else if (selection == GraphEntry.COLOR_GREEN) {
                return Color.parseColor("#2ecc71");
            } else if (selection == GraphEntry.COLOR_RED) {
                return Color.parseColor("#e74c3c");
            } else if (selection == GraphEntry.COLOR_YELLOW) {
                return Color.parseColor("#f0932b");
            }
            return Color.parseColor("#1e272e");
        }

    }
}
