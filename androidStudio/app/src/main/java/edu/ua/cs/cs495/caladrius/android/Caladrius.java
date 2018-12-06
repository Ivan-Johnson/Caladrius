package edu.ua.cs.cs495.caladrius.android;

import android.app.Application;
import android.content.Context;
import com.github.scribejava.apis.fitbit.FitBitOAuth2AccessToken;
import edu.ua.cs.cs495.caladrius.User;
import edu.ua.cs.cs495.caladrius.fitbit.FitbitInterface;
import edu.ua.cs.cs495.caladrius.server.ServerAccount;

/**
 * Logic check page of the the app with no error.
 */

public class Caladrius extends Application
{
	public static final String sharedPreferences = Caladrius.class.getCanonicalName() + "+SharedPreference";
	public static FitbitInterface fitbitInterface = null;
	protected static Context cntxt;
	private static User user = new User();

	public static User getUser()
	{
		return user;
	}

	public static void updateUser(FitBitOAuth2AccessToken token)
	{
		user.initialize(token);
	}

	public static void setNoLogin()
	{
		user.fAcc = null;
		user.sAcc = new ServerAccount("NoLogin");
	}

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
