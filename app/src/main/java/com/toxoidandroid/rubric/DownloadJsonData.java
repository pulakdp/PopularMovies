package com.toxoidandroid.rubric;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class DownloadJsonData extends AsyncTask<String, Void, String> {

    private static final String LOG_TAG = DownloadJsonData.class.getSimpleName();
    private List<Movie> mMovies;

    DownloadJsonData() {
        mMovies = new ArrayList<>();
    }

    public List<Movie> getMovies() {
        return this.mMovies;
    }

    public List<String> getPosters() {
        List<String> thumbnails = new ArrayList<>();
        for (int i = 0; i < mMovies.size(); i++) {
            thumbnails.add(mMovies.get(i).getThumbnailUrl());
        }
        return thumbnails;
    }

    @Override
    protected String doInBackground(String... params) {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        StringBuilder stringBuilder = new StringBuilder();

        try {
            URL url = new URL(params[0]);

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }
            return stringBuilder.toString();

        } catch (IOException e) {
            Log.d(LOG_TAG, "Error fetching json data", e);
            return null;
        } finally {
            if (urlConnection != null)
                urlConnection.disconnect();
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Log.d(LOG_TAG, "Error Closing Stream");
                }
            }
        }
    }

    @Override
    protected void onPostExecute(String rawData) {
        if (rawData != null) {
            final String RESULTS = "results";
            final String POSTER_PATH = "poster_path";
            final String OVERVIEW = "overview";
            final String RELEASE_DATE = "release_date";
            final String ID = "id";
            final String TITLE = "original_title";
            final String VOTE_AVERAGE = "vote_average";

            try {
                JSONObject jsonObject = new JSONObject(rawData);
                JSONArray jsonArray = jsonObject.getJSONArray(RESULTS);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject movieObject = jsonArray.getJSONObject(i);
                    String id = movieObject.getString(ID);
                    String posterPath = Constants.IMAGE_URL + movieObject.getString(POSTER_PATH);
                    String overview = movieObject.getString(OVERVIEW);
                    String releaseDate = movieObject.getString(RELEASE_DATE);
                    String title = movieObject.getString(TITLE);
                    String rating = movieObject.getString(VOTE_AVERAGE);

                    String[] year = releaseDate.split("-");

                    Movie aMovie = new Movie(id, title, posterPath, overview, rating, year[0]);
                    this.mMovies.add(aMovie);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}
