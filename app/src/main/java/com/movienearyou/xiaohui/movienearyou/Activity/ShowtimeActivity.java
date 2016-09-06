package com.movienearyou.xiaohui.movienearyou.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.movienearyou.xiaohui.movienearyou.Adapter.ShowtimeAdapter;
import com.movienearyou.xiaohui.movienearyou.R;
import com.movienearyou.xiaohui.movienearyou.model.showtime.Showtime;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qixiaohui on 8/11/16.
 */
public class ShowtimeActivity extends AppCompatActivity {
    public static final String SHOWTIME = "SHOWTIME";
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private Toolbar toolbar;
    private Showtime showtime;
    private Spinner spinner;
    private List<String> title = new ArrayList<>();

    public static void launchActivity(Activity fromActivity, Showtime showtime){
        Intent intent = new Intent(fromActivity, ShowtimeActivity.class);
        intent.putExtra(SHOWTIME, new Gson().toJson(showtime));
        fromActivity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showtime);
        createView();
    }

    private void createView(){
        showtime = new Gson().fromJson(getIntent().getStringExtra(SHOWTIME), Showtime.class);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.showtimeTabs);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        spinner = (Spinner) findViewById(R.id.showtimeSpinner);
        title.add("List");
        title.add("Map");
        ShowtimeAdapter showtimeAdapter = new ShowtimeAdapter(getSupportFragmentManager(), title, showtime);
        viewPager.setAdapter(showtimeAdapter);
        tabLayout.setupWithViewPager(viewPager);
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        spinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.showtime_spinner, menu);
        return true;
    }
}
