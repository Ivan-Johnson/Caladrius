package edu.ua.cs.cs495.caladrius.fitbit;

import android.util.Log;
import com.github.scribejava.apis.FitbitApi20;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;
import edu.ua.cs.cs495.caladrius.android.Caladrius;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class Fitbit{

	public Fitbit() {
	}

	private final OAuth20Service service = new ServiceBuilder("22D7HK")
			.apiSecret("0eefb77c8b921283cb5e4477ac063178")
			.scope("activity heartrate location nutrition profile settings sleep social weight") // replace with desired scope
			//your callback URL to store and handle the authorization code sent by Fitbit
			.callback("caladrius://authcallback")
			//.state("some_params")
			.build(FitbitApi20.instance());

	public PseudoResponse getFitbitData(FitbitAccount acc, String stat) throws JSONException, InterruptedException, ExecutionException, IOException
	{
		final OAuthRequest request = new OAuthRequest(Verb.GET, String.format("https://api.fitbit.com/1/user/%s/activities/%s/date/2018-10-07/1w.json",
				acc.privateToken.getUserId(), stat));


		request.addHeader("x-li-format", "json");

		service.signRequest(acc.getPrivateToken(), request);
		Response response = service.execute(request);
		//Log.w("TAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAG",response.getHeaders().toString());
		return new PseudoResponse(response.getBody(), response.getCode());
	}

	public class PseudoResponse{
	    public String body;
	    public int code;

	    public PseudoResponse(String b, int c){
	        this.body = b;
	        this.code = c;
        }
    }
}