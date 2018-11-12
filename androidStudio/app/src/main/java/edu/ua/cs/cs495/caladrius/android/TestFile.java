package edu.ua.cs.cs495.caladrius.android;

import java.util.*;

import android.os.AsyncTask;
import com.github.scribejava.apis.FitbitApi20;
import com.github.scribejava.apis.fitbit.FitBitOAuth2AccessToken;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;
import edu.ua.cs.cs495.caladrius.android.Caladrius;
import org.json.JSONArray;
import org.json.JSONObject;
import android.os.AsyncTask;
import com.github.scribejava.apis.FitbitApi20;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;

import static edu.ua.cs.cs495.caladrius.android.QueryEditor.getMonthForInt;

// This file just use for testing code

public class TestFile
{
	private static final String NETWORK_NAME = "Fitbit";

	private static final String PROTECTED_RESOURCE_URL = "https://api.fitbit.com/1/user/%s/profile.json";
	private static OAuth20Service service = new ServiceBuilder("22D7HK")
			.apiSecret("0eefb77c8b921283cb5e4477ac063178")
			.scope("activity heartrate location nutrition profile settings sleep social weight") // replace with desired scope
			//your callback URL to store and handle the authorization code sent by Fitbit
			.callback("caladrius://authcallback")
			//.state("some_params")
			.build(FitbitApi20.instance());

	public static void main(String... args) throws Exception
	{
//        System.out.println("Hello, this is Java");
//        System.out.println(getMonthForInt(10));
//        String date = getBackupFolderName();
//        System.out.println(date.substring(0, date.length()-5) + "th" +
//                date.substring(date.length()-5, date.length()));

		LocalDateTime now = LocalDateTime.now();
		int year = now.getYear();
		int month = now.getMonthValue();
		int day = now.getDayOfMonth();
		int hour = now.getHour();
		int minute = now.getMinute();
		int second = now.getSecond();
		int millis = now.get(ChronoField.MILLI_OF_SECOND); // Note: no direct getter available.

		System.out.printf("%s %02d %02d ", getMonthForInt(month), day, year);
		String[] array = new String[] {"John", "Mary", "Bob"};
		System.out.println(Arrays.toString(array));
		// Replace these with your client id and secret fron your app
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

		//GenerateRandomData(accessToken);

		final OAuthRequest request = new OAuthRequest(Verb.GET, String.format("https://api.fitbit.com/1/user/%s/activities/steps/date/2018-10-07/1w.json", accessToken.getUserId()));

		request.addHeader("x-li-format", "json");
		service.signRequest(accessToken, request);
		Response response = service.execute(request);

		System.out.println();
		System.out.println(response.getCode());
		System.out.println(response.getBody());
		System.out.println();
>>>>>>> c08146b4d7c1ca462e25fe1d65dfbf93663ba4a2
	}

	/*static String getMonthForInt(int m)
	{
		List<String> monthStr = Arrays.asList("Jan", "Feb", "Mar", "Apr", "May", "Jun",
				"Jul", "Aug", "Sep", "Oct", "Nov", "De-c");
		return monthStr.get(m);
	}

	public static String getBackupFolderName()
	{
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("MMM dd yyyy");
		return sdf.format(date);
	}*/

	public static void GenerateRandomData(FitBitOAuth2AccessToken accessToken)
	{
		Random r = new Random();
		for (int i = 1; i <= 7; i++)
		{
			for (int j = 0; j < 24; j++)
			{
				/**
				 * Random r = new Random();
				 * 		int numPoints = r.nextInt(10) + 5;
				 * 		ArrayList<Point> points = new ArrayList<>();
				 * 		for (int x = 0; x < numPoints; x++) {
				 * 			Point dp = new Point(r.nextDouble()
				 */
				try {
					final OAuthRequest request = new OAuthRequest(Verb.POST, String.format("https://api.fitbit.com/1/user/%s/activities.json", accessToken.getUserId()));
					if(r.nextInt(10) % 2 == 0)
						request.addParameter("activityId", "90013");
					else
						request.addParameter("activityId", "90009");
					request.addParameter("startTime", String.format("%s:00:00", j));
					request.addParameter("durationMillis", String.format("%s", (r.nextInt(36)+1)*100000));
					request.addParameter("date", String.format("2018-09-0%s", i));
					request.addParameter("distance", String.format("%s.%s%s", r.nextInt(10), r.nextInt(10), r.nextInt(9)+1));

					request.addHeader("x-li-format", "json");
					service.signRequest(accessToken, request);
					service.execute(request);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}
	}
}

