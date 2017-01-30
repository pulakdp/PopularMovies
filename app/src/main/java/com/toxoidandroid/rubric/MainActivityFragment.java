package com.toxoidandroid.rubric;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivityFragment extends Fragment {

    GridView mGridView;
    List<Movie> movies;
    ImageAdapter mImageAdapter;
    ProcessJsonData processJsonData;
    boolean isInternetAvailable = false;

    public MainActivityFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        movies = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        setHasOptionsMenu(true);
        mGridView = (GridView) rootView.findViewById(R.id.movieGrid);
        movies = new ArrayList<>();

        isInternetAvailable = Constants.hasInternetConnection(getContext());
        if (isInternetAvailable) {

            fetchJsonData(Constants.sortOrderSelected(getContext()));

            mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(getContext(), MovieDetail.class);
                    intent.putExtra("MOVIE_OBJECT", movies.get(position));
                    startActivity(intent);
                }
            });
        } else {
            Toast.makeText(getContext(), "Make sure you have an active internet connection. Close and Reopen the app once again", Toast.LENGTH_SHORT).show();
        }
        return rootView;
    }

    public void fetchJsonData(String sortOrder) {
        if (sortOrder.equals("POPULAR")) {
            processJsonData = new ProcessJsonData();
            processJsonData.execute(Constants.JSON_URL_POPULAR);
        } else if (sortOrder.equals("TOP_RATED")) {
            processJsonData = new ProcessJsonData();
            processJsonData.execute(Constants.JSON_URL_TOP_RATED);
        }
    }

    public class ProcessJsonData extends DownloadJsonData {

        @Override
        protected String doInBackground(String... params) {
            return super.doInBackground(params);
        }

        @Override
        protected void onPostExecute(String rawData) {
            super.onPostExecute(rawData);
            movies = new ArrayList<>();
            movies = processJsonData.getMovies();
            mImageAdapter = new ImageAdapter(getContext(), movies);
            mImageAdapter.onNewData(movies);
            mGridView.setAdapter(mImageAdapter);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
//        processJsonData = new ProcessJsonData();
//        processJsonData.execute(Constants.JSON_URL_POPULAR);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        if (Constants.sortOrderSelected(getContext()).equals("POPULAR")) {
            MenuItem menuItem = menu.getItem(0);
            menuItem.setChecked(true);
        } else {
            MenuItem menuItem = menu.getItem(1);
            menuItem.setChecked(true);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.popular) {
            if (!item.isChecked()) {
                item.setChecked(true);
                Constants.changeSortOrder(getContext(), "POPULAR");
                fetchJsonData(Constants.sortOrderSelected(getContext()));
            }
        } else if (id == R.id.top_rated) {
            if (!item.isChecked()) {
                item.setChecked(true);
                Constants.changeSortOrder(getContext(), "TOP_RATED");
                fetchJsonData(Constants.sortOrderSelected(getContext()));
            }
        }

        return super.onOptionsItemSelected(item);
    }
}
