package com.example.flixter;

import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixter.databinding.ActivityMovieTrailerBinding;
import com.example.flixter.models.Movie;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import okhttp3.Headers;

public class MovieTrailerActivity extends YouTubeBaseActivity {

    String videoKey;
    String site;
    final String TAG = "MovieTrailerActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        site = getIntent().getStringExtra("site");
        videoKey = getIntent().getStringExtra("videoKey");

        Log.d(TAG, "site: " + site + " & videoKey: " + videoKey);
        showVideo();
    }

    // Attempt to show video if video key has been found and video is on Youtube
    private void showVideo(){
        if (videoKey == null) {
            Toast.makeText(this, "No Trailer: Video key not found",
                    Toast.LENGTH_SHORT).show();
        } else if (!site.equals("YouTube")){
            Toast.makeText(this, "No Trailer: Video not on Youtube",
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Running video",
                    Toast.LENGTH_SHORT).show();

            // Resolve player view from the layout in xml file
            YouTubePlayerView playerView
                    = ActivityMovieTrailerBinding.inflate(getLayoutInflater()).player;

            // Initialize with API key stored in secrets.xml
            playerView.initialize(getString(R.string.youtube_api_key),
                    new YouTubePlayer.OnInitializedListener() {
                        @Override
                        public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                            YouTubePlayer youTubePlayer, boolean b) {
                            youTubePlayer.setFullscreen(true);
                            youTubePlayer.loadVideo(videoKey);
                        }

                        @Override
                        public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                            YouTubeInitializationResult
                                                                    youTubeInitializationResult) {
                            Log.e("MovieTrailerActivity", "Error initializing YouTube player");
                        }
                    });
        }
    }

}
