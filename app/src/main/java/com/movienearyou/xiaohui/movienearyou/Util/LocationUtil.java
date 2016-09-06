package com.movienearyou.xiaohui.movienearyou.Util;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;

/**
 * Created by qixiaohui on 9/5/16.
 */
public class LocationUtil {
    public static Double[] getCurrentLocation(Context context){
        Double [] location = new Double[2];
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        location[0] = lastKnownLocation.getLatitude();
        location[1] = lastKnownLocation.getLongitude();
        return location;
    }
}
