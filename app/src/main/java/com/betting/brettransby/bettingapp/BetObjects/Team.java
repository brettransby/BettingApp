package com.betting.brettransby.bettingapp.BetObjects;

import java.io.Serializable;

/**
 * Created by brettransby on 9/25/16.
 */
public class Team implements Serializable{

    private String name;
    private String logoUrl;


    public Team( final String name ){
        this.name = name;
    }


    public String getName(){
        return name;
    }
}
