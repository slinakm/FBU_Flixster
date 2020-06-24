package com.example.flixter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
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
        setContentView(R.layout.activity_main);

        AsyncHttpClient client = new AsyncHttpClient();

        client.get(NOW_PLAYING_URL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d(TAG, "onSuccess");
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray results = jsonObject.getJSONArray("results");
                    Log.i(TAG, "Results:" + results.toString());
                    movies = Movie.fromJsonArray(results);
                } catch (JSONException e) {
                    Log.e(TAG, "Hit json exception for movie results", e);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.d(TAG, "onFailure");
            }
        });

        client.get(CONFIGURATION_URL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d(TAG, "onSuccess");
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONObject images = jsonObject.getJSONObject("images");
                    Log.i(TAG, "Images:" + images.toString());

                    JSONArray posterSizes = images.getJSONArray("poster_sizes");
                    ArrayList<String> posterArr = createStringArrayFromJSON(posterSizes);
                    Movie.setPoster_sizes(posterArr);

                    JSONArray backdropSizes = images.getJSONArray("backdrop_sizes");
                    ArrayList<String> backdropArr = createStringArrayFromJSON(backdropSizes);
                    Movie.setBackdrop_sizes(backdropArr);
                } catch (JSONException e) {
                    Log.e(TAG, "Hit json exception for configuration results", e);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.d(TAG, "onFailure");
            }
        });
    }

    public static ArrayList<String> createStringArrayFromJSON(JSONArray jsonArray) throws JSONException {
        ArrayList<String> array = new ArrayList<String>();
        for (int i = 0; i < jsonArray.length(); i++) {
            array.add(jsonArray.getJSONObject(i).toString());
        }
        return array;
    }
}