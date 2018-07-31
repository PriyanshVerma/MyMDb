package com.example.lenovo.mymdb;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class TrailerVideoViewHolder extends RecyclerView.ViewHolder {
    ImageView trailerVideoImg;
    TextView youtubeVideoName;
    public TrailerVideoViewHolder(View itemView) {
        super(itemView);

        trailerVideoImg = itemView.findViewById(R.id.trailerVideoImg);
        youtubeVideoName = itemView.findViewById(R.id.youtubeVideoName);
    }
}
