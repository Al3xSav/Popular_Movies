package com.alexsav.popularmovies.adapters;

import android.content.Context;
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
import com.alexsav.popularmovies.model.TrailerResponse;
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

    @Override
    public TrailerAdapter.TrailerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutId = R.layout.trailer_item;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(layoutId, parent, false);
        TrailerViewHolder viewHolder = new TrailerViewHolder(view);
        return viewHolder;
        //return new TrailerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TrailerAdapter.TrailerViewHolder trailerViewHolder, int position) {

        TrailerResponse trailerResponse = movies.getTrailerResponse();
        Trailer trailer = trailerResponse.getTrailerResponse().get(position);
        if (movies.getImages() != null) {
            String backDrop = movies.getImages().getBackdropsList().get(position).getPath();
            Picasso.get()
                    .load(BuildConfig.BASE_URL_IMAGE_BACKDROP + backDrop)
                    .into(trailerViewHolder.imageViewTrailerBackdrop);
        }
        trailerViewHolder.textViewTrailerTitle.setText(trailer.getName());
        trailerViewHolder.itemView.setTag(trailer.getKey());
    }

    @Override
    public int getItemCount() {
        if (movies.getTrailerResponse() == null) {
            return 0;
        }
        return movies.getTrailerResponse().getTrailerResponse().size();
    }

    class TrailerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

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
