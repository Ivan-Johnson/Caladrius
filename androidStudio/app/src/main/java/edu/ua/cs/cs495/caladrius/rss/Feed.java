package edu.ua.cs.cs495.caladrius.rss;

import edu.ua.cs.cs495.caladrius.android.Caladrius;
import edu.ua.cs.cs495.caladrius.fitbit.FitbitAccount;
import edu.ua.cs.cs495.caladrius.rss.condition.Condition;
import edu.ua.cs.cs495.caladrius.rss.condition.ExtremeValue;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Feed implements Serializable
{
	public String name;
	public String url;
	public ArrayList<Condition> conditions;

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

		String stats[] = Caladrius.user.getValidStats();
		int count = ExtremeValue.extremeType.values().length;
		for (int x = 0; x < 100; x++) {
			String stat = stats[r.nextInt(stats.length)];
			ExtremeValue.extremeType type = ExtremeValue.extremeType.values()[r.nextInt(count)];
			Condition c = new ExtremeValue<>(stat, r.nextDouble() * 30 + 10, type);
			this.conditions.add(c);
		}
	}
}
