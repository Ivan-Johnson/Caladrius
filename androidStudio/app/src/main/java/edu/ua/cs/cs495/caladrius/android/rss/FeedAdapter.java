package edu.ua.cs.cs495.caladrius.android.rss;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import edu.ua.cs.cs495.caladrius.android.R;
import edu.ua.cs.cs495.caladrius.rss.Feed;

import java.util.ArrayList;
import java.util.Arrays;

public class FeedAdapter extends BaseAdapter
{
	public interface ClickEvent
	{
		void OnClick(int i, Feed f);
	}
	private static final String OUR_TAG = "FeedAdapter";
	private final FragmentManager fm;
	private LayoutInflater inflater;
	private Context c;
	private ArrayList<Feed> feeds;
	protected ClickEvent ce;

	public FeedAdapter(Context c, Feed[] feeds, FragmentManager fm, ClickEvent ce)
	{
		this.c = c;
		this.feeds = new ArrayList(Arrays.asList(feeds));
		this.fm = fm;
		this.ce = ce;
		inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount()
	{
		return feeds.size();
	}

	@Override
	public Object getItem(int i)
	{
		return feeds.get(i);
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
		text.setText(feeds.get(i).name);

		view.setOnClickListener((View v) -> ce.OnClick(i, feeds.get(i)));

		return view;
	}

	public void setItem(int i, Feed feed)
	{
		feeds.set(i, feed);
		this.notifyDataSetInvalidated();
	}

	public void addItem(Feed f)
	{
		feeds.add(f);
		this.notifyDataSetInvalidated();
	}
}
