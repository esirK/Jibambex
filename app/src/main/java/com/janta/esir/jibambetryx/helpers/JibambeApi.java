package com.janta.esir.jibambetryx.helpers;

import com.janta.esir.jibambetryx.models.Movie;
import com.janta.esir.jibambetryx.models.MoviesCategory;

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
}
