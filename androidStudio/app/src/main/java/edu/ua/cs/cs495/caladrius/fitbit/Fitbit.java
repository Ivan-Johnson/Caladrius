package edu.ua.cs.cs495.caladrius.fitbit;

import java.util.Scanner;

import com.github.scribejava.apis.FitbitApi20;
import com.github.scribejava.apis.fitbit.FitBitOAuth2AccessToken;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;

public class Fitbit {

	private static final String NETWORK_NAME = "Fitbit";

	private static final String PROTECTED_RESOURCE_URL = "https://api.fitbit.com/1/user/%s/profile.json";

	private Fitbit() {
	}

	public static void main(String... args) throws Exception {

		// Replace these with your client id and secret fron your app
		final String clientId = "22D7HK";
		final String clientSecret = "0eefb77c8b921283cb5e4477ac063178";
		final OAuth20Service service = new ServiceBuilder(clientId)
			.apiSecret(clientSecret)
			.scope("activity heartrate location nutrition profile settings sleep social weight") // replace with desired scope
			//your callback URL to store and handle the authorization code sent by Fitbit
			.callback("caladrius://authcallback")
			//.state("some_params")
			.build(FitbitApi20.instance());
		final Scanner in = new Scanner(System.in);

		System.out.println("=== " + NETWORK_NAME + "'s OAuth Workflow ===");
		System.out.println();

		// Obtain the Authorization URL
		System.out.println("Fetching the Authorization URL...");
		final String authorizationUrl = service.getAuthorizationUrl();
		System.out.println("Got the Authorization URL!");
		System.out.println("Now go and authorize ScribeJava here:");
		System.out.println(authorizationUrl);
		System.out.println("And paste the authorization code here");
		System.out.print(">>");
		final String code = in.nextLine();
		System.out.println();

		// Trade the Request Token and Verfier for the Access Token
		System.out.println("Trading the Request Token for an Access Token...");
		final OAuth2AccessToken oauth2AccessToken = service.getAccessToken(code);
		System.out.println("Got the Access Token!");
		System.out.println("(if your curious it looks like this: " + oauth2AccessToken
			+ ", 'rawResponse'='" + oauth2AccessToken.getRawResponse() + "')");
		System.out.println();

		if (!(oauth2AccessToken instanceof FitBitOAuth2AccessToken)) {
			System.out.println("oauth2AccessToken is not instance of FitBitOAuth2AccessToken. Strange enough. exit.");
			return;
		}

		final FitBitOAuth2AccessToken accessToken = (FitBitOAuth2AccessToken) oauth2AccessToken;
		// Now let's go and ask for a protected resource!
		// This will get the profile for this user
		System.out.println("Now we're going to access a protected resource...");

		final OAuthRequest request = new OAuthRequest(Verb.GET,
			String.format(PROTECTED_RESOURCE_URL, accessToken.getUserId()));
		request.addHeader("x-li-format", "json");

		service.signRequest(accessToken, request);

		final Response response = service.execute(request);
		System.out.println();
		System.out.println(response.getCode());
		System.out.println(response.getBody());

		System.out.println();
	}
}