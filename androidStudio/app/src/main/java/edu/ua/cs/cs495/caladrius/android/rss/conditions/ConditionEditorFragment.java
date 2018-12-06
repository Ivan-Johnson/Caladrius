package edu.ua.cs.cs495.caladrius.android.rss.conditions;

import android.support.v4.app.Fragment;
import edu.ua.cs.cs495.caladrius.rss.condition.Condition;
import edu.ua.cs.cs495.caladrius.rss.condition.ExtremeValue;

abstract class ConditionEditorFragment extends Fragment
{
	/**
	 * Constructs the fragment that is used to edit this condition
	 * <p>
	 * It does this by checking the runtime type of the condition against the runtime types that are appropriate for
	 * known editors. Admittedly, it would be *much* cleaner to just have a method in Condition so that we can
	 * simply say `return cond.makeFragment()`, /HOWEVER/ that is *NOT POSSIBLE* because the Condition class is also
	 * used to build server side code, and thus cannot use Fragments or anything Android code in the Condition
	 * class.
	 */
	public static ConditionEditorFragment makeEditor(Condition cond)
	{
		if (cond instanceof ExtremeValue) {
			return ExtremeValueEditor.newInstance((ExtremeValue) cond);
		} else {
			throw new IllegalArgumentException(cond.getClass()
			                                       .toString() +
				" is not an instance of a known subclass of Condition");
		}
	}

	/**
	 * Construct a Condition from whatever data the user has input
	 *
	 * @return
	 */
	abstract Condition getCondition();
}
