package edu.ua.cs.cs495.caladrius.fitbit;

import android.support.annotation.NonNull;

import java.util.Date;

/**
 * A custom point class for FitbitAccount.
 * <p>
 * Ideally, we'd just use GraphView's point class, but it would be impracticle to use that on Caladrius' server.
 * <p>
 * As a close second, we'd just use java.awt.Point, but Android doesn't support awt.
 * <p>
 * Thus, the need for a custom Point class.
 */
public class Point implements Comparable
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

	@Override
	public int compareTo(@NonNull Object o)
	{
		Point other = (Point) o;
		if (isXDate() != other.isXDate()) {
			throw new RuntimeException("Cannot compare points of different X types");
		}
		return (int) Math.signum(getX() - other.getX());
	}
}
