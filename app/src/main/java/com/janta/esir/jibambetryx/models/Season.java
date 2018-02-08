package com.janta.esir.jibambetryx.models;

/**
 * Created by isaiahngaruiya on 07/02/2018.
 */

public class Season {
    String name, thumbnail, num_episodes, id;

    Season(){

    }
    public Season(String name, String thumbnail, String num_episodes){
        this.name = name;
        this.thumbnail = thumbnail;
        this.num_episodes = num_episodes;
    }

    public String getName() {
        return name;
    }

    public String getNum_episodes() {
        return num_episodes;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getId() {
        return id;
    }
}
