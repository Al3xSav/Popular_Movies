package com.alexsav.popularmovies.model;

import com.google.gson.annotations.SerializedName;
import org.parceler.Parcel;

@Parcel
public class Reviews {
    @SerializedName("author")
    public String author;
    @SerializedName("content")
    public String content;

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }
}
