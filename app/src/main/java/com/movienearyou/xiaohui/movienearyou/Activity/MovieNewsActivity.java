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
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.movienearyou.xiaohui.movienearyou.Adapter.LatestNewsAdapter;
import com.movienearyou.xiaohui.movienearyou.Listener.EndlessRecyclerListener;
import com.movienearyou.xiaohui.movienearyou.Network.MoviesGateway;
import com.movienearyou.xiaohui.movienearyou.Network.RestClient;
import com.movienearyou.xiaohui.movienearyou.R;
import com.movienearyou.xiaohui.movienearyou.model.news.News;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by qixiaohui on 9/26/16.
 */
public class MovieNewsActivity extends AppCompatActivity {
    private static final String TAG = "MovieNewsActivity";

    private RecyclerView newsList;
    private Toolbar toolbar;

    private LatestNewsAdapter latestNewsAdapter;
    private int page = 1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_latest_news);
        initView();
    }

    private void initView() {
        newsList = (RecyclerView) findViewById(R.id.newsList);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        toolbar.setTitleTextColor(getResources().getColor(R.color.white
        ));
        toolbar.setTitle(R.string.latest_news);
        toolbar.setNavigationIcon(R.drawable.back);
        setSupportActionBar(toolbar);

        getNews(page);
    }

    private void getNews(int page) {
        MoviesGateway moviesGateway = RestClient.getMoviesGateway();
        moviesGateway.getLatestNews(page, new Callback<News>() {
            @Override
            public void success(News news, Response response) {
                if(news.getList().size() == 0) return;

                if(latestNewsAdapter == null) {
                    latestNewsAdapter = new LatestNewsAdapter(news, MovieNewsActivity.this);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MovieNewsActivity.this);
                    newsList.setLayoutManager(linearLayoutManager);
                    newsList.setAdapter(latestNewsAdapter);
                    buildScrollListener();
                } else {
                    latestNewsAdapter.addMore(news);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e(TAG, error.getMessage());
            }
        });
    }

    public static void launchActivity(Activity fromActivity) {
        Intent intent = new Intent(fromActivity, MovieNewsActivity.class);
        fromActivity.startActivity(intent);
        fromActivity.overridePendingTransition(R.anim.activity_start_leave, R.anim.activity_start_enter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
            MovieNewsActivity.this.overridePendingTransition(R.anim.activity_finish_leave, R.anim.activity_finish_enter);
        }
        return super.onOptionsItemSelected(item);
    }

    private void buildScrollListener() {
        newsList.addOnScrollListener(new EndlessRecyclerListener((LinearLayoutManager) newsList.getLayoutManager()) {
            @Override
            public void onLoadMore(int current_page) {
                if(current_page != page) {
                    page = current_page;
                } else {
                    return;
                }

                if(100 > newsList.getAdapter().getItemCount()) {
                    getNews(page);
                }
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            MovieNewsActivity.this.overridePendingTransition(R.anim.activity_finish_leave, R.anim.activity_finish_enter);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
