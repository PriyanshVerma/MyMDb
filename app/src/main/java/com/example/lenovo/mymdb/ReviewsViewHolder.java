package com.example.lenovo.mymdb;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class ReviewsViewHolder extends RecyclerView.ViewHolder {

    TextView content, author;

    public ReviewsViewHolder(View itemView) {
        super(itemView);
        content = itemView.findViewById(R.id.reviewContent);
        author = itemView.findViewById(R.id.reviewAuthor);
    }
}
