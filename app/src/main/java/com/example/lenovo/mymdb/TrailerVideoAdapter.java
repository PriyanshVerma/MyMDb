package com.example.lenovo.mymdb;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TrailerVideoAdapter extends RecyclerView.Adapter<TrailerVideoViewHolder> {
    ArrayList<TrailerVideo> trailerVideos;
    Context context;
    TrailerVideoClickListener listener;

    public TrailerVideoAdapter(ArrayList<TrailerVideo> trailerVideos, Context context, TrailerVideoClickListener listener) {
        this.trailerVideos = trailerVideos;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TrailerVideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.trailer_movie_card, parent, false);
        return new TrailerVideoViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull final TrailerVideoViewHolder holder, int position) {
        TrailerVideo trailerVideo = trailerVideos.get(position);

        holder.youtubeVideoName.setText(trailerVideo.name);
        Picasso.get().load("http://img.youtube.com/vi/" + trailerVideo.key + "/hqdefault.jpg").into(holder.trailerVideoImg);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.trailerVideoClicked(v, holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return trailerVideos.size();
    }
}
