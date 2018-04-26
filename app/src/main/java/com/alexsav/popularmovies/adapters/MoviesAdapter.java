package com.alexsav.popularmovies.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.view.View.OnClickListener;
import com.alexsav.popularmovies.R;
import com.alexsav.popularmovies.model.Movies;
import com.alexsav.popularmovies.utils.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> {

    Context context;
    List<Movies> moviesList;
    ListItemOnClickListener clickListener;


    public MoviesAdapter(List<Movies> moviesList, Context context,
                         ListItemOnClickListener clickListener) {
        this.moviesList = moviesList;
        this.context = context;
        this.clickListener = clickListener;
    }

    public void setMoviesList(List<Movies> list) {
        if (list != null) {
            this.moviesList = list;
            notifyDataSetChanged();
        }
    }


    @NonNull
    @Override
    public MoviesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        // Create View and inflate the movies_grid_item into it
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.movies_grid_item, viewGroup, false);
        // Return this new View we created
        return new MoviesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesViewHolder viewHolder, int position) {

        Movies movies = moviesList.get(position);

        Picasso.get()
                .load(NetworkUtils.POSTER_URL + movies.getPosterUrl())
                .error(R.drawable.error_img)
                .into(viewHolder.imageView);

        viewHolder.itemView.setTag(movies);
    }

    @Override
    public int getItemCount() {
        // return the number of the items in the list
        if (moviesList != null) {
            return moviesList.size();
        } else {
            return 0;
        }
    }

    public interface ListItemOnClickListener {
        void onListClick(int clickedItemIndex);
    }

    public class MoviesViewHolder extends RecyclerView.ViewHolder implements OnClickListener {

        private final ImageView imageView;

        private MoviesViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.main_grid_img);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            clickListener.onListClick(getAdapterPosition());
        }
    }
}
