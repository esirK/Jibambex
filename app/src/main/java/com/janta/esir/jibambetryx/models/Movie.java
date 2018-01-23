package com.janta.esir.jibambetryx.models;

/**
 * Created by isaiahngaruiya on 19/01/2018.
 */

public class Movie {
    private String name, thumbnail, source_url, duration;

    public Movie(){

    }
    public Movie(String name, String thumbnail, String source, String duration){
        this.name = name;
        this.thumbnail = thumbnail;
        this.source_url = source;
        this.duration = duration;
    }
    public String getName(){
        return this.name;
    }

    public String getThumbnail(){
        return this.thumbnail;
    }
    public String getSource(){
        return this.source_url;
    }
    public String getDuration(){
        return this.duration;
    }
}
