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

public class SeeAllAiringTodayTv extends AppCompatActivity {
    RecyclerView recyclerView;
    TvRecommendedAdapter seeAllAiringTodayAdapter;
    ArrayList<TvShow> allAiringTodayTv;
    GridLayoutManager manager;
    Boolean isScrolling = false;
    int currentItems, totalItems, scrollOutItems;
    int pageCnt = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_all_airing_today_tv);

        recyclerView = findViewById(R.id.recyclerView);
        SlideInRightAnimator animatorUpcmng = new SlideInRightAnimator();
        animatorUpcmng.setInterpolator(new OvershootInterpolator(1f));
        recyclerView.setItemAnimator(animatorUpcmng);
        recyclerView.getItemAnimator().setAddDuration(650);

        allAiringTodayTv = new ArrayList<>();
        seeAllAiringTodayAdapter = new TvRecommendedAdapter(this, allAiringTodayTv, new ItemClickListener() {
            @Override
            public void itemClicked(View view, int position) {
                TvShow tvShow = allAiringTodayTv.get(position);
                long id = tvShow.id;
                Intent intent = new Intent(SeeAllAiringTodayTv.this, TvShowDetailsActivity.class);
                intent.putExtra("movie_id", id);
                startActivity(intent);
            }
        });

        recyclerView.setAdapter(seeAllAiringTodayAdapter);
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
                    fetchAllAiringToday();
                }
            }
        });

        fetchAllAiringToday();
    }

    private void fetchAllAiringToday() {
//        if (pageCnt != 1)  pageCnt++;

        Call<TvShowResponse> call = ApiClient.getMoviesService().getAiringTodayTv(pageCnt);
        call.enqueue(new Callback<TvShowResponse>() {
            @Override
            public void onResponse(Call<TvShowResponse> call, Response<TvShowResponse> response) {
                TvShowResponse TvShowResponse = response.body();
                    {
                        allAiringTodayTv.addAll(TvShowResponse.results);
                        seeAllAiringTodayAdapter.notifyDataSetChanged();

                    }
            }

            @Override
            public void onFailure(Call<TvShowResponse> call, Throwable t) {

            }
        });
    }
}
