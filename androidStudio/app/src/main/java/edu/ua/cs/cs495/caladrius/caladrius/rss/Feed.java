package edu.ua.cs.cs495.caladrius.caladrius.rss;

import edu.ua.cs.cs495.caladrius.caladrius.FitBit;
import edu.ua.cs.cs495.caladrius.caladrius.rss.conditions.Condition;
import edu.ua.cs.cs495.caladrius.caladrius.rss.conditions.ExtremeValue;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Feed implements Serializable
{
	String name;
	String url;
	ArrayList<Condition> conditions;

	public Feed(String name, String url, ArrayList<Condition> conditions)
	{
		if (conditions == null) {
			conditions = new ArrayList<>();
		}

		this.conditions = conditions;
		this.url = url;
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
		String stats[] = FitBit.getValidStats();
		int count = ExtremeValue.extremeType.values().length;
		for (int x = 0; x < 100; x++) {
			String stat = stats[r.nextInt(stats.length)];
			ExtremeValue.extremeType type = ExtremeValue.extremeType.values()[r.nextInt(count)];
			Condition c = new ExtremeValue<>(stat, r.nextDouble() * 30 + 10, type);
			this.conditions.add(c);
		}
	}
}
