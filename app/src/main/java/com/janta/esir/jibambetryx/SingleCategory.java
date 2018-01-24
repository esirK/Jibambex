package com.janta.esir.jibambetryx;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.janta.esir.jibambetryx.adapters.MovieAdapter;
import com.janta.esir.jibambetryx.helpers.JibambeApi;
import com.janta.esir.jibambetryx.models.Movie;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by isaiahngaruiya on 21/01/2018.
 */

public class SingleCategory extends AppCompatActivity{

    private RecyclerView recyclerView;
    private List<Movie> movieList;
    private MovieAdapter movieAdapter;
    private Toolbar toolbar;
    private int category_id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_category);
        recyclerView = findViewById(R.id.movies_recycler_view);
        movieList = new ArrayList<>();
        movieAdapter = new MovieAdapter(this, movieList);

        toolbar = findViewById(R.id.toolbar);
        String title = getIntent().getExtras().getString("name");
        category_id = getIntent().getExtras().getInt("index");
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RecyclerView.LayoutManager portraitLayoutManager = new GridLayoutManager(this, 2);
        RecyclerView.LayoutManager landscapeLayoutManager = new GridLayoutManager(this, 4);

        if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.setLayoutManager(portraitLayoutManager);
        }else{
            recyclerView.setLayoutManager(landscapeLayoutManager);
        }
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(movieAdapter);

        sampleMovies();
    }
    private void sampleMovies() {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(Utils.URL)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        JibambeApi jibambeApi = retrofit.create(JibambeApi.class);
        Call<List<Movie>> call = jibambeApi.specificCategory(category_id);

        call.enqueue(new Callback<List<Movie>>() {
            @Override
            public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {
                movieList = response.body();
                movieAdapter.updateMovies(movieList);
            }

            @Override
            public void onFailure(Call<List<Movie>> call, Throwable t) {
                Toast.makeText(getBaseContext(), "Error ", Toast.LENGTH_LONG).show();
            }
        });
        movieAdapter.notifyDataSetChanged();
    }
}