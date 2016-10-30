package com.betting.brettransby.bettingapp.BetObjects;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by brettransby on 9/25/16.
 */
public class Game implements Serializable{


    private Team homeTeam;
    private Team awayTeam;

    private Line line;
    private Score score;

    private Winner winner;
    private double wager;

    private Bet bet;

    private Sport sport;
    private String matchTime;

    public Game( final Team homeTeam,
                 final Team awayTeam,
                 final String matchTime,
                 final Line line,
                    final double wager,
                 final Sport sport ){
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;

        this.line = line;
        this.wager = wager;
        this.sport = sport;

        this.matchTime = matchTime;

        this.score = new Score( 0, 0, "1st", "15:00", false );
        this.bet = new Bet( "", "" );
    }


    public Team getHomeTeam(){
        return homeTeam;
    }

    public Team getAwayTeam(){
        return awayTeam;
    }

    public double getWager(){ return wager; }

    public Line getLine(){
        return line;
    }

    public Sport getSport() { return  sport; }


    public void setBet( final Bet bet ){
        this.bet = bet;
    }
    public void setScore( final Score score ){ this.score = score; }


    public String getStatusString() {

        if ( score.isFinal() ) {
            return "Final";
        } else{
            return "Not Started";
        }
    }


    public String getPickString(){

        if ( Bet.TOTAL_BET.equals( bet.getType() ) ){
            return getTotalString();
        }

        return getSpreadString();
    }


    private String getSpreadString(){

        String team = "";
        String plusMinus = "";

        if ( Bet.HOME_TEAM_BET.equals( bet.getBetPlaced() ) ){
            team = getHomeTeam().getName();
        } else {
            team = getAwayTeam().getName();
        }

        if ( ( Bet.HOME_TEAM_BET.equals( bet.getBetPlaced() ) && line.isHomeTeamFavored() ) ||
         ( Bet.AWAY_TEAM_BET.equals( bet.getBetPlaced() ) && ! line.isHomeTeamFavored() ) ){

            plusMinus = "-";
        }  else{
            plusMinus = "+";
        }


        return team + " " + plusMinus + line.getSpreadHome();

    }


    private String getTotalString(){
        String stringToReturn = "";

        if ( Bet.OVER_BET.equals( bet.getBetPlaced() ) ){
            stringToReturn = "Over ";
        } else {
            stringToReturn = "Under ";
        }

        return stringToReturn + line.getTotal();
    }

    public boolean isComplete() {
        if ( null == score ) { return false; }
        return score.isFinal();
    }

    public double getOutcomeValue() {

        double betValue = getWager();
        betValue = figureWithOdds( betValue );

        if ( !didUserWin() ){
            betValue = betValue * -1;
        }
        return betValue;
    }


    private double figureWithOdds( double betValue ){
        return OddsCalculator.getValue( betValue, line.getOdds() );
    }


    public boolean didUserWin(){

        if ( Bet.OVER_BET.equals( bet.getType() ) ){
            return determineOverWinner();
        } else if ( Bet.SPREAD_BET.equals( bet.getType() ) ){
            return determineSpreadWinner();
        }

        return false;
    }


    private boolean determineSpreadWinner(){
        if ( Bet.HOME_TEAM_BET.equals( bet.getBetPlaced() ) ){
            return didHomeTeamWinBet();
        }
        return ! didHomeTeamWinBet();
    }


    private boolean determineOverWinner(){

        if ( Bet.OVER_BET.equals( bet.getBetPlaced() ) ){
            return didOverHit();
        }
        return ! didOverHit();
    }


    private int getOverTotal(){
        return score.getAwayTeamTotal() + score.getHomeTeamTotal();
    }


    private boolean didOverHit(){

        return getOverTotal() > line.getTotal();
    }


    private boolean didHomeTeamWinBet(){
        int homeTeamScore = score.getHomeTeamTotal();
        int awayTeamScore = score.getAwayTeamTotal();

        if ( line.isHomeTeamFavored() ){
            homeTeamScore = homeTeamScore + (int) Math.abs( line.getSpreadHome() );
        } else {
            awayTeamScore = awayTeamScore + (int) Math.abs( line.getSpreadHome() );
        }

        return homeTeamScore > awayTeamScore;
    }

    public Score getScore() {
        return score;
    }

    public String getWinner() {
        if ( score.getAwayTeamTotal() > score.getHomeTeamTotal() ){
            return awayTeam.getName();
        }

        return homeTeam.getName();
    }


    public String getFinalScoreText(){
        return score.getAwayTeamTotal() + " - " + score.getHomeTeamTotal() + " " + getWinner() + " win";
    }


    public String getMatchTime(){

        String time = matchTime + " Greenwich Mean Time";

        Date date;
        String formattedDate = "";
        try {
            date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss zzz", Locale.getDefault() ).parse(time);
            SimpleDateFormat sdf = new SimpleDateFormat("MMM dd h:mma",Locale.getDefault());
            final TimeZone timeZone = TimeZone.getDefault();
            sdf.setTimeZone( timeZone );
            formattedDate = sdf.format( date );
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return formattedDate;

    }
}
