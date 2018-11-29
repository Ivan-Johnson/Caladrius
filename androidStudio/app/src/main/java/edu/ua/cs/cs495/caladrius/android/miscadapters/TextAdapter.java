package edu.ua.cs.cs495.caladrius.android.miscadapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TextAdapter extends BaseAdapter
{
	TextView tv;

	public TextAdapter(Context c, String text)
	{
		this(c);
		tv.setText(text);
	}

	public TextAdapter(Context c, int resid)
	{
		this(c);
		tv.setText(resid);
	}

	public TextAdapter(Context c)
	{
		tv = new TextView(c);
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
