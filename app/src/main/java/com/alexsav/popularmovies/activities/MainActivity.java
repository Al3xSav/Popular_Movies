package com.alexsav.popularmovies.activities;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import org.parceler.Parcels;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.alexsav.popularmovies.R;
import com.alexsav.popularmovies.adapters.MoviesAdapter;
import com.alexsav.popularmovies.databinding.ActivityMainBinding;
import com.alexsav.popularmovies.model.*;
import com.alexsav.popularmovies.utils.*;

import static com.alexsav.popularmovies.activities.DetailsActivity.EXTRA_MOVIE_PARCELABLE;
import static com.alexsav.popularmovies.data.MoviesContract.FavoriteMoviesEntry.*;
import static com.alexsav.popularmovies.utils.ConnectionUtils.isNetworkAvailable;
import static com.alexsav.popularmovies.utils.NetworkUtils.*;

public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Cursor>, MoviesAdapter.ListItemOnClickListener {

    public static final String MENU_SELECTED = "menu_selected";
    private static final int LOADER_ID = 1050;
    public static boolean favoriteSorting;
    public ActivityMainBinding mActivityMainBinding;
    public MoviesAdapter mMoviesAdapter;
    public NetworkUtilsInterface mNetworkUtilsInterface;
    private List<Movies> mMoviesList;
    private int menuItemSelected = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        mActivityMainBinding.recyclerViewMovies.setLayoutManager(gridLayoutManager);
        mActivityMainBinding.recyclerViewMovies.setHasFixedSize(true);
        mNetworkUtilsInterface = NetworkUtils.createService(NetworkUtilsInterface.class);

        if (savedInstanceState != null) {
            menuItemSelected = savedInstanceState.getInt(MENU_SELECTED);
            mMoviesList = Parcels.unwrap(savedInstanceState.getParcelable("MOVIE_LIST"));

        }
        // Check internet connection
        else if (isNetworkAvailable(this)) {
            onItemLoaded();
            String sortBy = DBMoviesUtils.loadSelectedItem(MainActivity.this);
            if (sortBy.equals(NetworkUtils.MOST_POPULAR) || sortBy.equals(NetworkUtils.TOP_RATED)) {
                getMoviesData(sortBy);
            } else if (sortBy.equals(NetworkUtils.FAVORITE)) {
                getFavoriteMovies();
            }
        } else if (!isNetworkAvailable(this)) {
            Toast.makeText(this, "Please Check Your Internet Connection.\nSwipe to Refresh", Toast.LENGTH_SHORT).show();

            mActivityMainBinding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    refreshItems();
                }
            });
        } else {
            getFavoriteMovies();
            favoriteSorting = true;
        }
        setAdapter(mActivityMainBinding.recyclerViewMovies);
    }

    void refreshItems() {
        if (!isNetworkAvailable(MainActivity.this)) {
            Toast.makeText(MainActivity.this, "Please Try Again", Toast.LENGTH_SHORT).show();
            mActivityMainBinding.swipeRefreshLayout.setRefreshing(false);
        } else {
            String sortBy = DBMoviesUtils.loadSelectedItem(MainActivity.this);
            if (sortBy.equals(NetworkUtils.MOST_POPULAR) || sortBy.equals(NetworkUtils.TOP_RATED)) {
                getMoviesData(sortBy);
            } else if (sortBy.equals(NetworkUtils.FAVORITE)) {
                getFavoriteMovies();
            }
            onItemLoaded();
        }

    }

    void onItemLoaded() {
        if (isNetworkAvailable(this)) {
            mActivityMainBinding.swipeRefreshLayout.setEnabled(false);
            mActivityMainBinding.swipeRefreshLayout.setRefreshing(false);
        } else {
            mActivityMainBinding.swipeRefreshLayout.setEnabled(true);
            mActivityMainBinding.swipeRefreshLayout.setRefreshing(true);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (favoriteSorting) {
            getLoaderManager().restartLoader(LOADER_ID, null, this);
        }
    }

    public void setAdapter(RecyclerView recyclerView) {
        mMoviesAdapter = new MoviesAdapter(mMoviesList, this, this);
        recyclerView.setAdapter(mMoviesAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //mActivityMainBinding.progressBar.setVisibility(View.VISIBLE);
        switch (item.getItemId()) {
            // Response to a click on the "View By Popularity" menu option
            case R.id.by_most_popular:
                if (isNetworkAvailable(this)) {
                    DBMoviesUtils.storeSelectedItem(this, MOST_POPULAR);
                    favoriteSorting = false;
                    getMoviesData(NetworkUtils.MOST_POPULAR);
                } else {
                    Toast.makeText(this, "Please Check Your Internet Connection", Toast.LENGTH_SHORT).show();
                }
                return true;
            // Response to a click on the "View By Ratings" menu option
            case R.id.by_top_rated:
                if (isNetworkAvailable(this)) {
                    DBMoviesUtils.storeSelectedItem(this, TOP_RATED);
                    favoriteSorting = false;
                    getMoviesData(NetworkUtils.TOP_RATED);
                } else {
                    Toast.makeText(this, "Please Check Your Internet Connection", Toast.LENGTH_SHORT).show();
                }
                return true;
            // Response to a click on the "View My Favorites" menu option
            case R.id.by_my_favorites:
                favoriteSorting = true;
                DBMoviesUtils.storeSelectedItem(this, NetworkUtils.FAVORITE);
                getFavoriteMovies();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(MENU_SELECTED, menuItemSelected);
        outState.putParcelable("MOVIE_LIST", Parcels.wrap(mMoviesList));
    }

    @Override
    public void onListClick(int clickedItemIndex) {
        Movies movieClicked = mMoviesList.get(clickedItemIndex);
        Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
        intent.putExtra(EXTRA_MOVIE_PARCELABLE, Parcels.wrap(movieClicked));
        startActivity(intent);
    }

    private void getFavoriteMovies() {
        getLoaderManager().restartLoader(LOADER_ID, null, this);
    }

    public void getMoviesData(String sortBy) {
        Call<MoviesResponse> moviesResponseCall = mNetworkUtilsInterface.getPopular(sortBy);
        moviesResponseCall.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(@NonNull Call<MoviesResponse> call, @NonNull Response<MoviesResponse> response) {
                if (response.isSuccessful()) {
                    mMoviesList = response.body().getMoviesResults();
                    if (mMoviesList.size() != 0) {
                        mMoviesAdapter.setMoviesList(mMoviesList);
                    } else {
                        Toast.makeText(MainActivity.this,
                                "Error Occurred.\nPlease try again later.", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<MoviesResponse> call, @NonNull Throwable t) {
                Toast.makeText(MainActivity.this,
                        "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle bundle) {
        return new MoviesAsyncLoader(MainActivity.this);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        mMoviesList = loadDataFromCursor(cursor);
        mMoviesAdapter.setMoviesList(mMoviesList);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }

    public List<Movies> loadDataFromCursor(Cursor cursor) {
        List<Movies> moviesList = new ArrayList<>();
        while (cursor.moveToNext()) {
            Movies movies = new Movies();

            movies.setIsFavorite(true);
            movies.setId(cursor.getLong(cursor.getColumnIndex(COLUMN_ID)));
            movies.setPosterUrl(cursor.getString(cursor.getColumnIndex(COLUMN_POSTER)));
            movies.setBackdrop(cursor.getString(cursor.getColumnIndex(COLUMN_BACKDROP)));
            movies.setTitle(cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)));
            movies.setOverview(cursor.getString(cursor.getColumnIndex(COLUMN_OVERVIEW)));
            movies.setReleaseDate(cursor.getString(cursor.getColumnIndex(COLUMN_RELEASE_DATE)));
            movies.setRating(cursor.getString(cursor.getColumnIndex(COLUMN_RATING)));
            moviesList.add(movies);
        }
        return moviesList;
    }
}

