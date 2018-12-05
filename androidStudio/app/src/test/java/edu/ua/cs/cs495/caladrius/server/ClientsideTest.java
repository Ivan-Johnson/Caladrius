package edu.ua.cs.cs495.caladrius.server;

import android.support.annotation.NonNull;
import edu.ua.cs.cs495.caladrius.rss.Feed;
import edu.ua.cs.cs495.caladrius.rss.condition.ExtremeValue;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import static org.junit.Assert.*;

public class ClientsideTest
{
	Clientside cs;
	Random r;

	@Before
	public void setUp()
	{
		cs = new Clientside();

		// despite using randomness, we actually want each test to behave the same way every time, hence the need to
		// reset the Random state
		r = new Random(892739832432122345l);
	}

	public void testGetFeedIDs(String uuid, String ans[]) throws IOException
	{
		ServerAccount sa = new ServerAccount(uuid);
		sa.uuid = uuid;

		String feedids[] = cs.getFeedIDs(sa);

		Arrays.sort(ans);
		Arrays.sort(feedids);

		assertArrayEquals(ans, feedids);
	}

	@Test
	public void testGetFeedIDsLenMany() throws IOException
	{
		String ans[] = {"11573", "12484", "18693", "4144", "26184"};
		testGetFeedIDs("TEST_USER_0", ans);
	}

	@Test
	public void testGetFeedIDsLenZero() throws IOException
	{
		String ans[] = {};
		testGetFeedIDs("TEST_USER_1", ans);
	}

	@Test
	public void testGetFeedIDsLenOne() throws IOException
	{
		String ans[] = {"6427"};
		testGetFeedIDs("TEST_USER_2", ans);
	}

	public void testGetFeedString(String uuid, String feed, String ans) throws IOException
	{
		ServerAccount sa = new ServerAccount(uuid);
		sa.uuid = uuid;

		String result = cs.getFeedstring(uuid, feed);

		assertEquals(ans, result);
	}

	@Test
	public void testGetFeedString_Extreme() throws IOException
	{
		testGetFeedString("TEST_USER_3",
			"27560",
			"1XDSpDji2jcqJoQgfQpA8zC6rnK7uJ31OPObAccMXKEo6CLuVSYTUcNer85r9Vvo3Z1PE6EoxPgjUODEsWqSSMUZ2tfUnbrPyWMJkc43ZsfNNS4RMgz4tYXbeQ7cccDS");
	}

	public void testIdentity(String uuid, @NonNull Feed f) throws IOException
	{
		ServerAccount sa = new ServerAccount(uuid);
		sa.uuid = uuid;

		Feed feIn = f;
		cs.setFeed(sa, feIn);
		Feed feOut = cs.getFeed(sa, feIn.uuid);

		assertEquals(feIn, feOut);
	}

	@Test(expected = NullPointerException.class)
	public void testIdentity_null() throws IOException
	{
		testIdentity("TEST_USER_6", null);
	}

	@Test
	public void testIdentity_default() throws IOException
	{
		Feed f = new Feed("" + r.nextInt());
		testIdentity("TEST_USER_6", f);
	}

	@Test
	public void testIdentity_noConditions() throws IOException
	{
		Feed f = new Feed("" + r.nextInt());
		f.conditions = new ArrayList<>();
		testIdentity("TEST_USER_6", f);
	}

	@Test
	public void testIdentity_manyConditions() throws IOException
	{
		Feed f = new Feed("Unnamed obj");
		f.conditions = new ArrayList<>();
		ExtremeValue.extremeType types[] = ExtremeValue.extremeType.values();
		for (int x = 0; x < 100; x++) {
			ExtremeValue.extremeType type = types[r.nextInt(types.length)];
			ExtremeValue ev = new ExtremeValue("stat # " + r.nextInt(), r.nextDouble(), type);
			f.conditions.add(ev);
		}
		testIdentity("TEST_USER_6", f);
	}
}
