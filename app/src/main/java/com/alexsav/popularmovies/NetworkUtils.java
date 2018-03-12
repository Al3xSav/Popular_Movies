package com.alexsav.popularmovies;

import android.net.Uri;
import android.util.Log;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/*
* A way to communicate with theMovieDB
*/
class NetworkUtils {

    // TAG to help catch errors in Log
    private static final String TAG = NetworkUtils.class.getSimpleName();

    /* CONSTANTS For The URL*/
    // Base URL
    private static final String MOVIES_URL = "https://api.themoviedb.org/3/movie";

    /*  The Top Rated Feed
     * "https://api.themoviedb.org/3/movie/top_rated?api_key=API_KEY&page=1"
     *
     *  The Popular Feed
     * "https://api.themoviedb.org/3/movie/popular?api_key=API_KEY&page=1"
     */
    public static final String TOP_RATED = "top_rated";
    public static final String MOST_POPULAR = "popular";

    // The Query Params
    private static final String API_KEY = "api_key";
    private static final String LANGUAGE= "language";
    private static final String PAGINATION = "page";

    private static final String api_key = "5d3a982ae67e166c4444515e2b2632b3";
    private static final String language = "en-GR";
    private static final int page = 1;

    public static URL builderUrl(String FILTER) {

        Uri uri = Uri.parse(MOVIES_URL).buildUpon()
                .appendPath(FILTER)
                .appendQueryParameter(API_KEY, api_key)
                .appendQueryParameter(LANGUAGE, language)
                .appendQueryParameter(PAGINATION, Integer.toString(page))
                .build();

        URL url = null;
        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.v(TAG,"Built URI" + url );

        return url;
    }

    // Helper method by Udacity
    public static String getResponseFromHttpUrl (URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream inputStream = urlConnection.getInputStream();

            Scanner scanner = new Scanner(inputStream);
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
