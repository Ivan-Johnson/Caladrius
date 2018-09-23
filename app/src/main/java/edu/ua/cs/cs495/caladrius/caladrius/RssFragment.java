package edu.ua.cs.cs495.caladrius.caladrius;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class RssFragment extends Fragment {

    public RssFragment() {
        // Empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.rss_list, container, false);

        return rootView;
    }
}
