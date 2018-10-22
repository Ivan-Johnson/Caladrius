package edu.ua.cs.cs495.caladrius.caladrius;

import android.util.Log;

import com.jjoe64.graphview.series.DataPoint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public class FitBit {
    static final String allValidStats[] = {
            "BPM",
            "Caloric intake",
            "Steps",
            "Weight"
    };
    /**
     * @return new instance of an array listing all valid stats
     */
    public static String[] getValidStats()
    {
        return Arrays.copyOf(allValidStats, allValidStats.length);
    }

    /**
     * @param stat ignored, for the moment
     * @return array of random points, sorted in ascending X order
     */
    public static DataPoint[] getPoints(String stat)
    {
        Random r = new Random();
        int numPoints = r.nextInt(10) + 5;
        ArrayList<DataPoint> points = new ArrayList<>();
        for (int x = 0; x < numPoints; x++) {
            DataPoint dp = new DataPoint(r.nextDouble() * 10, r.nextDouble() * 10);
            points.add(dp);
        }
        Log.i("YOLO", "Adding " + numPoints + " points.");
        DataPoint tmp[] = {};
        points.sort(new Comparator<DataPoint>() {
            @Override
            public int compare(DataPoint dataPoint, DataPoint t1) {
                if (t1.getX() - dataPoint.getX() < 0) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });

        return points.toArray(tmp);
    }
}
