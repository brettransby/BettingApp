package com.betting.brettransby.bettingapp.BetObjects;

import java.io.Serializable;

/**
 * Created by brettransby on 9/25/16.
 */
public class Line implements Serializable{

    private boolean isHomeTeamFavored;
    private double spreadHome;
    private double spreadAway;
    private double total;
    private int odds;


    public Line( final boolean isHomeTeamFavored,
                 final double spreadHome,
                 final double spreadAway,
                 final double total,
                 final int odds ){
        this.isHomeTeamFavored = isHomeTeamFavored;
        this.spreadHome = spreadHome;
        this.spreadAway = spreadAway;
        this.total = total;
        this.odds = odds;
    }


    public boolean isHomeTeamFavored(){
        return isHomeTeamFavored;
    }

    public double getSpreadHome(){ return spreadHome; }

    public double getSpreadAway(){ return spreadAway; }

    public double getTotal(){
        return total;
    }

    public int getOdds(){ return  odds; }
}
