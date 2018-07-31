package com.example.lenovo.mymdb;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MoviesRecommendedViewHolder extends RecyclerView.ViewHolder{

    ImageView posterImage;
    TextView movieName, rating;
    View itemView;

    public MoviesRecommendedViewHolder(View itemView) {
        super(itemView);
        this.itemView = itemView;
        posterImage = itemView.findViewById(R.id.posterImage);
        movieName = itemView.findViewById(R.id.movieName);
        rating = itemView.findViewById(R.id.rating);
    }
}
