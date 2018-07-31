package com.example.lenovo.mymdb;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    RecyclerView recyclerView;
    SearchItemAdapter adapter;
    ArrayList<SearchResult> searchResultsArrayList;
    ConstraintLayout rootLayout;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        recyclerView = findViewById(R.id.recyclerView);
        rootLayout = findViewById(R.id.rootLayout);
        progressBar = findViewById(R.id.progressBar);

        progressBar.setVisibility(View.VISIBLE);
        rootLayout.setVisibility(View.INVISIBLE);

        searchResultsArrayList = new ArrayList<>();
        adapter = new SearchItemAdapter(searchResultsArrayList, this, new SearchItemClickListener() {
            @Override
            public void movieResultItemClicked(View view, int position) {
                SearchResult searchResult = searchResultsArrayList.get(position);
                long id = searchResult.id;
                Intent intent = new Intent(SearchActivity.this, MovieDetailsActivity.class);
                intent.putExtra("movie_id", id);
                startActivity(intent);
            }

            @Override
            public void tvResultItemClicked(View view, int position) {
                SearchResult searchResult = searchResultsArrayList.get(position);
                long id = searchResult.id;
                Intent intent = new Intent(SearchActivity.this, TvShowDetailsActivity.class);
                intent.putExtra("tvShow_id", id);
                startActivity(intent);
            }

            @Override
            public void personResultItemClicked(View view, int position) {
                SearchResult searchResult = searchResultsArrayList.get(position);
                long id = searchResult.id;
                Intent intent = new Intent(SearchActivity.this, PersonDetailsActivity.class);
                intent.putExtra("person_id", id);
                startActivity(intent);
            }
        });

        recyclerView.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
    }


    //MENU and Search
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        SearchView searchView = (SearchView)menu.findItem(R.id.search).getActionView();
        if(searchView != null) {
            searchView.setIconified(true);
            searchView.setQueryHint("Search movies, TV or people...");
            searchView.setIconifiedByDefault(true);
            searchView.setMaxWidth(Integer.MAX_VALUE);
            searchView.setOnQueryTextListener(this);
        }

        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        fetchSearchResults(query);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
    //MENU and Search

    private void fetchSearchResults(String query) {
        Call<SearchResponse> call = ApiClient.getMoviesService().getSearchResults(query);
        call.enqueue(new Callback<SearchResponse>() {
            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                SearchResponse searchResponses = response.body();
                ArrayList<SearchResult> results = searchResponses.results;

                searchResultsArrayList.addAll(results);
                adapter.notifyDataSetChanged();

                progressBar.setVisibility(View.INVISIBLE);
                rootLayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }
}
