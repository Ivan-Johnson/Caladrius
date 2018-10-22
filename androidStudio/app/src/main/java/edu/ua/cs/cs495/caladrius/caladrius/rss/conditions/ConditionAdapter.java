package edu.ua.cs.cs495.caladrius.caladrius.rss.conditions;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import edu.ua.cs.cs495.caladrius.caladrius.R;

import java.util.ArrayList;


public class ConditionAdapter extends BaseAdapter
{
	private static final String OUR_TAG = "FeedAdapter";
	private final FragmentManager fm;
	private LayoutInflater inflater;
	private Context c;
	private ArrayList<Condition> conditions;

	public ConditionAdapter(Context c, ArrayList<Condition> conditions, FragmentManager fm)
	{
		this.c = c;
		this.conditions = conditions;
		this.fm = fm;
		inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount()
	{
		return conditions.size();
	}

	@Override
	public Object getItem(int i)
	{
		return conditions.get(i);
	}

	@Override
	public long getItemId(int i)
	{
		return i;
	}

	@Override
	public View getView(final int i, View view, ViewGroup viewGroup)
	{
		if (view == null) {
			view = inflater.inflate(R.layout.rss_feed_item, null);
		}
		TextView text = view.findViewById(R.id.name);
		text.setText(conditions.get(i).toString());

		view.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				Intent intent = conditions.get(i).makeEditorIntent(c);
				c.startActivity(intent);
			}
		});

		return view;
	}
}
