package edu.ua.cs.cs495.caladrius.android;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.customtabs.CustomTabsClient;
import android.support.customtabs.CustomTabsIntent;
import android.support.customtabs.CustomTabsServiceConnection;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;
import com.github.scribejava.apis.FitbitApi20;
import com.github.scribejava.apis.fitbit.FitBitOAuth2AccessToken;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;
import edu.ua.cs.cs495.caladrius.fitbit.FitbitAccount;

public class FitbitLogin extends AppCompatActivity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);


		extractAuthCode(getIntent());
	}


	protected void extractAuthCode(Intent intent) {
		super.onNewIntent(intent);
		String action = intent.getAction();
		String data = intent.getDataString();//parse this to get the data
		String code = data.substring(data.indexOf("code=")+5, data.indexOf("#_=_"));
		if (Intent.ACTION_VIEW.equals(action) && data != null) {
			new RetrieveAuthToken().execute(code);
		}
	}


	class RetrieveAuthToken extends AsyncTask<String, Void, FitBitOAuth2AccessToken>
	{

		private final String PROTECTED_RESOURCE_URL = "https://api.fitbit.com/1/user/%s/profile.json";

		private final OAuth20Service service = new ServiceBuilder("22D7HK")
				.apiSecret("0eefb77c8b921283cb5e4477ac063178")
				.scope("activity heartrate location nutrition profile settings sleep social weight") // replace with desired scope
				//your callback URL to store and handle the authorization code sent by Fitbit
				.callback("caladrius://authcallback")
				//.state("some_params")
				.build(FitbitApi20.instance());


		protected FitBitOAuth2AccessToken doInBackground(String... code) {
			try{
				final OAuth2AccessToken oauth2AccessToken = service.getAccessToken(code[0]);

				FitBitOAuth2AccessToken accessToken = (FitBitOAuth2AccessToken) oauth2AccessToken;

				return accessToken;
			}
			catch (Exception e) {
				e.printStackTrace();
				return null;
			}

		}

		protected void onPostExecute(FitBitOAuth2AccessToken accessToken) {
			try {
				Caladrius.updateUser(accessToken);
				Intent intent = new Intent(FitbitLogin.this, SummaryPage.SummaryActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
				startActivity(intent);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}
}