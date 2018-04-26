package com.alexsav.popularmovies.model;

import com.google.gson.annotations.SerializedName;
import org.parceler.Parcel;
import java.util.List;

@Parcel
public class TrailerResponse {
    @SerializedName("results")
    public List<Trailer> trailers;
    public List<Trailer> getTrailerResponse() { return trailers; }
}
