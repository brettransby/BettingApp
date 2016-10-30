package com.betting.brettransby.bettingapp.BetObjects;

import com.betting.brettransby.bettingapp.BetValues.BetDefaults;

/**
 * Created by brettransby on 10/21/16.
 */
public class OddsCalculator {


    public static String RETURN_IF_WIN = "RETURN_IF_WIN";


    public static double getValue( final double wager, final double odds ){

        if ( BetDefaults.RETURN_IF_WIN && odds < 0 ){
            return  Math.abs( ( odds * wager ) / 100 );
        } else if ( odds < 0 ){
            return Math.abs( ( wager * 100)/odds );
        } else {
            return Math.abs( (wager * odds)/  100 );
        }

    }


}
