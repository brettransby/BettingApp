package com.betting.brettransby.bettingapp.MyPicks;

import android.content.Context;
import android.view.View;

import com.betting.brettransby.bettingapp.BetObjects.Game;
import com.betting.brettransby.bettingapp.GameList.GamesAdapter;
import com.betting.brettransby.bettingapp.R;

import java.text.NumberFormat;
import java.util.ArrayList;

/**
 * Created by brettransby on 10/27/16.
 */
public class PicksAdapter extends GamesAdapter {

    public PicksAdapter(Context context, ArrayList<Game> games) {
        super(context, games);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final Game game = games.get( position );

        String homeFinalScore = "";
        String awayFinalScore = "";

        if ( game.isComplete() && game.getScore() != null ){
            homeFinalScore = "" + game.getScore().getHomeTeamTotal();
            awayFinalScore = "" + game.getScore().getAwayTeamTotal();
        }

        holder.awayTeam.setText( game.getAwayTeam().getName() + " " + homeFinalScore );
        holder.homeTeam.setText( game.getHomeTeam().getName() + " " + awayFinalScore );

        holder.topSpreadTotal.setText( game.getPickString() );

        holder.oddsValue.setText( "" + game.getLine().getOdds() );

        if ( game.isComplete() ){
            holder.wagerAmount.setText( formatMoney( game.getOutcomeValue() ) );
            holder.bottomSpreadTotal.setText( R.string.game_final );
            holder.winLoss.setText( getWinLoss( game ) );
        } else {
            holder.wagerAmount.setText( formatMoney( game.getWager() ) );
            holder.bottomSpreadTotal.setText( R.string.pending );
            holder.winLoss.setVisibility( View.GONE );
        }



    }


    private int getWinLoss( final Game game ){
        if ( game.didUserWin() ){
            return R.string.win;
        }
        return R.string.loss;
    }


    @Override
    public int getLayoutId() { return R.layout.game_picked; }

}
