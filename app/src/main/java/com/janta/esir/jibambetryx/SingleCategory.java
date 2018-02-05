package com.janta.esir.jibambetryx;

import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.janta.esir.jibambetryx.adapters.MovieAdapter;
import com.janta.esir.jibambetryx.helpers.JibambeApi;
import com.janta.esir.jibambetryx.models.Movie;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by isaiahngaruiya on 21/01/2018.
 */

public class SingleCategory extends AppCompatActivity{

    private List<Movie> movieList;
    private MovieAdapter movieAdapter;
    private int category_id;

    @BindView(R.id.movies_recycler_view) RecyclerView recyclerView;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.loading_movies) ContentLoadingProgressBar loading_movies;
    @BindView(R.id.tv_no_movie) TextView tv_no_movie;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_category);

        ButterKnife.bind(this);

        movieList = new ArrayList<>();
        movieAdapter = new MovieAdapter(this, movieList);

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

        LoadMovies();
    }
    private void LoadMovies() {
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
                movieAdapter.updateMovies(movieList); //This will result in the recycler view being repopulated
                // On receiving response hide the loading movies progress dialog
                loading_movies.setVisibility(View.GONE);

                if(movieList.size() == 0){
                    tv_no_movie.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<List<Movie>> call, Throwable t) {
                loading_movies.setVisibility(View.GONE);
                Toast.makeText(getBaseContext(), "Network Error ", Toast.LENGTH_LONG).show();
                tv_no_movie.setText("Network Error");
                tv_no_movie.setTextColor(Color.RED);
                tv_no_movie.setTextSize(24);
                tv_no_movie.setVisibility(View.VISIBLE);
            }
        });
        movieAdapter.notifyDataSetChanged();
    }
}
