package edu.ua.cs.cs495.caladrius.android.rss;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import edu.ua.cs.cs495.caladrius.android.Caladrius;
import edu.ua.cs.cs495.caladrius.android.GenericEditor;
import edu.ua.cs.cs495.caladrius.android.R;
import edu.ua.cs.cs495.caladrius.android.rss.conditions.ConditionAdapter;
import edu.ua.cs.cs495.caladrius.android.rss.conditions.ConditionEditor;
import edu.ua.cs.cs495.caladrius.rss.Feed;
import edu.ua.cs.cs495.caladrius.rss.condition.Condition;
import edu.ua.cs.cs495.caladrius.server.Clientside;
import edu.ua.cs.cs495.caladrius.server.ServerAccount;
import org.w3c.dom.Text;

import java.io.IOException;

public class FeedEditor extends Fragment
{
	protected static class AsyncSaveFeed extends AsyncTask<Feed, Void, Boolean>
	{
		Clientside cs = new Clientside();
		ServerAccount sa = Caladrius.getUser().sAcc;
		Activity activity;


		public AsyncSaveFeed(Activity activity)
		{
			this.activity = activity;
		}

		@Override
		protected void onPostExecute(Boolean success) {
			if (success) {
				activity.finish();
			}
		}

		@Override
		protected Boolean doInBackground(Feed... feeds) {
			// TODO: progress bar of some sort? Probably not a literal bar though; feeds /should/ only be one long.
			boolean success = true;
			for (int x = 0; x < feeds.length; x++) {
				try {
					cs.setFeed(sa, feeds[x]);
				} catch (IOException e) {
					Log.w("AsyncSaveFeed", e);
					success = false;
				}
			}
			return success;
		}
	}

	protected static final String ARG_FEED = "FeedEditor_feed";
	private static final String LOGTAG = "FEED_EDITOR";
	protected Feed f;
	protected static final String EXTRA_RESULT = "iouwlkxnvljweefoiu";
	ConditionAdapter adapter;
	EditText name;

	public static FeedEditor newInstance(@NonNull Feed f)
	{
		FeedEditor fe = new FeedEditor();

		Bundle b = new Bundle();
		b.putSerializable(ARG_FEED, f);

		fe.setArguments(b);

		return fe;
	}

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater,
	                         @Nullable ViewGroup container,
	                         @Nullable Bundle savedInstanceState)
	{
		Bundle args = getArguments();
		f = (Feed) args.getSerializable(ARG_FEED);
		if (f == null) {
			throw new RuntimeException("FeedEditor must be provided with a feed to edit");
		}

		View rootView = inflater.inflate(R.layout.rss_feed_edit, container, false);
		name = rootView.findViewById(R.id.feedName);
		name.setText(f.name);

		TextView urlView = rootView.findViewById(R.id.url);
		String url = f.getURL();
		urlView.setText(url);

		ListView ll = rootView.findViewById(R.id.ConditionList);

		adapter = new ConditionAdapter(f.conditions, getContext(), (int i, Condition cond) ->
		{
			Intent in = ConditionEditor.createIntent(getContext(), cond);
			startActivityForResult(in, i);
		});

		ll.setAdapter(adapter);

		return rootView;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		int index = requestCode;
		if (resultCode == Activity.RESULT_CANCELED) {
			return;
		} else if (resultCode != Activity.RESULT_OK) {
			throw new RuntimeException("Condition editor yielded an unexpected result");
		}

		Condition cond = ConditionEditor.getCondition(data);
		adapter.setItem(index, cond);
	}

	public Feed updateFeed()
	{
		f.name = name.getText().toString();
		return f;
	}

	public static class FeedEditorActivity extends GenericEditor
	{
		FeedEditor fe;
		protected FeedEditorActivity () {
			super("Feed Editor", false);
		}
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
			fe = newInstance((Feed) bun.getSerializable(EXTRA_FEED));
			return fe;
		}

		@Override
		protected void save() {
			Intent in = new Intent();

			in.putExtra(EXTRA_RESULT, fe.updateFeed());
			setResult(Activity.RESULT_OK, in);

			AsyncSaveFeed assf = new AsyncSaveFeed(this);
			assf.execute(fe.f);
		}
	}

	public static Feed getFeed(Intent data)
	{
		return (Feed) data.getSerializableExtra(EXTRA_RESULT);
	}
}
