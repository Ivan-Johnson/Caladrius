package edu.ua.cs.cs495.caladrius.android.rss;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.SupportActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import edu.ua.cs.cs495.caladrius.android.Caladrius;
import edu.ua.cs.cs495.caladrius.android.QueryActivity;
import edu.ua.cs.cs495.caladrius.android.R;

import edu.ua.cs.cs495.caladrius.android.SingleFragmentActivity;

import edu.ua.cs.cs495.caladrius.android.graphData.GraphContract;
import edu.ua.cs.cs495.caladrius.android.miscadapters.ProgressAdapter;
import edu.ua.cs.cs495.caladrius.android.miscadapters.TextAdapter;

import edu.ua.cs.cs495.caladrius.rss.Feed;
import edu.ua.cs.cs495.caladrius.server.Clientside;
import edu.ua.cs.cs495.caladrius.server.ServerAccount;

import java.io.IOException;
import java.util.Objects;

/**
 * This fragment shows a list of all the user's RSS feeds, which are obtained from Caladrius' server via the ClientSide class.
 */
public class FeedList extends Fragment
{
	protected class AsyncInitialize extends AsyncTask<Void, Float, Feed[]>
	{
		ListView lv = FeedList.this.feedView;
		ProgressAdapter progressAdapter;

		@Override
		protected void onPreExecute()
		{
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

			FragmentManager fm = getActivity().getSupportFragmentManager();

			FeedAdapter.ClickEvent onClick = (int i, Feed f) ->
			{
				Fragment frag = FeedList.this;
				Intent in = FeedEditor.FeedEditorActivity.newIntent(frag.getContext(), f);
				frag.startActivityForResult(in, i+1);
			};

			FeedList.this.feedAdapter = new FeedAdapter(lv.getContext(), feeds, fm, onClick);
			lv.setAdapter(FeedList.this.feedAdapter);

			if (add != null) {
				add.show();
			}
		}

		/**
		 * @param sa ignored
		 * @return an array of feeds obtained from the Caladrius server
		 */
		@Override
		protected Feed[] doInBackground(Void... sa)
		{
			try {
				String ids[] = cs.getFeedIDs(acc);
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

	protected Clientside cs = new Clientside();
	protected ServerAccount acc;
	protected ListView feedView;
	protected FloatingActionButton add;
	protected FeedAdapter feedAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState)
	{
		acc = Caladrius.getUser().sAcc;

		View rootView = inflater.inflate(R.layout.rss_feed_list, container, false);
		Toolbar myToolbar = rootView.findViewById(R.id.rss_toolbar);
		myToolbar.setTitle("RSS Feed List");


		AppCompatActivity act = (AppCompatActivity) getActivity();
		act.setSupportActionBar(myToolbar);

		android.support.v7.app.ActionBar ab = ((AppCompatActivity) getActivity()).getSupportActionBar();
		if (ab != null) {
			ab.setDisplayHomeAsUpEnabled(true);
			ab.setDisplayShowHomeEnabled(true);
		}

		feedView = rootView.findViewById(R.id.FeedList);
		(new AsyncInitialize()).execute();

		add = rootView.findViewById(R.id.add_feed);
		add.setOnClickListener((View v) ->
		{
			String name = getContext().getString(R.string.rss_feed_default_name);
			Feed f = new Feed(name);
			Intent i = FeedEditor.FeedEditorActivity.newIntent(getContext(), f);
			startActivityForResult(i, 0);
		});
		add.hide();
		setHasOptionsMenu(true);

		return rootView;
	}


	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_CANCELED) {
			return;
		}
		Feed f = FeedEditor.getFeed(data);
		if (requestCode == 0) {
			if (f != null) {
				feedAdapter.addItem(f);
			}
		} else {
			if (f != null) {
				feedAdapter.setItem(requestCode - 1, f);
			} else {
				feedAdapter.removeItem(requestCode - 1);
			}

		}
	}

	public static class FeedListActivity extends SingleFragmentActivity {

		public FeedListActivity(){}

		@Override
		protected Fragment makeFragment()
		{
			return new FeedList();
		}
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// Inflate the menu options from the res/menu/menu_catalog.xml file.
		// This adds menu items to the app bar.
		inflater.inflate(R.menu.menu_feed_editor, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.delete_all_feeds:
			(new AsyncDelete()).execute();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public class AsyncDelete extends AsyncTask<Void, Void, Boolean> {
		@Override
		protected void onPostExecute(Boolean allPass)
		{
			if (!allPass) {
				Toast.makeText(getContext(), R.string.delete_all_feeds_fail, Toast.LENGTH_SHORT);
			}
		}

		@Override
		protected Boolean doInBackground(Void... foo)
		{
			boolean allPass = true;
			while (feedAdapter.getCount() > 0) {
				Feed f = (Feed) feedAdapter.getItem(0);
				boolean success = false;
				for (int x = 0; x < 3; x++) {
					try {
						cs.deleteFeed(acc, f.uuid);
						success = true;
						break;
					} catch (IOException e) {
						continue;
					}
				}
				if (!success) {
					allPass = false;
					continue;
				}
				feedAdapter.removeItemSilently(f);
				publishProgress();
			}
			return allPass;
		}

		@Override
		protected void onProgressUpdate(Void... values)
		{
			feedAdapter.notifyDataSetInvalidated();
		}
	}
}

