package com.example.android.calendar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.Calendar;

/**
 * Created by harshitha on 8/6/17.
 */

public class MonthFmAdapter extends FragmentPagerAdapter {

    public static int monthForRecyclerView,yearForRecyclerView;

    public MonthFmAdapter(FragmentManager fm){

        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        int current = position % 12;
        monthForRecyclerView = current+5;
        if (current == 0) {
            yearForRecyclerView=2017;
            return new JulyFragment();
        }
        else if (current == 1) {
            yearForRecyclerView=2017;
            return new AugustFragment();
        }
        else if (current==2){
            yearForRecyclerView=2017;
            return new SeptemberFragment();
        }
        else if (current == 3) {
            yearForRecyclerView=2017;
            return new OctoberFragment();
        }
        else if (current==4){
            yearForRecyclerView=2017;
            return new NovemberFragment();
        }
        else if (current == 5) {
            yearForRecyclerView=2017;
            return new DecemberFragment();
        }
        else if (current==6){
            yearForRecyclerView=2018;
            return new JanuaryFragment();
        }
        else if (current == 7) {
            yearForRecyclerView=2018;
            return new FebruaryFragment();
        }
        else if (current==8){
            yearForRecyclerView=2018;
            return new MarchFragment();
        }
        else if (current == 9) {
            yearForRecyclerView=2018;
            return new AprilFragment();
        }
        else if (current==10){
            yearForRecyclerView=2018;
            return new MayFragment();
        }
        else if (current == 11) {
            yearForRecyclerView=2018;
            return new JuneFragment();
        }
        else return null;

    }

    @Override
    public int getCount()
    {
        return Integer.MAX_VALUE;
    }
}
