package com.movienearyou.xiaohui.movienearyou.model;

import android.app.Activity;

import com.movienearyou.xiaohui.movienearyou.R;
import com.movienearyou.xiaohui.movienearyou.model.movies.Result;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by TQi on 7/23/16.
 */
public class DataStore extends HashMap<String, ArrayList<Result>>{
    public DataStore(Activity activity){
        put(activity.getString(R.string.popular_http), new ArrayList<Result>());
        put(activity.getString(R.string.upcoming_http), new ArrayList<Result>());
        put(activity.getString(R.string.top_rated_http), new ArrayList<Result>());
        put(activity.getString(R.string.now_playing_http), new ArrayList<Result>());
    }
}
