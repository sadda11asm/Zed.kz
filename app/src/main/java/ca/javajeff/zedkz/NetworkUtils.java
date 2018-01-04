package ca.javajeff.zedkz;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by Саддам on 02.01.2018.
 */

public class NetworkUtils {

    private static final String API_URL =
            "http://www.mocky.io/v2/5a488f243000004c15c3c57e";

    public static URL buildUrl() {
        // TODO (1) Fix this method to return the URL used to query Open Weather Map's API
        Uri buildUri = Uri.parse(API_URL).buildUpon().build();
        URL url = null;
        try {
            url = new URL(buildUri.toString());
            Log.v("dfsdf", url.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return url;
    }
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
