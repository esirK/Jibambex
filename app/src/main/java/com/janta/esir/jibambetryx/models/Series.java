package com.janta.esir.jibambetryx.models;

/**
 * Created by isaiahngaruiya on 04/02/2018.
 */

public class Series {
    private String name, thumbnail;
    private int seasons;

    public Series(){

    }

    public Series(String name, String thumbnail, int seasons){
        this.name = name;
        this.thumbnail = thumbnail;
        this.seasons = seasons;
    }

    public String getName(){
        return this.name;
    }
    public int getSeasons(){
        return this.seasons;
    }
    public String getThumbnail(){
        return this.thumbnail;
    }
}
