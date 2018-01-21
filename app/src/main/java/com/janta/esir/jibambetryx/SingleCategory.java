package com.janta.esir.jibambetryx;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.janta.esir.jibambetryx.adapters.MovieAdapter;
import com.janta.esir.jibambetryx.models.Movie;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by isaiahngaruiya on 21/01/2018.
 */

public class SingleCategory extends AppCompatActivity{

    private RecyclerView recyclerView;
    private List<Movie> movieList;
    private MovieAdapter movieAdapter;
    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_category);
        recyclerView = findViewById(R.id.movies_recycler_view);
        movieList = new ArrayList<>();
        movieAdapter = new MovieAdapter(this, movieList);

        toolbar = findViewById(R.id.toolbar);
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
        String url = "http://dl.tehmovies.org/94/series/24.legacy/s1/";
        String [] movies_urls = new String[]{
                url+"24.Legacy.S01E01.PROPER.480p.Tehmovies_me.mkv",
                url+"24.Legacy.S01E02.480p.Tehmovies_me.mkv",
                url+"24.Legacy.S01E03.480p.HDTV.Tehmovies_me.mkv",
                url+"24.Legacy.S01E04.480p.Tehmovies_me.mkv",
                url+"24.Legacy.S01E05.480p.Tehmovies_me.mkv"
        };
        for(int x=0; x<movies_urls.length; x++) {
            Movie a = new Movie("Legacy " + (x+1), "https://4.bp.blogspot.com/-jJg6Ohf7Tdw/WJpV583xEvI/AAAAAAAAJKM/rxrNefe-m2UzYcoG-3Ig-e-PxBu7PtrfgCLcB/s1600/16508321_10103551736179324_7216610480964308825_n.jpg", movies_urls[x], "Duration: 2hrs");
            movieList.add(a);
        }
        movieAdapter.notifyDataSetChanged();
    }
}
