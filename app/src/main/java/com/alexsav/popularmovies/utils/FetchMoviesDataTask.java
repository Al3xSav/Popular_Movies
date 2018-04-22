package com.alexsav.popularmovies.utils;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.alexsav.popularmovies.data.MoviesContract;

import java.io.IOException;
import java.net.URL;

public class FetchMoviesDataTask extends AsyncTaskLoader<Cursor>  {

    //private static final String TAG = "FetchMoviesDataTask";
    private Cursor moviesCursor = null;
    public Context context;

    public FetchMoviesDataTask(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        if (moviesCursor != null){
            deliverResult(moviesCursor);
        } else {
            forceLoad();
        }
    }

    @Override
    public Cursor loadInBackground() {
        try {
            return context.getContentResolver().query(MoviesContract.FavoriteMoviesEntry.CONTENT_URI,
                    null,
                    null,
                    null,
                    MoviesContract.FavoriteMoviesEntry._ID);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void deliverResult(Cursor data) {
        moviesCursor = data;
        super.deliverResult(data);
    }
}
