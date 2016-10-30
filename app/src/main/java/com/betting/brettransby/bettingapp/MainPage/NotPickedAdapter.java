package com.betting.brettransby.bettingapp.MainPage;

import android.content.Context;

import com.betting.brettransby.bettingapp.BetObjects.Game;
import com.betting.brettransby.bettingapp.GameList.GamesAdapter;
import com.betting.brettransby.bettingapp.R;

import java.util.ArrayList;

/**
 * Created by brettransby on 10/27/16.
 */
public class NotPickedAdapter extends GamesAdapter {
    public NotPickedAdapter(Context context, ArrayList<Game> games) {
        super(context, games);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final Game game = games.get( position );

        holder.awayTeam.setText( game.getAwayTeam().getName() );
        holder.homeTeam.setText( game.getHomeTeam().getName() );

        if ( game.getLine().isHomeTeamFavored() ) {
            holder.bottomSpreadTotal.setText( "" + game.getLine().getSpreadHome() );
            holder.topSpreadTotal.setText( "" + game.getLine().getTotal() );
        } else {
            holder.bottomSpreadTotal.setText( "" + game.getLine().getTotal() );
            holder.topSpreadTotal.setText( "" + game.getLine().getSpreadAway() );
        }

        holder.gameTime.setText( game.getMatchTime() );

    }

    @Override
    public int getLayoutId() { return R.layout.game_not_picked; }
}
