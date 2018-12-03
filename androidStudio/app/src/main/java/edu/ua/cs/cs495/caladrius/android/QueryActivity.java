package edu.ua.cs.cs495.caladrius.android;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.Series;
import edu.ua.cs.cs495.caladrius.android.graphData.GraphContract;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static edu.ua.cs.cs495.caladrius.android.Caladrius.getContext;

/**
 * This class is for showing the result from custom query data from fitbit.
 *
 * @author PeterJackson
 */

public class QueryActivity extends AppCompatActivity
{
	@SuppressLint("SetTextI18n") //TODO delete this suppression once placeholder text is removed
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_graph);
		Toolbar myToolbar = findViewById(R.id.activity_graph_toolbar);
		setSupportActionBar(myToolbar);
		Objects.requireNonNull(getSupportActionBar()).setTitle("Query Activity");

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
					add(GraphContract.GraphEntry.getGraphType(Integer.valueOf(graphType)));
				}};

		ArrayList<String> stats = new ArrayList<String>(){{
			add(statsList.get(Integer.valueOf(graphStats)));
		}};


		ArrayList<Integer> color = new ArrayList<Integer>(){{
			add(GraphContract.GraphEntry.GetColour(Integer.valueOf(graphColor)));
		}};

		if(Integer.valueOf(numberOfGraph) == GraphContract.GraphEntry.GRAPH_NUMBER_TWO){
			stats.add(statsList.get(Integer.valueOf(graph2Stats)));
			color.add(GraphContract.GraphEntry.GetColour(Integer.valueOf(graph2Color)));
			graphTypes.add(GraphContract.GraphEntry.getGraphType(Integer.valueOf(graph2Type)));
		}

		Query query = new Query(graphTypes,
				stats,
				color,
				graphTitle,
				startTime,
				endTime,
				Integer.valueOf(timeRangeTypeGraphs),
				graphTimeRange);

		FitbitGraphView fgv = null;
		try {
			fgv = new FitbitGraphView(this, query);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		CardView cardView = findViewById(R.id.card_view);
		List<Series> seriesList = fgv.getSeries();
		for (int i = 0; i < seriesList.size(); i++) {
			//graphView.addSeries(seriesList.get(i));
		}

		cardView.addView(fgv);


		final TextView queryInfoTextView = findViewById(R.id.queryInfo);
		queryInfoTextView.setText(
			"***QueryInfo***" +
				"\nStart date     : " + startTime +
				"\nEnd date     : " + endTime +
				"\ngraph_1_stats     :  " + graphStats +
				"\ngraph_2_stats     :  " + graph2Stats +
				"\nnum_graph     : " + numberOfGraph +
				"\ngraph_1_color     : " + graphColor +
				"\ngraph_2_color     : " + graph2Color +
				"\ngraph_1_type     : " + graphType +
				"\ngraph_2_type     : " + graph2Type +
				"\ntime_range_type     : " + timeRangeTypeGraphs +
				"\nrelative_time_type     : " + graphTimeRange);
	}
}
