package com.alexsav.popularmovies;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import com.alexsav.popularmovies.databinding.ActivityDetailsBinding;
import com.squareup.picasso.Picasso;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class DetailsActivity extends AppCompatActivity {

    ActivityDetailsBinding mActivityDetailsBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityDetailsBinding = DataBindingUtil.setContentView(this,R.layout.activity_details);

        Intent intent = getIntent();
        if (intent.hasExtra("MovieTitle")) {
            String movieTitle = intent.getStringExtra("MovieTitle");
            String movieDate = intent.getStringExtra("ReleaseDate");
            String moviePosterURL = intent.getStringExtra("MoviePoster");
            String movieOverview = intent.getStringExtra("MovieOverview");
            String movieRating = intent.getStringExtra("MovieRating");

            Picasso.get().load(moviePosterURL).into(mActivityDetailsBinding.imageDetails);
            mActivityDetailsBinding.movieTitleTextView.setText(movieTitle);
            mActivityDetailsBinding.dateTextView.setText(movieDate);
            mActivityDetailsBinding.synopsisTextView.setText(movieOverview);
            mActivityDetailsBinding.ratingTextView.setText(movieRating);
        }
    }
}
