package com.janta.esir.jibambetryx;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.janta.esir.jibambetryx.adapters.HomePagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by isaiahngaruiya on 17/01/2018.
 */

public class Home extends AppCompatActivity {
    HomePagerAdapter homePagerAdapter;

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.pager) ViewPager viewPager;
    @BindView(R.id.tabs) TabLayout tabLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        homePagerAdapter = new HomePagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(homePagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
