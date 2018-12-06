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
	protected Spinner evtype;

	@Override
	Condition getCondition()
	{
		String text = val.getText().toString();
		Double val;
		try {
			val = Double.valueOf(text);
		} catch(NumberFormatException nfe) {
			throw new RuntimeException("The number-only textfield somehow isn't a valid number");
		}

		text = (String) this.evtype.getSelectedItem();
		ExtremeValue.extremeType evtype = ExtremeValue.extremeType.construct(text);

		return new ExtremeValue<>((String) stat.getSelectedItem(), val, evtype);
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

		evtype = rootView.findViewById(R.id.ev_type);
		evtype.setAdapter(
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
