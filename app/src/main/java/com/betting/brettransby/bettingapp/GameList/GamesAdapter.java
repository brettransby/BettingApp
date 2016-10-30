package com.betting.brettransby.bettingapp.GameList;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.betting.brettransby.bettingapp.BetObjects.Game;
import com.betting.brettransby.bettingapp.R;

import org.w3c.dom.Text;

import java.text.NumberFormat;
import java.util.ArrayList;

/**
 * Created by brettransby on 9/25/16.
 */
public abstract class GamesAdapter extends RecyclerView.Adapter<GamesAdapter.ViewHolder> {


    protected ArrayList<Game> games;
    protected Context context;

    private View.OnClickListener listener;

    public GamesAdapter( final Context context, final ArrayList<Game> games ) {

        this.games = games;
        this.context = context;

    }



    public void setOnClickListener( final View.OnClickListener listener ){
        this.listener = listener;
    }


    protected String formatMoney( final double total ){
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        return formatter.format( total );
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate( getLayoutId(), parent, false);
        v.setOnClickListener( listener );

        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final Game game = games.get( position );

        holder.awayTeam.setText( game.getAwayTeam().getName() );
        holder.homeTeam.setText( game.getHomeTeam().getName() );

    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return games.size();
    }


    public abstract int getLayoutId();

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case

        CardView cardView;

        public TextView homeTeam;
        public TextView awayTeam;

        public TextView bottomSpreadTotal;
        public TextView topSpreadTotal;

        public TextView gameTime;

        public TextView oddsValue;
        public TextView wagerAmount;

        public TextView winLoss;


        public ViewHolder(View v) {
            super(v);

            homeTeam = (TextView) v.findViewById( R.id.homeTeam );
            awayTeam = (TextView) v.findViewById( R.id.awayTeam );

            topSpreadTotal = (TextView) v.findViewById( R.id.topSpreadTotal );
            bottomSpreadTotal = (TextView) v.findViewById( R.id.bottomSpreadTotal );

            gameTime = (TextView) v.findViewById( R.id.gameTime );

            oddsValue = (TextView) v.findViewById( R.id.oddsValue );
            wagerAmount = (TextView) v.findViewById( R.id.wagerAmount );

            winLoss = (TextView) v.findViewById( R.id.winLoss );

        }
    }
}
