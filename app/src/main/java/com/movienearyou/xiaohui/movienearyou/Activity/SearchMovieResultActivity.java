package com.movienearyou.xiaohui.movienearyou.Activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.github.ksoichiro.android.observablescrollview.ScrollUtils;
import com.movienearyou.xiaohui.movienearyou.Adapter.MovieGridAdapter;
import com.movienearyou.xiaohui.movienearyou.Network.MoviesGateway;
import com.movienearyou.xiaohui.movienearyou.Network.RestClient;
import com.movienearyou.xiaohui.movienearyou.R;
import com.movienearyou.xiaohui.movienearyou.Util.ViewUtil;
import com.movienearyou.xiaohui.movienearyou.model.movies.Movies;
import com.movienearyou.xiaohui.movienearyou.model.movies.Result;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by qixiaohui on 8/5/16.
 */
public class SearchMovieResultActivity extends AppCompatActivity {
    private Boolean loading = true;
    private MovieGridAdapter movieGridAdapter;
    private StaggeredGridLayoutManager staggeredGridLayoutManager;
    private int columnNum;
    private RecyclerView movieGridView;
    private int visibleItemCount;
    private int totalItemCount;
    private String query;
    private Toolbar toolbar;
    private ArrayList<Result> moviesResults = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_movie_result);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        toolbar.setTitleTextColor(getResources().getColor(R.color.white
        ));
        toolbar.setNavigationIcon(R.drawable.back);
        setSupportActionBar(toolbar);
        handleIntent(getIntent());
        createView();
    }

    private void createView(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        columnNum = ViewUtil.getColumn(SearchMovieResultActivity.this);
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(columnNum, StaggeredGridLayoutManager.VERTICAL);
        movieGridView = (RecyclerView) findViewById(R.id.movieList);
        movieGridView.setLayoutManager(staggeredGridLayoutManager);
        //inifite load listener
        movieGridView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) //check for scroll down
                {
                    visibleItemCount = staggeredGridLayoutManager.getChildCount();
                    totalItemCount = staggeredGridLayoutManager.getItemCount();
                    int[] into = new int[columnNum];
                    staggeredGridLayoutManager.findLastVisibleItemPositions(into);

                    if (loading) {
                        if (visibleItemCount >= 13) {
                            loading = false;
                            loadMovies((movieGridAdapter.getItemCount()/20)+1);
                        }
                    }
                }
            }
        });
    }

    private void loadMovies(final int pageNumber){
        getSupportActionBar().setTitle(query);
        supportInvalidateOptionsMenu();
        MoviesGateway moviesGateway = RestClient.getMoviesGateway();
        moviesGateway.getMovieSearchResult(query, pageNumber, new Callback<Movies>() {
            @Override
            public void success(Movies movies, Response response) {
                loading = true;
                if(pageNumber == 1) {
                    moviesResults.addAll(movies.getResults());
                    renderRows(moviesResults);
                    movieGridAdapter.notifyDataSetChanged();
                }else{
                    moviesResults.addAll(movies.getResults());
                    movieGridAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                loading = true;
                Log.e("HTTP ERROR", error.toString());
            }
        });
    }

    private void renderRows(ArrayList<Result> movies){
        movieGridAdapter = new MovieGridAdapter(SearchMovieResultActivity.this, movies, SearchMovieResultActivity.this);
        movieGridView.setAdapter(movieGridAdapter);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent){
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            query = getIntent().getStringExtra(SearchManager.QUERY);
            loadMovies(1);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
            SearchMovieResultActivity.this.overridePendingTransition(R.anim.activity_finish_leave, R.anim.activity_finish_enter);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            SearchMovieResultActivity.this.overridePendingTransition(R.anim.activity_finish_leave, R.anim.activity_finish_enter);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
