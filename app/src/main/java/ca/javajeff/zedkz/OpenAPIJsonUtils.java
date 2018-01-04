package ca.javajeff.zedkz;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Саддам on 02.01.2018.
 */

public class OpenAPIJsonUtils {
    public static JSONArray Arr;
    public static void getSimpleInfoStringsFromJson(Context context, String apiJsonStr)
            throws JSONException {

        final String OWM_RESULTS = "results";

        final String OWM_GENRES = "genres";

        /* Max temperature for the day */
        final String OWM_ID = "id";
        final String OWM_FIRST = "first_name";
        final String OWM_LAST = "last_name";
        final String OWM_EMAIL = "email";
        final String OWM_GENDER = "gender";
        final String OWM_IP = "ip_address";
        final String OWM_PHOTO = "photo";
        final String OWM_EMPLOY = "employment";
        final String OWM_JOB = "name";
        final String OWM_POS = "position";

        String[] parsedMovieData = null;
        Arr = new JSONArray(apiJsonStr);


        /* Is there an error? */

    }

}
