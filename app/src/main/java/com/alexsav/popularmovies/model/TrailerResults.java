package com.alexsav.popularmovies.model;

import com.google.gson.annotations.SerializedName;
import org.parceler.Parcel;
import java.util.List;

@Parcel
public class TrailerResults {
    @SerializedName("results")
    public List<Trailer> trailers;
    public List<Trailer> getTrailerResults() { return trailers; }
}
