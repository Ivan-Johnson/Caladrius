package edu.ua.cs.cs495.caladrius.android;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.*;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.Series;
import edu.ua.cs.cs495.caladrius.android.graphData.GraphContract;
import org.json.JSONArray;

import java.text.SimpleDateFormat;
import java.util.*;

import static edu.ua.cs.cs495.caladrius.android.Caladrius.getContext;

/**
 * This class is for showing the result from custom query data from fitbit.
 *
 * @author PeterJackson
 */

public class QueryActivity extends AppCompatActivity {

	private RecyclerView mRecyclerView;
	private QueryAdapter mRecyclerViewAdapter;
	private RecyclerView.LayoutManager mLayoutManager;

	/**
	 *
	 * @param savedInstanceState Restores saved state
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_graph);
		Toolbar myToolbar = findViewById(R.id.activity_graph_toolbar);
		setSupportActionBar(myToolbar);
		Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.query_activity_title);

		if(getSupportActionBar() != null){
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
			getSupportActionBar().setDisplayShowHomeEnabled(true);
		}
		Intent intent = getIntent();


		// Read the graph attributes from the Cursor for the current graph
		// 0->single day 1->several day 2->relative day
		int timeRangeTypeGraphs = intent.getExtras()
				.getInt("time_range_type");
		String startTime = intent.getExtras()
				.getString("startDate");
		String endTime = intent.getExtras()
				.getString("endDate");
		int graphTimeRange = intent.getExtras()
				.getInt("relative_time_type");
		final int graphType = intent.getExtras()
				.getInt("graph_1_type");
		final int graphStats = intent.getExtras()
				.getInt("graph_1_stats");
		final int graphColor = intent.getExtras()
				.getInt("graph_1_color");
		String graphTitle = intent.getExtras()
				.getString("graph_title");
		final int graph2Type = intent.getExtras()
				.getInt("graph_2_type");
		final int graph2Stats = intent.getExtras()
				.getInt("graph_2_stats");
		final int graph2Color = intent.getExtras()
				.getInt("graph_2_color");
		final int numberOfGraph = intent.getExtras()
				.getInt("num_graph");


		final List<String> statsList = Arrays.asList(this.getResources().getStringArray(R.array.array_graph_stats_options));

		ArrayList<FitbitGraphView.GraphViewGraph> graphTypes =
				new ArrayList<FitbitGraphView.GraphViewGraph>(){{
					add(GraphContract.GraphEntry.getGraphType(graphType));
				}};

		ArrayList<String> stats = new ArrayList<String>(){{
			add(statsList.get(graphStats));
		}};


		ArrayList<Integer> color = new ArrayList<Integer>(){{
			add(graphColor);
		}};

		if(numberOfGraph == GraphContract.GraphEntry.GRAPH_NUMBER_TWO){
			stats.add(statsList.get(graph2Stats));
			color.add(graph2Color);
			graphTypes.add(GraphContract.GraphEntry.getGraphType(graph2Type));
		}

		Query query = new Query(graphTypes,
				stats,
				color,
				graphTitle,
				startTime,
				endTime,
				timeRangeTypeGraphs,
				graphTimeRange);

		FitbitGraphView fgv = null;
		try {
			query.horizontalZoomAndScroll = true;
			fgv = new FitbitGraphView(this, query);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		CardView cardView = findViewById(R.id.card_view);
		cardView.addView(fgv);
		final List<String> statsListPretty = Arrays.asList(this.getResources().getStringArray(R.array.array_graph_stats_options_pretty));

		String stat1_header = "";
		String stat2_header = "";
		ArrayList<DataPair> points = new ArrayList<>();
		try {
			JSONArray arr1 = Caladrius.fitbitInterface.getFitbitData(statsList.get(graphStats), timeRangeTypeGraphs, startTime, endTime, graphTimeRange);
			JSONArray arr2 = new JSONArray();
			final TextView headerValue1 = findViewById(R.id.header_value);
			headerValue1.setText(statsListPretty.get(graphStats));
			stat1_header = statsListPretty.get(graphStats);
			if(numberOfGraph == GraphContract.GraphEntry.GRAPH_NUMBER_TWO) {
				final TextView headerValue2 = findViewById(R.id.header_value2);
				headerValue2.setText(statsListPretty.get(graph2Stats));
				stat2_header = statsListPretty.get(graph2Stats);
				arr2 = Caladrius.fitbitInterface.getFitbitData(statsList.get(graph2Stats), timeRangeTypeGraphs, startTime, endTime, graphTimeRange);
                for (int x = 0; x < arr2.length(); x++) {
                    String dt = arr2.getJSONObject(x).getString("dateTime");
                    int vl = arr1.getJSONObject(x).getInt("value");
                    int v2 = arr2.getJSONObject(x).getInt("value");
                    DataPair dp = new DataPair(dt, vl, v2);

                    points.add(dp);
                }
			}
			else {
                for (int x = 0; x < arr1.length(); x++) {
                    String dt = arr1.getJSONObject(x).getString("dateTime");
                    int vl = arr1.getJSONObject(x).getInt("value");
                    DataPair dp = new DataPair(dt, vl);

                    points.add(dp);
                }
            }
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		Collections.sort(points, new SortbyDate());

		//initializeRecyclerView();
		//populateRecyclerView(points);

		//final TextView headerDate = findViewById(R.id.header_date);
		//headerDate.setText("Date");



		TableRow.LayoutParams wrapWrapTableRowParams = new TableRow.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		int[] fixedColumnWidths = new int[]{26, 34, 34};
		int[] scrollableColumnWidths = new int[]{26, 34, 34};
		int fixedRowHeight = 70;
		int fixedHeaderHeight = 80;

		TableRow row = new TableRow(this);
		//header (fixed vertically)
		TableLayout header = (TableLayout) findViewById(R.id.table_header);
		row.setLayoutParams(wrapWrapTableRowParams);
		row.setBackgroundColor(Color.GRAY);
		row.addView(makeTableRowWithText("Date", fixedColumnWidths[0], fixedHeaderHeight));
		row.addView(makeTableRowWithText(stat1_header, fixedColumnWidths[1], fixedHeaderHeight));
		row.addView(makeTableRowWithText(stat2_header, fixedColumnWidths[2], fixedHeaderHeight));
		header.addView(row);
		//header (fixed horizontally)
		TableLayout fixedColumn = (TableLayout) findViewById(R.id.fixed_column);
		//rest of the table (within a scroll view)
		TableLayout scrollablePart = (TableLayout) findViewById(R.id.scrollable_part);
		for(int i = 0; i < points.size(); i++) {
			TextView fixedView = makeTableRowWithText(points.get(i).getDate(), scrollableColumnWidths[0], fixedRowHeight);
			fixedView.setBackgroundColor(Color.LTGRAY);
			fixedColumn.addView(fixedView);
			row = new TableRow(this);
			row.setLayoutParams(wrapWrapTableRowParams);
			row.setGravity(Gravity.CENTER);
			row.addView(makeTableRowWithText(points.get(i).getValue(), scrollableColumnWidths[1], fixedRowHeight));
			if (!points.get(i).getValue2().equals("-1"))
				row.addView(makeTableRowWithText(points.get(i).getValue2(), scrollableColumnWidths[2], fixedRowHeight));
			scrollablePart.addView(row);
		}
	}



	//util method
	private TextView recyclableTextView;

	public TextView makeTableRowWithText(String text, int widthInPercentOfScreenWidth, int fixedHeightInPixels) {
		int screenWidth = getResources().getDisplayMetrics().widthPixels;
		recyclableTextView = new TextView(this);
		recyclableTextView.setText(text);
		recyclableTextView.setTextColor(Color.BLACK);
		recyclableTextView.setTextSize(20);
		recyclableTextView.setWidth(widthInPercentOfScreenWidth * screenWidth / 100);
		recyclableTextView.setHeight(fixedHeightInPixels);
		return recyclableTextView;
	}

	private void initializeRecyclerView() {
		mRecyclerView = findViewById(R.id.data_list);
		mRecyclerView.setHasFixedSize(true);
		mLayoutManager = new LinearLayoutManager(this);
		mRecyclerView.setLayoutManager(mLayoutManager);
		mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
	}


	public void populateRecyclerView(ArrayList<DataPair> dataPairs) {
		mRecyclerViewAdapter = new QueryAdapter(dataPairs);
		mRecyclerView.setAdapter(mRecyclerViewAdapter);
	}

	class DataPair {
		private String date;
		private int value;
        private int value2;

        DataPair(String d, int v) {
            date = d;
            value = v;
            value2 = -1;
        }

		DataPair(String d, int v, int v2) {
			date = d;
			value = v;
			value2 = v2;
		}

		public String getDate() {
			return date;
		}

		public String getValue() {
			return String.valueOf(value);
		}

        public String getValue2() {
            return String.valueOf(value2);
        }
    }

	class SortbyDate implements Comparator<DataPair>
	{
		public int compare(DataPair a, DataPair b) {
			return b.getDate().compareTo(a.getDate());
		}
	}
}
