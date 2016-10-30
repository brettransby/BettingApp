package com.betting.brettransby.bettingapp;

import android.app.Application;
import android.os.Bundle;

import com.betting.brettransby.bettingapp.BetObjects.Sport;

import java.util.HashMap;

/**
 * Created by brettransby on 10/26/16.
 */
public class BettingAppSetup {


    private static HashMap<Sport,String> sportsKeys = new HashMap<Sport,String>();


    public static void loadSports(){

        sportsKeys.put( Sport.NFL, "nfl" );
        sportsKeys.put( Sport.NCAA, "nccaf" );
        sportsKeys.put( Sport.MLB, "mlb" );
        sportsKeys.put( Sport.NBA, "nba" );
        sportsKeys.put( Sport.NHL, "nhl" );
    }

    public static String getSportString( Sport sport ){
        return sportsKeys.get( sport );
    }

}
