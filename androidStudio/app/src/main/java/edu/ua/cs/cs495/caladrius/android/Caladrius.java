package edu.ua.cs.cs495.caladrius.android;

import android.app.Application;
import android.content.Context;
import edu.ua.cs.cs495.caladrius.User;

/**
 * Logic check page of the the app with no error.
 */

public class Caladrius extends Application
{
	protected static Context cntxt;
	public static User user = new User();

	public static Context getContext()
	{
		if (cntxt == null) {
			throw new RuntimeException(Caladrius.class.toString() + " was not properly initialized");
		}
		return cntxt;
	}

	@Override
	public void onCreate()
	{
		super.onCreate();

		if (cntxt != null) {
			throw new RuntimeException(Caladrius.class.toString() + " was initialized multiple times");
		}

		cntxt = getApplicationContext();
	}
}
