package com.example.lenovo.mymdb;

import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class SearchItemViewHolder extends RecyclerView.ViewHolder {

    ImageView posterOrPofile;
    TextView titleOrName, overviewOrKnownFor, date, type;
    View itemView;

    public SearchItemViewHolder(View itemView) {
        super(itemView);
        this.itemView = itemView;
        posterOrPofile = itemView.findViewById(R.id.posterOrProfile);
        titleOrName = itemView.findViewById(R.id.titleOrName);
        overviewOrKnownFor = itemView.findViewById(R.id.overviewOrKnownFor);
        date = itemView.findViewById(R.id.date);
        type = itemView.findViewById(R.id.type);
    }
}
