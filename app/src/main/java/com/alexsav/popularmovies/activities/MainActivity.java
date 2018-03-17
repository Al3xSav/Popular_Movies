package com.alexsav.popularmovies.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.alexsav.popularmovies.R;
import com.alexsav.popularmovies.adapters.MoviesAdapter;
import com.alexsav.popularmovies.databinding.ActivityMainBinding;
import com.alexsav.popularmovies.model.Movies;
import com.alexsav.popularmovies.utils.ConnectionUtils;
import com.alexsav.popularmovies.utils.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.alexsav.popularmovies.utils.NetworkUtils.MOST_POPULAR;
import static com.alexsav.popularmovies.utils.NetworkUtils.TOP_RATED;

public class MainActivity extends AppCompatActivity implements MoviesAdapter.ListItemOnClickListener {

    private final List<Movies> mMoviesList = new ArrayList<>();
    private ActivityMainBinding mActivityMainBinding;
    private RecyclerView.Adapter mAdapter;
    // 0 for Popular Movies, 1 for Top Rated
    private int filter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        mActivityMainBinding.recyclerViewMovies.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        mActivityMainBinding.recyclerViewMovies.setLayoutManager(gridLayoutManager);

        /*
         * Check internet connection
         * If false show error
         * if true start the AsyncTask to fetch the movie data
         */
        if (!ConnectionUtils.isNetworkAvailable(this)) {

            mActivityMainBinding.noConnectionImg.setVisibility(View.VISIBLE);
            mActivityMainBinding.textViewNoInternet.setVisibility(View.VISIBLE);
            mActivityMainBinding.retryBtn.setVisibility(View.VISIBLE);

            Toast.makeText(this, "There Is No Internet Connection", Toast.LENGTH_SHORT).show();
            // TODO change the refresh button to swipe refresh for the Popular Movies part 2
            mActivityMainBinding.retryBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!ConnectionUtils.isNetworkAvailable(MainActivity.this)) {
                        mActivityMainBinding.progressBar.setVisibility(View.VISIBLE);
                        Toast.makeText(MainActivity.this, "Please Try Again", Toast.LENGTH_SHORT).show();
                    } else {
                        QueryUtils();
                        mActivityMainBinding.noConnectionImg.setVisibility(View.GONE);
                        mActivityMainBinding.textViewNoInternet.setVisibility(View.GONE);
                        mActivityMainBinding.retryBtn.setVisibility(View.GONE);
                    }
                }
            });

        } else {
            QueryUtils();
            mActivityMainBinding.noConnectionImg.setVisibility(View.GONE);
            mActivityMainBinding.textViewNoInternet.setVisibility(View.GONE);
            mActivityMainBinding.retryBtn.setVisibility(View.GONE);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (ConnectionUtils.isNetworkAvailable(this)) {
            switch (item.getItemId()) {
                // Response to a click on the "View By Popularity" menu option
                case R.id.by_most_popular:
                    if (filter == 0) {
                        return true;
                    } else {
                        mMoviesList.clear();
                        filter = 0;
                        QueryUtils();
                        return true;
                    }

                    // Response to a click on the "View By Ratings" menu option
                case R.id.by_top_rated:
                    if (filter == 1) {
                        return true;
                    } else {
                        mMoviesList.clear();
                        filter = 1;
                        QueryUtils();
                        return true;
                    }
            }
        } else {
            Toast.makeText(this, "Connection Lost.\nCheck Internet Connection", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        Intent intent = new Intent(this, DetailsActivity.class);

        intent.putExtra("MovieTitle", mMoviesList.get(clickedItemIndex).getTitle());
        intent.putExtra("MovieReleaseDate", mMoviesList.get(clickedItemIndex).getReleaseData());
        intent.putExtra("MoviePoster", mMoviesList.get(clickedItemIndex).getPosterUrl());
        intent.putExtra("MovieRating", mMoviesList.get(clickedItemIndex).getRating());
        intent.putExtra("MovieOverview", mMoviesList.get(clickedItemIndex).getOverview());

        startActivity(intent);
    }

    private void QueryUtils() {
        // By default we filter by Most Popular, else Top Rated
        if (filter == 0) {
            URL queryUtils = NetworkUtils.builderUrl(MOST_POPULAR);
            new MoviesAsyncTask().execute(queryUtils);
        } else {
            URL queryUtils = NetworkUtils.builderUrl(TOP_RATED);
            new MoviesAsyncTask().execute(queryUtils);
        }
    }

    private class MoviesAsyncTask extends AsyncTask<URL, Void, String> {

        @Override
        protected String doInBackground(URL... urls) {

            URL searchURL = urls[0];
            String searchResults = null;

            try {
                searchResults = NetworkUtils.getResponseFromHttpUrl(searchURL);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return searchResults;
        }

        @Override
        protected void onPostExecute(String searchResults) {

            if (searchResults != null && !searchResults.equals("")) {
            /*
             * Parsing JSON
             */
                try {
                    JSONObject theMovieDBJsonObject = new JSONObject(searchResults);
                    JSONArray resultJsonArray = theMovieDBJsonObject.getJSONArray("results");
                    // Extract the result of the JSON
                    for (int i = 0; i < resultJsonArray.length(); i++) {
                        JSONObject movieJsonObject = resultJsonArray.getJSONObject(i);

                        Movies movies = new Movies(
                                movieJsonObject.getString("title"),
                                movieJsonObject.getString("release_date"),
                                movieJsonObject.getString("poster_path"),
                                movieJsonObject.getString("vote_average"),
                                movieJsonObject.getString("overview")
                        );
                        mMoviesList.add(movies);
                    }

                    mAdapter = new MoviesAdapter(mMoviesList, getApplicationContext(), MainActivity.this);
                    mActivityMainBinding.recyclerViewMovies.setAdapter(mAdapter);

                } catch (JSONException e) {
                    Log.e("QueryUtilities", "Problem parsing the news JSON results", e);
                }
            }
        }
    }
}

