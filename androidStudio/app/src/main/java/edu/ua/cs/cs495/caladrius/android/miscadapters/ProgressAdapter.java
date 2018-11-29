package edu.ua.cs.cs495.caladrius.android.miscadapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ProgressAdapter extends BaseAdapter
{
	TextView tv;
	double frac;

	public ProgressAdapter(Context c)
	{
		tv = new TextView(c);
		updateProgress(0);
	}

	public void updateProgress(double frac)
	{
		this.frac = frac;
		tv.setText("Progress: " + (frac*100) + "%");
	}

	@Override
	public int getCount()
	{
		return 1;
	}

	@Override
	public Object getItem(int i)
	{
		if (i != 0) {
			throw new IllegalArgumentException();
		}
		return tv;
	}

	@Override
	public long getItemId(int i)
	{
		return i;
	}

	@Override
	public View getView(int i, View view, ViewGroup viewGroup)
	{
		if (i != 0) {
			throw new IllegalArgumentException();
		}
		return tv;
	}
}
