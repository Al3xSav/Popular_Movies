package com.alexsav.popularmovies.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.alexsav.popularmovies.R;
import com.alexsav.popularmovies.databinding.ActivityDetailsBinding;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DetailsActivity extends AppCompatActivity {

    private ActivityDetailsBinding mActivityDetailsBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_details);
        Intent intent = getIntent();
        if (intent.hasExtra("MovieTitle")) {
            String movieTitle = intent.getStringExtra("MovieTitle");
            String movieDate = intent.getStringExtra("MovieReleaseDate");
            String moviePosterURL = intent.getStringExtra("MoviePoster");
            String movieOverview = intent.getStringExtra("MovieOverview");
            String movieRating = intent.getStringExtra("MovieRating");

            DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
            DateFormat outputFormat = new SimpleDateFormat("dd/MM/YYYY");

            try {
                String outputDateStr = outputFormat.format(inputFormat.parse(movieDate));
                mActivityDetailsBinding.dateTextView.setText(outputDateStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Picasso.get().load(moviePosterURL).into(mActivityDetailsBinding.imageDetails);
            mActivityDetailsBinding.movieTitleTextView.setText(movieTitle);
            mActivityDetailsBinding.synopsisTextView.setText(movieOverview);
            mActivityDetailsBinding.ratingTextView.setText(movieRating);
        }
    }
}
