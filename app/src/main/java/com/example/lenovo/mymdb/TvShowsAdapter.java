package com.example.lenovo.mymdb;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

class TvShowsAdapter  extends RecyclerView.Adapter<TvShowsViewHolder>{
    ArrayList<TvShow> tvShows;
    Context context;
    ItemClickListener listener;

    public TvShowsAdapter(ArrayList<TvShow> tvShows, Context context, ItemClickListener listener) {
        this.tvShows = tvShows;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TvShowsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.card_view_movies, parent,false);
        return new TvShowsViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull final TvShowsViewHolder holder, int position) {
        TvShow tvShow = tvShows.get(position);
        holder.title.setText(tvShow.name);
        Picasso.get().load("http://image.tmdb.org/t/p/w780" + tvShow.poster_path).into(holder.tvShowImage);
        holder.genres.setText("genres");
        holder.rating.setText(tvShow.vote_average + "");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.itemClicked(v, holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return tvShows.size();
    }
}
