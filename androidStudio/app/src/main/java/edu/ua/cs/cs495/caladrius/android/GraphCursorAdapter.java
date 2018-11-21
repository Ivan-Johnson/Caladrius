package edu.ua.cs.cs495.caladrius.android;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import edu.ua.cs.cs495.caladrius.android.graphData.GraphContract.GraphEntry;
import org.json.JSONException;

import static edu.ua.cs.cs495.caladrius.android.Caladrius.getContext;

/**
 * This is a cursor adapter for update graph list value by getting value from graph setting table.
 *
 * @author Hansheng Li
 */
public class GraphCursorAdapter extends CursorAdapter {

    private FitbitGraphView.GraphViewGraph getGraphType(int graphType){
        if (graphType == GraphEntry.BAR_GRAPH){
            return FitbitGraphView.GraphViewGraph.BarGraph;
        } else if (graphType == GraphEntry.LINE_GRAPH){
            return FitbitGraphView.GraphViewGraph.LineGraph;
        } else if (graphType == GraphEntry.POINTS_GRAPH){
            return FitbitGraphView.GraphViewGraph.PointsGraph;
        }
        return FitbitGraphView.GraphViewGraph.BarGraph;
    }

    private int GetColour(Integer selection){
        if (selection == GraphEntry.COLOR_BLACK) {
            return Color.BLACK;
        } else if (selection == GraphEntry.COLOR_BLUE) {
            return Color.BLUE;
        } else if (selection == GraphEntry.COLOR_CYAN) {
            return Color.CYAN;
        } else if (selection == GraphEntry.COLOR_GRAY) {
            return Color.GRAY;
        } else if (selection == GraphEntry.COLOR_GREEN) {
            return Color.GREEN;
        } else if (selection == GraphEntry.COLOR_RED) {
            return Color.RED;
        } else if (selection == GraphEntry.COLOR_YELLOW) {
            return Color.YELLOW;
        }
        return Color.DKGRAY;
    }

    public GraphCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.graph_list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        // Find the columns of graph attributes that we're interested in
        int timeRangeColumnIndex = cursor.getColumnIndex(GraphEntry.COLUMN_GRAPH_TIME_RANGE);
        int typeColumnIndex = cursor.getColumnIndex(GraphEntry.COLUMN_GRAPH_TYPE);
        int statsColumnIndex = cursor.getColumnIndex(GraphEntry.COLUMN_GRAPH_STATS);
        int colorColumnIndex = cursor.getColumnIndex(GraphEntry.COLUMN_GRAPH_COLORS);
        int titleColumnIndex = cursor.getColumnIndex(GraphEntry.COLUMN_GRAPH_TITLE);
        int idColumnIndex = cursor.getColumnIndex(GraphEntry._ID);

        // Read the graph attributes from the Cursor for the current graph
        String graphTimeRange = cursor.getString(timeRangeColumnIndex);
        final String graphType = cursor.getString(typeColumnIndex);
        final String graphStats = cursor.getString(statsColumnIndex);
        final String graphColor = cursor.getString(colorColumnIndex);
        String graphTitle = cursor.getString(titleColumnIndex);
        final String graphId = cursor.getString(idColumnIndex);


        List<String> timeRangeList = Arrays.asList(context.getResources().getStringArray(R.array.array_time_range_options));

        List<String> typeList = Arrays.asList(context.getResources().getStringArray(R.array.array_graph_type_options));


        final List<String> statsList = Arrays.asList(context.getResources().getStringArray(R.array.array_graph_stats_options));


        List<String> colorList = Arrays.asList(context.getResources().getStringArray(R.array.array_graph_color_options));


        LinearLayout graph_container = view.findViewById(R.id.graph_container);

        ArrayList<FitbitGraphView.GraphViewGraph> graphTypes =
                new ArrayList<FitbitGraphView.GraphViewGraph>(){{
                    add(getGraphType(Integer.valueOf(graphType)));
        }};

        ArrayList<String> stats = new ArrayList<String>(){{
            add(statsList.get(Integer.valueOf(graphStats)));
        }};

        ArrayList<Integer> color = new ArrayList<Integer>(){{
            add(GetColour(Integer.valueOf(graphColor)));
        }};

        Query query = new Query(graphTypes,
                                stats,
                                color,
                                graphTitle);

        FitbitGraphView fgv = null;
        try {
            fgv = new FitbitGraphView(getContext(), query);

            fgv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getContext(), GraphEditorActivity.class);

                    Uri currentPetUri = ContentUris.withAppendedId(
                            GraphEntry.CONTENT_URI, Integer.valueOf(graphId));

                    intent.setData(currentPetUri);

                    getContext().startActivity(intent);
                }
            });
        } catch (JSONException | InterruptedException | ExecutionException | IOException e) {
            e.printStackTrace();
        }

        if((graph_container).getChildCount() > 0)
            (graph_container).removeAllViews();
        graph_container.addView(fgv);

        TextView timeRangeTextView = view.findViewById(R.id.time_range);
        timeRangeTextView.setText(timeRangeList.get(Integer.valueOf(graphTimeRange)));

        TextView typeTextView = view.findViewById(R.id.graph_type);
        typeTextView.setText(typeList.get(Integer.valueOf(graphType)));

        TextView statsTextView = view.findViewById(R.id.graph_stats);
        statsTextView.setText(statsList.get(Integer.valueOf(graphStats)));

        TextView colorTextView = view.findViewById(R.id.graph_color);
        colorTextView.setText(colorList.get(Integer.valueOf(graphColor)));

        TextView titleTextView = view.findViewById(R.id.graph_title);
        titleTextView.setText(graphTitle);



    }
}