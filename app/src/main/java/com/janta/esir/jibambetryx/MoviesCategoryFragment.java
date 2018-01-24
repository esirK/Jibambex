package com.janta.esir.jibambetryx;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.janta.esir.jibambetryx.adapters.MoviesCategoryAdapter;
import com.janta.esir.jibambetryx.helpers.JibambeApi;
import com.janta.esir.jibambetryx.models.MoviesCategory;

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

public class MoviesCategoryFragment extends Fragment{

    private List<MoviesCategory> moviesCategoryList; //Program to an interface not implementation
    private MoviesCategoryAdapter moviesCategoryAdapter;
    private ContentLoadingProgressBar videoLoadingPb;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        //Inflate movie1 fragment. sharing it with MovieFragment
        View view = inflater.inflate(R.layout.movie_fragment, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.movies_recycler_view);
        videoLoadingPb = view.findViewById(R.id.loading_categories);
        videoLoadingPb.setVisibility(View.VISIBLE);
        moviesCategoryList = new ArrayList<>();
        moviesCategoryAdapter = new MoviesCategoryAdapter(getContext(), moviesCategoryList);

        RecyclerView.LayoutManager portraitLayoutManager = new GridLayoutManager(getContext(), 2);
        RecyclerView.LayoutManager landscapeLayoutManager = new GridLayoutManager(getContext(), 3);

        if(getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            recyclerView.setLayoutManager(portraitLayoutManager);
        }else{
            recyclerView.setLayoutManager(landscapeLayoutManager);
        }
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(moviesCategoryAdapter);

        setUpCategories();
        return view;
    }

    private void setUpCategories() {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(Utils.URL)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        JibambeApi jibambeApi = retrofit.create(JibambeApi.class);
        Call<List<MoviesCategory>> call = jibambeApi.moviesCategories();

        call.enqueue(new Callback<List<MoviesCategory>>() {
            @Override
            public void onResponse(Call<List<MoviesCategory>> call, Response<List<MoviesCategory>> response) {
                moviesCategoryList = response.body();
                moviesCategoryAdapter.updateCategories(moviesCategoryList);
                videoLoadingPb.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<List<MoviesCategory>> call, Throwable t) {
                Toast.makeText(getContext(), "Error ", Toast.LENGTH_LONG).show();
            }
        });
//        moviesCategoryAdapter.notifyDataSetChanged();
    }
}
