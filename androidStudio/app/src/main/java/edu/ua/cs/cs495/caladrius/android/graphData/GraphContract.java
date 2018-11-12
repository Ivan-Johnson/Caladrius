package edu.ua.cs.cs495.caladrius.android.graphData;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

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

        public static final int BAR_GRAPH = 0;
        public static final int POINTS_GRAPH = 1;
        public static final int LINE_GRAPH = 2;


        public static final int TIME_RANGE_TODAY= 0;
        public static final int TIME_RANGE_WEEK= 1;
        public static final int TIME_RANGE_MONTH= 2;
        public static final int TIME_RANGE_YEAR= 3;

        public static final int STATS_BPM = 0;
        public static final int STATS_STEPS = 1;
        public static final int STATS_CALORIC = 2;

        public static final int COLOR_BLACK = 0;
        public static final int COLOR_BLUE = 1;
        public static final int COLOR_CYAN = 2;
        public static final int COLOR_GRAY = 3;
        public static final int COLOR_GREEN = 4;
        public static final int COLOR_RED = 5;
        public static final int COLOR_YELLOW = 6;



//        public static boolean isValidGraphType(int gender) {
//            return gender == GENDER_UNKNOWN || gender == GENDER_MALE || gender == GENDER_FEMALE;
//        }
    }
}
