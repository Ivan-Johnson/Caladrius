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
	private Feed[] feeds;
	protected ClickEvent ce;

	public FeedAdapter(Context c, Feed[] feeds, FragmentManager fm, ClickEvent ce)
	{
		this.c = c;
		this.feeds = feeds;
		this.fm = fm;
		this.ce = ce;
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

		view.setOnClickListener((View v) -> ce.OnClick(i, feeds[i]));

		return view;
	}

	public void setItem(int i, Feed feed)
	{
		feeds[i] = feed;
		this.notifyDataSetInvalidated();
	}

	public void addItem(Feed f)
	{
		// TODO array-list-ify this
		Feed tmp[] = new Feed[feeds.length+1];
		for (int x = 0; x < feeds.length; x++) {
			tmp[x] = feeds[x];
		}
		tmp[feeds.length] = f;
		feeds = tmp;
		this.notifyDataSetInvalidated();
	}
}
