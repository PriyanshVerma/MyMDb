package com.example.lenovo.mymdb;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class MovieDetailsActivity extends AppCompatActivity {

    ImageView backgroundImg, posterImg, adult;
    Button favBtn, shareBtn;
    TextView ratingTv, nameTv, genresTv, overviewTv, releaseDateTv, runtimeTv;
    TextView recommendedHeaderTextView, similarHeaderTextView, reviewsHeaderTectView;

    RecyclerView castRv, trailersRv, recommendedRv, similarRv, reviewsRv;

    MovieCastAdapter castAdapter;
    MoviesRecommendedAdapter recommendedMoviesAdapter, similarMoviesAdapter;
    TrailerVideoAdapter trailerVideoAdapter;
    ReviewsAdapter reviewsAdapter;

    ArrayList<MovieCast> movieCastArrayList;
    ArrayList<Movie> recommendedMoviesArrayList, similarMoviesArrayList;
    ArrayList<TrailerVideo> trailerVideosArrayList;
    ArrayList<Review> reviewArrayList;

    long movie_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        Intent intent = getIntent();
        movie_id = intent.getLongExtra("movie_id", 0);

        adult = findViewById(R.id.adult);
        adult.setVisibility(View.GONE);
        backgroundImg = findViewById(R.id.backgrndImage);
        posterImg = findViewById(R.id.posterImage);
        ratingTv = findViewById(R.id.ratingTextView);
        nameTv = findViewById(R.id.nameTextView);
        overviewTv = findViewById(R.id.overviewTextView);
        releaseDateTv = findViewById(R.id.releaseDateTextView);
        runtimeTv = findViewById(R.id.runtimeTextView);

        similarHeaderTextView = findViewById(R.id.similardHeaderTextView);
        recommendedHeaderTextView = findViewById(R.id.recommendedHeaderTextView);
        reviewsHeaderTectView = findViewById(R.id.reviewsHeader);

        movieCastArrayList = new ArrayList<>();
        castRv = findViewById(R.id.castRecyclerView);//
        castAdapter = new MovieCastAdapter(movieCastArrayList, this, new ItemClickListener() {
            @Override
            public void itemClicked(View view, int position) {
                MovieCast movieCastPerson = movieCastArrayList.get(position);
                int id = movieCastPerson.id;
                Intent intent = new Intent(MovieDetailsActivity.this, PersonDetailsActivity.class);
                intent.putExtra("person_id", id);
                startActivity(intent);
            }
        });
        castRv.setAdapter(castAdapter);//
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        castRv.setLayoutManager(layoutManager1);//


        recommendedMoviesArrayList = new ArrayList<>();
        recommendedRv = findViewById(R.id.recommendedMoviesRecyclerView);
        recommendedMoviesAdapter = new MoviesRecommendedAdapter(this, recommendedMoviesArrayList, new ItemClickListener() {
            @Override
            public void itemClicked(View view, int position) {
                Movie movie = recommendedMoviesArrayList.get(position);
                long id = movie.id;
                Intent intent = new Intent(MovieDetailsActivity.this, MovieDetailsActivity.class);
                intent.putExtra("movie_id", id);
                startActivity(intent);
            }
        });
        recommendedRv.setAdapter(recommendedMoviesAdapter);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recommendedRv.setLayoutManager(layoutManager2);

        similarMoviesArrayList = new ArrayList<>();
        similarRv = findViewById(R.id.similarMoviesRecyclerView);//
        similarMoviesAdapter = new MoviesRecommendedAdapter(this, similarMoviesArrayList, new ItemClickListener() {
            @Override
            public void itemClicked(View view, int position) {
                Movie movie = similarMoviesArrayList.get(position);
                long id = movie.id;
                Intent intent = new Intent(MovieDetailsActivity.this, MovieDetailsActivity.class);
                intent.putExtra("movie_id", id);
                startActivity(intent);
            }
        });
        similarRv.setAdapter(similarMoviesAdapter);//
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
                    MovieDetailsActivity.this.startActivity(appIntent);
                } catch (ActivityNotFoundException ex) {
                    MovieDetailsActivity.this.startActivity(webIntent);
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


        //fetching functions
        fetchDetailsOfThisMovie();
        fetchCast();
        fetchTrailers();
        fetchRecommendedMovies();
        fetchSimilarMovies();
        fetchReviews();
    }

    private void fetchReviews() {
        Call<ReviewResponse> call = ApiClient.getMoviesService().getMovieReviews(movie_id);
        call.enqueue(new Callback<ReviewResponse>() {
            @Override
            public void onResponse(Call<ReviewResponse> call, Response<ReviewResponse> response) {
                ReviewResponse reviewResponse = response.body();
                ArrayList<Review> reviews = reviewResponse.results;

                reviewArrayList.clear();
                reviewArrayList.addAll(reviews);
                reviewsAdapter.notifyDataSetChanged();

                if (reviews.size() == 0){
                    reviewsHeaderTectView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ReviewResponse> call, Throwable t) {

            }
        });
    }

    private void fetchDetailsOfThisMovie() {
        Call<MovieExtended> call = ApiClient.getMoviesService().getThisMovieDetails(movie_id);
        call.enqueue(new Callback<MovieExtended>(){
            @Override
            public void onResponse(Call<MovieExtended> call, Response<MovieExtended> response) {
                MovieExtended movieExtended = response.body();

                boolean isAdult = false;
                if (movieExtended.getAdult() == true) adult.setVisibility(View.VISIBLE);
                nameTv.setText(movieExtended.getOriginalTitle());
                ratingTv.setText(movieExtended.getVoteAverage()+"");
                overviewTv.setText(movieExtended.getOverview()+"");
                releaseDateTv.setText(movieExtended.getReleaseDate());
                runtimeTv.setText(movieExtended.getRuntime()/60 + " hour(s), " + movieExtended.getRuntime()%60 + " minutes");
                Picasso.get().load("http://image.tmdb.org/t/p/w780" + movieExtended.getBackdropPath()).into(backgroundImg);
                Picasso.get().load("http://image.tmdb.org/t/p/w780" + movieExtended.getPosterPath()).into(posterImg);
            }

            @Override
            public void onFailure(Call<MovieExtended> call, Throwable t) {

            }
        });
    }

    private void fetchTrailers() {
        Call<TrailerVideoResponse> call = ApiClient.getMoviesService().getThisMovieTrailersVideos(movie_id);
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

    private void fetchCast() {
        Call<CastResponse> call = ApiClient.getMoviesService().getThisMovieCast(movie_id);
        call.enqueue(new Callback<CastResponse>(){
            @Override
            public void onResponse(Call<CastResponse> call, Response<CastResponse> response) {
                CastResponse castResponse = response.body();
                movieCastArrayList.clear();

                 {
                    movieCastArrayList.addAll(castResponse.castArrayList);
                    castAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<CastResponse> call, Throwable t) {

            }
        });
    }

    private void fetchRecommendedMovies() {
        Call<MovieResponse> call = ApiClient.getMoviesService().getRecommendedMovies(movie_id);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                MovieResponse movieResponse = response.body();
                ArrayList<Movie> movies = movieResponse.results;

                recommendedMoviesArrayList.clear();
                recommendedMoviesArrayList.addAll(movies);
                recommendedMoviesAdapter.notifyDataSetChanged();

                if (movies.size() == 0){
                    recommendedHeaderTextView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

            }
        });
    }

    private void fetchSimilarMovies() {
        Call<MovieResponse> call = ApiClient.getMoviesService().getSimilarMovies(movie_id);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                MovieResponse movieResponse = response.body();
                ArrayList<Movie> movies = movieResponse.results;

                similarMoviesArrayList.clear();
                similarMoviesArrayList.addAll(movies);
                similarMoviesAdapter.notifyDataSetChanged();

                if (movies.size() == 0){
                    similarHeaderTextView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
//
            }
        });
    }

    public void goBack(View view) {
        finish();
    }
}