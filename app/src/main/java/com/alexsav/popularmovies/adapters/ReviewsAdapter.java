package com.alexsav.popularmovies.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alexsav.popularmovies.R;
import com.alexsav.popularmovies.model.*;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewViewHolder> {

    private final ReviewListener reviewListener;
    public Context context;
    public Movies movies;

    public ReviewsAdapter(ReviewListener reviewListener, Context context, Movies movies) {
        this.reviewListener = reviewListener;
        this.context = context;
        this.movies = movies;
    }

    @NonNull
    @Override
    public ReviewsAdapter.ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        int layoutId = R.layout.reviews_item;
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(layoutId, viewGroup, false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewsAdapter.ReviewViewHolder reviewViewHolder, int position) {
        ReviewResponse reviewResponse = movies.getReviewResponse();
        Reviews reviews = reviewResponse.getReviews().get(position);
        reviewViewHolder.textViewAuthor.setText(reviews.getAuthor());
        reviewViewHolder.textViewReview.setText(reviews.getContent());
    }

    @Override
    public int getItemCount() {
        if (movies.getReviewResponse() == null) {
            return 0;
        }
        return movies.getReviewResponse().getReviews().size();
    }

    public interface ReviewListener {
        void onReviewsClick(int index);
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
            if (reviewListener != null) {
                reviewListener.onReviewsClick(getAdapterPosition());
            }
        }
    }
}
