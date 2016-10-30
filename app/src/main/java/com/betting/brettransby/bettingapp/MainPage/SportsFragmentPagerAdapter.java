package com.betting.brettransby.bettingapp.MainPage;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.betting.brettransby.bettingapp.BetObjects.Sport;

import java.util.ArrayList;

/**
 * Created by brettransby on 9/25/16.
 */
public class SportsFragmentPagerAdapter extends FragmentPagerAdapter {

    private final ArrayList<Fragment> fragmentList = new ArrayList<>();
    private final ArrayList<String> fragmentTitleList = new ArrayList<>();

    public SportsFragmentPagerAdapter(final FragmentManager fragmentManager ){
        super( fragmentManager );
    }



    public void addFragment( Fragment fragment, Sport sport ) {
        fragmentList.add(fragment);
        fragmentTitleList.add( sport.toString() );
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentTitleList.get(position);
    }


    @Override
    public Fragment getItem(int position) {
        return fragmentList.get( position );
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
