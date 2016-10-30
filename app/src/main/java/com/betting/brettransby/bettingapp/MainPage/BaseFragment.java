package com.betting.brettransby.bettingapp.MainPage;

import android.support.v4.app.Fragment;

/**
 * Created by brettransby on 10/27/16.
 */
public class BaseFragment extends Fragment {

    public void startLoadingSpinner(){
        ((BaseActivity)getActivity()).startLoadingSpinner();
    }


    public void stopLoadingSpinner(){
        ((BaseActivity)getActivity()).stopLoadingSpinner();
    }
}
