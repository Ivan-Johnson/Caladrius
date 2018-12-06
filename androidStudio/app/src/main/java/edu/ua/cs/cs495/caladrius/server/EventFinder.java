package edu.ua.cs.cs495.caladrius.server;

import edu.ua.cs.cs495.caladrius.User;
import edu.ua.cs.cs495.caladrius.fitbit.Fitbit;
import edu.ua.cs.cs495.caladrius.rss.Feed;
import edu.ua.cs.cs495.caladrius.rss.condition.Condition;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * This file is compiled into a separate jar file to be run on the server.
 * <p>
 * It lists the days on which a feed's conditions were tripped. It expects two arguments: 1: a base 64 representation of
 * a User object 2: a base 64 representation of a Feed object
 * <p>
 * The first line is the name of the feed; each subsequent line of the output of this file specifies a single day that
 * triggered at least one conditions, and is formatted as follows: YYYY-MM-DD [title]
 *
 * @author Ivan Johnson
 */
public class EventFinder
{
	public static final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

	public static void main(String args[]) throws JSONException, InterruptedException, ExecutionException, IOException, ParseException
	{
		if (args.length != 2) {
			System.err.println(
				"Usage: " + EventFinder.class.getSimpleName() + " <base 64 user> <base 64 feed>");
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

		System.out.println(feed.name);
		for (int iCond = 0; iCond < feed.conditions.size(); iCond++) {
			Condition cond = feed.conditions.get(iCond);
			String stat = cond.getStat();
			JSONArray arr = Fitbit.getFitbitData(user.fAcc,
				stat,
				Fitbit.TIME_RANGE_TYPE_RELATIVE,
				null,
				null,
				Fitbit.TIME_RANGE_MONTH);
			ArrayList<String> matches = cond.getMatches(arr);
			for (String s : matches) {
				System.out.println(s);
			}
		}
	}
}
