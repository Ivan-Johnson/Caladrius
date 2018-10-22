package edu.ua.cs.cs495.caladrius.caladrius;

import com.jjoe64.graphview.series.DataPoint;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static junit.framework.Assert.*;

public class FitBitTest {
    //TODO create a FitBit account specifically for testing
    DataPoint points[];

    @Before
    public void setUp()
    {
        points = FitBit.getPoints("asdf");
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
        String arr1[] = FitBit.getValidStats();
        String arr2[] = FitBit.getValidStats();
        assertTrue(Arrays.equals(arr1, arr2));
        arr1[0] = "";
        assertFalse(Arrays.equals(arr1, arr2));
    }
}
