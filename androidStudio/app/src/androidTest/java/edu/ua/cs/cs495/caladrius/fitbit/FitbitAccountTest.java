package edu.ua.cs.cs495.caladrius.fitbit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static junit.framework.Assert.*;

public class FitbitAccountTest
{
    //TODO create a FitBit account specifically for testing
    FitbitAccount.Point points[];

    @Before
    public void setUp()
    {
        points = FitbitAccount.getPoints("asdf");
    }

    @After
    public void tearDown()
    {
        points = null;
    }

    @Test
    public void getPoints_exists() {
        assertNotNull(points);
        assertTrue(points.length > 2);
    }

    @Test
    public void getPoints_isSorted() {
        double xOld = points[0].getX();
        for (int i = 0; i < points.length; i++) {
            double xNew = points[i].getX();
            assertTrue(xOld <= xNew);
            xOld = xNew;
        }
    }

    @Test
    public void getValidStats_isNewInstance()
    {
        String arr1[] = FitbitAccount.getValidStats();
        String arr2[] = FitbitAccount.getValidStats();
        assertTrue(Arrays.equals(arr1, arr2));
        arr1[0] = "";
        assertFalse(Arrays.equals(arr1, arr2));
    }
}
