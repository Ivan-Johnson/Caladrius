package edu.ua.cs.cs495.caladrius.android;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

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

		Intent intent = getIntent();
		String startDate = intent.getExtras()
		                         .getString("startDate");
		String endDate = intent.getExtras()
		                       .getString("endDate");
		int graph_1_status = intent.getExtras()
		                      .getInt("graph_1_status");
		int graph_2_status = intent.getExtras()
		                              .getInt("graph_2_status");
		int num_graph = intent.getExtras()
		                              .getInt("num_graph");
		int graph_1_color = intent.getExtras()
		                              .getInt("graph_1_color");
		int graph_2_color = intent.getExtras()
		                              .getInt("graph_2_color");
		int graph_1_type = intent.getExtras()
		                              .getInt("graph_1_type");
		int graph_2_type = intent.getExtras()
		                              .getInt("graph_2_type");
		// 0->single day 1->several day 2->relative day
		int time_range_type = intent.getExtras()
		                              .getInt("time_range_type");
		int relative_time_type = intent.getExtras()
		                              .getInt("relative_time_type");


		final TextView queryInfoTextView = findViewById(R.id.queryInfo);
		queryInfoTextView.setText(
			"***QueryInfo***" +
				"\nStart date     : " + startDate +
				"\nEnd date     : " + endDate +
				"\ngraph_1_status     :  " + graph_1_status +
				"\ngraph_2_status     :  " + graph_2_status +
				"\nnum_graph     : " + num_graph +
				"\ngraph_1_color     : " + graph_1_color +
				"\ngraph_2_color     : " + graph_2_color +
				"\ngraph_1_type     : " + graph_1_type +
				"\ngraph_2_type     : " + graph_2_type +
				"\ntime_range_type     : " + time_range_type +
				"\nrelative_time_type     : " + relative_time_type);
	}
}
