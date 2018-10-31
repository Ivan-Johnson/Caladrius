package edu.ua.cs.cs495.caladrius.rss;

import edu.ua.cs.cs495.caladrius.rss.condition.Condition;
import edu.ua.cs.cs495.caladrius.rss.condition.ExtremeValue;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Feed implements Serializable
{
	private static final long serialVersionUID = -7808241266602491257L;
	public String name;
	protected static String BASEURL="https://caladrius.ivanjohnson.net/webapi/feed?id=";
	public String uuid;
	public String url; // TODO delete this
	public int id;
	public ArrayList<Condition> conditions;

	public Feed(String name, String uuid, ArrayList<Condition> conditions)
	{
		if (conditions == null) {
			conditions = new ArrayList<>();
		}

		this.conditions = conditions;
		this.uuid = uuid;
		this.url = BASEURL + uuid;
		this.name = name;
	}

	public Feed(String name, String url, Condition[] conditions)
	{
		this(
			name,
			url,
			new ArrayList<>(Arrays.asList(conditions))
		);
	}

	public Feed(String name, String url)
	{
		this(name, url, new ArrayList<Condition>());

		// TODO: don't add random conditions to new feeds
		Random r = new Random();

		String stats[] = {"asdf", "fdsa", "asdsdfsdsd"};
		int count = ExtremeValue.extremeType.values().length;
		for (int x = 0; x < 100; x++) {
			String stat = stats[r.nextInt(stats.length)];
			ExtremeValue.extremeType type = ExtremeValue.extremeType.values()[r.nextInt(count)];
			Condition c = new ExtremeValue<>(stat, r.nextDouble() * 30 + 10, type);
			this.conditions.add(c);
		}
	}
}
