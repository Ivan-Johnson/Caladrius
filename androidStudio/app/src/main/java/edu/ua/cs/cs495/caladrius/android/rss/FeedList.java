package edu.ua.cs.cs495.caladrius.android.rss;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import edu.ua.cs.cs495.caladrius.android.Caladrius;
import edu.ua.cs.cs495.caladrius.android.R;
import edu.ua.cs.cs495.caladrius.rss.Feed;
import edu.ua.cs.cs495.caladrius.server.Clientside;
import edu.ua.cs.cs495.caladrius.server.ServerAccount;

import java.io.IOException;

public class FeedList extends Fragment
{
	protected class AsyncInitialize extends AsyncTask<Void, Void, Feed[]>
	{
		@Override
		protected void onPreExecute()
		{
			//TODO: loading bar
		}

		@Override
		protected void onPostExecute(Feed[] feeds)
		{
			//TODO: remove loading bar
			ListView lv = FeedList.this.feedView;
			if (feeds == null) {
				TextView tv = new TextView(getContext());
				tv.setText(R.string.error_contact_server);

				lv.addView(tv);
				return;
			}

			FragmentManager fm = getActivity().getSupportFragmentManager();
			FeedAdapter adapter;
			adapter = new FeedAdapter(lv.getContext(), feeds, fm);

			lv.setAdapter(adapter);
		}

		@Override
		protected Feed[] doInBackground(Void... sa)
		{
			try {
				int ids[] = cs.getFeedIDs(acc);
				Feed feeds[] = new Feed[ids.length];
				for (int c = 0; c < ids.length; c++) {
					//TODO loading bar
					feeds[c] = cs.getFeed(acc, ids[c]);
					if (isCancelled()) {
						break;
					}
				}
				return feeds;
			} catch (IOException e) {
				return null;
			}
		}
	}

	public FeedList()
	{
		// Empty public constructor
	}

	protected Clientside cs = new Clientside();
	protected ServerAccount acc = new ServerAccount();
	protected ListView feedView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState)
	{
		acc = Caladrius.user.sAcc;

		View rootView = inflater.inflate(R.layout.rss_feed_list, container, false);

		feedView = rootView.findViewById(R.id.FeedList);

		(new AsyncInitialize()).execute();

		return rootView;
	}
}
