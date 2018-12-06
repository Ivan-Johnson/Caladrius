package edu.ua.cs.cs495.caladrius.rss;

import edu.ua.cs.cs495.caladrius.rss.condition.Condition;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

/**
 * This class represents a single RSS feed, which has an item added to it when any one of many conditions is met.
 */
public class Feed implements Serializable
{
	protected static final String BASEURL = "https://caladrius.ivanjohnson.net/webapi/feed?id=";
	private static final long serialVersionUID = -7800998776796687275L;
	// don't forget to update the "equals" function when adding new fields
	public String name;
	public String uuid;
	public ArrayList<Condition> conditions;

	public Feed(String name, String uuid, ArrayList<Condition> conditions)
	{
		if (conditions == null) {
			conditions = new ArrayList<>();
		}

		this.conditions = conditions;
		this.uuid = uuid;
		this.name = name;
	}

	public Feed(String name)
	{
		this(name,
			UUID.randomUUID()
			    .toString(),
			null);
	}

	public String getURL()
	{
		return BASEURL + uuid;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof Feed)) {
			return false;
		}
		Feed other = (Feed) obj;

		boolean equal = true;

		// note that short circuiting can remove the need to perform each of these potentially expensive evaluations
		equal = equal && Objects.equals(other.uuid, this.uuid);
		equal = equal && Objects.equals(other.name, this.name);
		equal = equal && Objects.equals(other.conditions, this.conditions);

		return equal;
	}

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("Feed: {");

		sb.append("name: \"");
		sb.append(name);
		sb.append("\", ");

		sb.append("UUID: \"");
		sb.append(uuid);
		sb.append("\", ");

		sb.append("Conditions: [");
		for (Condition condition : conditions) {
			sb.append(condition);
			sb.append(", ");
		}
		sb.append("], ");

		sb.append('}');
		return sb.toString();
	}
}
