package edu.ua.cs.cs495.caladrius.android;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsClient;
import android.support.customtabs.CustomTabsIntent;
import android.support.customtabs.CustomTabsServiceConnection;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import com.github.scribejava.apis.FitbitApi20;
import com.github.scribejava.apis.fitbit.FitBitOAuth2AccessToken;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;

public class FitbitLogin extends AppCompatActivity
{

	private static final String PROTECTED_RESOURCE_URL = "https://api.fitbit.com/1/user/%s/profile.json";

	private static final OAuth20Service service = new ServiceBuilder("22D7HK")
				.apiSecret("0eefb77c8b921283cb5e4477ac063178")
				.scope("activity heartrate location nutrition profile settings sleep social weight") // replace with desired scope
				//your callback URL to store and handle the authorization code sent by Fitbit
				.callback("caladrius://authcallback")
				//.state("some_params")
				.build(FitbitApi20.instance());

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		//Intent pager = new Intent(this, PagerActivity.class);
		//startActivity(pager);

		Toast.makeText(this, "NewIntentSuccessful", Toast.LENGTH_SHORT).show();

		/*final String authorizationUrl = service.getAuthorizationUrl();


		CustomTabsServiceConnection connection = new CustomTabsServiceConnection() {
			@Override
			public void onCustomTabsServiceConnected(ComponentName componentName, CustomTabsClient client) {
				CustomTabsIntent intent = new CustomTabsIntent.Builder()
					.addDefaultShareMenuItem()
					.setShowTitle(true)
					.build();
				client.warmup(0L); // This prevents backgrounding after redirection
				intent.launchUrl(FitbitLogin.this, Uri.parse(authorizationUrl));//pass the url you need to open
			}

			@Override
			public void onServiceDisconnected(ComponentName name) {

			}
		};
		CustomTabsClient.bindCustomTabsService(this, "com.android.chrome", connection);//mention package name which can handle the CCT their many browser present.*/
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		Toast.makeText(this, "NewIntentSuccessful", Toast.LENGTH_SHORT).show();
		String action = intent.getAction();
		String data = intent.getDataString();//parse this to get the data
		if (Intent.ACTION_VIEW.equals(action) && data != null) {
			try{
				final OAuth2AccessToken oauth2AccessToken = service.getAccessToken(data);

				//if (!(oauth2AccessToken instanceof FitBitOAuth2AccessToken)) {
				//	System.out.println("oauth2AccessToken is not instance of FitBitOAuth2AccessToken. Strange enough. exit.");
				//	return;
				//}

				final FitBitOAuth2AccessToken accessToken = (FitBitOAuth2AccessToken) oauth2AccessToken;

				final OAuthRequest request = new OAuthRequest(Verb.GET,
					String.format(PROTECTED_RESOURCE_URL, accessToken.getUserId()));
				request.addHeader("x-li-format", "json");

				service.signRequest(accessToken, request);

				final Response response = service.execute(request);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
