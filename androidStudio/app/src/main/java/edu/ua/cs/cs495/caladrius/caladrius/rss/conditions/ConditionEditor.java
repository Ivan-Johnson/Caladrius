package edu.ua.cs.cs495.caladrius.caladrius.rss.conditions;

import android.content.Context;
import android.content.Intent;
import edu.ua.cs.cs495.caladrius.rss.condition.Condition;
import edu.ua.cs.cs495.caladrius.rss.condition.ExtremeValue;

public class ConditionEditor
{
	public static Intent makeEditIntent(Context cntxt, Condition cond)
	{
		if (cond instanceof ExtremeValue) {
			return ExtremeValueEditor.ExtremeValueEditorActivity.newIntent(cntxt,(ExtremeValue) cond);
		} else {
			throw new RuntimeException(cond.getClass().toString() + " is not an instance of a known subclass of Condition");
		}
	}
}
