package com.example.lenovo.mymdb;

import java.util.ArrayList;

class Movie {
    int vote_count, id;
    float vote_average, popularity;
    String title, poster_path, original_language, original_title, backdrop_path, overview, release_date;
    ArrayList<Integer> genre_ids;
    boolean adult, video;
}
