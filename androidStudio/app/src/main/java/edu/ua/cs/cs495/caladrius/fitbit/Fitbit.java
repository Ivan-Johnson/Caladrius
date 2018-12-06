package edu.ua.cs.cs495.caladrius.fitbit;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.github.scribejava.apis.FitbitApi20;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;

public class Fitbit{
	public static final int TIME_RANGE_TODAY= 0;
	public static final int TIME_RANGE_WEEK= 1;
	public static final int TIME_RANGE_MONTH= 2;
	public static final int TIME_RANGE_YEAR= 3;
	public static final int TIME_RANGE_TYPE_SINGLE = 0;
	public static final int TIME_RANGE_TYPE_SEVERAL = 1;
	public static final int TIME_RANGE_TYPE_RELATIVE = 2;

	public Fitbit() {
	}

	private static final OAuth20Service service = new ServiceBuilder("22D7HK")
			.apiSecret("0eefb77c8b921283cb5e4477ac063178")
			.scope("activity heartrate location nutrition profile settings sleep social weight") // replace with desired scope
			//your callback URL to store and handle the authorization code sent by Fitbit
			.callback("caladrius://authcallback")
			//.state("some_params")
			.build(FitbitApi20.instance());

	public static JSONArray getFitbitData(FitbitAccount acc, String stat, int timeType, String start, String end, int timeRange) throws JSONException, InterruptedException, ExecutionException, IOException
	{
		String url = "";
		switch (timeType) {
			case TIME_RANGE_TYPE_SINGLE:
				url = String.format("https://api.fitbit.com/1/user/%s/activities/%s/date/%s/1d.json", acc.privateToken.getUserId(), stat, getReformattedData(start));
				break;
			case TIME_RANGE_TYPE_SEVERAL:
				url = String.format("https://api.fitbit.com/1/user/%s/activities/%s/date/%s/%s.json", acc.privateToken.getUserId(), stat, getReformattedData(start), getReformattedData(end));
				break;
			case TIME_RANGE_TYPE_RELATIVE:
				String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
				url = String.format("https://api.fitbit.com/1/user/%s/activities/%s/date/%s/%s.json", acc.privateToken.getUserId(), stat, date, getTimeRange(timeRange));
				break;
		}

		System.out.println(url);
		final OAuthRequest request = new OAuthRequest(Verb.GET, url);


		request.addHeader("x-li-format", "json");

		service.signRequest(acc.getPrivateToken(), request);
		Response response_tmp = service.execute(request);
		PseudoResponse response = new PseudoResponse(response_tmp.getBody(), response_tmp.getCode());
		//Log.w("TAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAG",response.getHeaders().toString());
		if (response.code > 299 || response.code < 200)
			throw new IOException(response.body);

		JSONObject obj = new JSONObject(response.body);
		return obj.getJSONArray("activities-" + stat);
	}

	protected static String getTimeRange(int timeRange) {
		switch (timeRange) {
			case TIME_RANGE_TODAY:
				return "1d";
			case TIME_RANGE_WEEK:
				return "1w";
			case TIME_RANGE_MONTH:
				return "1m";
			case TIME_RANGE_YEAR:
				return "1y";
			default:
				return "1d";
		}
	}

	protected static String getReformattedData(String oldDate) {
		final SimpleDateFormat sdf1 = new SimpleDateFormat("MMM dd'th' yyyy");
		Date date = new Date();
		try {
			date = sdf1.parse(oldDate);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(date);
	}

	public static class PseudoResponse{
	    public String body;
	    public int code;

	    public PseudoResponse(String b, int c){
	        this.body = b;
	        this.code = c;
        }
    }
}