package com.example.lenovo.mymdb;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.Toolbar;

import java.util.ArrayList;

import jp.wasabeef.recyclerview.animators.SlideInRightAnimator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesActivity extends AppCompatActivity {
    ProgressBar progressBar;
    RecyclerView recyclerViewPopular, recyclerViewNowPlaying, recyclerViewUpcoming, recyclerViewTopRated;
    MoviesAdapter adapterPopular, adapterNowPlaying, adapterUpcoming, adapterTopRated;
    ArrayList<Movie> moviesPopular, moviesNowPlaying, moviesUpcoming, moviesTopRated;
    ConstraintLayout rootLayout;
//    android.support.v7.widget.Toolbar toolbar;
    boolean isLoaded1 = false, isLoaded2 = false, isLoaded3 = false, isLoaded4 = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

//        toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        progressBar = findViewById(R.id.progressBar);

        rootLayout = findViewById(R.id.rootLayout);

        recyclerViewUpcoming = findViewById(R.id.recyclerViewUpcoming);
        SlideInRightAnimator animatorUpcmng = new SlideInRightAnimator();
        animatorUpcmng.setInterpolator(new OvershootInterpolator(1f));
        recyclerViewUpcoming.setItemAnimator(animatorUpcmng);
        recyclerViewUpcoming.getItemAnimator().setAddDuration(650);

        recyclerViewNowPlaying = findViewById(R.id.recyclerViewNowPlaying);
        SlideInRightAnimator animatorNowPl = new SlideInRightAnimator();
        animatorNowPl.setInterpolator(new OvershootInterpolator(1f));
        recyclerViewNowPlaying.setItemAnimator(animatorNowPl);
        recyclerViewNowPlaying.getItemAnimator().setAddDuration(650);

        recyclerViewPopular = findViewById(R.id.recyclerViewPopular);
        SlideInRightAnimator animatorPopular = new SlideInRightAnimator();
        animatorPopular.setInterpolator(new OvershootInterpolator(1f));
        recyclerViewPopular.setItemAnimator(animatorPopular);
        recyclerViewPopular.getItemAnimator().setAddDuration(650);

        recyclerViewTopRated = findViewById(R.id.recyclerViewTopRated);
        SlideInRightAnimator animatorTopRtd = new SlideInRightAnimator();
        animatorTopRtd.setInterpolator(new OvershootInterpolator(1f));
        recyclerViewTopRated.setItemAnimator(animatorTopRtd);
        recyclerViewTopRated.getItemAnimator().setAddDuration(650);

        moviesPopular = new ArrayList<>();
        moviesNowPlaying = new ArrayList<>();
        moviesUpcoming = new ArrayList<>();
        moviesTopRated = new ArrayList<>();

        adapterPopular = new MoviesAdapter(moviesPopular, this, new ItemClickListener() {
            @Override
            public void itemClicked(View view, int position) {
                Movie movie = moviesPopular.get(position);
                long id = movie.id;
                Intent intent = new Intent(MoviesActivity.this, MovieDetailsActivity.class);
                intent.putExtra("movie_id", id);
                startActivity(intent);
            }
        });
        adapterNowPlaying = new MoviesAdapter(moviesNowPlaying, this, new ItemClickListener() {
            @Override
            public void itemClicked(View view, int position) {
                Movie movie = moviesNowPlaying.get(position);
                long id = movie.id;
                Intent intent = new Intent(MoviesActivity.this, MovieDetailsActivity.class);
                intent.putExtra("movie_id", id);
                startActivity(intent);
            }
        });
        adapterTopRated = new MoviesAdapter(moviesTopRated, this, new ItemClickListener() {
            @Override
            public void itemClicked(View view, int position) {
                Movie movie = moviesTopRated.get(position);
                long id = movie.id;
                Intent intent = new Intent(MoviesActivity.this, MovieDetailsActivity.class);
                intent.putExtra("movie_id", id);
                startActivity(intent);
            }
        });
        adapterUpcoming = new MoviesAdapter(moviesUpcoming, this, new ItemClickListener() {
            @Override
            public void itemClicked(View view, int position) {
                Movie movie = moviesUpcoming.get(position);
                long id = movie.id;
                Intent intent = new Intent(MoviesActivity.this, MovieDetailsActivity.class);
                intent.putExtra("movie_id", id);
                startActivity(intent);
            }
        });

        recyclerViewPopular.setAdapter(adapterPopular);
        recyclerViewNowPlaying.setAdapter(adapterNowPlaying);
        recyclerViewTopRated.setAdapter(adapterTopRated);
        recyclerViewUpcoming.setAdapter(adapterUpcoming);

        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewPopular.setLayoutManager(layoutManager1);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewNowPlaying.setLayoutManager(layoutManager2);
        LinearLayoutManager layoutManager3 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewUpcoming.setLayoutManager(layoutManager3);
        LinearLayoutManager layoutManager4 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewTopRated.setLayoutManager(layoutManager4);

        SnapHelper snapHelper1 = new LinearSnapHelper();
        snapHelper1.attachToRecyclerView(recyclerViewPopular);
        SnapHelper snapHelper2 = new LinearSnapHelper();
        snapHelper2.attachToRecyclerView(recyclerViewUpcoming);
        SnapHelper snapHelper3 = new LinearSnapHelper();
        snapHelper3.attachToRecyclerView(recyclerViewTopRated);
        SnapHelper snapHelper4 = new LinearSnapHelper();
        snapHelper4.attachToRecyclerView(recyclerViewNowPlaying);

        progressBar.setVisibility(View.VISIBLE);
        rootLayout.setVisibility(View.GONE);

        fetchUpcoming();
        fetchNowPlaying();
        fetchPopularMovies();
        fetchTopRated();

//        checkLoaded();
    }

    private void checkLoaded() {
        if (isLoaded1 && isLoaded2 && isLoaded3 && isLoaded4){
            progressBar.setVisibility(View.INVISIBLE);
            rootLayout.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.search) {
            Intent intent = new Intent(this, SearchActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    private void fetchNowPlaying() {

        Call<MovieResponse> call = ApiClient.getMoviesService().getPopularMovies(1);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                MovieResponse movieResponses = response.body();
                moviesNowPlaying.clear();
                for (int i = 0; i < 20; i++)
                {
                    moviesNowPlaying.add(movieResponses.results.get(i));
                    adapterNowPlaying.notifyItemInserted(i);
                    isLoaded1 = true;
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void fetchPopularMovies() {

        Call<MovieResponse> call = ApiClient.getMoviesService().getNowPlayingMovies(1);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                MovieResponse movieResponses = response.body();
                moviesPopular.clear();
                for (int i = 0; i < 20; i++) {
                    moviesPopular.add(movieResponses.results.get(i));
                    adapterPopular.notifyItemInserted(i);
                    isLoaded2 = true;
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
            }
        });

    }

    private void fetchTopRated(){

        Call<MovieResponse> call = ApiClient.getMoviesService().getTopRatedMovies(1);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                MovieResponse movieResponses = response.body();
                moviesTopRated.clear();
                for (int i = 0; i < 20; i++)
                {
                    moviesTopRated.add(movieResponses.results.get(i));
                    adapterTopRated.notifyItemInserted(i);
                    isLoaded3 = true;
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void fetchUpcoming(){

        Call<MovieResponse> call = ApiClient.getMoviesService().getAllUpcomingMovies(1);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                MovieResponse movieResponses = response.body();
                moviesUpcoming.clear();
                for (int i = 0; i < 20; i++)
                {
                    moviesUpcoming.add(movieResponses.results.get(i));
                    adapterUpcoming.notifyItemInserted(i);
                    isLoaded4 = true;

                    {
                        progressBar.setVisibility(View.INVISIBLE);
                        rootLayout.setVisibility(View.VISIBLE);
                    }

                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }

    public void seeAll(View view) {
        long id = view.getId();
        if (id == R.id.seeAllUpcomingBtn){
            Intent intent = new Intent(this, SeeAllUpcoming.class);
            startActivity(intent);
        }
        else if (id == R.id.seeAllNowPlayingBtn){
            Intent intent = new Intent(this, SeeAllNowPlaying.class);
            startActivity(intent);
        }
        else if (id == R.id.seeAllPopularBtn){
            Intent intent = new Intent(this, SeeAllPopular.class);
            startActivity(intent);
        }
        else {
            Intent intent = new Intent(this, SeeAllTopRated.class);
            startActivity(intent);
        }
    }
}