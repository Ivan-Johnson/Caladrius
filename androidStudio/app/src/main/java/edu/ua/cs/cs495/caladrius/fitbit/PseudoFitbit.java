package edu.ua.cs.cs495.caladrius.fitbit;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class PseudoFitbit implements FitbitInterface {
    public JSONArray getFitbitData(String stat) throws JSONException, InterruptedException, ExecutionException, IOException
    {
        switch (stat)
        {
            case "calories": return new JSONArray("[{\"dateTime\":\"2018-10-01\",\"value\":\"677\"},{\"dateTime\":\"2018-10-02\",\"value\":\"663\"},{\"dateTime\":\"2018-10-03\",\"value\":\"840\"},{\"dateTime\":\"2018-10-04\",\"value\":\"687\"},{\"dateTime\":\"2018-10-05\",\"value\":\"830\"},{\"dateTime\":\"2018-10-06\",\"value\":\"690\"},{\"dateTime\":\"2018-10-07\",\"value\":\"579\"}]");

            case "steps": return new JSONArray("[{\"dateTime\":\"2018-10-01\",\"value\":\"753455\"},{\"dateTime\":\"2018-10-02\",\"value\":\"510761\"},{\"dateTime\":\"2018-10-03\",\"value\":\"553379\"},{\"dateTime\":\"2018-10-04\",\"value\":\"378001\"},{\"dateTime\":\"2018-10-05\",\"value\":\"453660\"},{\"dateTime\":\"2018-10-06\",\"value\":\"458941\"},{\"dateTime\":\"2018-10-07\",\"value\":\"536136\"}]");

            case "caloriesBMR": return new JSONArray("[{\"dateTime\":\"2018-10-01\",\"value\":\"1067\"},{\"dateTime\":\"2018-10-02\",\"value\":\"1067\"},{\"dateTime\":\"2018-10-03\",\"value\":\"1067\"},{\"dateTime\":\"2018-10-04\",\"value\":\"1067\"},{\"dateTime\":\"2018-10-05\",\"value\":\"1067\"},{\"dateTime\":\"2018-10-06\",\"value\":\"1067\"},{\"dateTime\":\"2018-10-07\",\"value\":\"1067\"}]");

            case "minutesSedentary": return new JSONArray("[{\"dateTime\":\"2018-10-01\",\"value\":\"695\"},{\"dateTime\":\"2018-10-02\",\"value\":\"745\"},{\"dateTime\":\"2018-10-03\",\"value\":\"696\"},{\"dateTime\":\"2018-10-04\",\"value\":\"653\"},{\"dateTime\":\"2018-10-05\",\"value\":\"647\"},{\"dateTime\":\"2018-10-06\",\"value\":\"684\"},{\"dateTime\":\"2018-10-07\",\"value\":\"880\"}]");

            case "minutesLightlyActive": return new JSONArray("[{\"dateTime\":\"2018-10-01\",\"value\":\"115\"},{\"dateTime\":\"2018-10-02\",\"value\":\"230\"},{\"dateTime\":\"2018-10-03\",\"value\":\"43\"},{\"dateTime\":\"2018-10-04\",\"value\":\"248\"},{\"dateTime\":\"2018-10-05\",\"value\":\"156\"},{\"dateTime\":\"2018-10-06\",\"value\":\"140\"},{\"dateTime\":\"2018-10-07\",\"value\":\"129\"}]");

            case "minutesFairlyActive": return new JSONArray("[{\"dateTime\":\"2018-10-01\",\"value\":\"68\"},{\"dateTime\":\"2018-10-02\",\"value\":\"29\"},{\"dateTime\":\"2018-10-03\",\"value\":\"0\"},{\"dateTime\":\"2018-10-04\",\"value\":\"25\"},{\"dateTime\":\"2018-10-05\",\"value\":\"0\"},{\"dateTime\":\"2018-10-06\",\"value\":\"47\"},{\"dateTime\":\"2018-10-07\",\"value\":\"60\"}]");

            case "minutesVeryActive": return new JSONArray("[{\"dateTime\":\"2018-10-01\",\"value\":\"562\"},{\"dateTime\":\"2018-10-02\",\"value\":\"436\"},{\"dateTime\":\"2018-10-03\",\"value\":\"701\"},{\"dateTime\":\"2018-10-04\",\"value\":\"514\"},{\"dateTime\":\"2018-10-05\",\"value\":\"637\"},{\"dateTime\":\"2018-10-06\",\"value\":\"569\"},{\"dateTime\":\"2018-10-07\",\"value\":\"371\"}]");

            case "activityCalories": return new JSONArray("[{\"dateTime\":\"2018-10-01\",\"value\":\"4038\"},{\"dateTime\":\"2018-10-02\",\"value\":\"3905\"},{\"dateTime\":\"2018-10-03\",\"value\":\"5128\"},{\"dateTime\":\"2018-10-04\",\"value\":\"4132\"},{\"dateTime\":\"2018-10-05\",\"value\":\"5099\"},{\"dateTime\":\"2018-10-06\",\"value\":\"4130\"},{\"dateTime\":\"2018-10-07\",\"value\":\"3239\"}]");

            default: throw new IllegalArgumentException("ARE YOU AN IDIOT?? THAT IS NOT A VALID ARGUMENT FOR PSEUDOFITBIT GETFITBITDATA! SHAME ON YOU!!");
        }
    }
}
