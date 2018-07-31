package com.example.lenovo.mymdb;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TvShowDetailsActivity extends AppCompatActivity {

    ImageView backgroundImg, posterImg, adult;
    Button favBtn, shareBtn;
    TextView ratingTv, nameTv, genresTv, overviewTv, firstDateTv, runtimeTv, lastDateTv, webTv, seasonsTv, episodesTv;
    TextView recommendedHeaderTextView, similarHeaderTextView, reviewsHeaderTextView;

    RecyclerView castRv, trailersRv, recommendedRv, similarRv, reviewsRv;

    MovieCastAdapter castAdapter;
    TvRecommendedAdapter recommendedTvAdapter, similarTvAdapter;
    TrailerVideoAdapter trailerVideoAdapter;
    ReviewsAdapter reviewsAdapter;

    ArrayList<MovieCast> tvCastArrayList;
    ArrayList<TvShow> recommendedTvArrayList, similarTvArrayList;
    ArrayList<TrailerVideo> trailerVideosArrayList;
    ArrayList<Review> reviewArrayList;

    long tv_id;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_details);

        Intent intent = getIntent();
        tv_id = intent.getLongExtra("tvShow_id", 0);

        adult = findViewById(R.id.adult);
        adult.setVisibility(View.GONE);
        backgroundImg = findViewById(R.id.backgrndImage);
        posterImg = findViewById(R.id.posterImage);
        ratingTv = findViewById(R.id.ratingTextView);
        nameTv = findViewById(R.id.nameTextView);
        overviewTv = findViewById(R.id.overviewTextView);
        firstDateTv = findViewById(R.id.firstDateTextView);
        runtimeTv = findViewById(R.id.runtimeTextView);
        lastDateTv = findViewById(R.id.lastDateTextView);
        webTv = findViewById(R.id.web);
        seasonsTv = findViewById(R.id.seasonsNumber);
        episodesTv = findViewById(R.id.episodesNumber);

        similarHeaderTextView = findViewById(R.id.similardHeaderTextView);
        recommendedHeaderTextView = findViewById(R.id.recommendedHeaderTextView);
        reviewsHeaderTextView = findViewById(R.id.reviewsHeader);
        
        /////
        tvCastArrayList = new ArrayList<>();
        castRv = findViewById(R.id.castRecyclerView);//
        castAdapter = new MovieCastAdapter(tvCastArrayList, this, new ItemClickListener() {
            @Override
            public void itemClicked(View view, int position) {
                MovieCast tvCastPerson = tvCastArrayList.get(position);
                int id = tvCastPerson.id;
                Intent intent = new Intent(TvShowDetailsActivity.this, PersonDetailsActivity.class);
                intent.putExtra("person_id", id);
                startActivity(intent);
            }
        });
        castRv.setAdapter(castAdapter);//
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        castRv.setLayoutManager(layoutManager1);//


        recommendedTvArrayList = new ArrayList<>();
        recommendedRv = findViewById(R.id.recommendedTvRecyclerView);
        recommendedTvAdapter = new TvRecommendedAdapter(this, recommendedTvArrayList, new ItemClickListener() {
            @Override
            public void itemClicked(View view, int position) {
                TvShow tvShow = recommendedTvArrayList.get(position);
                long id = tvShow.id;
                Intent intent = new Intent(TvShowDetailsActivity.this, TvShowDetailsActivity.class);
                intent.putExtra("tvShow_id", id);
                startActivity(intent);
            }
        });
        recommendedRv.setAdapter(recommendedTvAdapter);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recommendedRv.setLayoutManager(layoutManager2);

        similarTvArrayList = new ArrayList<>();
        similarRv = findViewById(R.id.similarTvRecyclerView);//
        similarTvAdapter = new TvRecommendedAdapter(this, similarTvArrayList, new ItemClickListener() {
            @Override
            public void itemClicked(View view, int position) {
                TvShow tvShow = similarTvArrayList.get(position);
                long id = tvShow.id;
                Intent intent = new Intent(TvShowDetailsActivity.this, TvShowDetailsActivity.class);
                intent.putExtra("tvShow_id", id);
                startActivity(intent);
            }
        });
        similarRv.setAdapter(similarTvAdapter);//
        LinearLayoutManager layoutManager3 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        similarRv.setLayoutManager(layoutManager3);//

        trailerVideosArrayList = new ArrayList<>();
        trailersRv = findViewById(R.id.trailersRecyclerView);
        trailerVideoAdapter = new TrailerVideoAdapter(trailerVideosArrayList, this, new TrailerVideoClickListener() {
            @Override
            public void trailerVideoClicked(View view, int position) {
                TrailerVideo trailerVideo = trailerVideosArrayList.get(position);
                String key = trailerVideo.key;
                Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + key));
                Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + key));

                try{
                    TvShowDetailsActivity.this.startActivity(appIntent);
                } catch (ActivityNotFoundException ex) {
                    TvShowDetailsActivity.this.startActivity(webIntent);
                }
            }
        });
        trailersRv.setAdapter(trailerVideoAdapter);
        LinearLayoutManager layoutManager4 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        trailersRv.setLayoutManager(layoutManager4);

        reviewArrayList = new ArrayList<>();
        reviewsRv = findViewById(R.id.reviewsRecyclerView);
        reviewsAdapter = new ReviewsAdapter(reviewArrayList, this);
        reviewsRv.setAdapter(reviewsAdapter);
        LinearLayoutManager llm = new LinearLayoutManager(this, StaggeredGridLayoutManager.VERTICAL, false);
        reviewsRv.setLayoutManager(llm);



        fetchDetailsOfThisTvShow();
        fetchCast();
        fetchTrailers();
        fetchRecommendedTv();
        fetchSimilarTv();
        fetchReviews();

    }

    private void fetchReviews() {
        Call<ReviewResponse> call = ApiClient.getMoviesService().getTvReviews(tv_id);
        call.enqueue(new Callback<ReviewResponse>() {
            @Override
            public void onResponse(Call<ReviewResponse> call, Response<ReviewResponse> response) {
                ReviewResponse reviewResponse = response.body();
                ArrayList<Review> reviews = reviewResponse.results;

                reviewArrayList.clear();
                reviewArrayList.addAll(reviews);
                reviewsAdapter.notifyDataSetChanged();

                if (reviews.size() == 0){
                    reviewsHeaderTextView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ReviewResponse> call, Throwable t) {

            }
        });
    }

    private void fetchDetailsOfThisTvShow() {
        Call<TvShow> call = ApiClient.getMoviesService().getThisTvDetails(tv_id);
        call.enqueue(new Callback<TvShow>(){
            @Override
            public void onResponse(Call<TvShow> call, Response<TvShow> response) {
                TvShow tvShow = response.body();

                boolean isAdult = false;
//                if (tvShow.() == true) adult.setVisibility(View.VISIBLE);
                nameTv.setText(tvShow.name);
                ratingTv.setText(tvShow.vote_average+"");
                overviewTv.setText(tvShow.overview+"");
                firstDateTv.setText(tvShow.first_air_date);
                lastDateTv.setText(tvShow.last_air_date);
                int i;
                for (i = 0; i < tvShow.episode_run_time.size() - 1; i ++){
                    runtimeTv.setText(tvShow.episode_run_time + " minutes");
                }

                webTv.setText(tvShow.homepage);
                seasonsTv.setText(tvShow.number_of_seasons + "");
                episodesTv.setText(tvShow.number_of_episodes + "");

                Picasso.get().load("http://image.tmdb.org/t/p/w780" + tvShow.backdrop_path).into(backgroundImg);
                Picasso.get().load("http://image.tmdb.org/t/p/w780" + tvShow.poster_path).into(posterImg);
            }

            @Override
            public void onFailure(Call<TvShow> call, Throwable t) {
//
            }
        });
    }

    private void fetchCast() {
        Call<CastResponse> call = ApiClient.getMoviesService().getThisTvCast(tv_id);
        call.enqueue(new Callback<CastResponse>(){
            @Override
            public void onResponse(Call<CastResponse> call, Response<CastResponse> response) {
                CastResponse castResponse = response.body();

                {
                    tvCastArrayList.addAll(castResponse.castArrayList);
                    castAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<CastResponse> call, Throwable t) {

            }
        });
    }

    private void fetchTrailers() {
        Call<TrailerVideoResponse> call = ApiClient.getMoviesService().getThisTvTrailersVideos(tv_id);
        call.enqueue(new Callback<TrailerVideoResponse>() {
            @Override
            public void onResponse(Call<TrailerVideoResponse> call, Response<TrailerVideoResponse> response) {
                TrailerVideoResponse trailerVideoResponse = response.body();
                ArrayList<TrailerVideo> trailerVideos = trailerVideoResponse.results;

                trailerVideosArrayList.clear();
                trailerVideosArrayList.addAll(trailerVideos);
                trailerVideoAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<TrailerVideoResponse> call, Throwable t) {

            }
        });
    }

    private void fetchRecommendedTv() {
        Call<TvShowResponse> call = ApiClient.getMoviesService().getRecommendedTv(tv_id);
        call.enqueue(new Callback<TvShowResponse>() {
            @Override
            public void onResponse(Call<TvShowResponse> call, Response<TvShowResponse> response) {
                TvShowResponse tvShowResponse = response.body();
                ArrayList<TvShow> tvShows = tvShowResponse.results;

                recommendedTvArrayList.clear();
                recommendedTvArrayList.addAll(tvShows);
                recommendedTvAdapter.notifyDataSetChanged();

                if (tvShows.size() == 0){
                    recommendedHeaderTextView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<TvShowResponse> call, Throwable t) {

            }
        });
    }

    private void fetchSimilarTv() {
        Call<TvShowResponse> call = ApiClient.getMoviesService().getSimilarTv(tv_id);
        call.enqueue(new Callback<TvShowResponse>() {
            @Override
            public void onResponse(Call<TvShowResponse> call, Response<TvShowResponse> response) {
                TvShowResponse tvShowResponse = response.body();
                ArrayList<TvShow> tvShows = tvShowResponse.results;

                similarTvArrayList.clear();
                similarTvArrayList.addAll(tvShows);
                similarTvAdapter.notifyDataSetChanged();

                if (tvShows.size() == 0){
                    recommendedHeaderTextView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<TvShowResponse> call, Throwable t) {

            }
        });
    }

    public void goBack(View view) {
        finish();
    }
}
