package com.alexsav.popularmovies.model;

import com.google.gson.annotations.SerializedName;
import org.parceler.Parcel;

import java.util.List;

/**
 * Constructor Class
 */
@Parcel
public class Movies {

    @SerializedName("runtime")
    String runtime;
    @SerializedName("images")
    Images images;
    @SerializedName("reviews")
    ReviewResponse reviewResponse;
    @SerializedName("videos")
    TrailerResponse trailerResponse;
    public boolean favorite;
    @SerializedName("id")
    public long id;
    @SerializedName("original_title")
    public String title;
    @SerializedName("releaseDate")
    public String releaseDate;
    @SerializedName("posterUrl")
    public String posterUrl;
    @SerializedName("rating")
    public String rating;
    @SerializedName("overview")
    public String overview;
    @SerializedName("backdrop_path")
    public String backdrop;

    public Movies(){}

    /*
    * image url from themoviedb
    * possible sizes: "w92", "w154", "w185", "w342", "w500", "w780", "original"
    */

    /* Set */
    public void setRuntime(String runtime) {this.runtime = runtime; }
    public void setImages(Images images) { this.images = images; }
    public void setReviewResponse(ReviewResponse reviewResponse) { this.reviewResponse = reviewResponse; }
    public void setTrailerResponse(TrailerResponse trailerResponse) { this.trailerResponse = trailerResponse; }
    public void setIsFavorite(boolean favorite) { this.favorite = favorite; }
    public void setId(long id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setReleaseDate(String releaseDate) {this.releaseDate = releaseDate; }
    public void setPosterUrl(String posterUrl) { this.posterUrl = posterUrl; }
    public void setRating(String rating) { this.rating = rating; }
    public void setOverview(String overview) { this.overview = overview;}
    public void setBackdrop(String backdrop) {this.backdrop = backdrop; }

    /* Get */
    public String getRuntime() { return runtime; }
    public Images getImages() { return images; }
    public ReviewResponse getReviewResponse() { return reviewResponse; }
    public TrailerResponse getTrailerResponse() { return trailerResponse; }
    public boolean getIsFavorite() { return favorite; }
    public long getId() { return id; }
    public String getTitle() { return title; }
    public String getReleaseDate() { return releaseDate; }
    public String getPosterUrl() { return posterUrl; }
    public String getRating() { return rating; }
    public String getOverview() { return overview; }
    public String getBackdrop() { return backdrop; }

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
