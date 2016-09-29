package com.movienearyou.xiaohui.movienearyou.Activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.transition.Visibility;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.facebook.FacebookSdk;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;
import com.movienearyou.xiaohui.movienearyou.Adapter.MovieGridAdapter;
import com.movienearyou.xiaohui.movienearyou.Network.MoviesGateway;
import com.movienearyou.xiaohui.movienearyou.Network.RestClient;
import com.movienearyou.xiaohui.movienearyou.R;
import com.movienearyou.xiaohui.movienearyou.Util.ViewUtil;
import com.movienearyou.xiaohui.movienearyou.Views.RoundedImageView;
import com.movienearyou.xiaohui.movienearyou.model.DataStore;
import com.movienearyou.xiaohui.movienearyou.model.movies.Movies;
import com.movienearyou.xiaohui.movienearyou.model.movies.Result;
import com.movienearyou.xiaohui.movienearyou.model.user.User;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MovieBaseActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView movieGridView;
    private StaggeredGridLayoutManager staggeredGridLayoutManager;
    private String CURRENT_CATEGORY;

    private int visibleItemCount;
    private int totalItemCount;
    private int pastVisiblesItems;

    private Boolean loading = true;
    private MovieGridAdapter movieGridAdapter;

    private DataStore dataStore;
    private int columnNum;
    private Toolbar toolbar;

    private MenuItem login;
    private MenuItem logout;

    private TextView userId;

    private User firebaseUser;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.now_playing));
        setSupportActionBar(toolbar);
        columnNum = ViewUtil.getColumn(MovieBaseActivity.this);
        CURRENT_CATEGORY = this.getString(R.string.now_playing_http);
        dataStore = new DataStore(this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View view = navigationView.getHeaderView(0);
        userId = (TextView) view.findViewById(R.id.userId);
        navigationView.setNavigationItemSelectedListener(this);

        createView();
    }

    private void createView(){
        //initialize facebook sdk
        FacebookSdk.sdkInitialize(getApplicationContext());
        /**
         * create grid view and set layout manager
         */
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(columnNum, StaggeredGridLayoutManager.VERTICAL);
        movieGridView = (RecyclerView) findViewById(R.id.movieList);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        login = navigationView.getMenu().findItem(R.id.login);
        logout = navigationView.getMenu().findItem(R.id.signout);

        movieGridView.setLayoutManager(staggeredGridLayoutManager);
        loadMovies(1);
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
                            loadMovies((dataStore.get(CURRENT_CATEGORY).size()/20)+1);
                        }
                    }
                }
            }
        });
    }

    private void loadMovies(final int pageNumber){
        MoviesGateway moviesGateway = RestClient.getMoviesGateway();
        moviesGateway.getMovies(CURRENT_CATEGORY, pageNumber, new Callback<Movies>() {
            @Override
            public void success(Movies movies, Response response) {
                dataStore.get(CURRENT_CATEGORY).addAll((ArrayList<Result>) movies.getResults());
                loading = true;
                if (pageNumber == 1) {
                    renderRows(dataStore.get(CURRENT_CATEGORY));
                    movieGridAdapter.notifyDataSetChanged();
                } else {
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
        if(movieGridAdapter == null) {
            movieGridAdapter = new MovieGridAdapter(MovieBaseActivity.this, movies, MovieBaseActivity.this);
            movieGridView.setAdapter(movieGridAdapter);
        }else{
            movieGridAdapter.swapCategory(movies);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.movie_base_bar, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.popular) {
            toolbar.setTitle(this.getResources().getString(R.string.popular));
           if(CURRENT_CATEGORY != getResources().getString(R.string.popular_http)){
               CURRENT_CATEGORY = getResources().getString(R.string.popular_http);
               if(dataStore.get(CURRENT_CATEGORY).size() == 0){
                   loadMovies(1);
               }else{
                    movieGridAdapter.swapCategory(dataStore.get(CURRENT_CATEGORY));
               }
           }
        } else if (id == R.id.top_rated) {
            toolbar.setTitle(this.getResources().getString(R.string.top_rated));
            if(CURRENT_CATEGORY != getResources().getString(R.string.top_rated_http)){
                CURRENT_CATEGORY = getResources().getString(R.string.top_rated_http);
            }
            if(dataStore.get(CURRENT_CATEGORY).size() == 0){
                loadMovies(1);
            }else{
                movieGridAdapter.swapCategory(dataStore.get(CURRENT_CATEGORY));
            }
        } else if (id == R.id.upcoming) {
            toolbar.setTitle(this.getResources().getString(R.string.upcoming));
            if(CURRENT_CATEGORY != getResources().getString(R.string.upcoming_http)){
                CURRENT_CATEGORY = getResources().getString(R.string.upcoming_http);
            }
            if(dataStore.get(CURRENT_CATEGORY).size() == 0){
                loadMovies(1);
            }else{
                movieGridAdapter.swapCategory(dataStore.get(CURRENT_CATEGORY));
            }
        } else if (id == R.id.now_playing) {
            toolbar.setTitle(this.getResources().getString(R.string.now_playing));
            if(CURRENT_CATEGORY != getResources().getString(R.string.now_playing_http)){
                CURRENT_CATEGORY = getResources().getString(R.string.now_playing_http);
            }
            if(dataStore.get(CURRENT_CATEGORY).size() == 0){
                loadMovies(1);
            }else{
                movieGridAdapter.swapCategory(dataStore.get(CURRENT_CATEGORY));
            }
        } else if (id == R.id.collection) {
            if(login.isVisible()){
                new AlertDialog.Builder(this)
                        .setTitle("Login")
                        .setMessage("Please login first")
                        .setIcon(R.drawable.login)
                        .setPositiveButton(R.string.loginRedirect, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                SignonActivity.launchActivity(MovieBaseActivity.this);
                            }})
                        .setNegativeButton(R.string.cancel, null).show();
            }else{
                MyCollectionActivity.launchActivity(MovieBaseActivity.this);
            }
        } else if(id == R.id.login) {
            SignonActivity.launchActivity(MovieBaseActivity.this);
        } else if(id == R.id.signout) {
            new AlertDialog.Builder(this)
                    .setTitle("Sign out")
                    .setMessage("Do you really want to sign out?")
                    .setIcon(R.drawable.logout)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int whichButton) {
                            //show a dialog to confirm logout
                            SignonActivity.singOutFb(MovieBaseActivity.this);
                            updateNavMenu();
                        }})
                    .setNegativeButton(android.R.string.no, null).show();
        } else if (id == R.id.news) {
            MovieNewsActivity.launchActivity(MovieBaseActivity.this);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateNavMenu();
    }

    private void updateNavMenu(){
        if(ViewUtil.checkContainsData(MovieBaseActivity.this, ViewUtil.LOGINSTRING)){
            firebaseUser = new Gson().fromJson(ViewUtil.getData(MovieBaseActivity.this, ViewUtil.LOGINSTRING), User.class);
            userId.setText(firebaseUser.getDisplayName());
            login.setVisible(false);
            logout.setVisible(true);
        }else{
            userId.setText(null);
            login.setVisible(true);
            logout.setVisible(false);
        }
    }
}
