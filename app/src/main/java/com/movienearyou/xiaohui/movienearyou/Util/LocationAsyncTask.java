package com.movienearyou.xiaohui.movienearyou.Util;

import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

/**
 * Created by qixiaohui on 9/5/16.
 */
public class LocationAsyncTask extends AsyncTask<String, String, String> {
    private static final String TAG = "LocationAsync";
    private GoogleMap googleMap;
    private Geocoder geocoder;
    private String cinemaName;

    public LocationAsyncTask(GoogleMap googleMap, Geocoder geocoder){
        this.googleMap = googleMap;
        this.geocoder = geocoder;
    }

    @Override
    protected String doInBackground(String... params) {
        cinemaName = params[0];
        try {
            List<Address> addresses = geocoder.getFromLocationName(params[0], 1);
            if(addresses == null || addresses.size() == 0){
                return null;
            }else{
                return new Gson().toJson(addresses.get(0));
            }
        } catch (IOException e){
            Log.e(TAG, e.toString());
            return null;
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if(s == null){
            return;
        }

        Address location = new Gson().fromJson(s, Address.class);
        googleMap.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude())).title(cinemaName));
    }
}
