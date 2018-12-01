package edu.ua.cs.cs495.caladrius.rss.condition;

import java.io.Serializable;
import java.util.Objects;

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

	public String getStat()
	{
		return new String(stat);
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
		lessThan,
		lessThanOrEqual,
		equal,
		greaterThan,
		greaterThanOrEqual
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
