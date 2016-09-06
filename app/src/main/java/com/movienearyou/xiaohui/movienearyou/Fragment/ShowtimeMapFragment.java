package com.movienearyou.xiaohui.movienearyou.Fragment;

import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.movienearyou.xiaohui.movienearyou.Adapter.ShowtimeAdapter;
import com.movienearyou.xiaohui.movienearyou.R;
import com.movienearyou.xiaohui.movienearyou.Util.LocationAsyncTask;
import com.movienearyou.xiaohui.movienearyou.Util.LocationUtil;
import com.movienearyou.xiaohui.movienearyou.model.showtime.Showtime;
import com.movienearyou.xiaohui.movienearyou.model.showtime.Theater;

import java.io.IOException;
import java.util.List;
import java.util.logging.Handler;

/**
 * Created by qixiaohui on 9/5/16.
 */
public class ShowtimeMapFragment extends Fragment {
    private static final String TAG = "ShowtimeMapFragment";
    private Showtime showtime;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.showtime_map_fragment, container, false);
        Bundle bundle = getArguments();
        showtime = new Gson().fromJson(bundle.getString(ShowtimeAdapter.SHOWTIME_BUNDLE), Showtime.class);

        initMap();
        return view;
    }

    private void initMap(){
        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.cinemaLocation);
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                Double[] lastKnownLocation = LocationUtil.getCurrentLocation(getContext());
                googleMap.moveCamera( CameraUpdateFactory.newLatLngZoom(new LatLng(lastKnownLocation[0], lastKnownLocation[1]) , 9.5f) );
                googleMap.setMyLocationEnabled(true);
                final Geocoder geocoder = new Geocoder(getContext());
                if(!geocoder.isPresent()) return;

                for(final Theater theater : showtime.getMovies().get(0).getTheaters()){
                    LocationAsyncTask locationAsyncTask = new LocationAsyncTask(googleMap, geocoder);
                    locationAsyncTask.execute(theater.getAddress());
                }
            }
        });
    }

}
