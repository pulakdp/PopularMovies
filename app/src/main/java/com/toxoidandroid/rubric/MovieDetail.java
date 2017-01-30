package com.toxoidandroid.rubric;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeStandalonePlayer;
import com.squareup.picasso.Picasso;

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

public class MovieDetail extends AppCompatActivity {

    public static String VIDEO_ID;
    public static final String LOG_TAG = MovieDetail.class.getSimpleName();
    private List<MovieVideo> mMovieVideos;
    RecyclerView videoList;
    ListAdapter mListAdapter;

    public MovieDetail() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        Movie movie = (Movie) intent.getSerializableExtra("MOVIE_OBJECT");

        VIDEO_ID = movie.getId();
        mMovieVideos = new ArrayList<>();
        videoList = (RecyclerView) findViewById(R.id.videoList);
        videoList.setLayoutManager(new LinearLayoutManager(this));

        TextView movieName = (TextView) findViewById(R.id.movieTitle);
        movieName.setText(movie.getTitle());

        ImageView movieImage = (ImageView) findViewById(R.id.movieImage);
        Picasso.with(this).load(movie.getThumbnailUrl()).placeholder(R.drawable.images)
                .error(R.drawable.images).into(movieImage);

        TextView releaseYear = (TextView) findViewById(R.id.movieReleaseYear);
        releaseYear.setText(movie.getReleaseYear());

        TextView rating = (TextView) findViewById(R.id.movieRating);
        rating.setText(movie.getUserRating() + "/10");

        TextView overview = (TextView) findViewById(R.id.movieOverview);
        overview.setText(movie.getSynopsis());

        String videosUrl = Constants.VIDEO_URL_1 + movie.getId() + Constants.VIDEO_URL_2;

        DownloadTrailerInfo downloadTrailerInfo = new DownloadTrailerInfo();
        downloadTrailerInfo.execute(videosUrl);

        videoList.addOnItemTouchListener(new RecyclerItemClickListener(this, videoList, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent;
                intent = YouTubeStandalonePlayer.createVideoIntent(
                        MovieDetail.this,
                        Constants.YOUTUBE_API_KEY,
                        mMovieVideos.get(position).getVideoKey());
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {
                onItemClick(view, position);
            }
        }));

    }

    public class DownloadTrailerInfo extends AsyncTask<String, Void, String> {

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
                    stringBuilder.append(line);
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
        protected void onPostExecute(String videoData) {
            if (videoData != null) {
                final String RESULTS = "results";
                final String MOVIE_ID = "id";
                final String VIDEO_ID = "id";
                final String NAME = "name";
                final String KEY = "key";
                final String SITE = "site";
                final String TYPE = "type";

                try {
                    JSONObject jsonObject = new JSONObject(videoData);
                    String movieId = jsonObject.getString(MOVIE_ID);
                    JSONArray jsonArray = jsonObject.getJSONArray(RESULTS);
                    for (int i = 0; i< jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        String videoId = object.getString(VIDEO_ID);
                        String name = object.getString(NAME);
                        String videoKey = object.getString(KEY);
                        String videoSite = object.getString(SITE);
                        String videoType = object.getString(TYPE);

                        MovieVideo movieVideo = new MovieVideo(movieId, name, videoId, videoKey, videoSite, videoType);
                        Log.d("VideoInfo", videoId + ", " + videoKey);
                        mMovieVideos.add(movieVideo);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }finally {
                    mListAdapter = new ListAdapter(getApplicationContext(), mMovieVideos);
                    mListAdapter.onNewData(mMovieVideos);
                    videoList.setAdapter(mListAdapter);
                }
            }
        }
    }

}
