package com.example.flixter;

import android.content.res.Configuration;
import android.media.Image;
import android.os.Bundle;
import android.os.Parcel;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.flixter.databinding.ActivityMovieDetailsBinding;
import com.example.flixter.models.Movie;

import org.parceler.Parcels;

public class MovieDetailsActivity extends AppCompatActivity {
    Movie movie;

    ImageView tvPoster;
    TextView tvTitle;
    TextView tvOverview;
    RatingBar rbVoteAverage;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMovieDetailsBinding binding = ActivityMovieDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot()); //R.layout.activity_movie_details

        tvPoster = binding.tvPoster;
        tvTitle = binding.tvTitle;
        tvOverview = binding.tvOverview;
        rbVoteAverage = binding.rbVoteAverage;


        movie = Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));

        try {
            Log.d("MovieDetailsActivity", String.format("Showing details for '%s'", movie.getTitle()));

            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());
            rbVoteAverage.setRating(movie.getVoteAverage().floatValue()/2.0f);
        } catch (NullPointerException e) {
            Log.w("MovieDetailsActivity", "Can't find movie for new Activity");
        }

        int placeholder;
        String imageURL;
        if (this.getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE) {
            placeholder = R.drawable.flicks_backdrop_placeholder;
            imageURL = movie.getBackdropPath();
        } else {
            placeholder = R.drawable.flicks_movie_placeholder;
            imageURL = movie.getPosterPath();
        }

        Glide.with(this).
                load(imageURL).
                transform(new RoundedCorners(40)).
                placeholder(placeholder).
                into(tvPoster);
    }
}
