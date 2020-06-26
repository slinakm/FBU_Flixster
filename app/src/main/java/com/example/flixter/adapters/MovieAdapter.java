package com.example.flixter.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.flixter.MainActivity;
import com.example.flixter.MovieDetailsActivity;
import com.example.flixter.R;
import com.example.flixter.databinding.ItemMovieBinding;
import com.example.flixter.models.Movie;

import org.parceler.Parcels;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    Activity context;
    List<Movie> movies;

    public MovieAdapter(Activity context, List<Movie> movies) {
        super();
        this.context = context;
        this.movies = movies;
    }

    // Inflate layout from XML and return ViewHolder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("MovieAdapter", "onCreateViewHolder");
        ItemMovieBinding binding =
                ItemMovieBinding.inflate(context.getLayoutInflater(), parent, false);
//        View movieView = binding.getRoot();
// LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(binding);
    }

    // Bind new data at position to holder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("MovieAdapter", "onBindViewHolder");
        holder.bind(movies.get(position));
    }

    // Return total number of items
    @Override
    public int getItemCount() {
        Log.d("MovieAdapter", "getItemCount");
        return movies.size();
    }

    // Custom ViewHolders for Movies
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView title;
        TextView overview;
        ImageView poster;

        public ViewHolder(@NonNull ItemMovieBinding itemView) {
            super(itemView.getRoot());


            title = itemView.tvTitle;
            overview = itemView.tvOverview;
            poster = itemView.tvPoster;

            itemView.getRoot().setOnClickListener(this);
        }

        public void bind(Movie movie) {
            title.setText(movie.getTitle());
            overview.setText(movie.getOverview());

            // Changes movie image based on orientation
            int placeholder;
            String imageURL;
            if (context.getResources().getConfiguration().orientation
                    == Configuration.ORIENTATION_LANDSCAPE) {
                placeholder = R.drawable.flicks_backdrop_placeholder;
                imageURL = movie.getBackdropPath();
            } else {
                placeholder = R.drawable.flicks_movie_placeholder;
                imageURL = movie.getPosterPath();
            }

            Glide.with(context).
                    load(imageURL).
                    transform(new RoundedCorners(40)).
                    placeholder(placeholder).
                    into(poster);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();

            if (position != RecyclerView.NO_POSITION) {
                Movie movie = movies.get(position);
                Intent intent = new Intent(context, MovieDetailsActivity.class);
                intent.putExtra(Movie.class.getSimpleName(), Parcels.wrap(movie));
                context.startActivity(intent);
            }
        }
    }
}
