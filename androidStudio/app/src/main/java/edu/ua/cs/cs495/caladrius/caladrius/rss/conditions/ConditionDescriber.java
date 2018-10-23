package edu.ua.cs.cs495.caladrius.caladrius.rss.conditions;

import android.content.Context;
import android.support.annotation.Nullable;
import edu.ua.cs.cs495.caladrius.caladrius.Caladrius;
import edu.ua.cs.cs495.caladrius.caladrius.R;
import edu.ua.cs.cs495.caladrius.rss.condition.Condition;
import edu.ua.cs.cs495.caladrius.rss.condition.ExtremeValue;

public class ConditionDescriber
{
	/**
	 * Creates a string that describes the given condition
	 *
	 * @param cond condition to describe
	 * @return strind description of cond. Is null if cond is not an instance of any of the known subclasses of Condition
	 */
	@Nullable
	public static String describe(Condition cond)
	{
		if (cond instanceof ExtremeValue) {
			return describeExtreme((ExtremeValue) cond);
		} else {
			return null;
		}
	}

	public static String describeExtreme(ExtremeValue ev)
	{
		StringBuilder sb = new StringBuilder();
		sb.append(ev.getStat());
		sb.append(' ');
		int tmp;
		switch (ev.getType()) {
			case equal:
				tmp = R.string.cmp_eq;
				break;
			case lessThan:
				tmp = R.string.cmp_lt;
				break;
			case greaterThan:
				tmp = R.string.cmp_gt;
				break;
			case lessThanOrEqual:
				tmp = R.string.cmp_lte;
				break;
			case greaterThanOrEqual:
				tmp = R.string.cmp_gte;
				break;
			default:
				throw new RuntimeException("Type \"" + ev.getType() + "\" was not a valid extremeType as of the writing of this message");
		}
		Context cntxt = Caladrius.getContext();
		sb.append(cntxt.getText(tmp));
		sb.append(' ');
		sb.append(cntxt.getText(R.string.ev_singleValue));

		return sb.toString();
	}

}