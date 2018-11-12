package edu.ua.cs.cs495.caladrius.android;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.ua.cs.cs495.caladrius.android.graphData.GraphContract.GraphEntry;

import static edu.ua.cs.cs495.caladrius.android.Caladrius.getContext;

public class GraphCursorAdapter extends CursorAdapter {

    public GraphCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.graph_list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        LinearLayout graphList = view.findViewById(R.id.graph_item);


        // Find the columns of graph attributes that we're interested in
        int timeRangeColumnIndex = cursor.getColumnIndex(GraphEntry.COLUMN_GRAPH_TIME_RANGE);
        int typeColumnIndex = cursor.getColumnIndex(GraphEntry.COLUMN_GRAPH_TYPE);
        int statsColumnIndex = cursor.getColumnIndex(GraphEntry.COLUMN_GRAPH_STATS);
        int colorColumnIndex = cursor.getColumnIndex(GraphEntry.COLUMN_GRAPH_COLORS);
        int titleColumnIndex = cursor.getColumnIndex(GraphEntry.COLUMN_GRAPH_TITLE);

        // Read the graph attributes from the Cursor for the current graph
        String graphtimerange = cursor.getString(timeRangeColumnIndex);
        String graphtype = cursor.getString(typeColumnIndex);
        String graphstats = cursor.getString(statsColumnIndex);
        String graphcolor = cursor.getString(colorColumnIndex);
        String graphtitle = cursor.getString(titleColumnIndex);

//        FitbitGraphView.GraphViewGraph[] defaultGraphTypes =
//                {FitbitGraphView.GraphViewGraph.BarGraph};
//
//        String[] defaultGraphStats = {"Heart Rate"};
//
//        ArrayList<FitbitGraphView.GraphViewGraph> graphTypes =
//                new ArrayList<>(Arrays.asList(defaultGraphTypes));
//
//        Integer[] defaultGraphColors = {Color.CYAN};
//
//        String[] defaultGraphTitles = {"Heart Rate"};
//
//        ArrayList<String> stats = new ArrayList<>(Arrays.asList(defaultGraphStats));
//
//        ArrayList<Integer> color = new ArrayList<>(Arrays.asList(defaultGraphColors));
//
//        String title = defaultGraphTitles[0];
//
//        Query query = new Query(graphTypes,
//                stats,
//                color,
//                title);
//
//        FitbitGraphView fgv = new FitbitGraphView(getContext(),
//                query
//        );
//
//        graphList.addView(fgv, 0);

//        List<String> timeRangeArrayList = Arrays.asList(Resources.getSystem().getStringArray(R.array.array_time_range_options));

//        String[] timeRangeArrayList = Resources.getSystem().getStringArray(R.array.array_time_range_options);

        List<String> timeRangeList = Arrays.asList(context.getResources().getStringArray(R.array.array_time_range_options));


        List<String> typeList = Arrays.asList(context.getResources().getStringArray(R.array.array_graph_type_options));


        List<String> statsList = Arrays.asList(context.getResources().getStringArray(R.array.array_graph_stats_options));


        List<String> colorList = Arrays.asList(context.getResources().getStringArray(R.array.array_graph_color_options));


        TextView timeRangeTextView = view.findViewById(R.id.time_range);
        timeRangeTextView.setText(timeRangeList.get(Integer.valueOf(graphtimerange)));

        TextView typeTextView = view.findViewById(R.id.graph_type);
        typeTextView.setText(typeList.get(Integer.valueOf(graphtype)));

        TextView statsTextView = view.findViewById(R.id.graph_stats);
        statsTextView.setText(statsList.get(Integer.valueOf(graphstats)));

        TextView colorTextView = view.findViewById(R.id.graph_color);
        colorTextView.setText(colorList.get(Integer.valueOf(graphcolor)));

        TextView titleTextView = view.findViewById(R.id.graph_title);
        titleTextView.setText(graphtitle);

    }
}