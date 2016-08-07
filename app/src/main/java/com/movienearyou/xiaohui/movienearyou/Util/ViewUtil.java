package com.movienearyou.xiaohui.movienearyou.Util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

    public static void intentChooser(Context context, Intent intent){
        // Always use string resources for UI text.
        // This says something like "Share this photo with"
        String title = context.getResources().getString(R.string.chooser);
        // Create intent to show chooser
        Intent chooser = Intent.createChooser(intent, title);

        // Verify the intent will resolve to at least one activity
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(chooser);
        }
    }
}
