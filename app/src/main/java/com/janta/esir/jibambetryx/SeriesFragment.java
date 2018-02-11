package com.janta.esir.jibambetryx;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.janta.esir.jibambetryx.adapters.SeriesAdapter;
import com.janta.esir.jibambetryx.helpers.JibambeApi;
import com.janta.esir.jibambetryx.models.Series;

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
 * Created by isaiahngaruiya on 17/01/2018.
 */

public class SeriesFragment extends Fragment{

    private List<Series> seriesList;
    private SeriesAdapter seriesAdapter;

    @BindView(R.id.loading_categories) ContentLoadingProgressBar loadingProgressBar;
    @BindView(R.id.movies_recycler_view) RecyclerView recyclerView;
    @BindView(R.id.tv_no_series) TextView tv_no_series;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.movie_fragment, container, false);
        ButterKnife.bind(this, rootView);

        seriesList = new ArrayList<>();
        seriesAdapter = new SeriesAdapter(getContext(), seriesList);
        RecyclerView.LayoutManager portraitLayoutManager = new GridLayoutManager(getContext(), 2);
        RecyclerView.LayoutManager landscapeLayoutManager = new GridLayoutManager(getContext(), 4);

        if(getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.setLayoutManager(portraitLayoutManager);
        }else{
            recyclerView.setLayoutManager(landscapeLayoutManager);
        }
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(seriesAdapter);

        getSeries();
        return rootView;
    }

    private void getSeries() {

        // Loads different series from the LAN server

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(Utils.URL)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        JibambeApi jibambeApi = retrofit.create(JibambeApi.class);
        Call<List<Series>> call = jibambeApi.allSeries();

        call.enqueue(new Callback<List<Series>>() {
            @Override
            public void onResponse(Call<List<Series>> call, Response<List<Series>> response) {
                seriesList = response.body();
                loadingProgressBar.setVisibility(View.GONE);
                seriesAdapter.updateSeries(seriesList);
                if(seriesList != null) {
                    if (seriesList.size() == 0) {
                        tv_no_series.setVisibility(View.VISIBLE);
                    }
                }else{
                    tv_no_series.setText("Server Error");
                    tv_no_series.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<List<Series>> call, Throwable t) {
                loadingProgressBar.setVisibility(View.GONE);
                tv_no_series.setText("No Network Access: Server not found");
                tv_no_series.setVisibility(View.VISIBLE);
            }
        });
    }
}
