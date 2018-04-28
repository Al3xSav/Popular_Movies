package com.alexsav.popularmovies.model;

import com.google.gson.annotations.SerializedName;
import org.parceler.Parcel;
import java.util.List;

@Parcel
public class ReviewResponse {

    @SerializedName("results")
    public List<Reviews> reviews;

    public List<Reviews> getReviews() {
        return reviews;
    }
}
