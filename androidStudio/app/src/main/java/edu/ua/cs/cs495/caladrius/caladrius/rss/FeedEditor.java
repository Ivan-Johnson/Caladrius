package edu.ua.cs.cs495.caladrius.caladrius.rss;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import edu.ua.cs.cs495.caladrius.caladrius.R;
import edu.ua.cs.cs495.caladrius.caladrius.rss.conditions.ConditionAdapter;

public class FeedEditor extends DialogFragment {
    private static final   String LOGTAG   = "FEED_EDITOR";
    protected static final String ARG_FEED = "FeedEditor_feed";
    protected Feed f;

    public static FeedEditor newInstance(Feed f)
    {
        FeedEditor fe = new FeedEditor();

        Bundle b = new Bundle();
        b.putSerializable(ARG_FEED, f);

        fe.setArguments(b);

        return fe;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
        f = (Feed) args.getSerializable(ARG_FEED);
        if(f == null) {
            throw new RuntimeException("FeedEditor must be provided with a feed to edit");
        }

        View rootView = inflater.inflate(R.layout.rss_feed_edit, container, false);
        TextView nm = rootView.findViewById(R.id.feedName);
        nm.setText(f.name);

        ListView ll = rootView.findViewById(R.id.ConditionList);

        FragmentManager fm = getActivity().getSupportFragmentManager();
        ConditionAdapter adapter;
        adapter = new ConditionAdapter(getContext(), f.conditions, fm, getId());

        ll.setAdapter(adapter);

        return rootView;
    }
}
