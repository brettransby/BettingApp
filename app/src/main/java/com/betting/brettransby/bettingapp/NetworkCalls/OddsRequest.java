package com.betting.brettransby.bettingapp.NetworkCalls;

import android.content.Context;

import com.betting.brettransby.bettingapp.BetObjects.Game;
import com.betting.brettransby.bettingapp.BetObjects.Line;
import com.betting.brettransby.bettingapp.BetObjects.Sport;
import com.betting.brettransby.bettingapp.BetObjects.Team;
import com.betting.brettransby.bettingapp.BettingAppSetup;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;

import java.util.ArrayList;

/**
 * Created by brettransby on 10/26/16.
 */
public class OddsRequest {

    public static String ODDS_URL_STRING = "odds/";


    public void getOddsBySport(final Context context, final Sport sport, FutureCallback<JsonArray> callback ){

        final String urlAddOn = ODDS_URL_STRING + BettingAppSetup.getSportString( sport );
        NetworkCall.makeArray( context, urlAddOn, callback );

    }


    public ArrayList<Game> parseGames( final Sport sport, final JsonArray array ){

        final ArrayList<Game> games = new ArrayList<Game>();

        for ( int i = 0; i < array.size(); i++ ){

            Line line = null;

            JsonElement element = array.get( i );
            JsonObject matchObject = element.getAsJsonObject();

            final String id = matchObject.get( "ID" ).getAsString();
            final String homeTeam = matchObject.get( "HomeTeam" ).getAsString();
            final String awayTeam = matchObject.get( "AwayTeam" ).getAsString();
            final String matchTime = matchObject.get( "MatchTime" ).getAsString();

            final JsonArray odds = matchObject.getAsJsonArray( "Odds" ).getAsJsonArray();

            for ( int j = 0; j < odds.size(); j++ ){

                JsonObject oddsObject = odds.get( j ).getAsJsonObject();
                final double moneyLineHome = oddsObject.get( "MoneyLineHome" ).getAsDouble();
                final String moneyLineAway = oddsObject.get( "MoneyLineAway" ).getAsString();
                final int pointSpreadHomeLine = oddsObject.get( "PointSpreadHomeLine" ).getAsInt();
                final double pointSpreadHome = oddsObject.get( "PointSpreadHome" ).getAsDouble();
                final double pointSpreadAway = oddsObject.get( "PointSpreadAway" ).getAsDouble();
                final double totalNumber = oddsObject.get( "TotalNumber" ).getAsDouble();

                boolean isHomeTeamFavored = true;
                if ( pointSpreadHome > 0 ){
                    isHomeTeamFavored = false;
                }

                line = new Line( isHomeTeamFavored,
                            pointSpreadHome,
                            pointSpreadAway,
                            totalNumber,
                            pointSpreadHomeLine );
            }

            final Game game = new Game( new Team( homeTeam ),
                                        new Team( awayTeam ),
                                        matchTime,
                                        line,
                                        0.0,
                                        sport );
            games.add( game );
        }



        return games;
    }
}
