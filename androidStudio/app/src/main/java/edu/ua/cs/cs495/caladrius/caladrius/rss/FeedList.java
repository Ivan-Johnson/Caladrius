package edu.ua.cs.cs495.caladrius.caladrius.rss;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import edu.ua.cs.cs495.caladrius.caladrius.R;

public class FeedList extends Fragment {

    public FeedList() {
        // Empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.rss_feed_list, container, false);

        ListView ll = rootView.findViewById(R.id.FeedList);

        final int len = 20;
        Feed[] feeds = new Feed[len];
        for (int c = 0; c < len; c++) {
            feeds[c] = new Feed(
                    "feed name #" + Integer.toString(c),
                    "https://caladrius.ivanjohnson.net/feed/"+Integer.toString(c)
            );
        }

        FragmentManager fm = getActivity().getSupportFragmentManager();
        FeedAdapter adapter;
        adapter = new FeedAdapter(this.getContext(), feeds, fm, this.getId());

        ll.setAdapter(adapter);

        return rootView;
    }
}
