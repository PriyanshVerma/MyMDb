package com.example.lenovo.mymdb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.AbsListView;

import java.util.ArrayList;

import jp.wasabeef.recyclerview.animators.SlideInRightAnimator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SeeAllPopular extends AppCompatActivity {

    RecyclerView recyclerView;
    MoviesRecommendedAdapter seeAllPopularAdapter;
    ArrayList<Movie> allPopularMovies;
    GridLayoutManager manager;


    Boolean isScrolling = false;
    int currentItems, totalItems, scrollOutItems;
    int pageCnt = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_all_popular);

        recyclerView = findViewById(R.id.recyclerView);
        SlideInRightAnimator animatorUpcmng = new SlideInRightAnimator();
        animatorUpcmng.setInterpolator(new OvershootInterpolator(1f));
        recyclerView.setItemAnimator(animatorUpcmng);
        recyclerView.getItemAnimator().setAddDuration(650);

        allPopularMovies = new ArrayList<>();
        seeAllPopularAdapter = new MoviesRecommendedAdapter(this, allPopularMovies, new ItemClickListener() {
            @Override
            public void itemClicked(View view, int position) {
                Movie movie = allPopularMovies.get(position);
                long id = movie.id;
                Intent intent = new Intent(SeeAllPopular.this, MovieDetailsActivity.class);
                intent.putExtra("movie_id", id);
                startActivity(intent);
            }
        });

        recyclerView.setAdapter(seeAllPopularAdapter);
        manager = new GridLayoutManager(this, 3, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                    isScrolling = true;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                currentItems = manager.getChildCount();
                totalItems = manager.getItemCount();
                scrollOutItems = manager.findFirstVisibleItemPosition();

                if (isScrolling && (currentItems + scrollOutItems == totalItems)){
                    isScrolling = false;
                    pageCnt++;
                    fetchAllPopular();
                }
            }
        });

        fetchAllPopular();
    }

    private void fetchAllPopular() {
        Call<MovieResponse> call = ApiClient.getMoviesService().getPopularMovies(pageCnt);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                MovieResponse movieResponses = response.body();

                if (pageCnt <= movieResponses.total_pages)
                {
                    allPopularMovies.addAll(movieResponses.results);
                    seeAllPopularAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

            }
        });
    }
}






