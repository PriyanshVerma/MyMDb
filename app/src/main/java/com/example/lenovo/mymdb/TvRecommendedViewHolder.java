package com.example.lenovo.mymdb;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class TvRecommendedViewHolder extends RecyclerView.ViewHolder {
    ImageView posterImage;
    TextView name, rating;
    View itemView;
    public TvRecommendedViewHolder(View itemView) {
        super(itemView);
        this.itemView = itemView;
        posterImage = itemView.findViewById(R.id.posterImage);
        name = itemView.findViewById(R.id.movieName);
        rating = itemView.findViewById(R.id.rating);
    }
}
