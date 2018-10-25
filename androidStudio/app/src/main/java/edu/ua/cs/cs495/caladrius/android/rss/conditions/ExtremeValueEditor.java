package edu.ua.cs.cs495.caladrius.android.rss.conditions;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import edu.ua.cs.cs495.caladrius.android.GenericEditor;
import edu.ua.cs.cs495.caladrius.android.R;
import edu.ua.cs.cs495.caladrius.rss.condition.ExtremeValue;

public class ExtremeValueEditor extends Fragment
{
	public static class ExtremeValueEditorActivity extends GenericEditor
	{
		protected static final String EXTRA_EV = "feed";

		public static Intent newIntent(Context cntxt, ExtremeValue ev)
		{
			Intent in = new Intent(cntxt, ExtremeValueEditorActivity.class);
			in.putExtra(EXTRA_EV, ev);
			return in;
		}

		@Override
		protected Fragment makeFragment()
		{
			Bundle bun = getIntent().getExtras();
			ExtremeValue ev = null;
			if (bun != null) {
				ev = (ExtremeValue) bun.getSerializable(EXTRA_EV);
			}
			return ExtremeValueEditor.newInstance(ev);
		}
	}

	protected static final String ARG_EXTREMEVALUE = "ExtremeValueEditor EXTREMEVALUE";
	ExtremeValue ev;

	public static ExtremeValueEditor newInstance(ExtremeValue ev)
	{
		Bundle args = new Bundle();

		args.putSerializable(ARG_EXTREMEVALUE, ev);

		ExtremeValueEditor fragment = new ExtremeValueEditor();
		fragment.setArguments(args);
		return fragment;
	}

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		Bundle b = getArguments();
		this.ev = (ExtremeValue) b.getSerializable(ARG_EXTREMEVALUE);

		View rootView = inflater.inflate(R.layout.rss_condition_extremevalue_editor,
			container, false);

		Spinner sp = rootView.findViewById(R.id.ev_type);
		sp.setAdapter(
			ArrayAdapter.createFromResource(
				getContext(),
				R.array.rss_conditions_extremevalue_boundarytype,
				R.layout.spinner_item
			)
		);

		return rootView;
	}
}
