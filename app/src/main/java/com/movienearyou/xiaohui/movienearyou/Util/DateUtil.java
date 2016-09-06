package com.movienearyou.xiaohui.movienearyou.Util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by qixiaohui on 9/6/16.
 */
public class DateUtil {

    public static List<String> getShowtimeDates(int days){
        ArrayList<String> showtimeDates = new ArrayList<>();
        for(int i=0; i<days; i++){
            GregorianCalendar gc = new GregorianCalendar();
            gc.add(Calendar.DATE, i);
            showtimeDates.add(gc.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, new Locale("en")));
        }
        return showtimeDates;
    }
}
