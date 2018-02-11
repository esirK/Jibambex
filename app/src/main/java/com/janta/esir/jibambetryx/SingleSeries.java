package com.janta.esir.jibambetryx;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.janta.esir.jibambetryx.adapters.SeasonAdapter;
import com.janta.esir.jibambetryx.helpers.JibambeApi;
import com.janta.esir.jibambetryx.models.Season;

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
 * Created by isaiahngaruiya on 07/02/2018.
 */

public class SingleSeries extends AppCompatActivity{

    SeasonAdapter seasonAdapter;
    List<Season> seasonList;
    String SeriesTitle;

    @BindView(R.id.movies_recycler_view) RecyclerView recyclerView;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.loading_movies) ContentLoadingProgressBar loading_season;
    @BindView(R.id.tv_no_movie) TextView tv_no_seasons;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_category);
        ButterKnife.bind(this);

        SeriesTitle = getIntent().getExtras().getString("series_name");
        toolbar.setTitle(SeriesTitle);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Log.e("SeriesTitle", SeriesTitle);

        seasonList = new ArrayList<>();
        seasonAdapter = new SeasonAdapter(seasonList, this);

        recyclerView.setAdapter(seasonAdapter);

        recyclerView.setItemAnimator(new DefaultItemAnimator());

        //For portrait and landscape mode, have two different recyclerview layouts

        RecyclerView.LayoutManager portraitLayout = new GridLayoutManager(this, 2);
        RecyclerView.LayoutManager landscapeLayout = new GridLayoutManager(this, 4);

        if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.setLayoutManager(portraitLayout);
        }else{
            recyclerView.setLayoutManager(landscapeLayout);
        }
        int series_id = getIntent().getExtras().getInt("series_id");
        loadSeasons(series_id);
    }

    private void loadSeasons(int series_id) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(Utils.URL)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        JibambeApi jibambeApi = retrofit.create(JibambeApi.class);
        Call<List<Season>> call = jibambeApi.SingleSeries(series_id);

        call.enqueue(new Callback<List<Season>>() {
            @Override
            public void onResponse(Call<List<Season>> call, Response<List<Season>> response) {
                seasonList = response.body();
                loading_season.setVisibility(View.GONE);
                seasonAdapter.updateSeasons(seasonList);
                if(seasonList != null) {
                    if (seasonList.size() == 0) {
                        tv_no_seasons.setText("No Seasons Available");
                        tv_no_seasons.setVisibility(View.VISIBLE);
                    }
                }else{
                    tv_no_seasons.setText("Server Error");
                    tv_no_seasons.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<List<Season>> call, Throwable t) {
                loading_season.setVisibility(View.GONE);
                tv_no_seasons.setText("Network Error. Server Not Found");
                tv_no_seasons.setVisibility(View.VISIBLE);
            }
        });
    }
}
