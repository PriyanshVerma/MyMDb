package com.example.lenovo.mymdb;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CastResponse {
    int id;
    @SerializedName("cast")
    ArrayList<MovieCast> castArrayList;
}
