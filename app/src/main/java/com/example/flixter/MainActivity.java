package com.example.flixter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixter.adapters.MovieAdapter;
import com.example.flixter.databinding.ActivityMainBinding;
import com.example.flixter.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class MainActivity extends AppCompatActivity {

    public static final String CONFIGURATION_URL =
            "https://api.themoviedb.org/3/configuration?api_key=970a3c4389490ee855a71b051c6e0574";
    public static final String NOW_PLAYING_URL =
            "https://api.themoviedb.org/3/movie/now_playing?api_key=970a3c4389490ee855a71b051c6e0574";
    public static final String TAG = "MainActivity";

    private List<Movie> movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot()); //R.layout.activity_main

        movies = new ArrayList<>();

        AsyncHttpClient client = new AsyncHttpClient();
        populateMovieWImgSize(client);

        RecyclerView mvRView = findViewById(R.id.movieList);
        final MovieAdapter mvAdapter = new MovieAdapter(this, movies);
        mvRView.setAdapter(mvAdapter);
        mvRView.setLayoutManager(new LinearLayoutManager(this));

        client.get(NOW_PLAYING_URL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d(TAG, "onSuccess Now Playing");
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray results = jsonObject.getJSONArray("results");
                    Log.i(TAG, "Results:" + results.toString());
                    movies.addAll(Movie.fromJsonArray(results));
                    mvAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    Log.e(TAG, "Hit json exception for movie results", e);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.d(TAG, "onFailure Now Playing");
            }
        });
    }

    public static void populateMovieWImgSize(AsyncHttpClient client){
        client.get(CONFIGURATION_URL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d(TAG, "onSuccess Images");
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONObject images = jsonObject.getJSONObject("images");
                    Log.i(TAG, "Images:" + images.toString());

                    JSONArray posterSizes = images.getJSONArray("poster_sizes");
                    ArrayList<String> posterArr = createStringArrayFromJSON(posterSizes);
                    Movie.setPosterSizes(posterArr);
                    Log.i(TAG, "Poster sizes:" + posterArr.toString());

                    JSONArray backdropSizes = images.getJSONArray("backdrop_sizes");
                    ArrayList<String> backdropArr = createStringArrayFromJSON(backdropSizes);
                    Movie.setBackdropSizes(backdropArr);
                    Log.i(TAG, "Backdrop sizes:" + backdropArr.toString());
                } catch (JSONException e) {
                    Log.e(TAG, "Hit json exception for configuration results", e);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.d(TAG, "onFailure Images");
            }
        });
    }

    public static ArrayList<String> createStringArrayFromJSON(JSONArray jsonArray) throws JSONException {
        ArrayList<String> array = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            array.add(jsonArray.getString(i));
        }
        return array;
    }
}