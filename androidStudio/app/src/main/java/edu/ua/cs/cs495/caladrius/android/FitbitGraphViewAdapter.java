package edu.ua.cs.cs495.caladrius.android;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

/**
 * The FitBitGraphViewAdapter module generates FitbitGraphView objects based on predetermined
 * lists of input parameters.
 */
public class FitbitGraphViewAdapter extends BaseAdapter
{
	FitbitGraphView fgv[];

	public FitbitGraphViewAdapter(Context cntxt,
	                              FitbitGraphView.GraphViewGraph[][] graphTypes,
	                              String[][] defaultGraphStats,
	                              Integer[][] defaultGraphColors,
	                              String[] defaultGraphTitles) throws JSONException, InterruptedException, ExecutionException, IOException
	{
		int len = graphTypes.length;
		if (len != defaultGraphStats.length ||
			len != defaultGraphColors.length) {
			throw new IllegalArgumentException("arrays must all be the same length");
		}

		fgv = new FitbitGraphView[len];
		for (int c = 0; c < len; c++) {
			ArrayList<FitbitGraphView.GraphViewGraph> types = new ArrayList<>();
			types.addAll(Arrays.asList(graphTypes[c]));

			ArrayList<String> stats = new ArrayList<>();
			stats.addAll(Arrays.asList(defaultGraphStats[c]));

			ArrayList<Integer> color = new ArrayList<>();
			color.addAll(Arrays.asList(defaultGraphColors[c]));

			String title = defaultGraphTitles[c];

			Query query = new Query(types, stats, color, title,
				false, false, false, false, true);

			fgv[c] = new FitbitGraphView(
				cntxt, query);
		}
	}

	@Override
	public int getCount()
	{
		return fgv.length;
	}

	@Override
	public Object getItem(int i)
	{
		return fgv[i];
	}

	@Override
	public long getItemId(int i)
	{
		return i;
	}

	@Override
	public View getView(int i, View view, ViewGroup viewGroup)
	{
		return fgv[i];
	}
}
