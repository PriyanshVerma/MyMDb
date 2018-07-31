package com.example.lenovo.mymdb;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PersonDetailsActivity extends AppCompatActivity {

    ImageView personImg, backgrndImg;
    TextView name, departments, biography, born, died, gender, placeOfBirth, web;
    RecyclerView moviesRv, tvShowsRv;
    ArrayList<PersonMovieCast> moviesArrayList;
    PersonMovieCreditsAdapter moviesAdapter;
    ArrayList<PersonTvCast> tvShowsArrayList;
    PersonTvCreditsAdapter tvShowsAdapter;
    long movieId, personId, tvShowId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_details);

        Intent intent = getIntent();
        personId = (long) intent.getIntExtra("person_id", 1);

        personImg = findViewById(R.id.personImg);
        name = findViewById(R.id.name);
        departments = findViewById(R.id.departments);
        backgrndImg = findViewById(R.id.backgroundImg);
        biography = findViewById(R.id.biography);
        born = findViewById(R.id.born);
        died = findViewById(R.id.death);
        gender = findViewById(R.id.gender);
        placeOfBirth = findViewById(R.id.placeOfBirth);
        web = findViewById(R.id.website);
        moviesRv = findViewById(R.id.moviesRv);
        tvShowsRv = findViewById(R.id.tvShowsRv);

        moviesArrayList = new ArrayList<>();

        moviesAdapter = new PersonMovieCreditsAdapter(this, moviesArrayList, new ItemClickListener() {
            @Override
            public void itemClicked(View view, int position) {
                PersonMovieCast movie = moviesArrayList.get(position);
                movieId = movie.id;
                Intent intent = new Intent(PersonDetailsActivity.this, MovieDetailsActivity.class);
                intent.putExtra("movie_id", movieId);
                startActivity(intent);
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        moviesRv.setLayoutManager(layoutManager);
        moviesRv.setAdapter(moviesAdapter);


        tvShowsArrayList = new ArrayList<>();
        tvShowsAdapter = new PersonTvCreditsAdapter(tvShowsArrayList, this, new ItemClickListener() {
                @Override
                public void itemClicked(View view, int position) {
                    PersonTvCast tvShow = tvShowsArrayList.get(position);
                    tvShowId = tvShow.id;
                    Intent intent = new Intent(PersonDetailsActivity.this, TvShowDetailsActivity.class);
                    intent.putExtra("tvShow_id", tvShowId);
                    startActivity(intent);
                }
        });
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        tvShowsRv.setLayoutManager(layoutManager2);
        tvShowsRv.setAdapter(tvShowsAdapter);


        //data fetching
        fetchThisPersonDetails();
        fetchMovies();
        fetchTvShows();
    }

    private void fetchThisPersonDetails() {
        Call<Person> call = ApiClient.getMoviesService().getThisPersonDetails(personId);
        call.enqueue(new Callback<Person>() {
            @Override
            public void onResponse(Call<Person> call, Response<Person> response) {
                Person person = response.body();

                name.setText(person.getName());
                departments.setText(person.getKnownForDepartment());
                biography.setText(person.getBiography());
                born.setText(person.getBirthday());
                if (person.getDeathday()!=null) died.setText(person.getDeathday());
                else {
                    died.setText("N/A");
                    died.setTypeface(null, Typeface.ITALIC);
                }
                if (person.getGender() == 1)    gender.setText("Female");
                else gender.setText("Male");

                if (person.getHomepage() != null) web.setText(person.getHomepage());
                else {
                    web.setText("N/A");
                    web.setTypeface(null, Typeface.ITALIC);
                }
                placeOfBirth.setText(person.getPlaceOfBirth());
                Picasso.get().load("http://image.tmdb.org/t/p/w780" + person.getProfilePath()).into(personImg);

            }

            @Override
            public void onFailure(Call<Person> call, Throwable t) {

            }
        });
    }


    private void fetchMovies() {
        Call<PersonMovieCredits> call = ApiClient.getMoviesService().getThisPersonMovies(personId);
        call.enqueue(new Callback<PersonMovieCredits>() {
            @Override
            public void onResponse(Call<PersonMovieCredits> call, Response<PersonMovieCredits> response) {
                PersonMovieCredits personMovieCredits = response.body();
                ArrayList<PersonMovieCast> castDets = personMovieCredits.casts;
                moviesArrayList.clear();
                moviesArrayList.addAll(castDets);
                moviesAdapter.notifyDataSetChanged();


            }

            @Override
            public void onFailure(Call<PersonMovieCredits> call, Throwable t) {
                Log.d("Err msg", t.getMessage());
            }
        });
    }

    private void fetchTvShows() {
        Call<PersonTvCredits> call = ApiClient.getMoviesService().getThisPersonTvShows(personId);
        call.enqueue(new Callback<PersonTvCredits>() {
            @Override
            public void onResponse(Call<PersonTvCredits> call, Response<PersonTvCredits> response) {
                PersonTvCredits personMovieCredits = response.body();
                ArrayList<PersonTvCast> castDets = personMovieCredits.casts;
                tvShowsArrayList.clear();
                tvShowsArrayList.addAll(castDets);
                tvShowsAdapter.notifyDataSetChanged();


            }

            @Override
            public void onFailure(Call<PersonTvCredits> call, Throwable t) {
                Log.d("Err msg", t.getMessage());
            }
        });
    }

}