package com.alexsav.popularmovies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import static com.alexsav.popularmovies.data.MoviesContract.FavoriteMoviesEntry.*;

public class MoviesDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "movies.db";
    private static final int DATABASE_VERSION = 1;

    public MoviesDBHelper(Context context) { super(context, DATABASE_NAME, null, DATABASE_VERSION);}

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_MOVIES_TABLE = "CREATE TABLE " +
                TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_ID + " LONG NOT NULL," +
                COLUMN_TITLE + " TEXT NOT NULL," +
                COLUMN_POSTER + " TEXT NOT NULL," +
                COLUMN_RATING + " TEXT NOT NULL," +
                COLUMN_BACKDROP + " TEXT NOT NULL," +
                COLUMN_OVERVIEW + " TEXT NOT NULL," +
                COLUMN_RELEASE_DATE + " TEXT NOT NULL," +
                COLUMN_RUNTIME + " TEXT NOT NULL" +
                ");";

        sqLiteDatabase.execSQL(SQL_CREATE_MOVIES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        final String SQL_DROP_MOVIES_TABLE = "ALTER TABLE " + TABLE_NAME;
        db.execSQL(SQL_DROP_MOVIES_TABLE);
        onCreate(db);
    }
}
