package edu.ua.cs.cs495.caladrius.rss;

import edu.ua.cs.cs495.caladrius.rss.condition.Condition;
import edu.ua.cs.cs495.caladrius.rss.condition.ExtremeValue;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FeedTest
{
	private static final int FEEDS_COUNT = 10;
	private static final Feed feeds1[] = new Feed[FEEDS_COUNT];
	private static final Feed feeds2[] = new Feed[FEEDS_COUNT];
	private static Random r;

	@Before
	public void setUp()
	{
		r = new Random(87222923092344353l);

		ExtremeValue.extremeType types[] = ExtremeValue.extremeType.values();
		for (int x = 0; x < FEEDS_COUNT; x++) {
			ArrayList<Condition> conditions1, conditions2;
			{ // initialize conditions1 and conditions2
				ExtremeValue ev1, ev2;
				int stat = r.nextInt();
				double val = r.nextDouble();
				ExtremeValue.extremeType type = types[r.nextInt(types.length)];

				ev1 = new ExtremeValue("stat # " + stat, val, type);
				ev2 = new ExtremeValue("stat # " + stat, val, type);

				conditions1 = new ArrayList();
				conditions2 = new ArrayList();

				conditions1.add(ev1);
				conditions2.add(ev2);
			}

			String uuid = new UUID(r.nextLong(), r.nextLong()).toString();
			String name = "name #" + r.nextLong();

			feeds1[x] = new Feed(new String(name), new String(uuid), conditions1);
			feeds2[x] = new Feed(new String(name), new String(uuid), conditions2);
		}
	}

	public void testEquals_parallel(Feed arr1[], Feed arr2[])
	{
		for (int a = 0; a < FEEDS_COUNT; a++) {
			for (int b = 0; b < FEEDS_COUNT; b++) {
				boolean equal = Objects.equals(arr1[a], arr2[b]);
				if (a == b) {
					assertTrue(equal);
				} else {
					assertFalse(equal);
				}
			}
		}
	}

	@Test
	public void testEquals_self()
	{
		for (int x = 0; x < FEEDS_COUNT; x++) {
			assertTrue(Objects.equals(feeds1[x], feeds1[x]));
		}
	}

	@Test
	public void testEquals_clone()
	{
		for (int x = 0; x < FEEDS_COUNT; x++) {
			assertTrue(Objects.equals(feeds1[x], feeds2[x]));
		}
	}

	@Test
	public void testEquals_notOther()
	{
		for (int a = 0; a < FEEDS_COUNT; a++) {
			for (int b = 0; b < FEEDS_COUNT; b++) {
				if (a == b) {
					continue;
				}
				assertFalse(Objects.equals(feeds1[a], feeds2[b]));
			}
		}
	}

	@Test
	public void testEquals_null()
	{
		for (int x = 0; x < FEEDS_COUNT; x++) {
			assertFalse(Objects.equals(feeds1[x], null));
		}
	}

	@Test
	public void testEquals_String()
	{
		for (int x = 0; x < FEEDS_COUNT; x++) {
			assertFalse(Objects.equals(feeds1[x], "iouwerkljsd"));
		}
	}

	@Test
	public void testEquals_nullConditions()
	{
		for (int x = 0; x < FEEDS_COUNT; x++) {
			feeds1[x].conditions = null;
			feeds2[x].conditions = null;
		}
		testEquals_parallel(feeds1, feeds2);
	}

	@Test
	public void testEquals_nullUUID()
	{
		for (int x = 0; x < FEEDS_COUNT; x++) {
			feeds1[x].uuid = null;
			feeds2[x].uuid = null;
		}
		testEquals_parallel(feeds1, feeds2);
	}

	@Test
	public void testEquals_nullName()
	{
		for (int x = 0; x < FEEDS_COUNT; x++) {
			feeds1[x].name = null;
			feeds2[x].name = null;
		}
		testEquals_parallel(feeds1, feeds2);
	}
}
