package com.alexsav.popularmovies.activities;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.alexsav.popularmovies.R;
import com.alexsav.popularmovies.databinding.ActivityReviewBinding;

public class ReviewActivity extends AppCompatActivity {

    ActivityReviewBinding mActivityReviewBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivityReviewBinding = DataBindingUtil.setContentView(this, R.layout.activity_review);
        mActivityReviewBinding.textViewAuthor.setText(getIntent().getStringExtra("EXTRA_REVIEW_AUTHOR"));
        mActivityReviewBinding.textViewContent.setText(getIntent().getStringExtra("EXTRA_REVIEW_CONTENT"));
    }
}
