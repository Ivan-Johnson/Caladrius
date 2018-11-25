package edu.ua.cs.cs495.caladrius.android.rss.conditions;

import android.content.Context;
import android.content.Intent;
import edu.ua.cs.cs495.caladrius.rss.condition.Condition;
import edu.ua.cs.cs495.caladrius.rss.condition.ExtremeValue;

public class ConditionEditor
{

	/**
	 * Constructs an intent to launch an editor appropriate for the given conditions.
	 *
	 * It does this by checking the runtime type of the condition against the runtime types that are appropriate
	 * for known editors. Admittedly, it would be *much* cleaner to add a method declaration something like the
	 * following to Condition:
	 *     Intent makeEditorIntent(Context cntxt);
	 * /HOWEVER/ that is *NOT POSSIBLE* because the Condition class is also used to build server side code, and thus
	 * cannot use Intents or anything else from Android.
	 */
	public static Intent makeEditIntent(Context cntxt, Condition cond)
	{
		if (cond instanceof ExtremeValue) {
			return ExtremeValueEditor.ExtremeValueEditorActivity.newIntent(cntxt, (ExtremeValue) cond);
		} else {
			throw new IllegalArgumentException(cond.getClass().toString() +
				" is not an instance of a known subclass of Condition");
		}
	}
}
