package com.betting.brettransby.bettingapp.BetObjects;

import java.io.Serializable;

/**
 * Created by brettransby on 10/21/16.
 */
public class Score implements Serializable{

    private int homeTeamTotal;
    private int awayTeamTotal;

    private String period;
    private String timeRemaining;

    private boolean isFinal;


    public Score( int homeTeamTotal,
                  int awayTeamTotal,
                  String period,
                  String timeRemaining,
                  boolean isFinal ){

        this.homeTeamTotal = homeTeamTotal;
        this.awayTeamTotal = awayTeamTotal;
        this.period = period;
        this.timeRemaining = timeRemaining;
        this.isFinal = isFinal;

    }


    public int getHomeTeamTotal(){ return homeTeamTotal; }
    public int getAwayTeamTotal(){ return awayTeamTotal; }
    public String getPeriod(){ return period; }
    public String getTimeRemaining(){ return timeRemaining; }
    public boolean isFinal(){ return isFinal; }

    public void updateScore( int homeTeamTotal,
                             int awayTeamTotal,
                             String period,
                             String timeRemaining,
                             boolean isFinal ){

        this.homeTeamTotal = homeTeamTotal;
        this.awayTeamTotal = awayTeamTotal;
        this.period = period;
        this.timeRemaining = timeRemaining;
        this.isFinal = isFinal;

    }

}
