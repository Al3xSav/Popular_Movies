package com.alexsav.popularmovies.utils;

import com.alexsav.popularmovies.model.Movies;
import com.alexsav.popularmovies.model.MoviesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface NetworkUtilsInterface {
    @GET("movie/{sortby}")
    Call<MoviesResponse> getPopular(@Path("sortby") String sortBy);

    @GET("movie/{movie_id}")
    Call<Movies> getMoviesInfo
            (@Path("movie_id") long moviesID, @Query("append_to_response") String moviesResponse);

}
