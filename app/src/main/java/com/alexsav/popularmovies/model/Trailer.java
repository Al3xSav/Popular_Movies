package com.alexsav.popularmovies.model;

import com.google.gson.annotations.SerializedName;
import org.parceler.Parcel;

@Parcel
public class Trailer {
    @SerializedName("key")
    public String key;
    @SerializedName("name")
    public String name;

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }
}
