package com.example.lenovo.mymdb;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieCastAdapter extends RecyclerView.Adapter<MovieCastViewHolder> {
    ArrayList<MovieCast> castArrayList;
    Context context;
    ItemClickListener listener;

    public MovieCastAdapter(ArrayList<MovieCast> castArrayList, Context context, ItemClickListener listener) {
        this.castArrayList = castArrayList;
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
        MovieCast cast = castArrayList.get(position);
        holder.realName.setText(cast.name);
        holder.characterName.setText(cast.character);
        Picasso.get().load("http://image.tmdb.org/t/p/w780" + cast.profile_path).into(holder.castImg);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.itemClicked(v, holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return castArrayList.size();
    }
}
