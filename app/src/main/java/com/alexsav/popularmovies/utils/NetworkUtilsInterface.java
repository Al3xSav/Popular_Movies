package com.alexsav.popularmovies.utils;

import com.alexsav.popularmovies.model.Movies;
import com.alexsav.popularmovies.model.MoviesResults;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface NetworkUtilsInterface {
    @GET("movies/{sortby}")
    Call<MoviesResults> getPopular(@Path("sortby") String sortBy);

    @GET("movies/{movie_id}")
    Call<Movies> getMoviesInfo
            (@Path("movie_id") long moviesID, @Query("append_to_response") String moviesResponse);

}
