package edu.ua.cs.cs495.caladrius.server;

import edu.ua.cs.cs495.caladrius.User;
import edu.ua.cs.cs495.caladrius.rss.Feed;
import edu.ua.cs.cs495.caladrius.rss.condition.Condition;
import edu.ua.cs.cs495.caladrius.rss.condition.ExtremeValue;

import java.io.IOException;

/**
 * This file is compiled into a separate jar file to be run on the server.
 *
 * It lists the days on which a feed's conditions were tripped. It expects two arguments:
 * 1: a base 64 representation of a User object
 * 2: a base 64 representation of a Feed object
 *
 * The first line is the name of the feed; each subsequent line of the output of this file specifies a single day that triggered at least one conditions, and is formatted as follows:
 * YYYY-MM-DD [title]
 *
 * @author Ivan Johnson
 */
public class ConditionLister
{
	public static void main(String args[])
	{
		if (args.length != 2) {
			System.err.println("Usage: " + ConditionLister.class.getSimpleName() + " <base 64 user> <base 64 feed>");
			System.exit(1);
		}
		String base64User = args[0];
		String base64Feed = args[1];

		User user = null;
		Feed feed = null;

		try {
			user = (User) Clientside.objectFromBase64(base64User);
			feed = (Feed) Clientside.objectFromBase64(base64Feed);
		} catch (IOException e) {
			e.printStackTrace(System.err);
			System.exit(1);
		}

		System.out.println(feed.name + user.toString());
		for (int iCond = 0; iCond < feed.conditions.size(); iCond++) {
			Condition cond = feed.conditions.get(iCond);
			if (!(cond instanceof ExtremeValue)) {
				System.err.println("Unexpected sublass of conditon");
				System.exit(1);
			}
			ExtremeValue ev = (ExtremeValue) cond;
			System.out.println("2018-12-05 "+cond.toString());
		}
	}
}
