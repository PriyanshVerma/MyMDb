package com.example.lenovo.mymdb;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TvRecommendedAdapter extends RecyclerView.Adapter<TvRecommendedViewHolder> {
    ArrayList<TvShow> tvRecommended;
    Context context;
    ItemClickListener listener;

    public TvRecommendedAdapter( Context context, ArrayList<TvShow> tvRecommended, ItemClickListener listener) {
        this.tvRecommended = tvRecommended;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TvRecommendedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.card_view_small_movie_card, parent, false);
        return new TvRecommendedViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull final TvRecommendedViewHolder holder, int position) {
        TvShow tvShow = tvRecommended.get(position);
        holder.name.setText(tvShow.name);
        Picasso.get().load("http://image.tmdb.org/t/p/w780" + tvShow.poster_path).into(holder.posterImage);
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
        return tvRecommended.size();
    }
}
