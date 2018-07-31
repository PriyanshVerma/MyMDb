package com.example.lenovo.mymdb;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MovieCastViewHolder extends RecyclerView.ViewHolder {
    ImageView castImg;
    TextView realName, characterName;
    View itemView;

    public MovieCastViewHolder(View itemView) {
        super(itemView);
        this.itemView = itemView;
        castImg = itemView.findViewById(R.id.trailerVideoImg);
        characterName = itemView.findViewById(R.id.characterName);
        realName = itemView.findViewById(R.id.youtubeVideoName);
    }
}
