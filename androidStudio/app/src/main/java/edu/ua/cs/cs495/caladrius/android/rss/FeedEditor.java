package edu.ua.cs.cs495.caladrius.android.rss;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import edu.ua.cs.cs495.caladrius.android.GenericEditor;
import edu.ua.cs.cs495.caladrius.android.R;
import edu.ua.cs.cs495.caladrius.android.rss.conditions.ConditionAdapter;
import edu.ua.cs.cs495.caladrius.rss.Feed;

public class FeedEditor extends Fragment
{
	protected static final String ARG_FEED = "FeedEditor_feed";
	private static final String LOGTAG = "FEED_EDITOR";
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
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
	{
		Bundle args = getArguments();
		f = (Feed) args.getSerializable(ARG_FEED);
		if (f == null) {
			throw new RuntimeException("FeedEditor must be provided with a feed to edit");
		}

		View rootView = inflater.inflate(R.layout.rss_feed_edit, container, false);
		TextView nm = rootView.findViewById(R.id.feedName);
		nm.setText(f.name);

		ListView ll = rootView.findViewById(R.id.ConditionList);

		FragmentManager fm = getActivity().getSupportFragmentManager();
		ConditionAdapter adapter;
		adapter = new ConditionAdapter(getContext(), f.conditions, fm);

		ll.setAdapter(adapter);

		return rootView;
	}

	public static class FeedEditorActivity extends GenericEditor
	{
		protected static final String EXTRA_FEED = "feed";

		public static Intent newIntent(Context cntxt, Feed feed)
		{
			Intent in = new Intent(cntxt, FeedEditorActivity.class);
			in.putExtra(EXTRA_FEED, feed);
			return in;
		}

		@Override
		protected Fragment makeFragment()
		{
			Bundle bun = getIntent().getExtras();
			if (bun != null) {
				Feed f = (Feed) bun.getSerializable(EXTRA_FEED);
				return newInstance(f);
			} else {
				return new FeedEditor();
			}
		}
	}
}
