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

/**
 * This fragment shows a list of all the user's RSS feeds, which are obtained from Caladrius' server via the ClientSide class.
 */
public class FeedList extends Fragment
{
	protected class AsyncInitialize extends AsyncTask<Void, Void, Feed[]>
	{
		@Override
		protected void onPreExecute()
		{
			// TODO: add progress bar
		}

		@Override
		protected void onPostExecute(Feed[] feeds)
		{
			// TODO: remove progress bar bar
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
					// TODO change 2nd generic type to a float or something
					// https://developer.android.com/reference/android/os/AsyncTask
					// publishProgress(c / ids.length); (for example)
				}
				return feeds;
			} catch (IOException e) {
				return null;
			}
		}

		@Override
		protected void onProgressUpdate(Void... values)
		{
			// TODO: update progres bar
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
