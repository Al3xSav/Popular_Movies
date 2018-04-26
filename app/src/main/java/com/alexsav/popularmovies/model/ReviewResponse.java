package com.alexsav.popularmovies.model;

import com.google.gson.annotations.SerializedName;
import org.parceler.Parcel;
import java.util.List;

@Parcel
public class ReviewResponse {

    @SerializedName("results")
    public List<Reviews> reviews;
    @SerializedName("total_results")
    public int totalReviews;

    public List<Reviews> getReviews() { return reviews; }
    public int getTotalReviews() {
        return totalReviews;
    }
}
