package com.janta.esir.jibambetryx;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.janta.esir.jibambetryx.adapters.MoviesCategoryAdapter;
import com.janta.esir.jibambetryx.models.MoviesCategory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by isaiahngaruiya on 21/01/2018.
 */

public class MoviesCategoryFragment extends Fragment{

    private List<MoviesCategory> moviesCategoryList; //Program to an interface not implementation
    private MoviesCategoryAdapter moviesCategoryAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        //Inflate movie fragment. sharing it with MovieFragment
        View view = inflater.inflate(R.layout.movie_fragment, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.movies_recycler_view);

        moviesCategoryList = new ArrayList<>();
        moviesCategoryAdapter = new MoviesCategoryAdapter(getContext(), moviesCategoryList);

        RecyclerView.LayoutManager portraitLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(portraitLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(moviesCategoryAdapter);

        setUpCategories();
        return view;
    }

    private void setUpCategories() {
        String[] moviesCategoriesNames = new String[]{
                "Animations",
                "Action",
                "Comedy",
                "Sci-fi",
                "Horror"
        };
        String[] moviesCategoriesThumbnails = new String[]{
                "https://i.pinimg.com/564x/03/6a/e4/036ae4354a75a2652cbb44a01783551e--movie-list.jpg",
                "https://cnet3.cbsistatic.com/img/q0uiGtyscUabD73kcVbHd7HrXhM=/770x433/2016/02/17/ddc19049-cda2-41d9-83d2-d87c3bb9b746/4k-blu-ray.jpg",
                "https://pandaneo.com/wp-content/uploads/2016/06/camping-movies-800x510.jpg",
                "https://crisisoninfinitethoughts.files.wordpress.com/2014/11/894a9-best2bsci2bfi2bfilms2bcollage2b2.jpg",
                "http://top101news.com/wp-content/uploads/2016/09/The-Exorcist-Deadly-Horror-Movies-of-All-Time-2018.jpg"
        };
        for(int x = 0; x<moviesCategoriesNames.length; x++){
            MoviesCategory moviesCategory = new MoviesCategory(moviesCategoriesNames[x], x, moviesCategoriesThumbnails[x]);
            moviesCategoryList.add(moviesCategory);
        }
        moviesCategoryAdapter.notifyDataSetChanged();
    }
}
