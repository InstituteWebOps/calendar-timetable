package com.example.android.calendar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.Calendar;

/**
 * Created by harshitha on 8/6/17.
 */

public class MonthFmAdapter extends FragmentPagerAdapter {



    public MonthFmAdapter(FragmentManager fm){

        super(fm);
    }



    @Override
    public Fragment getItem(int position) {
        int current = position % 12;
        if (current == 0) {
            return new JulyFragment();
        }
        else if (current == 1) {
            return new AugustFragment();
        }
        else if (current==2){
            return new SeptemberFragment();
        }
        else if (current == 3) {
            return new OctoberFragment();
        }
        else if (current==4){
            return new NovemberFragment();
        }
        else if (current == 5) {
            return new DecemberFragment();
        }
        else if (current==6){
            return new JanuaryFragment();
        }
        else if (current == 7) {
            return new FebruaryFragment();
        }
        else if (current==8){
            return new MarchFragment();
        }
        else if (current == 9) {
            return new AprilFragment();
        }
        else if (current==10){
            return new MayFragment();
        }
        else if (current == 11) {
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
