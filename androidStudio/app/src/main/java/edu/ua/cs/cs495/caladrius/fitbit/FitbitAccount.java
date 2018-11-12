package edu.ua.cs.cs495.caladrius.fitbit;

import com.github.scribejava.apis.fitbit.FitBitOAuth2AccessToken;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class FitbitAccount implements Serializable
{
	private static final long serialVersionUID = -1474806684094301473L;

	static final String allValidStats[] = {
		"BPM",
		"Caloric intake",
		"Steps",
		"Weight"
	};

	protected FitBitOAuth2AccessToken privateToken;

	public FitbitAccount()
	{
		this.privateToken = null;
	}

	public FitbitAccount(FitBitOAuth2AccessToken privateToken)
	{
		this.privateToken = privateToken;
	}

	public FitBitOAuth2AccessToken getPrivateToken()
	{
		return this.privateToken;
	}

	public void setPrivateToken(FitBitOAuth2AccessToken token)
	{
		this.privateToken = token;
	}

	/**
	 * @return new instance of an array listing all valid stats
	 */
	public String[] getValidStats()
	{
		return Arrays.copyOf(allValidStats, allValidStats.length);
	}

	/**
	 * @param stat ignored, for the moment
	 * @return array of random points, sorted in ascending X order
	 */
	public Point[] getPoints(String stat)
	{
		Random r = new Random();
		int numPoints = r.nextInt(10) + 5;
		ArrayList<Point> points = new ArrayList<>();
		for (int x = 0; x < numPoints; x++) {
			Point dp = new Point(r.nextDouble() * 10, r.nextDouble() * 10);
			points.add(dp);
		}

		Collections.sort(points);

		Point tmp[] = {};
		return points.toArray(tmp);
	}
}
