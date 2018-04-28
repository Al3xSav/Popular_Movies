package com.alexsav.popularmovies.activities;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alexsav.popularmovies.R;
import com.alexsav.popularmovies.adapters.*;
import com.alexsav.popularmovies.databinding.ActivityDetailsBinding;
import com.alexsav.popularmovies.model.*;
import com.alexsav.popularmovies.utils.*;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.alexsav.popularmovies.data.MoviesContract.FavoriteMoviesEntry.*;
import static com.alexsav.popularmovies.utils.NetworkUtils.VIDEO_URL;

public class DetailsActivity extends MainActivity
        implements TrailerAdapter.TrailerListener, ReviewsAdapter.ReviewListener {

    public static final String SHARED_PREF_FILE = "favorites";
    public static final String EXTRA_MOVIE_PARCELABLE = "extra_movie_parcelable";
    public static final String MOVIE_DETAILS_STATE = "movie_details_state";
    private static boolean isFavoriteChecked;
    public ReviewsAdapter reviewsAdapter;
    public TrailerResponse trailerResponse;
    public TrailerAdapter trailerAdapter;
    public NetworkUtilsInterface networkUtilsInterface;
    StringBuilder stringBuilder;
    private ActivityDetailsBinding mActivityDetailsBinding;
    private Movies moviesList;
    private ReviewResponse reviewResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_details);

        if (savedInstanceState != null) {
            moviesList = Parcels.unwrap(savedInstanceState.getParcelable(MOVIE_DETAILS_STATE));
            trailerResponse = Parcels.unwrap(savedInstanceState.getParcelable("TRAILERS"));
            reviewResponse = Parcels.unwrap(savedInstanceState.getParcelable("REVIEWS"));
            setLayoutManagers();
            setReviewsAdapter(mActivityDetailsBinding.recyclerViewReviews);
            setTrailerAdapter(mActivityDetailsBinding.recyclerViewTrailers);
        } else {
            networkUtilsInterface = NetworkUtils.createService(NetworkUtilsInterface.class);
            moviesList = Parcels.unwrap(getIntent().getParcelableExtra(EXTRA_MOVIE_PARCELABLE));
            getMoviesResponse(moviesList.getId());
        }

        isFavoriteChecked = DBMoviesUtils.getState(this, moviesList);
        getDetails(moviesList);
    }

    private void setLayoutManagers() {
        if (moviesList.getReviewResponse() != null && moviesList.getTrailerResponse() != null) {

            LinearLayoutManager trailerManager =
                    new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
            mActivityDetailsBinding.recyclerViewTrailers.setLayoutManager(trailerManager);
            mActivityDetailsBinding.recyclerViewTrailers.setHasFixedSize(true);

            LinearLayoutManager reviewsManager =
                    new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
            mActivityDetailsBinding.recyclerViewReviews.setLayoutManager(reviewsManager);
            mActivityDetailsBinding.recyclerViewReviews.setHasFixedSize(true);
        }
    }

    private void setTrailerAdapter(RecyclerView recyclerView) {
        trailerAdapter = new TrailerAdapter(this, this, moviesList);
        recyclerView.setAdapter(trailerAdapter);
    }

    private void setReviewsAdapter(RecyclerView recyclerView) {
        reviewsAdapter = new ReviewsAdapter(this, this, moviesList);
        recyclerView.setAdapter(reviewsAdapter);
    }

    private void getDetails(final Movies moviesList) {

        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat outputFormat = new SimpleDateFormat("dd/MM/YYYY");

        try {
            String outputDateStr = outputFormat.format(inputFormat.parse(moviesList.getReleaseDate()));
            mActivityDetailsBinding.dateTextView.setText(outputDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        mActivityDetailsBinding.movieTitleTextView.setText(moviesList.getTitle());
        mActivityDetailsBinding.ratingTextView.setText(moviesList.getRating());
        mActivityDetailsBinding.synopsisTextView.setText(moviesList.getOverview());

        Picasso.get()
                .load(NetworkUtils.POSTER_URL + moviesList.getPosterUrl())
                .error(R.drawable.error_img)
                .into(mActivityDetailsBinding.imageDetails);

        Picasso.get()
                .load(NetworkUtils.BACKDROP_URL + moviesList.getBackdrop())
                .error(R.drawable.error_img)
                .into(mActivityDetailsBinding.imageBackdropImage);

        if (isFavoriteChecked) {
            mActivityDetailsBinding.fab.setImageResource(R.drawable.ic_added_to_favorite);
        } else {
            mActivityDetailsBinding.fab.setImageResource(R.drawable.ic_add_favorite);
        }

        mActivityDetailsBinding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFavoriteChecked) {
                    Toast.makeText(getApplicationContext(), "Deleted from my Favorites", Toast.LENGTH_SHORT).show();
                    mActivityDetailsBinding.fab.setImageResource(R.drawable.ic_add_favorite);
                    isFavoriteChecked = false;
                    String moviesID = String.valueOf(moviesList.getId());
                    Uri uri = CONTENT_URI.buildUpon().appendPath(moviesID).build();
                    getContentResolver().delete(uri, null, null);
                    SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_FILE, MODE_PRIVATE);
                    SharedPreferences.Editor sharedEditor = sharedPreferences.edit();
                    sharedEditor.putBoolean(moviesList.getTitle(), isFavoriteChecked);
                    sharedEditor.apply();
                } else {
                    Toast.makeText(getApplicationContext(), "Added to My Favorites", Toast.LENGTH_SHORT).show();
                    mActivityDetailsBinding.fab.setImageResource(R.drawable.ic_added_to_favorite);
                    isFavoriteChecked = true;
                    // Insert to the TABLE
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(COLUMN_ID, moviesList.getId());
                    contentValues.put(COLUMN_TITLE, moviesList.getTitle());
                    contentValues.put(COLUMN_OVERVIEW, moviesList.getOverview());
                    contentValues.put(COLUMN_POSTER, moviesList.getPosterUrl());
                    contentValues.put(COLUMN_BACKDROP, moviesList.getBackdrop());
                    contentValues.put(COLUMN_RATING, moviesList.getRating());
                    contentValues.put(COLUMN_RELEASE_DATE, moviesList.getReleaseDate());

                    String runtime;
                    if (moviesList.getRuntime() == null) {
                        runtime = " ";
                    } else {
                        runtime = moviesList.getRuntime();
                    }
                    contentValues.put(COLUMN_RUNTIME, runtime);

                    getContentResolver().insert(CONTENT_URI, contentValues);
                    SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_FILE, MODE_PRIVATE);
                    SharedPreferences.Editor sharedEditor = sharedPreferences.edit();
                    sharedEditor.putBoolean(moviesList.getTitle(), isFavoriteChecked);
                    sharedEditor.apply();
                }
            }
        });
    }

    private void getMoviesResponse(long id) {
        stringBuilder = new StringBuilder();
        stringBuilder.append(getString(R.string.append_videos));
        stringBuilder.append(",");
        stringBuilder.append(getString(R.string.append_reviews));
        stringBuilder.append(",");
        stringBuilder.append(getString(R.string.append_images));

        Call<Movies> moviesCall = networkUtilsInterface.getMoviesInfo(id, stringBuilder.toString());
        moviesCall.enqueue(new Callback<Movies>() {
            @Override
            public void onResponse(@NonNull Call<Movies> call, @NonNull Response<Movies> response) {
                moviesList = response.body();
                trailerResponse = moviesList.getTrailerResponse();
                moviesList.setTrailerResponse(trailerResponse);
                setLayoutManagers();
                setTrailerAdapter(mActivityDetailsBinding.recyclerViewTrailers);
                reviewResponse = moviesList.getReviewResponse();
                moviesList.setReviewResponse(reviewResponse);
                setReviewsAdapter(mActivityDetailsBinding.recyclerViewReviews);

            }

            @Override
            public void onFailure(@NonNull Call<Movies> call, @NonNull Throwable t) {
                Log.d(DetailsActivity.class.getSimpleName(), t.getMessage());

            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(MOVIE_DETAILS_STATE, Parcels.wrap(moviesList));
        outState.putParcelable("TRAILERS", Parcels.wrap(trailerResponse));
        outState.putParcelable("REVIEWS", Parcels.wrap(reviewResponse));
    }

    @Override
    public void onTrailerClick(int index) {
        Trailer trailer = moviesList.getTrailerResponse().getTrailerResponse().get(index);
        Intent trailerIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(VIDEO_URL + trailer.getKey()));
        if (trailerIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(trailerIntent);
        }
    }

    @Override
    public void onReviewsClick(int index) {
        Intent reviewsIntent = new Intent(this, ReviewActivity.class);
        Reviews reviews = reviewResponse.getReviews().get(index);
        reviewsIntent.putExtra("REVIEW_AUTHOR", reviews.getAuthor());
        reviewsIntent.putExtra("REVIEW_CONTENT", reviews.getContent());
        startActivity(reviewsIntent);
    }
}
