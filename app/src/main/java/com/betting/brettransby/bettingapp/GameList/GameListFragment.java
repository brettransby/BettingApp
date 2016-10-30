package com.betting.brettransby.bettingapp.GameList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.betting.brettransby.bettingapp.BetObjects.Game;
import com.betting.brettransby.bettingapp.BetObjects.Sport;
import com.betting.brettransby.bettingapp.BetValues.BetHistory;
import com.betting.brettransby.bettingapp.MainPage.BaseActivity;
import com.betting.brettransby.bettingapp.MainPage.BaseFragment;
import com.betting.brettransby.bettingapp.MainPage.MainGameListActivity;
import com.betting.brettransby.bettingapp.MainPage.NotPickedAdapter;
import com.betting.brettransby.bettingapp.MyPicks.PicksAdapter;
import com.betting.brettransby.bettingapp.NetworkCalls.NetworkCall;
import com.betting.brettransby.bettingapp.NetworkCalls.OddsRequest;
import com.betting.brettransby.bettingapp.R;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;

import java.util.ArrayList;

/**
 * Created by brettransby on 9/25/16.
 */
public class GameListFragment extends BaseFragment implements View.OnClickListener {

    private View view;
    private RecyclerView listOfGames;

    private Sport sport;
    private ArrayList<Game> games;


    public static GameListFragment getInstance( Sport sport ){
        final GameListFragment gamesListFragment = new GameListFragment();

        final Bundle bundle = new Bundle();
        bundle.putSerializable( Sport.class.toString(), sport );
        gamesListFragment.setArguments( bundle );

        return gamesListFragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreateView(inflater,container,savedInstanceState);


        view = inflater.inflate( R.layout.betlistview_fragment, container, false );
        this.sport = (Sport) getArguments().getSerializable( Sport.class.toString() );

        return view;

    }


    @Override
    public void onResume(){
        super.onResume();
        getGames();
    }



    private void setListView( final ArrayList<Game> games ){

        listOfGames = (RecyclerView) view.findViewById( R.id.list_of_games_recycleview );
        listOfGames.setLayoutManager(new LinearLayoutManager( getActivity() ) );

        final GamesAdapter gamesAdapter = getCorrectAdapter( games );
        gamesAdapter.setOnClickListener( this );
        listOfGames.setAdapter( gamesAdapter );


        listOfGames.invalidate();
    }


    private GamesAdapter getCorrectAdapter( ArrayList<Game> games ){
        if ( Sport.PICKS == sport ) {
            return new PicksAdapter( getActivity(), games );
        }
        return new NotPickedAdapter( getActivity(), games);
    }



    public void getGames(){

        startLoadingSpinner();

        final OddsRequest request = new OddsRequest();
        request.getOddsBySport(getActivity(), sport, new FutureCallback<JsonArray>() {
            @Override
            public void onCompleted(Exception e, JsonArray result) {

                games = request.parseGames( sport, result );
                setListView( games );

                stopLoadingSpinner();

            }
        });


    }

    @Override
    public void onClick(View view) {
        final int itemPosition = listOfGames.indexOfChild( view );
        final Game game = games.get( itemPosition );

        ((MainGameListActivity) getActivity()).makeBetPopup( game );
    }
}
