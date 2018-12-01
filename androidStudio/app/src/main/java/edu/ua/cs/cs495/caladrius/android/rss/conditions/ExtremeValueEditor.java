package edu.ua.cs.cs495.caladrius.android.rss.conditions;

import android.os.Bundle;
import android.renderscript.RSInvalidStateException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import edu.ua.cs.cs495.caladrius.android.Caladrius;
import edu.ua.cs.cs495.caladrius.android.R;
import edu.ua.cs.cs495.caladrius.rss.condition.Condition;
import edu.ua.cs.cs495.caladrius.rss.condition.ExtremeValue;

public class ExtremeValueEditor extends ConditionEditorFragment
{
	protected static final String ARG_EXTREMEVALUE = "ExtremeValueEditor EXTREMEVALUE";
	protected ExtremeValue ev;
	protected Spinner stat;
	protected EditText val;

	@Override
	Condition getCondition()
	{
		String text = val.getText().toString();
		Double val;
		try {
			val = Double.valueOf(text);
		} catch(NumberFormatException nfe) {
			// TODO it's awful to silently change the value like this. Instead, change val to some sort of input field that ONLY produced valid numbers
			// (e.g. you can't type letters in it)
			val = 0.0;
		}

		return new ExtremeValue<>((String) stat.getSelectedItem(),
			val,
			ExtremeValue.extremeType.lessThan);
	}

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
	public View onCreateView(@NonNull LayoutInflater inflater,
	                         @Nullable ViewGroup container,
	                         @Nullable Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		Bundle b = getArguments();
		assert b != null;
		this.ev = (ExtremeValue) b.getSerializable(ARG_EXTREMEVALUE);

		View rootView = inflater.inflate(R.layout.rss_condition_extremevalue_editor,
			container, false);

		Spinner sp = rootView.findViewById(R.id.ev_type);
		sp.setAdapter(
			ArrayAdapter.createFromResource(
				Caladrius.getContext(),
				R.array.rss_conditions_extremevalue_boundarytype,
				R.layout.spinner_item
			)
		);

		stat = rootView.findViewById(R.id.ev_statname);
		stat.setAdapter(
				ArrayAdapter.createFromResource(
						Caladrius.getContext(),
						R.array.array_graph_stats_options,
						R.layout.spinner_item
				)
		);

		val = rootView.findViewById(R.id.ev_val);
		val.setText(this.ev.getValueString());

		return rootView;
	}
}
