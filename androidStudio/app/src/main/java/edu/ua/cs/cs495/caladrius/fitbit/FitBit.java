package edu.ua.cs.cs495.caladrius.fitbit;

import java.util.*;

public class FitBit {
    /**
     * A custom point class for FitBit.
     *
     * Ideally, we'd just use GraphView's point class, but it would be impracticle to use that on Caladrius' server.
     *
     * As a close second, we'd just use java.awt.Point, but Android doesn't support awt.
     *
     * Thus, the need for a custom Point class.
     */
    public static class Point
    {
        protected boolean xIsDate;
        protected double fX, y;
        protected long iX;

        public Point(double x, double y)
        {
            this.fX = x;
            this.y = y;
            xIsDate = false;
        }

        public Point(Date x, double y)
        {
            xIsDate = true;
            this.iX = x.getTime();
            this.y = y;
        }

        public Date getXAsDate()
        {
            if (xIsDate) {
                return new Date(iX);
            } else {
                throw new IllegalStateException("Attempting to get X as a date while it is not a date");
            }
        }

        public boolean isXDate()
        {
            return xIsDate;
        }

        public double getX()
        {
            if (xIsDate) {
                return (double) iX;
            } else {
                return fX;
            }
        }

        public double getY()
        {
            return y;
        }
    }
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
    public static Point[] getPoints(String stat)
    {
        Random r = new Random();
        int numPoints = r.nextInt(10) + 5;
        ArrayList<Point> points = new ArrayList<>();
        for (int x = 0; x < numPoints; x++) {
            Point dp = new Point(r.nextDouble() * 10, r.nextDouble() * 10);
            points.add(dp);
        }
        Point tmp[] = {};
        points.sort(new Comparator<Point>() {
            @Override
            public int compare(Point dataPoint, Point t1) {
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
