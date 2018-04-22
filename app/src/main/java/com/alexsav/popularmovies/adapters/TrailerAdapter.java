package com.alexsav.popularmovies.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.alexsav.popularmovies.BuildConfig;
import com.alexsav.popularmovies.R;
import com.alexsav.popularmovies.model.Movies;
import com.alexsav.popularmovies.model.Trailer;
import com.alexsav.popularmovies.model.TrailerResults;
import com.squareup.picasso.Picasso;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder> {

    final private TrailerListener trailerListener;
    public Context context;
    private Movies movies;

    public TrailerAdapter (TrailerListener trailerListener, Context context, Movies movies) {
        this.trailerListener = trailerListener;
        this.context = context;
        this.movies = movies;
    }

    public interface TrailerListener {
        void onTrailerClick(int index);
    }

    @NonNull
    @Override
    public TrailerAdapter.TrailerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutId = R.layout.trailer_item;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(layoutId, parent, false);
        return new TrailerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerAdapter.TrailerViewHolder holder, int position) {

        TrailerResults trailerResults = movies.getTrailerResults();
        Trailer trailer = trailerResults.getTrailerResults().get(position);
        if (movies.getImages() != null) {
            String backDrop = movies.getImages().getBackdropsList().get(position).getPath();
            Picasso.get()
                    .load(BuildConfig.BASE_URL_IMAGE_BACKDROP + backDrop)
                    .into(holder.imageViewTrailerBackdrop);
        }
        holder.textViewTrailerTitle.setText(trailer.getName());
        holder.itemView.setTag(trailer.getKey());
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class TrailerViewHolder extends RecyclerView.ViewHolder implements ViewGroup.OnClickListener {

        ImageView imageViewTrailerBackdrop;
        TextView textViewTrailerTitle;

        private TrailerViewHolder(View trailerViewHolder) {
            super(trailerViewHolder);
            imageViewTrailerBackdrop = trailerViewHolder.findViewById(R.id.image_trailer_backdrop);
            textViewTrailerTitle = trailerViewHolder.findViewById(R.id.text_view_trailer);
            trailerViewHolder.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            trailerListener.onTrailerClick(getAdapterPosition()); }
        }
}
