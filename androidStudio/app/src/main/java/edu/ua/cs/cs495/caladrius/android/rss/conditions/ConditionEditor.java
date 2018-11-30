package edu.ua.cs.cs495.caladrius.android.rss.conditions;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import edu.ua.cs.cs495.caladrius.android.GenericEditor;
import edu.ua.cs.cs495.caladrius.rss.condition.Condition;

import java.io.Serializable;

public class ConditionEditor extends GenericEditor
{
	protected static final String EXTRA_COND = "condition";
	protected static final String EXTRA_RET_CONDITION = "EXTRA_RET_CONDITION";
	ConditionEditorFragment editor;

	protected ConditionEditor()
	{
		super("Condition Editor", true);
	}

	public static Intent createIntent(Context cntxt, Condition cond)
	{
		Intent in = new Intent(cntxt, ConditionEditor.class);
		in.putExtra(EXTRA_COND, cond);
		return in;
	}

	@Override
	protected Fragment makeFragment()
	{
		Bundle bun = getIntent().getExtras();
		if (bun == null) {
			throw new IllegalArgumentException("This should never happen?");
		}

		Condition cond = (Condition) bun.getSerializable(EXTRA_COND);

		editor = ConditionEditorFragment.makeEditor(cond);
		return editor;
	}

	@Override
	protected void doSave()
	{
		Condition c = editor.getCondition();

		Intent in = new Intent();
		in.putExtra(EXTRA_RET_CONDITION, c);

		setResult(Activity.RESULT_OK, in);
	}

	public static Condition getCondition(Intent in)
	{
		Serializable s = in.getSerializableExtra(EXTRA_RET_CONDITION);
		if (! (s instanceof Condition)) {
			throw new IllegalArgumentException("Unexpected object in intent");
		}
		return (Condition) s;
	}

	@Override
	protected void onCancel()
	{
		setResult(Activity.RESULT_CANCELED);
	}
}
