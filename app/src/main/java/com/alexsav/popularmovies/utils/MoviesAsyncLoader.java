package com.alexsav.popularmovies.utils;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.database.Cursor;

import static com.alexsav.popularmovies.data.MoviesContract.FavoriteMoviesEntry.CONTENT_URI;
import static com.alexsav.popularmovies.data.MoviesContract.FavoriteMoviesEntry._ID;

public class MoviesAsyncLoader extends AsyncTaskLoader<Cursor> {

    public Context context;
    private Cursor moviesCursor;

    public MoviesAsyncLoader(Context context) {
        super(context);
        this.context = context;
        moviesCursor = null;
    }

    @Override
    protected void onStartLoading() {
        if (moviesCursor != null) {
            deliverResult(moviesCursor);
        } else {
            forceLoad();
        }
    }

    @Override
    public Cursor loadInBackground() {
        try {
            return context.getContentResolver().query(CONTENT_URI,
                    null,
                    null,
                    null,
                    _ID);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void deliverResult(Cursor data) {
        super.deliverResult(data);
    }
}
