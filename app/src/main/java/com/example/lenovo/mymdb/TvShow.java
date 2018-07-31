package com.example.lenovo.mymdb;

import java.util.ArrayList;

class TvShow {
    String backdrop_path, first_air_date, homepage, last_air_date, name, overview,
    poster_path, status, type;

    ArrayList<Integer> episode_run_time;
    ArrayList<Genre> genres;
    ArrayList<ShowCreator> created_by;

    int id, number_of_episodes, number_of_seasons, vote_count;

    float vote_average, popularity;

    boolean in_production;

    TvEpisode last_episode_to_air;
}
