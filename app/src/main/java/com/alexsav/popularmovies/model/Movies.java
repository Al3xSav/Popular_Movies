package com.alexsav.popularmovies.model;

import com.google.gson.annotations.SerializedName;
import org.parceler.Parcel;
import java.util.List;

/**
 * Constructor Class
 */
@Parcel
public class Movies {

    public boolean favorite;
    @SerializedName("id")
    public long id;
    @SerializedName("original_title")
    public String title;
    @SerializedName("release_date")
    public String releaseDate;
    @SerializedName("poster_path")
    public String posterUrl;
    @SerializedName("vote_average")
    public String rating;
    @SerializedName("overview")
    public String overview;
    @SerializedName("backdrop_path")
    public String backdrop;
    @SerializedName("runtime")
    String runtime;
    @SerializedName("images")
    Images images;
    @SerializedName("reviews")
    ReviewResponse reviewResponse;
    @SerializedName("videos")
    TrailerResponse trailerResponse;

    public Movies() {
    }

    /*
     * image url from themoviedb
     * possible sizes: "w92", "w154", "w185", "w342", "w500", "w780", "original"
     */

    /* Get */
    public String getRuntime() {
        return runtime;
    }

    /* Set */
    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public Images getImages() {
        return images;
    }

    public void setImages(Images images) {
        this.images = images;
    }

    public ReviewResponse getReviewResponse() {
        return reviewResponse;
    }

    public void setReviewResponse(ReviewResponse reviewResponse) {
        this.reviewResponse = reviewResponse;
    }

    public TrailerResponse getTrailerResponse() {
        return trailerResponse;
    }

    public void setTrailerResponse(TrailerResponse trailerResponse) {
        this.trailerResponse = trailerResponse;
    }

    public boolean getIsFavorite() {
        return favorite;
    }

    public void setIsFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getBackdrop() {
        return backdrop;
    }

    public void setBackdrop(String backdrop) {
        this.backdrop = backdrop;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", poster='" + posterUrl + '\'' +
                ", backdrop='" + backdrop + '\'' +
                ", synopsis='" + overview + '\'' +
                ", rating='" + rating + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", runtime='" + runtime + '\'' +
                ", trailersResults=" + trailerResponse +
                ", reviewsResults=" + reviewResponse +
                ", images=" + images +
                ", isFavourite=" + favorite +
                '}';
    }

    @Parcel
    public static class Images {

        @SerializedName("backdrops")
        List<Backdrops> backdropsList;

        public List<Backdrops> getBackdropsList() {
            return backdropsList;
        }
    }

    @Parcel
    public static class Backdrops {

        @SerializedName("file_path")
        String path;

        public String getPath() {
            return path;
        }
    }
}
