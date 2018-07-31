package com.example.lenovo.mymdb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button movies, tvshows;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    movies = findViewById(R.id.moviesBtn);
    tvshows = findViewById(R.id.TVshowsBtn);
    }

    public void goToMovies(View view) {
        startActivity(new Intent(this, MoviesActivity.class));
    }

    public void goToTvShows(View view) {
        startActivity(new Intent(this, TvActivity.class));
    }
}
