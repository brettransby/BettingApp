package com.betting.brettransby.bettingapp.MainPage;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.betting.brettransby.bettingapp.R;

/**
 * Created by brettransby on 10/2/16.
 */
public class BaseActivity extends AppCompatActivity{


    ProgressBar progressBar;


    @Override
    protected void onCreate( Bundle savedInstanceState ){
        super.onCreate( savedInstanceState );
    }


    @Override
    public void onResume(){
        super.onResume();
        setProgressBar();
    }


    private void setProgressBar(){
        progressBar = (ProgressBar) findViewById( R.id.progressBar );
        progressBar.setVisibility(View.GONE );
    }


    public void startLoadingSpinner(){
        if ( progressBar.getVisibility() != View.VISIBLE ){
            progressBar.setVisibility( View.VISIBLE );
        }
    }


    public void stopLoadingSpinner(){
        progressBar.setVisibility( View.GONE );
    }
}
