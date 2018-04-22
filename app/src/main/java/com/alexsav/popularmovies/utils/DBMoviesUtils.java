package com.alexsav.popularmovies.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.alexsav.popularmovies.model.Movies;

import static com.alexsav.popularmovies.activities.MainActivity.SHARED_PREF_FILE;
import static com.alexsav.popularmovies.utils.NetworkUtils.TOP_RATED;

public class DBMoviesUtils {
    private static final String SELECTED_ITEM_MENU = "selected_item_menu";

    public static void storeSelectedItem(@NonNull Context context,String sort) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SELECTED_ITEM_MENU, sort);
        editor.apply();
    }

    public static boolean getState(@NonNull Context context, Movies movies) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREF_FILE, Context.MODE_PRIVATE);
        return preferences.getBoolean(movies.getTitle(), false);
    }

    public static String loadSelectedItem(@NonNull Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_FILE, Context.MODE_PRIVATE);
        return sharedPreferences.getString(SELECTED_ITEM_MENU, TOP_RATED);
    }
}
