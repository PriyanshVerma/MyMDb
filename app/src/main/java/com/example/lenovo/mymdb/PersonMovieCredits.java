package com.example.lenovo.mymdb;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

@SuppressWarnings("unused")
public class PersonMovieCredits {
    @SerializedName("cast")
    ArrayList<PersonMovieCast> casts;

}

