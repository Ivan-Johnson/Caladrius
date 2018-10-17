package edu.ua.cs.cs495.caladrius.caladrius;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class RSSFeedList extends Fragment {

    public RSSFeedList() {
        // Empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.rss_feed_list, container, false);

        ListView ll = rootView.findViewById(R.id.FeedList);

        final int len = 20;
        RSSFeed[] feeds = new RSSFeed[len];
        for (int c = 0; c < len; c++) {
            feeds[c] = new RSSFeed("URL - " + Integer.toString(c));
        }

        FragmentManager fm = getActivity().getSupportFragmentManager();
        RSSFeedItemAdapter adapter;
        adapter = new RSSFeedItemAdapter(this.getContext(), feeds, fm, this.getId());

        ll.setAdapter(adapter);

        return rootView;
    }
}
