package edu.ua.cs.cs495.caladrius.android.rss.conditions;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import edu.ua.cs.cs495.caladrius.android.GenericEditor;
import edu.ua.cs.cs495.caladrius.rss.condition.Condition;
import edu.ua.cs.cs495.caladrius.rss.condition.ExtremeValue;

public class ConditionEditor extends GenericEditor
{
	protected static final String EXTRA_COND = "condition";

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

	/**
	 * Constructs the fragment that is used to edit this condition
	 *
	 * It does this by checking the runtime type of the condition against the runtime types that are appropriate
	 * for known editors. Admittedly, it would be *much* cleaner to just have a method in Condition so that we can
	 * simply say `return cond.makeFragment()`, /HOWEVER/ that is *NOT POSSIBLE* because the Condition class is also
	 * used to build server side code, and thus cannot use Fragments or anything else from Android.
	 */
	@Override
	protected Fragment makeFragment()
	{
		Bundle bun = getIntent().getExtras();
		Condition cond = null;
		if (bun != null) {
			cond = (Condition) bun.getSerializable(EXTRA_COND);
		}

		if (cond instanceof ExtremeValue) {
			return ExtremeValueEditor.newInstance((ExtremeValue) cond);
		} else {
			throw new IllegalArgumentException(cond.getClass().toString() +
				" is not an instance of a known subclass of Condition");
		}
	}

	@Override
	protected void doSave()
	{
		// TODO implement this
		Log.i("editor", "Saving Condition");
	}
}
