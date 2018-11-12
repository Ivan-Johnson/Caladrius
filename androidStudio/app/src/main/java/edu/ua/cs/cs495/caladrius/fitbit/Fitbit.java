package edu.ua.cs.cs495.caladrius.fitbit;

import android.os.AsyncTask;
import com.github.scribejava.apis.FitbitApi20;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;
import edu.ua.cs.cs495.caladrius.android.Caladrius;
import org.json.JSONArray;
import org.json.JSONObject;

public class Fitbit {

	public Fitbit() {
	}

	public JSONArray getFitbitData(String stat)
	{
		try{
			String ret = new MakeAnyCall().execute(String.format("https://api.fitbit.com/1/user/%s/activities/%s/date/2018-11-06/1w.json",
							Caladrius.user.fAcc.privateToken.getUserId(), stat)).get();
			JSONObject obj = new JSONObject(ret);

			return obj.getJSONArray("activities-" + stat);
		}
		catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}



	class MakeAnyCall extends AsyncTask<String, Void, String>
	{
		private final OAuth20Service service = new ServiceBuilder("22D7HK")
				.apiSecret("0eefb77c8b921283cb5e4477ac063178")
				.scope("activity heartrate location nutrition profile settings sleep social weight") // replace with desired scope
				//your callback URL to store and handle the authorization code sent by Fitbit
				.callback("caladrius://authcallback")
				//.state("some_params")
				.build(FitbitApi20.instance());


		protected String doInBackground(String... url) {
			try{
				/**
				 * Get Profile
				 */
				final OAuthRequest request = new OAuthRequest(Verb.GET, url[0]);


				request.addHeader("x-li-format", "json");

				service.signRequest(Caladrius.user.fAcc.getPrivateToken(), request);
				return service.execute(request).getBody();
			}
			catch (Exception e) {
				e.printStackTrace();
				return null;
			}

		}

		protected void onPostExecute(String response) {
			try {
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}
}