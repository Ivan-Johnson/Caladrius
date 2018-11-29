package edu.ua.cs.cs495.caladrius.android.rss;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import edu.ua.cs.cs495.caladrius.android.Caladrius;
import edu.ua.cs.cs495.caladrius.android.R;
import edu.ua.cs.cs495.caladrius.android.miscadapters.ProgressAdapter;
import edu.ua.cs.cs495.caladrius.android.miscadapters.TextAdapter;
import edu.ua.cs.cs495.caladrius.rss.Feed;
import edu.ua.cs.cs495.caladrius.server.Clientside;
import edu.ua.cs.cs495.caladrius.server.ServerAccount;

import java.io.IOException;

/**
 * This fragment shows a list of all the user's RSS feeds, which are obtained from Caladrius' server via the ClientSide class.
 */
public class FeedList extends Fragment
{
	protected class AsyncInitialize extends AsyncTask<Void, Float, Feed[]>
	{
		ListView lv = FeedList.this.feedView;
		ProgressAdapter progressAdapter;
		ListAdapter initAdapter;

		@Override
		protected void onPreExecute()
		{
			initAdapter = lv.getAdapter();

			progressAdapter = new ProgressAdapter(lv.getContext());
			lv.setAdapter(progressAdapter);
		}

		@Override
		protected void onPostExecute(Feed[] feeds)
		{
			if (feeds == null) {
				lv.setAdapter(new TextAdapter(lv.getContext(), R.string.error_contact_server));

				return;
			}

			lv.setAdapter(initAdapter);

			FragmentManager fm = getActivity().getSupportFragmentManager();
			FeedAdapter adapter;
			adapter = new FeedAdapter(lv.getContext(), feeds, fm);

			lv.setAdapter(adapter);
		}

		/**
		 * @param sa ignored
		 * @return an array of feeds obtained from the Caladrius server
		 */
		@Override
		protected Feed[] doInBackground(Void... sa)
		{
			try {
				int ids[] = cs.getFeedIDs(acc);
				Feed feeds[] = new Feed[ids.length];
				for (int c = 0; c < ids.length; c++) {
					feeds[c] = cs.getFeed(acc, ids[c]);
					if (isCancelled()) {
						break;
					}
					publishProgress((float) c / ids.length);
				}
				return feeds;
			} catch (IOException e) {
				//Log.w("FeedList", e);
				Log.w("TAG8322", "Hello, World!", e);
				return null;
			}
		}

		@Override
		protected void onProgressUpdate(Float... values)
		{
			if (values == null || values.length != 1) {
				throw new IllegalArgumentException();
			}
			progressAdapter.updateProgress(values[0]);
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
