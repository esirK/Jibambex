package com.janta.esir.jibambetryx.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.janta.esir.jibambetryx.MoviesFragment;

/**
 * Created by isaiahngaruiya on 17/01/2018.
 */

public class HomePagerAdapter extends FragmentPagerAdapter{
    public HomePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new MoviesFragment();
        Bundle args = new Bundle();
        args.putInt("Title", position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        super.getPageTitle(position);
        return ("Checki "+position);
    }
}
