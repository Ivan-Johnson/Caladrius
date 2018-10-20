package edu.ua.cs.cs495.caladrius.caladrius.rss;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;
import android.widget.TextView;

import edu.ua.cs.cs495.caladrius.caladrius.R;

public class FeedAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private Context c;
    private Feed[] feeds;
    private final FragmentManager fm;

    private static final String OUR_TAG = "FeedAdapter";

    public FeedAdapter(Context c, Feed[] feeds, FragmentManager fm) {
        this.c = c;
        this.feeds = feeds;
        this.fm = fm;
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
    public View getView(final int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = inflater.inflate(R.layout.rss_feed_item, null);
        }
        TextView text = view.findViewById(R.id.name);
        text.setText(feeds[i].name);

        view.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final DialogFragment fbDialogue = FeedEditor.newInstance(feeds[i]);
                fbDialogue.show(fm, OUR_TAG);
            }
        });

        return view;
    }

    @Override
    public Object getItem(int i) {
        return feeds[i];
    }
}
