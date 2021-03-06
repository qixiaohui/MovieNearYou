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
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.movienearyou.xiaohui.movienearyou.Adapter.MyCollectionAdapter;
import com.movienearyou.xiaohui.movienearyou.Application.AppController;
import com.movienearyou.xiaohui.movienearyou.R;


/**
 * Created by qixiaohui on 8/8/16.
 */
public class MyCollectionActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView recyclerView;
    private TextView noCollection;

    public static void launchActivity(Activity fromActivity){
        Intent intent = new Intent(fromActivity, MyCollectionActivity.class);
        fromActivity.startActivity(intent);
        fromActivity.overridePendingTransition(R.anim.activity_start_leave, R.anim.activity_start_enter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_collection);
        recyclerView = (RecyclerView) findViewById(R.id.movieList);
        noCollection = (TextView) findViewById(R.id.noCollection);
        linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        toolbar.setTitleTextColor(getResources().getColor(R.color.white
        ));
        toolbar.setTitle(R.string.mycollection);
        toolbar.setNavigationIcon(R.drawable.back);
        setSupportActionBar(toolbar);
        createView();
    }

    private void createView(){
        if(AppController.getInstance().getMovies().size() > 0) {
            noCollection.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            recyclerView.setAdapter(new MyCollectionAdapter(MyCollectionActivity.this, AppController.getInstance().getMovies(), MyCollectionActivity.this));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
            MyCollectionActivity.this.overridePendingTransition(R.anim.activity_finish_leave, R.anim.activity_finish_enter);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            MyCollectionActivity.this.overridePendingTransition(R.anim.activity_finish_leave, R.anim.activity_finish_enter);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
