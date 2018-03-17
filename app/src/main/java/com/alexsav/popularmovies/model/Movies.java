package com.alexsav.popularmovies.model;

/**
 * Constructor Class
 */
public class Movies {

    private final String title;
    private final String releaseData;
    private final String posterUrl;
    private final String rating;
    private final String overview;

    /*
    * image url from themoviedb
    * possible sizes: "w92", "w154", "w185", "w342", "w500", "w780", "original"
    */
    private final String IMG_URL = "http://image.tmdb.org/t/p/w342/";

    /* Constructor */
    public Movies(String title, String releaseData, String posterUrl,
                  String rating, String overview) {

        this.title = title;
        this.releaseData = releaseData;
        this.posterUrl = posterUrl;
        this.rating = rating;
        this.overview = overview;
    }

    /* Get */
    public String getTitle() {
        return title;
    }

    public String getReleaseData() {
        return releaseData;
    }

    public String getPosterUrl() {
        return IMG_URL + posterUrl;
    }

    public String getRating() {
        return rating;
    }

    public String getOverview() {
        return overview;
    }


}
