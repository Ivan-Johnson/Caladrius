package edu.ua.cs.cs495.caladrius.caladrius.rss;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import edu.ua.cs.cs495.caladrius.caladrius.R;
import edu.ua.cs.cs495.caladrius.rss.Feed;

public class FeedAdapter extends BaseAdapter
{
	private static final String OUR_TAG = "FeedAdapter";
	private final FragmentManager fm;
	private LayoutInflater inflater;
	private Context c;
	private Feed[] feeds;

	public FeedAdapter(Context c, Feed[] feeds, FragmentManager fm)
	{
		this.c = c;
		this.feeds = feeds;
		this.fm = fm;
		inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount()
	{
		return feeds.length;
	}

	@Override
	public Object getItem(int i)
	{
		return feeds[i];
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
		text.setText(feeds[i].name);

		view.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				Intent in = FeedEditor.FeedEditorActivity.newIntent(c, feeds[i]);
				c.startActivity(in);
			}
		});

		return view;
	}
}
