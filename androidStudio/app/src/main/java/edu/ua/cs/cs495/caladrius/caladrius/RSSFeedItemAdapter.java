package edu.ua.cs.cs495.caladrius.caladrius;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;
import android.widget.TextView;

import static android.content.ContentValues.TAG;

public class RSSFeedItemAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    Context c;
    RSSFeed[] feeds;
    public RSSFeedItemAdapter(Context c, RSSFeed[] feeds) {
        this.c = c;
        this.feeds = feeds;
        inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return feeds.length;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = inflater.inflate(R.layout.rss_feed_item, null);
        }
        TextView text = view.findViewById(R.id.name);
        text.setText(feeds[i].url);
        return view;
    }

    @Override
    public Object getItem(int i) {
        return feeds[i];
    }
}
