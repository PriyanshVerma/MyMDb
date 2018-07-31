package com.example.lenovo.mymdb;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MoviesRecommendedAdapter extends RecyclerView.Adapter<MoviesRecommendedViewHolder> {
    ArrayList<Movie> moviesRecommended;
    Context context;
    ItemClickListener listener;


    public MoviesRecommendedAdapter(Context context, ArrayList<Movie> moviesRecommended, ItemClickListener listener) {
        this.context = context;
        this.moviesRecommended = moviesRecommended;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MoviesRecommendedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.card_view_small_movie_card, parent, false);
        return new MoviesRecommendedViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull final MoviesRecommendedViewHolder holder, int position) {
        Movie movie = moviesRecommended.get(position);
        holder.movieName.setText(movie.title);
        Picasso.get().load("http://image.tmdb.org/t/p/w780" + movie.poster_path).into(holder.posterImage);
        holder.rating.setText(movie.vote_average + "");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.itemClicked(v, holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return moviesRecommended.size();
    }
}
