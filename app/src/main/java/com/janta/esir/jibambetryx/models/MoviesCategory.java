package com.janta.esir.jibambetryx.models;

/**
 * Created by isaiahngaruiya on 21/01/2018.
 */

public class MoviesCategory {
    private String name, thumbnail;
    private int id;

    public MoviesCategory(){

    }
    public MoviesCategory(String name, int index, String thumbnail){
        this.name = name;
        this.id = index;
        this.thumbnail = thumbnail;
    }

    public String getName(){
        return this.name;
    }

    public int getId(){
        return this.id;
    }

    public String getThumbnail(){
        return this.thumbnail;
    }

}
