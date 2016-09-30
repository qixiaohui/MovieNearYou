package com.movienearyou.xiaohui.movienearyou.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.movienearyou.xiaohui.movienearyou.Adapter.TicketListAdapter;
import com.movienearyou.xiaohui.movienearyou.R;
import com.movienearyou.xiaohui.movienearyou.model.showtime.Schedule;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by qixiaohui on 9/6/16.
 */
public class CinemaScheduleActivity extends AppCompatActivity {
    public static final String SCHEDULES = "SCHEDULES";

    private RecyclerView recyclerView;
    private List<Schedule> schedules;

    private Toolbar mToolbarView;

    public static void launchActivity(Activity fromActivity, List<Schedule> schedules){
        Intent  intent = new Intent(fromActivity, CinemaScheduleActivity.class);
        intent.putExtra(SCHEDULES, new Gson().toJson(schedules));
        fromActivity.startActivity(intent);
        fromActivity.overridePendingTransition(R.anim.activity_start_leave, R.anim.activity_start_enter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cinema_schedule);
        recyclerView = (RecyclerView) findViewById(R.id.scheduleTime);
        Type listType = new TypeToken<ArrayList<Schedule>>(){}.getType();
        schedules = new Gson().fromJson(getIntent().getStringExtra(SCHEDULES), listType);
        if(schedules == null) return;
        initView();
    }

    private void initView(){
        mToolbarView = (Toolbar)findViewById(R.id.toolbar);
        mToolbarView.setTitleTextColor(getResources().getColor(R.color.white));
        mToolbarView.setNavigationIcon(R.drawable.back);
        mToolbarView.setTitle(getResources().getString(R.string.tickets));
        setSupportActionBar(mToolbarView);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getBaseContext());
        TicketListAdapter ticketListAdapter = new TicketListAdapter(schedules, CinemaScheduleActivity.this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(ticketListAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
            CinemaScheduleActivity.this.overridePendingTransition(R.anim.activity_finish_leave, R.anim.activity_finish_enter);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            CinemaScheduleActivity.this.overridePendingTransition(R.anim.activity_finish_leave, R.anim.activity_finish_enter);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
