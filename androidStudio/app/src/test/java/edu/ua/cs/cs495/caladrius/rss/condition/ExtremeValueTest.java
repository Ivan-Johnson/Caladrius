package edu.ua.cs.cs495.caladrius.rss.condition;

import org.junit.Before;
import org.junit.Test;

import java.util.Objects;
import java.util.Random;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ExtremeValueTest
{
	private static final int EVS_COUNT = 10;
	private static final ExtremeValue evs1[] = new ExtremeValue[EVS_COUNT];
	private static final ExtremeValue evs2[] = new ExtremeValue[EVS_COUNT];
	private static Random r;

	@Before
	public void setUp()
	{
		r = new Random(87222923092344353l);

		ExtremeValue.extremeType types[] = ExtremeValue.extremeType.values();
		for (int x = 0; x < EVS_COUNT; x++) {
			int stat = r.nextInt();
			double val = r.nextDouble();
			ExtremeValue.extremeType type = types[r.nextInt(types.length)];

			evs1[x] = new ExtremeValue("stat # " + stat, val, type);
			evs2[x] = new ExtremeValue("stat # " + stat, val, type);
		}
	}

	public void testEquals_parallel(ExtremeValue arr1[], ExtremeValue arr2[])
	{
		for (int a = 0; a < EVS_COUNT; a++) {
			for (int b = 0; b < EVS_COUNT; b++) {
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
		for (int x = 0; x < EVS_COUNT; x++) {
			assertTrue(Objects.equals(evs1[x], evs1[x]));
		}
	}

	@Test
	public void testEquals_clone()
	{
		for (int x = 0; x < EVS_COUNT; x++) {
			assertTrue(Objects.equals(evs1[x], evs2[x]));
		}
	}

	@Test
	public void testEquals_notOther()
	{
		for (int a = 0; a < EVS_COUNT; a++) {
			for (int b = 0; b < EVS_COUNT; b++) {
				if (a == b) {
					continue;
				}
				assertFalse(Objects.equals(evs1[a], evs2[b]));
			}
		}
	}

	@Test
	public void testEquals_null()
	{
		for (int x = 0; x < EVS_COUNT; x++) {
			assertFalse(Objects.equals(evs1[x], null));
		}
	}

	@Test
	public void testEquals_String()
	{
		for (int x = 0; x < EVS_COUNT; x++) {
			assertFalse(Objects.equals(evs1[x], "iouwerkljsd"));
		}
	}

	@Test
	public void testEquals_nullStat()
	{
		for (int x = 0; x < EVS_COUNT; x++) {
			evs1[x].stat = null;
			evs2[x].stat = null;
		}
		testEquals_parallel(evs1, evs2);
	}

	@Test
	public void testEquals_nullValue()
	{
		for (int x = 0; x < EVS_COUNT; x++) {
			evs1[x].value = null;
			evs2[x].value = null;
		}
		testEquals_parallel(evs1, evs2);
	}

	@Test
	public void testEquals_nullType()
	{
		for (int x = 0; x < EVS_COUNT; x++) {
			evs1[x].type = null;
			evs2[x].type = null;
		}
		testEquals_parallel(evs1, evs2);
	}
}
