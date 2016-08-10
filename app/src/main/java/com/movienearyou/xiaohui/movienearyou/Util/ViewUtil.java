package com.movienearyou.xiaohui.movienearyou.Util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;

import com.google.firebase.auth.FirebaseUser;
import com.movienearyou.xiaohui.movienearyou.R;
import com.movienearyou.xiaohui.movienearyou.model.user.User;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by TQi on 7/23/16.
 */
public class ViewUtil {
    public static final String LOGINSTRING = "LOGINSTRING";
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

    public static void putData(Context context, String key, String value){
        SharedPreferences sharedpreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static void removeData(Context context, String key){
        SharedPreferences sharedpreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.remove(key);
        editor.commit();
    }

    public static boolean checkContainsData(Context context, String key){
        SharedPreferences sharedpreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        if(sharedpreferences.contains(key)){
            return true;
        }else{
            return false;
        }
    }

    public static String getData(Context context, String key){
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, "");
    }

    public static User getUserPojo(FirebaseUser firebaseUser){
        User user = new User();
        user.setDisplayName(firebaseUser.getDisplayName());
        user.setEmail(firebaseUser.getEmail());
        user.setUid(firebaseUser.getUid());

        return user;
    }
}
