package edu.ua.cs.cs495.caladrius.android;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Parcelable;
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

    private Integer mPosition;

    GraphCursorAdapter(Context context, Cursor c, Integer p) {
        super(context, c, 0 /* flags */);
        mPosition = p;
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
        int graph2TypeColumnIndex = cursor.getColumnIndex(GraphEntry.COLUMN_GRAPH2_TYPE);
        int graph2StatsColumnIndex = cursor.getColumnIndex(GraphEntry.COLUMN_GRAPH2_STATS);
        int graph2ColorColumnIndex = cursor.getColumnIndex(GraphEntry.COLUMN_GRAPH2_COLORS);
        int numberGraphsColumnIndex = cursor.getColumnIndex(GraphEntry.COLUMN_NUMBER_OF_GRAPH);
        int timeRangeTypeGraphsColumnIndex = cursor.getColumnIndex(GraphEntry.COLUMN_GRAPH_TIME_RANGE_TYPE);
        int startTimeColumnIndex = cursor.getColumnIndex(GraphEntry.COLUMN_GRAPH_START_TIME);
        int endTimeColumnIndex = cursor.getColumnIndex(GraphEntry.COLUMN_GRAPH_END_TIME);

        int idColumnIndex = cursor.getColumnIndex(GraphEntry._ID);

        // Read the graph attributes from the Cursor for the current graph
        String timeRangeTypeGraphs = cursor.getString(timeRangeTypeGraphsColumnIndex);
        String startTime = cursor.getString(startTimeColumnIndex);
        String endTime = cursor.getString(endTimeColumnIndex);
        String graphTimeRange = cursor.getString(timeRangeColumnIndex);
        final String graphType = cursor.getString(typeColumnIndex);
        final String graphStats = cursor.getString(statsColumnIndex);
        final String graphColor = cursor.getString(colorColumnIndex);
        final String graphTitle = cursor.getString(titleColumnIndex);
        final String graphId = cursor.getString(idColumnIndex);
        final String graph2Type = cursor.getString(graph2TypeColumnIndex);
        final String graph2Stats = cursor.getString(graph2StatsColumnIndex);
        final String graph2Color = cursor.getString(graph2ColorColumnIndex);
        final String numberOfGraph = cursor.getString(numberGraphsColumnIndex);


        List<String> timeRangeList = Arrays.asList(context.getResources().getStringArray(R.array.array_time_range_options));

        List<String> typeList = Arrays.asList(context.getResources().getStringArray(R.array.array_graph_type_options));


        final List<String> statsList = Arrays.asList(context.getResources().getStringArray(R.array.array_graph_stats_options));


        List<String> colorList = Arrays.asList(context.getResources().getStringArray(R.array.array_graph_color_options));

        LinearLayout graph_container = view.findViewById(R.id.graph_container);

        ArrayList<FitbitGraphView.GraphViewGraph> graphTypes =
                new ArrayList<FitbitGraphView.GraphViewGraph>(){{
                    add(GraphEntry.getGraphType(Integer.valueOf(graphType)));
        }};

        ArrayList<String> stats = new ArrayList<String>(){{
            add(statsList.get(Integer.valueOf(graphStats)));
        }};


        ArrayList<Integer> color = new ArrayList<Integer>(){{
            add(GraphEntry.GetColour(Integer.valueOf(graphColor)));
        }};

        if(Integer.valueOf(numberOfGraph) == GraphEntry.GRAPH_NUMBER_TWO){
            stats.add(statsList.get(Integer.valueOf(graph2Stats)));
            color.add(GraphEntry.GetColour(Integer.valueOf(graph2Color)));
            graphTypes.add(GraphEntry.getGraphType(Integer.valueOf(graph2Type)));
        }

        Query query = new Query(graphTypes,
                                stats,
                                color,
                                graphTitle,
                                startTime,
                                endTime,
                                Integer.valueOf(timeRangeTypeGraphs),
                                Integer.valueOf(graphTimeRange));


        FitbitGraphView fgv = null;
        try {
            fgv = new FitbitGraphView(getContext(), query);
            if (mPosition == 0){
                fgv.setOnClickListener(view1 ->
                {
                    Intent intent = new Intent(getContext(), GraphEditorActivity.class);

                    Uri currentPetUri = ContentUris.withAppendedId(
                            GraphEntry.CONTENT_URI, Integer.valueOf(graphId));

                    intent.setData(currentPetUri);

                    getContext().startActivity(intent);
                });
            } else {
                fgv.setOnClickListener(view1 ->
                {
                    Intent intent = new Intent(getContext(), QueryActivity.class);
                    intent.putExtra("startDate", startTime);
                    intent.putExtra("endDate", endTime);
                    intent.putExtra("graph_1_stats", Integer.valueOf(graphStats));
                    intent.putExtra("graph_2_stats", Integer.valueOf(graph2Stats));
                    intent.putExtra("num_graph", Integer.valueOf(numberOfGraph));
                    intent.putExtra("graph_1_color", GraphEntry.GetColour(Integer.valueOf(graphColor)));
                    intent.putExtra("graph_2_color", GraphEntry.GetColour(Integer.valueOf(graph2Color)));
                    intent.putExtra("graph_1_type", Integer.valueOf(graphType));
                    intent.putExtra("graph_2_type", Integer.valueOf(graph2Type));
                    intent.putExtra("time_range_type", Integer.valueOf(timeRangeTypeGraphs));
                    intent.putExtra("relative_time_type", Integer.valueOf(graphTimeRange));
                    intent.putExtra("graph_title", graphTitle);
                    getContext().startActivity(intent);
                });

            }
        } catch (JSONException | InterruptedException | ExecutionException | IOException e) {
            e.printStackTrace();
        }

        if((graph_container).getChildCount() > 0)
            (graph_container).removeAllViews();

        graph_container.addView(fgv);


    }
}