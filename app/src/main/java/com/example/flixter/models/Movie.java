package com.example.flixter.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
public class Movie {
    String posterPath;
    String backdropPath;
    String title;
    static List<String> posterSizes;
    static List<String> backdropSizes;
    Double voteAverage;
    String overview;
    int id;

    public Movie() { }

    public Movie(JSONObject jsonObject) throws JSONException {
        posterPath = jsonObject.getString("poster_path");
        backdropPath = jsonObject.getString("backdrop_path");
        title = jsonObject.getString("title");
        overview = jsonObject.getString("overview");
        voteAverage = jsonObject.getDouble("vote_average");
        id = jsonObject.getInt("id");
    }

    public static List<Movie> fromJsonArray(JSONArray movieJsonArray) throws JSONException {
        List<Movie> movies = new ArrayList<>();
        for (int i = 0; i < movieJsonArray.length(); i++) {
            movies.add(new Movie(movieJsonArray.getJSONObject(i)));
        }
        return movies;
    }

    public static void setPosterSizes(List<String> posterSizes) {
        Movie.posterSizes = posterSizes;
    }

    public static void setBackdropSizes(List<String> backdropSizes) {
        Movie.backdropSizes = backdropSizes;
    }

    public String getPosterPath() {
        String size =  "w342";
        if (posterSizes != null) {
            size = posterSizes.get(posterSizes.size()-1);
        }
        return "https://image.tmdb.org/t/p/" + size + "/" + posterPath;
    }

    public String getBackdropPath() {
        String size =  "w780";
        if (backdropSizes != null) {
            size = backdropSizes.get(backdropSizes.size()-1);
        }
        return "https://image.tmdb.org/t/p/" + size + "/" + backdropPath;
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public int getId() {
        return id;
    }
}
