package com.example.android.calendar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by harshitha on 8/6/17.
 */

public class MonthFmAdapter extends FragmentPagerAdapter {

    public MonthFmAdapter(FragmentManager fm){

        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new JulyFragment();
        }
        else if (position == 1) {
            return new AugustFragment();
        }
        else if (position==2){
            return new SeptemberFragment();
        }
        else if (position == 3) {
            return new OctoberFragment();
        }
        else if (position==4){
            return new NovemberFragment();
        }
        else if (position == 5) {
            return new DecemberFragment();
        }
        else if (position==6){
            return new JanuaryFragment();
        }
        else if (position == 7) {
            return new FebruaryFragment();
        }
        else if (position==8){
            return new MarchFragment();
        }
        else if (position == 9) {
            return new AprilFragment();
        }
        else if (position==10){
            return new MayFragment();
        }
        else if (position == 11) {
            return new JuneFragment();
        }
        else return null;

    }

    @Override
    public int getCount()
    {
        return 12;
    }
}
