package com.example.lenovo.mymdb;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesViewHolder> {

    ArrayList<Movie> movieList;
    Context context;
    ItemClickListener listener;

    public MoviesAdapter(ArrayList<Movie> movieList, Context context, ItemClickListener listener) {
        this.movieList = movieList;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.card_view_movies, parent,false);
        return new MoviesViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull final MoviesViewHolder holder, int position) {

        Movie movie = movieList.get(position);
        holder.movieTitle.setText(movie.title);
        Picasso.get().load("http://image.tmdb.org/t/p/w780" + movie.poster_path).into(holder.movieImage);
        holder.genres.setText("genres");
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
        return movieList.size();
    }
}
