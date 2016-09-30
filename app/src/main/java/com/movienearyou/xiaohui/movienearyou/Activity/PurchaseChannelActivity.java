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
import com.movienearyou.xiaohui.movienearyou.Adapter.ChannelListAdapter;
import com.movienearyou.xiaohui.movienearyou.R;
import com.movienearyou.xiaohui.movienearyou.model.channels.Channel;

import java.util.ArrayList;

/**
 * Created by qixiaohui on 8/4/16.
 */
public class PurchaseChannelActivity extends AppCompatActivity {
    private static final String CHANNEL = "CHANNEL";
    private RecyclerView.LayoutManager layoutManager;

    private RecyclerView recyclerView;
    private Toolbar mToolbarView;

    private ArrayList<Channel> channels;

    public static void launchActivity(Activity fromActivity, ArrayList<Channel> channels){
        Intent intent = new Intent(fromActivity, PurchaseChannelActivity.class);
        intent.putExtra(CHANNEL, new Gson().toJson(channels));
        fromActivity.startActivity(intent);
        fromActivity.overridePendingTransition(R.anim.activity_start_leave, R.anim.activity_start_enter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_channel);
        TypeToken<ArrayList<Channel>> channelType = new TypeToken<ArrayList<Channel>>() {};

        channels = new Gson().fromJson(getIntent().getStringExtra(CHANNEL), channelType.getType());

        createView();
    }

    private void createView(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        mToolbarView = (Toolbar)findViewById(R.id.toolbar);
        mToolbarView.setTitle(getResources().getString(R.string.channels));
        mToolbarView.setTitleTextColor(getResources().getColor(R.color.white));
        mToolbarView.setNavigationIcon(R.drawable.back);
        setSupportActionBar(mToolbarView);

        recyclerView = (RecyclerView) findViewById(R.id.channelList);
        recyclerView.setAdapter(new ChannelListAdapter(PurchaseChannelActivity.this, channels));
        layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
            PurchaseChannelActivity.this.overridePendingTransition(R.anim.activity_finish_leave, R.anim.activity_finish_enter);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            PurchaseChannelActivity.this.overridePendingTransition(R.anim.activity_finish_leave, R.anim.activity_finish_enter);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
