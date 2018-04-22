package com.alexsav.popularmovies.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.alexsav.popularmovies.R;
import com.alexsav.popularmovies.model.Movies;
import com.alexsav.popularmovies.model.ReviewResults;
import com.alexsav.popularmovies.model.Reviews;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewViewHolder> {

    private final ReviewListener reviewListener;
    public Context context;
    private Movies movies;

    public ReviewsAdapter(ReviewListener reviewListener, Context context, Movies movies) {
        this.reviewListener = reviewListener;
        this.context = context;
        this.movies = movies;
    }

    public interface ReviewListener {
        void onReviewsClick(int index);
    }

    @Override
    @NonNull
    public ReviewsAdapter.ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutId = R.layout.reviews_item;
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewsAdapter.ReviewViewHolder  reviewViewHolder, int position) {
        ReviewResults reviewResults = movies.getReviewResults();
        Reviews reviews = reviewResults.getReviews().get(position);
        reviewViewHolder.textViewAuthor.setText(reviews.getAuthor());
        reviewViewHolder.textViewReview.setText(reviews.getContent());
    }

    @Override
    public int getItemCount() {
        if (movies.getReviewResults() == null) {
            return 0;
        }
        return movies.getReviewResults().getReviews().size();
    }

    protected class ReviewViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textViewReview, textViewAuthor;

        private ReviewViewHolder(View ReviewsView) {
            super(ReviewsView);
            textViewReview = ReviewsView.findViewById(R.id.text_view_review);
            textViewAuthor = ReviewsView.findViewById(R.id.text_view_author);
            ReviewsView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            reviewListener.onReviewsClick(getAdapterPosition());
        }
    }
}
