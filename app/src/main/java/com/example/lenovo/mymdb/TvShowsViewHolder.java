package com.example.lenovo.mymdb;

import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

class TvShowsViewHolder  extends RecyclerView.ViewHolder{

    ImageView tvShowImage;
    TextView title, genres, rating;
    View itemView;

    public TvShowsViewHolder(View itemView) {
        super(itemView);
        this.itemView = itemView;
        tvShowImage = itemView.findViewById(R.id.MovieImage);
        title = itemView.findViewById(R.id.MovieTitle);
        title.setMovementMethod(new ScrollingMovementMethod());
        genres = itemView.findViewById(R.id.genresTextView);
        rating = itemView.findViewById(R.id.ratingTextView);
    }
}
