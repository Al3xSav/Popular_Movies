package com.alexsav.popularmovies.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.alexsav.popularmovies.R;
import com.alexsav.popularmovies.model.Movies;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {

    public final Context context;
    private final List<Movies> moviesList;
    private final ListItemOnClickListener mClickListener;

    public MoviesAdapter(List<Movies> moviesList, Context context,
                         ListItemOnClickListener mClickListener) {
        this.moviesList = moviesList;
        this.context = context;
        this.mClickListener = mClickListener;
    }

    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        // Create View and inflate the movies_grid_item into ti
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.movies_grid_item, viewGroup, false);
        // Return this new View we created
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {

        Movies movies = moviesList.get(position);
        // .with(context) wont work.

        Picasso.get().load(movies.getPosterUrl()).into(viewHolder.imageView);

    }

    @Override
    public int getItemCount() {
        // return the number of the items in the list
        return moviesList.size();
    }

    public interface ListItemOnClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final ImageView imageView;

        private ViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.main_grid_img);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            mClickListener.onListItemClick(clickedPosition);
        }
    }
}
