package edu.ua.cs.cs495.caladrius.android;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * The FitBitGraphViewAdapter module generates FitbitGraphView objects based on predetermined
 * lists of input parameters.
 */
public class FitbitGraphViewAdapter extends BaseAdapter
{
	public interface FitbitGraphViewOnClickAdapter {
		void onClick(FitbitGraphView fgv);
	}

	ArrayList<FitbitGraphView> fgvs;
	public FitbitGraphViewAdapter(final Context cntxt,
								  final ArrayList<Query> queries, final FitbitGraphViewOnClickAdapter onClick)  throws JSONException, InterruptedException, ExecutionException, IOException
	{
	    fgvs = new ArrayList();
	    for (int x = 0; x < queries.size(); x++) {
	    	FitbitGraphView fgv = new FitbitGraphView(cntxt, queries.get(x));
	    	if (onClick != null) {
				fgv.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						if (!(view instanceof FitbitGraphView)) {
							throw new RuntimeException("This should never happen");
						}
						FitbitGraphView fgv = (FitbitGraphView) view;
						onClick.onClick(fgv);
					}
				});
			}
	        fgvs.add(fgv);
        }
	}

	@Override
	public int getCount()
	{
		return fgvs.size();
	}

	@Override
	public Object getItem(int i)
	{
		return fgvs.get(i);
	}

	@Override
	public long getItemId(int i)
	{
		return i;
	}

	@Override
	public View getView(int i, View view, ViewGroup viewGroup)
	{
		return fgvs.get(i);
	}
}
