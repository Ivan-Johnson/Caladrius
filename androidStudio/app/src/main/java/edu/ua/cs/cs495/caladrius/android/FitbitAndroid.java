package edu.ua.cs.cs495.caladrius.android;

import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;
import com.github.scribejava.apis.FitbitApi20;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.oauth.OAuth20Service;
import edu.ua.cs.cs495.caladrius.fitbit.Fitbit;
import edu.ua.cs.cs495.caladrius.fitbit.FitbitInterface;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 *
 */
public class FitbitAndroid implements FitbitInterface
{

	public FitbitAndroid()
	{
	}

	public static String[] getSupportedStats(Context context)
	{
		Resources resources = context.getResources();
		String[] stats = resources.getStringArray(R.array.array_graph_stats_options);
		return stats;
	}

	public JSONArray getFitbitData(String stat,
	                               int timeType,
	                               String start,
	                               String end,
	                               int timeRange) throws JSONException, InterruptedException, ExecutionException, IOException
	{
		return new GetDataCall().execute(stat, String.valueOf(timeType), start, end, String.valueOf(timeRange))
		                        .get();
	}

	public void logout()
	{
		new RevokeToken().execute();
	}


	class GetDataCall extends AsyncTask<String, Void, JSONArray>
	{
		private final OAuth20Service service = new ServiceBuilder("22D7HK")
			.apiSecret("0eefb77c8b921283cb5e4477ac063178")
			.scope("activity heartrate location nutrition profile settings sleep social weight") // replace with desired scope
			//your callback URL to store and handle the authorization code sent by Fitbit
			.callback("caladrius://authcallback")
			//.state("some_params")
			.build(FitbitApi20.instance());


		protected JSONArray doInBackground(String... args)
		{
			try {
				return new Fitbit().getFitbitData(Caladrius.getUser().fAcc,
					args[0],
					Integer.parseInt(args[1]),
					args[2],
					args[3],
					Integer.parseInt(args[4]));
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
	}


	class RevokeToken extends AsyncTask<Void, Void, Void>
	{

		private final OAuth20Service service = new ServiceBuilder("22D7HK")
			.apiSecret("0eefb77c8b921283cb5e4477ac063178")
			.scope("activity heartrate location nutrition profile settings sleep social weight") // replace with desired scope
			//your callback URL to store and handle the authorization code sent by Fitbit
			.callback("caladrius://authcallback")
			//.state("some_params")
			.build(FitbitApi20.instance());


		protected Void doInBackground(Void... things)
		{
			try {
				service.revokeToken(Caladrius.getUser().fAcc.getPrivateToken()
				                                            .toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void v)
		{
			try {
				Caladrius.updateUser(null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}