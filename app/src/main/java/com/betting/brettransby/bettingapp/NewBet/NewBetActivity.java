package com.betting.brettransby.bettingapp.NewBet;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.betting.brettransby.bettingapp.BetObjects.Bet;
import com.betting.brettransby.bettingapp.BetObjects.Game;
import com.betting.brettransby.bettingapp.BetObjects.Line;
import com.betting.brettransby.bettingapp.BetObjects.Score;
import com.betting.brettransby.bettingapp.BetObjects.Sport;
import com.betting.brettransby.bettingapp.BetObjects.Team;
import com.betting.brettransby.bettingapp.BetValues.BetDefaults;
import com.betting.brettransby.bettingapp.BetValues.BetHistory;
import com.betting.brettransby.bettingapp.MainPage.BaseActivity;
import com.betting.brettransby.bettingapp.R;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;

/**
 * Created by brettransby on 10/2/16.
 */
public class NewBetActivity extends BaseActivity implements TextWatcher, View.OnClickListener {


    private EditText awayTeamEntry;
    private EditText homeTeamEntry;
    private EditText wagerAmountEntry;
    private EditText oddsEntry;
    private EditText spreadEntry;
    private EditText totalEntry;

    private RadioButton homeRadio;
    private RadioButton awayRadio;
    private RadioButton overRadio;
    private RadioButton underRadio;

    private boolean homeTeamFavored = true;
    private Button swapFavorite;
    private Button setScore;
    private TextView finalScore;

    private Game game;
    private Score gameScore;

    @Override
    public void onCreate( Bundle savedInstanceState ){
        super.onCreate( savedInstanceState );

        setContentView( R.layout.make_bet );

        game = getGame();

        setView();
        setUI();

        setDefaults();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.new_bet_menu, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        if (id == R.id.save) {

            if ( validate() ) {

                saveBet();

                setResult(AppCompatActivity.RESULT_OK);
                finish();
                return true;
            } else {
                Snackbar.make( findViewById( android.R.id.content ), R.string.notAllFieldsAreFull, Snackbar.LENGTH_LONG ).show();

            }

        }

        return super.onOptionsItemSelected(item);
    }

    private void setView(){

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        myToolbar.setTitleTextColor( getResources().getColor( R.color.mainTitleColor ) );

        myToolbar.setNavigationIcon( android.R.drawable.ic_menu_close_clear_cancel );


    }


    private void setUI(){

        awayTeamEntry = (EditText) findViewById( R.id.awayTeamEntry );
        awayTeamEntry.addTextChangedListener( this );

        homeTeamEntry = (EditText) findViewById( R.id.homeTeamEntry );
        homeTeamEntry.addTextChangedListener( this );

        spreadEntry = (EditText) findViewById( R.id.spread );
        spreadEntry.addTextChangedListener( this );

        oddsEntry = (EditText) findViewById( R.id.odds );
        wagerAmountEntry = (EditText) findViewById( R.id.betValue );

        totalEntry = (EditText) findViewById( R.id.total );
        totalEntry.addTextChangedListener( this );

        homeRadio = (RadioButton) findViewById( R.id.homeTeamRadio );
        awayRadio = (RadioButton) findViewById( R.id.awayTeamRadio );
        overRadio = (RadioButton) findViewById( R.id.overRadio );
        underRadio = (RadioButton) findViewById( R.id.underRadio );

        swapFavorite = (Button) findViewById( R.id.swap );
        swapFavorite.setOnClickListener( this );

        setScore = (Button) findViewById( R.id.setScore );
        setScore.setOnClickListener( this );

        finalScore = (TextView) findViewById( R.id.finalScoreText );

        if ( null != game.getScore() && game.getScore().isFinal() ){
            finalScore.setText( game.getFinalScoreText() );
            finalScore.setVisibility( View.VISIBLE );
        } else {
            finalScore.setVisibility( View.GONE );
        }

        setSportSpinner();
        setStatusBar();

    }



    private void setStatusBar(){
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor( ContextCompat.getColor( this, R.color.colorPrimaryDark));
    }


    private void setSportSpinner(){

        final ArrayList<Sport> sports = Sport.getAllSports();
        final Spinner spinner = (Spinner) findViewById( R.id.sportPicker );

        final ArrayAdapter arrayAdapter = new ArrayAdapter( this, android.R.layout.simple_dropdown_item_1line, sports );
        spinner.setAdapter( arrayAdapter );
    }


    private void setDefaults(){

        if ( null != game.getLine() && game.getLine().getSpreadAway() < 0 ){
            homeTeamFavored = false;
        }

        homeTeamEntry.setText( game.getHomeTeam().getName() );
        awayTeamEntry.setText( game.getAwayTeam().getName() );

        oddsEntry.setText( "" + BetDefaults.getOdds() );
        totalEntry.setText( "" + game.getLine().getTotal() );

        double spreadValue = game.getLine().getSpreadHome();
        spreadValue = Math.abs( spreadValue );
        spreadEntry.setText( "" + spreadValue );
        wagerAmountEntry.setText( "" + BetDefaults.getWager() );

        awayRadio.setChecked( true );

        invalidateRadioButtons();
    }


    private Game getValues(){

        final String homeTeam = homeTeamEntry.getEditableText().toString();
        final String awayTeam = awayTeamEntry.getEditableText().toString();
        final double spread = Double.parseDouble( spreadEntry.getEditableText().toString() );
        final double wager = Double.parseDouble( wagerAmountEntry.getEditableText().toString() );
        final int odds = Integer.parseInt( oddsEntry.getEditableText().toString() );

        final Line line = new Line( true, spread,
                                            spread * -1,
                                            0,
                                            odds );

        final Game game = new Game( new Team( homeTeam ),
                                    new Team( awayTeam ),
                                    "",
                                    line,
                                    wager,
                                    Sport.NCAA );
        game.setBet( getBet() );
        game.setScore( gameScore );

        return game;
    }


    private Bet getBet(){

        String type = "";
        String pick;

        if ( homeRadio.isChecked() ) {
            type = Bet.SPREAD_BET;
            pick = Bet.HOME_TEAM_BET;
        } else if ( awayRadio.isChecked() ){
            type = Bet.SPREAD_BET;
            pick = Bet.AWAY_TEAM_BET;
        } else if ( overRadio.isChecked() ){
            type = Bet.TOTAL_BET;
            pick = Bet.OVER_BET;
        } else {
            type = Bet.TOTAL_BET;
            pick = Bet.UNDER_BET;
        }

        return new Bet( type, pick );
    }



    private boolean validate(){

        return hasText( homeTeamEntry ) &&
                hasText( awayTeamEntry ) &&
                hasText( spreadEntry ) &&
                hasText( wagerAmountEntry ) &&
                hasText( oddsEntry ) &&
                hasText( totalEntry );
    }


    private boolean hasText( final EditText editText ){
        return ! editText.getEditableText().toString().isEmpty();
    }


    private void invalidateRadioButtons(){

        String homeSpreadPrefix = "";
        String awaySpreadPrefix = "";

        if ( homeTeamFavored ){
            homeSpreadPrefix = "-";
            awaySpreadPrefix = "+";
        } else {
            homeSpreadPrefix = "+";
            awaySpreadPrefix = "-";
        }

        homeRadio.setText( homeSpreadPrefix + getTextForEditText( spreadEntry ) + " " + getTextForEditText( homeTeamEntry ) );
        awayRadio.setText( awaySpreadPrefix + getTextForEditText( spreadEntry ) + " " + getTextForEditText( awayTeamEntry ) );
        overRadio.setText( "Over " + getTextForEditText( totalEntry ) );
        underRadio.setText( "Under " + getTextForEditText( totalEntry ) );


    }


    private void saveBet(){

        final Game game = getValues();
        BetHistory.getInstance().addGameToList( this, game );

    }


    private void swapFavorite(){
        homeTeamFavored = ! homeTeamFavored;
        invalidateRadioButtons();
    }


    private void setScore(){

        showAddScoreDialog();
    }



    private void showAddScoreDialog(){

        final AlertDialog.Builder builder = new AlertDialog.Builder( this );
        final View entryView = LayoutInflater.from( this ).inflate( R.layout.score_entry, null );
        builder.setView( entryView );
        final AlertDialog dialog = builder.create();
        final Button button = (Button) entryView.findViewById( R.id.saveScore );
        final EditText awayScoreEntry = (EditText) entryView.findViewById( R.id.awayTeamScoreEntry );
        final EditText homeScoreEntry = (EditText) entryView.findViewById( R.id.homeTeamScoreEntry );


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String awayScore = awayScoreEntry.getEditableText().toString();
                final String homeScore = homeScoreEntry.getEditableText().toString();

                final Score score = new Score( Integer.parseInt( homeScore ),
                                                Integer.parseInt( awayScore ),
                                                "0",
                                                "0",
                                                true );


                setUI();
                gameScore = score;
                game.setScore( score );
                showScorePopup();
                dialog.dismiss();
            }
        });

        dialog.show();


    }



    private void showScorePopup(){

        Snackbar.make( findViewById( android.R.id.content ),
                                    "Score has been saved as " + game.getFinalScoreText()
                                    , Snackbar.LENGTH_LONG ).show();
    }

    private String getTextForEditText( final EditText editText ){

        final Editable editable = editText.getEditableText();

        if ( editable.length() == 0 ) {
            return "";
        }

        return editable.toString();
    }



    private Game getGame(){

        final Intent intent = getIntent();
        final Bundle extras = intent.getExtras();

        Game game = (Game) extras.get( "GAME" );
        if ( null == game ){

            Line line = new Line( true,
                                    BetDefaults.getSpread(),
                                    BetDefaults.getSpread() * -1,
                                    BetDefaults.getOver(),
                                    BetDefaults.getOdds() );

            game = new Game( new Team( "" ),
                                new Team( "" ),
                                "",
                                line,
                                BetDefaults.getWager(),
                                null );

        }

        return game;
    }


    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        invalidateRadioButtons();
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public void onClick(View view) {

        if ( view.getId() == R.id.swap ) {
            swapFavorite();
        } else if ( view.getId() == R.id.setScore ){

            //if ( validate() ) {
                setScore();
            //}
        }

    }
}
