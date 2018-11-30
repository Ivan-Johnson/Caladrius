package edu.ua.cs.cs495.caladrius.rss;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

import edu.ua.cs.cs495.caladrius.fitbit.Fitbit;
import edu.ua.cs.cs495.caladrius.rss.condition.Condition;
import edu.ua.cs.cs495.caladrius.rss.condition.ExtremeValue;

public class Feed implements Serializable
{
	private static final long serialVersionUID = -7808241266602491257L;
	public String name;
	protected static final String BASEURL="https://caladrius.ivanjohnson.net/webapi/feed?id=";
	public String uuid;
	public int id;
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
		this(name, UUID.randomUUID().toString(), null);

		// TODO: don't add random conditions to new feeds
		Random r = new Random();

		String stats[] = Fitbit.getSupportedStats();
		int count = ExtremeValue.extremeType.values().length;
		for (int x = 0; x < 5; x++) {
			String stat = stats[r.nextInt(stats.length)];
			ExtremeValue.extremeType type = ExtremeValue.extremeType.values()[r.nextInt(count)];
			Condition c = new ExtremeValue<>(stat, r.nextDouble() * 30 + 10, type);
			this.conditions.add(c);
		}
	}

	public String getURL()
	{
		return BASEURL + uuid;
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

		sb.append("id: ");
		sb.append(id);
		sb.append(", ");

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
