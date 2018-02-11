package com.janta.esir.jibambetryx.helpers;

import com.janta.esir.jibambetryx.models.Movie;
import com.janta.esir.jibambetryx.models.MoviesCategory;
import com.janta.esir.jibambetryx.models.Season;
import com.janta.esir.jibambetryx.models.Series;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by isaiahngaruiya on 22/01/2018.
 */

public interface JibambeApi {
    @GET("/moviescategories")
    Call<List<MoviesCategory>> moviesCategories();

    @GET("/moviescategories/{id}")
    Call<List<Movie>> specificCategory(@Path("id") int id);

    @GET("/episodes/{id}")
    Call<List<Movie>> specificSeason(@Path("id") int id);

    @GET("/series")
    Call<List<Series>> allSeries();

    @GET("/series/{id}")
    Call<List<Season>> SingleSeries(@Path("id") int id);

}
