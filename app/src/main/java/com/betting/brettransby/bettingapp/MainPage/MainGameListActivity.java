package com.betting.brettransby.bettingapp.MainPage;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.betting.brettransby.bettingapp.BetObjects.Game;
import com.betting.brettransby.bettingapp.BetObjects.Sport;
import com.betting.brettransby.bettingapp.BettingAppSetup;
import com.betting.brettransby.bettingapp.GameList.GameListFragment;
import com.betting.brettransby.bettingapp.MyPicks.MyPicksFragment;
import com.betting.brettransby.bettingapp.NewBet.NewBetActivity;
import com.betting.brettransby.bettingapp.R;

public class MainGameListActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {


    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeBetPopup( null );

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        BettingAppSetup.loadSports();


        setViewPager();

        tabLayout = (TabLayout) findViewById( R.id.tabBar );
        tabLayout.setupWithViewPager( viewPager );

    }



    private void setViewPager(){

        viewPager = (ViewPager) findViewById( R.id.sportsPager );

        SportsFragmentPagerAdapter adapter = new SportsFragmentPagerAdapter( getSupportFragmentManager() );

        adapter.addFragment( GameListFragment.getInstance( Sport.NFL ), Sport.NFL );
        adapter.addFragment( GameListFragment.getInstance( Sport.NCAA ), Sport.NCAA );
        adapter.addFragment( GameListFragment.getInstance( Sport.NHL ), Sport.NHL );
        adapter.addFragment( GameListFragment.getInstance( Sport.MLB ), Sport.MLB );
        adapter.addFragment( GameListFragment.getInstance( Sport.NBA ), Sport.NBA );

        viewPager.setAdapter( adapter );
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            int count = getSupportFragmentManager().getBackStackEntryCount();
            if ( count == 0 ) {
                super.onBackPressed();
            } else {
                getSupportFragmentManager().popBackStack();
            }
        }
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if ( id == R.id.myPicks ) {
           goToMyPicksPage();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void swapFragments( final Fragment fragment ){
        final FragmentManager fm = getSupportFragmentManager();
        final FragmentTransaction ft = fm.beginTransaction();
        ft.addToBackStack( "" );
        ft.replace( R.id.fragmentHolder, fragment  );
        ft.commit();
    }


    private void goToMyPicksPage(){

        swapFragments( new MyPicksFragment() );
    }


    public void makeBetPopup( final Game game ){

        Intent intent = new Intent( this, NewBetActivity.class );
        intent.putExtra( "GAME", game );

        startActivityForResult( intent, 0 );

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(resultCode == AppCompatActivity.RESULT_OK){

            Snackbar.make( findViewById( android.R.id.content ), R.string.betAdded, Snackbar.LENGTH_LONG ).show();

        } if (resultCode == AppCompatActivity.RESULT_CANCELED) {

        }

    }



}
