package edu.ua.cs.cs495.caladrius.fitbit;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public interface FitbitInterface
{
	JSONArray getFitbitData(String stat,
	                        int timeType,
	                        String start,
	                        String end,
	                        int timeRange) throws JSONException, InterruptedException, ExecutionException, IOException;

	void logout();
}
