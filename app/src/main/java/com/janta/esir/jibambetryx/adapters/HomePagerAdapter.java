package com.janta.esir.jibambetryx.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.janta.esir.jibambetryx.MoviesCategoryFragment;
import com.janta.esir.jibambetryx.SeriesFragment;

/**
 * Created by isaiahngaruiya on 17/01/2018.
 */

public class HomePagerAdapter extends FragmentPagerAdapter{
    public HomePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        //Show Movies categories on first tab and series on the other tab
        Fragment moviesCategoriesFragment = new MoviesCategoryFragment();
        Fragment seriesFragment = new SeriesFragment();

        if(position == 0) {
            return moviesCategoriesFragment;
        }
        else {
            return seriesFragment;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        super.getPageTitle(position);
        if(position == 0){
            return ("Movies");
        }
        return ("Series");
    }
}
