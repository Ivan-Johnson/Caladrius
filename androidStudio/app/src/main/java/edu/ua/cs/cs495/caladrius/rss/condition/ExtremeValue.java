package edu.ua.cs.cs495.caladrius.rss.condition;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

import edu.ua.cs.cs495.caladrius.android.Caladrius;
import edu.ua.cs.cs495.caladrius.android.R;
import org.json.JSONArray;
import org.json.JSONException;

public class ExtremeValue<T extends Serializable> implements Condition
{
	private static final long serialVersionUID = -7141565572258698935L;
	protected String stat;
	protected T value;
	protected extremeType type;
	public ExtremeValue(String stat, T value, extremeType type)
	{
		this.stat = stat;
		this.value = value;
		this.type = type;
	}

	@Override
	public String getStat()
	{
		return stat;
	}

	@Override
	public ArrayList<String> getMatches(JSONArray data)
	{
		ArrayList<String> messages = new ArrayList<>();
		if (!(this.value instanceof Double)) { // TODO remove generics from ExtremeValue
			throw new RuntimeException("Unknown generic type");
		}
		double this_value = (Double) this.value;
		for (int x = 0; x < data.length(); x++) {
			try {
				double val = data.getJSONObject(x)
				                 .getInt("value");
				boolean met = false;
				switch (this.type) {
				case equal:
					met = val == this_value;
					break;
				case greaterThan:
					met = val > this_value;
					break;
				case greaterThanOrEqual:
					met = val >= this_value;
					break;
				case lessThan:
					met = val < this_value;
					break;
				case lessThanOrEqual:
					met = val <= this_value;
					break;
				default:
					throw new RuntimeException("Unknown extreme type");
				}
				if (met) {
					String dt = data.getJSONObject(x)
					                .getString("dateTime");

					messages.add(dt + " the value of " + stat + " is " + val +
						"; which is beyond the specified limit of " + this_value);
				}
			} catch (JSONException e) {
				throw new RuntimeException("This should never happen");
			}
		}
		return messages;
	}

	public String getValueString()
	{
		return value.toString();
	}

	public extremeType getType()
	{
		return type;
	}

	public enum extremeType
	{
		lessThan(R.string.cmp_lt),
		lessThanOrEqual(R.string.cmp_lte),
		equal(R.string.cmp_eq),
		greaterThan(R.string.cmp_gt),
		greaterThanOrEqual(R.string.cmp_gte);

		public String text;

		extremeType(int resid)
		{
			text = Caladrius.getContext().getString(resid);
		}
		public static extremeType construct (String text)
		{
			for (extremeType e : extremeType.values()) {
				if (Objects.equals(e.text, text)) {
					return e;
				}
			}
			throw new IllegalArgumentException("Provided string does not correspond to any extremeType");
		}
	}

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder("Extreme Value: {");

                sb.append(stat);
                sb.append(' ');
                String tmp;

                switch (type) {
                case equal:
                        tmp = "=";
                        break;
                case lessThan:
                        tmp = "<";
                        break;
                case greaterThan:
                        tmp = ">";
                        break;
                case lessThanOrEqual:
                        tmp = "≤";
                        break;
                case greaterThanOrEqual:
                        tmp = "≥";
                        break;
                default:
                        throw new RuntimeException("Type \"" + type +
                                "\" was not a valid extremeType as of 20181101");
                }
                sb.append(tmp);
                sb.append(' ');
                sb.append(value.toString());
		sb.append('}');
		return sb.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ExtremeValue)) {
			return false;
		}
		ExtremeValue other = (ExtremeValue) obj;
		boolean equal = true;
		equal = equal && Objects.equals(other.type, this.type);
		equal = equal && Objects.equals(other.stat, this.stat);
		equal = equal && Objects.equals(other.value, this.value);

		return equal;
	}
}
