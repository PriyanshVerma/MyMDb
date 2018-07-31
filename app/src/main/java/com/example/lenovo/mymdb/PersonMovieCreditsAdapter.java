package com.example.lenovo.mymdb;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PersonMovieCreditsAdapter extends RecyclerView.Adapter<MovieCastViewHolder> {
    ArrayList<PersonMovieCast> personMovies;
    Context context;
    ItemClickListener listener;

    public PersonMovieCreditsAdapter(Context context, ArrayList<PersonMovieCast> personMovies, ItemClickListener listener) {
        this.personMovies = personMovies;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MovieCastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.card_view_cast, parent, false);
        return new MovieCastViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull final MovieCastViewHolder holder, int position) {
        PersonMovieCast movie = personMovies.get(position);
        holder.realName.setText(movie.original_title);
        Picasso.get().load("http://image.tmdb.org/t/p/w780" + movie.poster_path).into(holder.castImg);
        holder.characterName.setText(movie.character);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.itemClicked(v, holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return personMovies.size();
    }
}
