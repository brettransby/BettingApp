package com.betting.brettransby.bettingapp.BetValues;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.Editable;

import com.betting.brettransby.bettingapp.BetObjects.Game;
import com.betting.brettransby.bettingapp.BetObjects.Sport;
import com.google.gson.Gson;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.NumberFormat;
import java.util.ArrayList;

/**
 * Created by brettransby on 10/21/16.
 */
public class BetHistory {


    private static BetHistory instance;
    private ArrayList<Game> games;

    public static final String GAMES = "GAMES";

    public static BetHistory getInstance(){
        if ( null == instance ){

            instance = new BetHistory();

        }
        return instance;
    }


    public BetHistory(){
        games = new ArrayList<Game>();
    }


    public ArrayList<Game> getGames( final Context context ){

        if ( null != instance.games && ! instance.games.isEmpty() ) {
            return instance.games;
        }
        instance.games = getSavedGamesSaveToMemory( context );
        return instance.games;
    }


    public ArrayList<Game> getGamesForSport( final Context context, final Sport sport ){
        ArrayList<Game> gamesForSport = new ArrayList<Game>();
        instance.games = getSavedGamesSaveToMemory( context );

        for ( Game game : instance.games ){

            if ( game.getSport() == sport || sport == Sport.PICKS ){
                gamesForSport.add( game );
            }
        }

        return gamesForSport;
    }


    public ArrayList<Game> getAllCompletedBets( final  Context context ){

        final ArrayList<Game> completedGames = new ArrayList<Game>();

        final ArrayList<Game> picks = instance.getGamesForSport( context, Sport.PICKS );
        for ( Game game : picks ){
            if ( game.isComplete() ){

                completedGames.add( game );
            }
        }

        return completedGames;
    }



    public String calculateBalance( final Context context ){

        double total = 0;
        final ArrayList<Game> games = instance.getAllCompletedBets( context );

        for ( Game game: games ){

            total = total + game.getOutcomeValue();

        }

        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        return formatter.format( total );

    }



    public void addGameToList( final Context context, final Game gameToAdd ){
        final ArrayList<Game> games = instance.getGames( context );
        instance.games.add( gameToAdd );
        instance.saveGamesToDisk( context, games );
    }



    public static void saveGamesToDisk(Context context, ArrayList<Game> games) {
        try {
            FileOutputStream fos = context.openFileOutput(GAMES, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(games);
            oos.close();
            fos.close();
        } catch ( Exception ex ){

        }
    }

    public static ArrayList<Game> getSavedGamesSaveToMemory(Context context)  {
        try {
            FileInputStream fis = context.openFileInput(GAMES);
            ObjectInputStream ois = new ObjectInputStream(fis);
            ArrayList<Game> object = (ArrayList<Game>) ois.readObject();
            return object;
        }catch ( Exception ex ){

        }
        return new ArrayList<Game>();
    }


}
