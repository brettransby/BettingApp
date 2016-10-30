package com.betting.brettransby.bettingapp.BetObjects;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by brettransby on 10/21/16.
 */
public enum Sport implements Serializable {


    MLB,
    NFL,
    NCAA,
    NBA,
    NHL,
    PICKS,
    OTHER;


    @Override
    public String toString() {

        switch (this) {
            case MLB:
                return "MLB";
            case NFL:
                return "NFL";
            case NCAA:
                return "NCAA";
            case NBA:
                return "NBA";
            case NHL:
                return "NHL";
            case PICKS:
                return "PICKS";
            default:
                return "OTHER";

        }


    }


    public static ArrayList<Sport> getAllSports(){
        final ArrayList<Sport> sports = new ArrayList<Sport>();

        sports.add( Sport.NFL );
        sports.add( Sport.NCAA );
        sports.add( Sport.MLB );
        sports.add( Sport.NHL );
        sports.add( Sport.NBA );
        sports.add( Sport.OTHER );

        return sports;
    }
}