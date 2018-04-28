package com.alexsav.popularmovies.data;

import android.net.Uri;
import android.provider.BaseColumns;

public class MoviesContract {
    public static final String AUTHORITY = "com.alexsav.popularmovies";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final String FAV_MOVIES_PATH = "favorite_movie";

    private MoviesContract() {
    }

    public static final class FavoriteMoviesEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(FAV_MOVIES_PATH)
                .build();

        public static final String TABLE_NAME = "favorite_movies";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_POSTER = "posterUrl";
        public static final String COLUMN_BACKDROP = "backdrop";
        public static final String COLUMN_OVERVIEW = "overview";
        public static final String COLUMN_RATING = "rating";
        public static final String COLUMN_RELEASE_DATE = "releaseDate";
        public static final String COLUMN_RUNTIME = "runtime";
    }
}
