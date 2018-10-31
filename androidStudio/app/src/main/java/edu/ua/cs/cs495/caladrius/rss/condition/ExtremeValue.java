package edu.ua.cs.cs495.caladrius.rss.condition;

import java.io.Serializable;

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
}
