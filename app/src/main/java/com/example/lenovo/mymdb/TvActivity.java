package com.example.lenovo.mymdb;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.ProgressBar;

import java.util.ArrayList;

import jp.wasabeef.recyclerview.animators.SlideInRightAnimator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TvActivity extends AppCompatActivity {
    
    ProgressBar progressBar;
    RecyclerView recyclerViewAiringToday, recyclerViewOnTheAir, recyclerViewPopular, recyclerViewTopRated;
    TvShowsAdapter adapterAiringToday, adapterOnTheAir, adapterPopular, adapterTopRated;
    ArrayList<TvShow> tvShowsAiringToday, tvShowsOnTheAir, tvShowsPopular, tvShowsTopRated;
    ConstraintLayout rootLayout;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv);

        progressBar = findViewById(R.id.progressBar);

        rootLayout = findViewById(R.id.rootLayout);

        recyclerViewAiringToday = findViewById(R.id.recyclerViewAiringToday);
        SlideInRightAnimator animatorUpcmng = new SlideInRightAnimator();
        animatorUpcmng.setInterpolator(new OvershootInterpolator(1f));
        recyclerViewAiringToday.setItemAnimator(animatorUpcmng);
        recyclerViewAiringToday.getItemAnimator().setAddDuration(650);

        recyclerViewOnTheAir = findViewById(R.id.recyclerViewOnTheAir);
        SlideInRightAnimator animatorNowPl = new SlideInRightAnimator();
        animatorNowPl.setInterpolator(new OvershootInterpolator(1f));
        recyclerViewOnTheAir.setItemAnimator(animatorNowPl);
        recyclerViewOnTheAir.getItemAnimator().setAddDuration(650);

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

        tvShowsPopular = new ArrayList<>();
        tvShowsOnTheAir = new ArrayList<>();
        tvShowsAiringToday = new ArrayList<>();
        tvShowsTopRated = new ArrayList<>();

        adapterPopular = new TvShowsAdapter(tvShowsPopular, this, new ItemClickListener() {
            @Override
            public void itemClicked(View view, int position) {
                TvShow tvShow = tvShowsPopular.get(position);
                long id = tvShow.id;
                Intent intent = new Intent(TvActivity.this, TvShowDetailsActivity.class);
                intent.putExtra("tvShow_id", id);
                startActivity(intent);
            }
        });
        adapterOnTheAir = new TvShowsAdapter(tvShowsOnTheAir, this, new ItemClickListener() {
            @Override
            public void itemClicked(View view, int position) {
                TvShow tvShow = tvShowsOnTheAir.get(position);
                long id = tvShow.id;
                Intent intent = new Intent(TvActivity.this, TvShowDetailsActivity.class);
                intent.putExtra("tvShow_id", id);
                startActivity(intent);
            }
        });
        adapterTopRated = new TvShowsAdapter(tvShowsTopRated, this, new ItemClickListener() {
            @Override
            public void itemClicked(View view, int position) {
                TvShow tvShow = tvShowsTopRated.get(position);
                long id = tvShow.id;
                Intent intent = new Intent(TvActivity.this, TvShowDetailsActivity.class);
                intent.putExtra("tvShow_id", id);
                startActivity(intent);
            }
        });
        adapterAiringToday = new TvShowsAdapter(tvShowsAiringToday, this, new ItemClickListener() {
            @Override
            public void itemClicked(View view, int position) {
                TvShow tvShow = tvShowsAiringToday.get(position);
                long id = tvShow.id;
                Intent intent = new Intent(TvActivity.this, TvShowDetailsActivity.class);
                intent.putExtra("tvShow_id", id);
                startActivity(intent);
            }
        });

        recyclerViewPopular.setAdapter(adapterPopular);
        recyclerViewOnTheAir.setAdapter(adapterOnTheAir);
        recyclerViewTopRated.setAdapter(adapterTopRated);
        recyclerViewAiringToday.setAdapter(adapterAiringToday);

        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewPopular.setLayoutManager(layoutManager1);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewOnTheAir.setLayoutManager(layoutManager2);
        LinearLayoutManager layoutManager3 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewAiringToday.setLayoutManager(layoutManager3);
        LinearLayoutManager layoutManager4 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewTopRated.setLayoutManager(layoutManager4);

        SnapHelper snapHelper1 = new LinearSnapHelper();
        snapHelper1.attachToRecyclerView(recyclerViewPopular);
        SnapHelper snapHelper2 = new LinearSnapHelper();
        snapHelper2.attachToRecyclerView(recyclerViewAiringToday);
        SnapHelper snapHelper3 = new LinearSnapHelper();
        snapHelper3.attachToRecyclerView(recyclerViewTopRated);
        SnapHelper snapHelper4 = new LinearSnapHelper();
        snapHelper4.attachToRecyclerView(recyclerViewOnTheAir);

        progressBar.setVisibility(View.VISIBLE);
        rootLayout.setVisibility(View.GONE);

        fetchAiringToday();
        fetchOnTheAir();
        fetchPopular();
        fetchTopRated();
        
    }

    private void fetchAiringToday() {
        Call<TvShowResponse> call = ApiClient.getMoviesService().getAiringTodayTv(1);
        call.enqueue(new Callback<TvShowResponse>() {
            @Override
            public void onResponse(Call<TvShowResponse> call, Response<TvShowResponse> response) {
                TvShowResponse tvShowResponse = response.body();

                tvShowsAiringToday.clear();
                for (int i = 0; i < 20; i++)
                {
                    tvShowsAiringToday.add(tvShowResponse.results.get(i));
                    adapterAiringToday.notifyItemInserted(i);

                }
            }

            @Override
            public void onFailure(Call<TvShowResponse> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void fetchOnTheAir() {
        Call<TvShowResponse> call = ApiClient.getMoviesService().getOnTheAirTv(1);
        call.enqueue(new Callback<TvShowResponse>() {
            @Override
            public void onResponse(Call<TvShowResponse> call, Response<TvShowResponse> response) {
                TvShowResponse tvShowResponse = response.body();

                tvShowsOnTheAir.clear();
                for (int i = 0; i < 20; i++)
                {
                    tvShowsOnTheAir.add(tvShowResponse.results.get(i));
                    adapterOnTheAir.notifyItemInserted(i);

                }
            }

            @Override
            public void onFailure(Call<TvShowResponse> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void fetchPopular() {
        Call<TvShowResponse> call = ApiClient.getMoviesService().getPopularTv(1);
        call.enqueue(new Callback<TvShowResponse>() {
            @Override
            public void onResponse(Call<TvShowResponse> call, Response<TvShowResponse> response) {
                TvShowResponse tvShowResponse = response.body();

                tvShowsPopular.clear();
                for (int i = 0; i < 20; i++)
                {
                    tvShowsPopular.add(tvShowResponse.results.get(i));
                    adapterPopular.notifyItemInserted(i);
                }
            }

            @Override
            public void onFailure(Call<TvShowResponse> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void fetchTopRated() {
        Call<TvShowResponse> call = ApiClient.getMoviesService().getTopRatedTv(1);
        call.enqueue(new Callback<TvShowResponse>() {
            @Override
            public void onResponse(Call<TvShowResponse> call, Response<TvShowResponse> response) {
                TvShowResponse tvShowResponse = response.body();

                tvShowsTopRated.clear();
                for (int i = 0; i < 20; i++)
                {
                    tvShowsTopRated.add(tvShowResponse.results.get(i));
                    adapterTopRated.notifyItemInserted(i);

                    progressBar.setVisibility(View.INVISIBLE);
                    rootLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<TvShowResponse> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }
//
    public void seeAll(View view) {

        long id = view.getId();
        if (id == R.id.seeAllAiringTodayBtn){
            Intent intent = new Intent(this, SeeAllAiringTodayTv.class);
            startActivity(intent);
        }
        else if (id == R.id.seeAllOnTheAirBtn){
            Intent intent = new Intent(this, SeeAllOnTheAirTv.class);
            startActivity(intent);
        }
        else if (id == R.id.seeAllPopularBtn){
            Intent intent = new Intent(this, SeeAllPopularTv.class);
            startActivity(intent);
        }
        else {
            Intent intent = new Intent(this, SeeAllTopRatedTv.class);
            startActivity(intent);
        }

    }
}
