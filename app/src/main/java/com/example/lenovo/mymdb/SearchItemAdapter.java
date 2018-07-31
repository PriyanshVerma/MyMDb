package com.example.lenovo.mymdb;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SearchItemAdapter extends RecyclerView.Adapter<SearchItemViewHolder> {
    ArrayList<SearchResult> results;
    Context context;
    SearchItemClickListener listener;

    public SearchItemAdapter(ArrayList<SearchResult> results, Context context, SearchItemClickListener listener) {
        this.results = results;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SearchItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.search_result_item, parent, false);
        return new SearchItemViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull final SearchItemViewHolder holder, int position) {
        SearchResult result = results.get(position);
        String type = result.media_type;
        if (type == "movie"){
            holder.titleOrName.setText(result.title);
            holder.overviewOrKnownFor.setText(result.overview);
            holder.date.setText(result.release_date);
            holder.type.setText(result.media_type);
            Picasso.get().load("http://image.tmdb.org/t/p/w780" + result.poster_path).into(holder.posterOrPofile);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.movieResultItemClicked(v, holder.getAdapterPosition());
                }
            });
        }

        else if (type == "tv"){
            holder.titleOrName.setText(result.title);
            holder.overviewOrKnownFor.setText(result.overview);
            holder.date.setText(result.first_air_date);
            holder.type.setText(result.media_type);
            Picasso.get().load("http://image.tmdb.org/t/p/w780" + result.poster_path).into(holder.posterOrPofile);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.tvResultItemClicked(v, holder.getAdapterPosition());
                }
            });
        }

        else if (type == "person"){
            holder.titleOrName.setText(result.name);
            //knownFor
            //date - no data
            holder.type.setText(result.media_type);
            Picasso.get().load("http://image.tmdb.org/t/p/w780" + result.profile_path).into(holder.posterOrPofile);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.personResultItemClicked(v, holder.getAdapterPosition());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return results.size();
    }
}
