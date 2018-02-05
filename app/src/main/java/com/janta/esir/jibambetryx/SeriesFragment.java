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

import com.janta.esir.jibambetryx.adapters.MovieAdapter;
import com.janta.esir.jibambetryx.adapters.SeriesAdapter;
import com.janta.esir.jibambetryx.models.Movie;
import com.janta.esir.jibambetryx.models.Series;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by isaiahngaruiya on 17/01/2018.
 */

public class SeriesFragment extends Fragment{

    private List<Series> seriesList;
    private SeriesAdapter seriesAdapter;

    @BindView(R.id.loading_categories) ContentLoadingProgressBar loadingProgressBar;
    @BindView(R.id.movies_recycler_view) RecyclerView recyclerView;

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

        sampleMovies();
        return rootView;
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
            Series a = new Series("Legacy " + (x+1), "https://4.bp.blogspot.com/-jJg6Ohf7Tdw/WJpV583xEvI/AAAAAAAAJKM/rxrNefe-m2UzYcoG-3Ig-e-PxBu7PtrfgCLcB/s1600/16508321_10103551736179324_7216610480964308825_n.jpg", x);
            seriesList.add(a);
        }
        loadingProgressBar.setVisibility(View.GONE);
        seriesAdapter.notifyDataSetChanged();
    }
}
