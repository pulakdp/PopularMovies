package com.toxoidandroid.rubric;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;

public class Constants {

    public static final String API_KEY = "tba"; //TODO: Add API KEY
    public static final String BASE_JSON_URL_POPULAR = "http://api.themoviedb.org/3/movie/popular?api_key=";
    public static final String BASE_JSON_URL_TOP_RATED = "http://api.themoviedb.org/3/movie/top_rated?api_key=";
    public static final String BASE_IMAGE_URL = "http://image.tmdb.org/t/p/";
    public static final String IMAGE_QUALITY = "w185";
    public static final String JSON_URL_POPULAR = BASE_JSON_URL_POPULAR + API_KEY;
    public static final String JSON_URL_TOP_RATED = BASE_JSON_URL_TOP_RATED + API_KEY;
    public static final String IMAGE_URL = BASE_IMAGE_URL + IMAGE_QUALITY;

    public static final String YOUTUBE_API_KEY = "tba"; //TODO: Add API KEY
    public static final String VIDEO_URL_1 = "http://api.themoviedb.org/3/movie/";
    public static final String VIDEO_URL_2 = "/videos?api_key=" + API_KEY;

    public static boolean hasInternetConnection(Context context) {
        ConnectivityManager connectivityManager = ((ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE));

        return connectivityManager != null &&
                connectivityManager.getActiveNetworkInfo() != null &&
                connectivityManager.getActiveNetworkInfo().isConnected();
    }

    public static String sortOrderSelected(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("SORT_ORDER_PREFERENCE", Context.MODE_PRIVATE);
        return preferences.getString("SORT_ORDER", "POPULAR");
    }

    public static void changeSortOrder(Context context, String newOrder) {
        SharedPreferences preferences = context.getSharedPreferences("SORT_ORDER_PREFERENCE", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("SORT_ORDER", newOrder);
        editor.apply();
    }

}
