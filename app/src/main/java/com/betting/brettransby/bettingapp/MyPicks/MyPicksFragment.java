package com.betting.brettransby.bettingapp.MyPicks;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.betting.brettransby.bettingapp.BetValues.BetHistory;
import com.betting.brettransby.bettingapp.BetObjects.Game;
import com.betting.brettransby.bettingapp.GameList.GamesAdapter;
import com.betting.brettransby.bettingapp.R;

import java.util.ArrayList;

/**
 * Created by brettransby on 10/2/16.
 */
public class MyPicksFragment extends Fragment {


    private View view;

    private RecyclerView listOfBestRecyclerView;
    private TextView currentBalence;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ){
        super.onCreateView( inflater, container, savedInstanceState );

        view = inflater.inflate( R.layout.my_picks, container, false );

        setUI();
        setGameList();



        return view;
    }


    private void setUI(){

        listOfBestRecyclerView = (RecyclerView) view.findViewById( R.id.listOfBets );
        currentBalence = (TextView) view.findViewById( R.id.currentBalence );

        setBalance();
    }


    private void setBalance(){

        String balance = getActivity().getString( R.string.currentBalance ) +
                BetHistory.getInstance().calculateBalance( getActivity() );

        currentBalence.setText( balance );

    }


    private void setGameList(){
        listOfBestRecyclerView.setLayoutManager(new LinearLayoutManager( getActivity() ) );

        final ArrayList<Game> myPicks = BetHistory.getInstance().getGames( getActivity() );
        final GamesAdapter gamesAdapter = new PicksAdapter( getActivity(), myPicks );
        listOfBestRecyclerView.setAdapter( gamesAdapter );

        listOfBestRecyclerView.invalidate();
    }



}
