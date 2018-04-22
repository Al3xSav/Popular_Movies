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
    private String runtime;
    @SerializedName("images")
    private Images images;
    @SerializedName("reviews")
    private ReviewResults reviewResults;
    @SerializedName("videos")
    TrailerResults trailerResults;
    private boolean favorite;
    @SerializedName("id")
    private long id;
    @SerializedName("title")
    private String title;
    @SerializedName("releaseDate")
    private String releaseDate;
    @SerializedName("posterUrl")
    private String posterUrl;
    @SerializedName("rating")
    private String rating;
    @SerializedName("overview")
    private String overview;
    @SerializedName("backdrop_path")
    private String backdrop;

    public Movies(){}

    /*
    * image url from themoviedb
    * possible sizes: "w92", "w154", "w185", "w342", "w500", "w780", "original"
    */

    /* Set */
    public void setRuntime(String runtime) {this.runtime = runtime; }
    public void setImages(Images images) { this.images = images; }
    public void setReviewResults(ReviewResults reviewResults) { this.reviewResults = reviewResults; }
    public void setTrailerResults(TrailerResults trailerResults) { this.trailerResults = trailerResults; }
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
    public ReviewResults getReviewResults() { return reviewResults; }
    public TrailerResults getTrailerResults() { return trailerResults; }
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
        return "Movies{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", poster='" + posterUrl + '\'' +
                ", backdrop='" + backdrop + '\'' +
                ", synopsis='" + overview + '\'' +
                ", rating='" + rating + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", runtime='" + runtime + '\'' +
                ", trailersResults=" + trailerResults +
                ", reviewsResults=" + reviewResults +
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
