package com.alexsav.popularmovies.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class MoviesResults {

    @SerializedName("results")
    public List<Movies> moviesResults;
    public List<Movies> getMoviesResults() {
        return moviesResults;
    }
}
