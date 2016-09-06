package com.movienearyou.xiaohui.movienearyou.Adapter;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.google.gson.Gson;
import com.movienearyou.xiaohui.movienearyou.Fragment.ShowtimeListFragment;
import com.movienearyou.xiaohui.movienearyou.Fragment.ShowtimeMapFragment;
import com.movienearyou.xiaohui.movienearyou.model.showtime.Showtime;

import java.util.List;

/**
 * Created by qixiaohui on 9/5/16.
 */
public class ShowtimeAdapter extends FragmentStatePagerAdapter {
    public static final String SHOWTIME_BUNDLE = "SHOWTIME_BUNDLE";
    private FragmentManager fragmentManager;
    private List<String> titles;
    private Showtime showtime;

    public ShowtimeAdapter(FragmentManager fragmentManager, List<String> titles, Showtime showtime){
        super(fragmentManager);
        this.fragmentManager = fragmentManager;
        this.titles = titles;
        this.showtime = showtime;
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0){
            ShowtimeListFragment showtimeListFragment = new ShowtimeListFragment();
            Bundle bundle = new Bundle();
            bundle.putString(SHOWTIME_BUNDLE, new Gson().toJson(showtime));
            showtimeListFragment.setArguments(bundle);
            return showtimeListFragment;
        }else if(position == 1){
            ShowtimeMapFragment showtimeMapFragment = new ShowtimeMapFragment();
            Bundle bundle = new Bundle();
            bundle.putString(SHOWTIME_BUNDLE, new Gson().toJson(showtime));
            showtimeMapFragment.setArguments(bundle);
            return showtimeMapFragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return titles.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
