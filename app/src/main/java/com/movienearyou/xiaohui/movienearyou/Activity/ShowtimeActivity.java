package com.movienearyou.xiaohui.movienearyou.Activity;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.movienearyou.xiaohui.movienearyou.Adapter.ShowtimeAdapter;
import com.movienearyou.xiaohui.movienearyou.Network.MoviesGateway;
import com.movienearyou.xiaohui.movienearyou.Network.RestClient;
import com.movienearyou.xiaohui.movienearyou.R;
import com.movienearyou.xiaohui.movienearyou.Util.DateUtil;
import com.movienearyou.xiaohui.movienearyou.model.showtime.Showtime;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by qixiaohui on 8/11/16.
 */
public class ShowtimeActivity extends AppCompatActivity {
    public static final String SHOWTIME = "SHOWTIME";
    public static final String TITLE = "TITLE";
    public static final String ADDRESS = "ADDRESS";
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private Toolbar toolbar;
    private Showtime showtime;
    private Spinner spinner;
    private String movieTitle;
    private Address address;
    private ShowtimeAdapter showtimeAdapter;
    private int currentSelection = 0;
    private List<String> title = new ArrayList<>();

    public static void launchActivity(Activity fromActivity, Showtime showtime, String title, Address address){
        Intent intent = new Intent(fromActivity, ShowtimeActivity.class);
        intent.putExtra(SHOWTIME, new Gson().toJson(showtime));
        intent.putExtra(TITLE, title);
        intent.putExtra(ADDRESS, new Gson().toJson(address));
        fromActivity.startActivity(intent);
        fromActivity.overridePendingTransition(R.anim.activity_start_leave, R.anim.activity_start_enter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showtime);
        createView();
    }

    private void createView(){
        showtime = new Gson().fromJson(getIntent().getStringExtra(SHOWTIME), Showtime.class);
        movieTitle = getIntent().getStringExtra(TITLE);
        address = new Gson().fromJson(getIntent().getStringExtra(ADDRESS), Address.class);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.showtimeTabs);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        spinner = (Spinner) findViewById(R.id.showtimeSpinner);
        title.add("List");
        title.add("Map");
        showtimeAdapter = new ShowtimeAdapter(getSupportFragmentManager(), title, showtime);
        viewPager.setAdapter(showtimeAdapter);
        tabLayout.setupWithViewPager(viewPager);
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                ShowtimeActivity.this.overridePendingTransition(R.anim.activity_finish_leave, R.anim.activity_finish_enter);
            }
        });
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        ArrayList<String> showtimeDates = (ArrayList<String>) DateUtil.getShowtimeDates(3);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ShowtimeActivity.this, R.layout.dropdown_spinner_item, showtimeDates);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == currentSelection) return;
                currentSelection = position;
                getShowtimes(currentSelection);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void getShowtimes(int position){
        MoviesGateway moviesGateway = RestClient.getMoviesGateway();
        moviesGateway.getShowtime(movieTitle.replaceAll(" ", "+"), address.getPostalCode(), Integer.toString(position), new Callback<Showtime>() {
            @Override
            public void success(final Showtime showtime, Response response) {
                Log.i("showtime", Integer.toString(showtime.getMovies().size()));
                if(showtime.getMovies().size() == 0) return;
                showtimeAdapter.setShowtime(showtime);
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
            ShowtimeActivity.this.overridePendingTransition(R.anim.activity_finish_leave, R.anim.activity_finish_enter);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
