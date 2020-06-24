package com.example.flixter.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Movie {
    private String poster_path;
    private String title;
    private static List<String> poster_sizes;
    private static List<String> backdrop_sizes;

    private String overview;

    public Movie(JSONObject jsonObject) throws JSONException {
        poster_path = jsonObject.getString("poster_path");
        title = jsonObject.getString("title");
        overview = jsonObject.getString("overview");
    }

    public static List<Movie> fromJsonArray(JSONArray movieJsonArray) throws JSONException {
        List<Movie> movies = new ArrayList<>();
        for (int i = 0; i < movieJsonArray.length(); i++) {
            movies.add(new Movie(movieJsonArray.getJSONObject(i)));
        }
        return movies;
    }

    public static void setPoster_sizes(List<String> poster_sizes) {
        Movie.poster_sizes = poster_sizes;
    }

    public static void setBackdrop_sizes(List<String> backdrop_sizes) {
        Movie.backdrop_sizes = backdrop_sizes;
    }

    public String getPoster_path() {
        return "https://image.tmdb.org/t/p/" + poster_sizes.get(poster_sizes.size()-1) + "/" + poster_path;
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }
}
