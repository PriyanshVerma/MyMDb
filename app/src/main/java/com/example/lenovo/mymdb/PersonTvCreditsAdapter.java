package com.example.lenovo.mymdb;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PersonTvCreditsAdapter extends RecyclerView.Adapter<MovieCastViewHolder>  {

    ArrayList<PersonTvCast> personTvShows;
    Context context;
    ItemClickListener listener;

    public PersonTvCreditsAdapter(ArrayList<PersonTvCast> personTvShows, Context context, ItemClickListener listener) {
        this.personTvShows = personTvShows;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MovieCastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.card_view_cast, parent, false);
        return new MovieCastViewHolder(layout);    }

    @Override
    public void onBindViewHolder(@NonNull final MovieCastViewHolder holder, int position) {
        PersonTvCast tvShow = personTvShows.get(position);
        holder.realName.setText(tvShow.original_name);
        Picasso.get().load("http://image.tmdb.org/t/p/w780" + tvShow.poster_path).into(holder.castImg);
        holder.characterName.setText(tvShow.character);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.itemClicked(v, holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return personTvShows.size();
    }
}
