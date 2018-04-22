package com.alexsav.popularmovies.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import static com.alexsav.popularmovies.data.MoviesContract.FavoriteMoviesEntry.*;

public class MoviesDetailsContentProvider extends ContentProvider {


    public static final int FAVORITES_MOVIE = 100;
    public static final int FAVORITES_MOVIE_WITH_ID = 200;

    public static final String UNKNOWN_URI = "Unknown uri: ";
    public static final String INSERT_FAILED = "Failed to insert row to  ";
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    private MoviesDBHelper moviesDBHelper;

    static {
        sUriMatcher.addURI(MoviesContract.AUTHORITY, MoviesContract.FAV_MOVIES_PATH, FAVORITES_MOVIE);
        sUriMatcher.addURI(MoviesContract.AUTHORITY, MoviesContract.FAV_MOVIES_PATH + "/#", FAVORITES_MOVIE_WITH_ID);
    }

    @Override
    public boolean onCreate() {
        moviesDBHelper = new MoviesDBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection,
                        @Nullable String selection, @Nullable String[] selectionArgs,
                        @Nullable String sortOrder) {

        final SQLiteDatabase db = moviesDBHelper.getReadableDatabase();
        Cursor returnCursor;
        switch (sUriMatcher.match(uri)) {
            case FAVORITES_MOVIE:
                returnCursor = db.query(TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            default:
                throw new UnsupportedOperationException(UNKNOWN_URI + uri);
        }

        returnCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return returnCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) { return null; }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final SQLiteDatabase db = moviesDBHelper.getWritableDatabase();
        Uri returnUri;
        switch (sUriMatcher.match(uri)) {
            case FAVORITES_MOVIE:
                long id = db.insert(TABLE_NAME, null, values);
                if (id > 0) {
                    returnUri = ContentUris.withAppendedId(CONTENT_URI, id);
                } else {
                    throw new SQLException(INSERT_FAILED + uri);
                }
                break;

            default:
                throw new UnsupportedOperationException(UNKNOWN_URI + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = moviesDBHelper.getWritableDatabase();
        int deletedMovies;
        switch (sUriMatcher.match(uri)) {
            case FAVORITES_MOVIE_WITH_ID:
                String stringId = uri.getPathSegments().get(1);
                deletedMovies = db.delete(TABLE_NAME, "id=?", new String[]{stringId});
                break;
            default:
                throw new UnsupportedOperationException(UNKNOWN_URI + uri);
        }
        if (deletedMovies != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return deletedMovies;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
