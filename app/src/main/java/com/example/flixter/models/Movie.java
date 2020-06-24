package com.example.flixter.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Movie {
    private String poster_path;
    private String title;

    public String getPoster_path() {
        return String.format("",poster_path);
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

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
}
