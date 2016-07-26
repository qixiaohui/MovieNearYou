package com.movienearyou.xiaohui.movienearyou.Util;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;

import com.movienearyou.xiaohui.movienearyou.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by TQi on 7/23/16.
 */
public class ViewUtil {
    public static int getColumn(Activity activity){
        Display display = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics ();
        display.getMetrics(outMetrics);

        TypedValue scaleFactor = new TypedValue();

        return Math.round((outMetrics.widthPixels)/(int)(activity.getResources().getDimension(R.dimen.movie_card_width)));
    }

    public static  String getDate(){
        Calendar c = Calendar.getInstance();

        SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy");
        String formattedDate = df.format(c.getTime());
        return formattedDate.toString();
    }
}
