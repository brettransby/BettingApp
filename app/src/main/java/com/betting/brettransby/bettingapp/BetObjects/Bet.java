package com.betting.brettransby.bettingapp.BetObjects;

import java.io.Serializable;

/**
 * Created by brettransby on 10/21/16.
 */
public class Bet implements Serializable{

    public static String TOTAL_BET = "TOTAL_BET";
    public static String SPREAD_BET = "SPREAD_BET";

    public static String HOME_TEAM_BET = "HOME_TEAM_BET";
    public static String AWAY_TEAM_BET = "AWAY_TEAM_BET";
    public static String OVER_BET = "OVER_BET";
    public static String UNDER_BET = "UNDER_BET";


    private String type;
    private String betPlaced;

    public Bet( String type, String betPlaced ){
        this.type = type;
        this.betPlaced = betPlaced;
    }


    public String getType(){ return type; }
    public String getBetPlaced(){ return  betPlaced; }

}
