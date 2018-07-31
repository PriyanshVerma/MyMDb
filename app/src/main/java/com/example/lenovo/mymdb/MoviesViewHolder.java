package com.example.lenovo.mymdb;

import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MoviesViewHolder extends RecyclerView.ViewHolder {

    ImageView movieImage;
    TextView movieTitle, genres, rating;
    View itemView;

    public MoviesViewHolder(View itemView) {
        super(itemView);
        this.itemView = itemView;
        movieImage = itemView.findViewById(R.id.MovieImage);
        movieTitle = itemView.findViewById(R.id.MovieTitle);
        movieTitle.setMovementMethod(new ScrollingMovementMethod());
        genres = itemView.findViewById(R.id.genresTextView);
        rating = itemView.findViewById(R.id.ratingTextView);
    }
}
