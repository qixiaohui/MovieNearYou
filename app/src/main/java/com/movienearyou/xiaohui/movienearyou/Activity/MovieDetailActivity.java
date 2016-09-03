package com.movienearyou.xiaohui.movienearyou.Activity;

import android.Manifest;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.github.ksoichiro.android.observablescrollview.ScrollUtils;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.gson.Gson;
import com.movienearyou.xiaohui.movienearyou.Application.AppController;
import com.movienearyou.xiaohui.movienearyou.Fragment.CastDetailFragment;
import com.movienearyou.xiaohui.movienearyou.Network.MoviesGateway;
import com.movienearyou.xiaohui.movienearyou.Network.RestClient;
import com.movienearyou.xiaohui.movienearyou.R;
import com.movienearyou.xiaohui.movienearyou.Util.ViewUtil;
import com.movienearyou.xiaohui.movienearyou.Views.CastCard;
import com.movienearyou.xiaohui.movienearyou.Views.SimilarCard;
import com.movienearyou.xiaohui.movienearyou.model.channels.Channel;
import com.movienearyou.xiaohui.movienearyou.model.channels.Format;
import com.movienearyou.xiaohui.movienearyou.model.credit.Cast;
import com.movienearyou.xiaohui.movienearyou.model.credit.Credit;
import com.movienearyou.xiaohui.movienearyou.model.credit.Crew;
import com.movienearyou.xiaohui.movienearyou.model.movies.Movies;
import com.movienearyou.xiaohui.movienearyou.model.movies.Result;
import com.movienearyou.xiaohui.movienearyou.model.showtime.Movie;
import com.movienearyou.xiaohui.movienearyou.model.showtime.Schedule;
import com.movienearyou.xiaohui.movienearyou.model.showtime.Showtime;
import com.movienearyou.xiaohui.movienearyou.model.videos.Video;
import com.nineoldandroids.view.ViewHelper;
import com.squareup.okhttp.internal.Util;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by TQi on 7/23/16.
 */
public class MovieDetailActivity extends AppCompatActivity implements ObservableScrollViewCallbacks, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, NavigationView.OnNavigationItemSelectedListener{
    private static final String TAG = "MovieDetailActivity";
    public static final String CAST="CAST";
    private Toolbar toolbar;
    private ImageView mImageView;
    private Toolbar mToolbarView;
    private ObservableScrollView mScrollView;
    private int mParallaxImageHeight;
    private Result movie;

    private CoordinatorLayout coordinateLayout;
    private ImageView poster;
    private TextView title;
    private TextView rate;
    private TextView releaseDate;
    private TextView lan;
    private TextView description;

    //fixed 5 videos, so hardcode all videos
    //TODO: convert to listview
    private ImageView screenShot1;
    private ImageView screenShot2;
    private ImageView screenShot3;
    private ImageView screenShot4;
    private ImageView screenShot5;

    private TextView videoTitle1;
    private TextView videoTitle2;
    private TextView videoTitle3;
    private TextView videoTitle4;
    private TextView videoTitle5;

    private static final int PERMISSION_ACCESS_COARSE_LOCATION = 200;
    private GoogleApiClient googleApiClient;

    private TextView dateView;
    private TextView cinemaView;
    private TextView scheduleView;
    private LinearLayout showtimeView;
    private LinearLayout creditWrapper;
    private LinearLayout similarWrapper;
    private LinearLayout channelWrapper;
    private RelativeLayout channelWrapperRelative;
    private TextView channelTag;
    private ImageView channelLogo;
    private LinearLayout channelTextWrapper;
    private TextView channelViewAll;
    private TextView showtimeViewAll;
    private FloatingActionButton fab;
    private ArrayList<Result> movies;
    private Boolean movieAdded = false;

    public static void launchActivity(Activity fromActivity, Result movie){
        Intent intent = new Intent(fromActivity, MovieDetailActivity.class);
        intent.putExtra("movie", new Gson().toJson(movie));
        fromActivity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        movies = AppController.getInstance().getMovies();

        movie = new Gson().fromJson((String) getIntent().getExtras().get("movie"), Result.class);

        googleApiClient = new GoogleApiClient.Builder(this, this, this).addApi(LocationServices.API).build();

        createView();

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        mToolbarView.bringToFront();
    }

    private void createView(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        coordinateLayout = (CoordinatorLayout) findViewById(R.id.coordinate);
        mImageView = (ImageView)findViewById(R.id.image);
        mToolbarView = (Toolbar)findViewById(R.id.toolbar);
        mToolbarView.setBackgroundColor(ScrollUtils.getColorWithAlpha(0, getResources().getColor(R.color.colorPrimary)));
        mToolbarView.setTitle(movie.getTitle());
        mToolbarView.setTitleTextColor(ScrollUtils.getColorWithAlpha(0, getResources().getColor(R.color.white)));

        screenShot1 = (ImageView) findViewById(R.id.screenshot1);
        screenShot2 = (ImageView) findViewById(R.id.screenshot2);
        screenShot3 = (ImageView) findViewById(R.id.screenshot3);
        screenShot4 = (ImageView) findViewById(R.id.screenshot4);
        screenShot5 = (ImageView) findViewById(R.id.screenshot5);

        videoTitle1 = (TextView) findViewById(R.id.videoTitle1);
        videoTitle2 = (TextView) findViewById(R.id.videoTitle2);
        videoTitle3 = (TextView) findViewById(R.id.videoTitle3);
        videoTitle4 = (TextView) findViewById(R.id.videoTitle4);
        videoTitle5 = (TextView) findViewById(R.id.videoTitle5);

        creditWrapper = (LinearLayout) findViewById(R.id.creditWrapper);
        similarWrapper = (LinearLayout) findViewById(R.id.similarWrapper);
        channelWrapper = (LinearLayout) findViewById(R.id.channel);
        channelWrapperRelative = (RelativeLayout) findViewById(R.id.channelWrapper);
        channelTag = (TextView) findViewById(R.id.channelTag);
        channelViewAll = (TextView) findViewById(R.id.channelViewAll);
        showtimeViewAll = (TextView) findViewById(R.id.showtimeViewAll);
        fab = (FloatingActionButton) findViewById(R.id.fab);

        for(Result children : AppController.getInstance().getMovies()){
            if(children.getId().intValue() == movie.getId().intValue()){
                fab.setVisibility(View.GONE);
                movieAdded = true;
                break;
            }
        }


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar snackbar = Snackbar.make(coordinateLayout, "Movie successfully added", Snackbar.LENGTH_LONG);
                snackbar.show();
                fab.setVisibility(View.GONE);
                AppController.getInstance().addMovie(movie);
                movieAdded = true;
                return;
            }
        });

        poster = (ImageView) findViewById(R.id.poster);
        title = (TextView) findViewById(R.id.title);
        rate = (TextView) findViewById(R.id.rate);
        releaseDate = (TextView) findViewById(R.id.releaseDate);
        lan = (TextView) findViewById(R.id.lan);
        description = (TextView) findViewById(R.id.description);

        channelLogo = (ImageView) findViewById(R.id.channelLogo);
        channelTextWrapper = (LinearLayout) findViewById(R.id.channelTextWrapper);

        mToolbarView.setNavigationIcon(R.drawable.back);
        mScrollView = (ObservableScrollView) findViewById(R.id.scroll);
        mScrollView.setScrollViewCallbacks(this);

        mParallaxImageHeight = getResources().getDimensionPixelSize(R.dimen.headerImageHeight);

        loadData();
        getVideos();
        getCredit();
        getSimilar();
        getChannels();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    PERMISSION_ACCESS_COARSE_LOCATION);
        }
    }

    private void loadData(){
        Picasso.with(this).load("https://image.tmdb.org/t/p/w780"+movie.getBackdropPath()).into(mImageView);
        Picasso.with(this).load("https://image.tmdb.org/t/p/w300"+movie.getPosterPath()).into(poster);
        title.setText(Html.fromHtml(movie.getTitle()));
        description.setText(Html.fromHtml("Description: " + "<font color='#2d2d2d'>" + movie.getOverview() + "</font>"));
        rate.setText(Html.fromHtml("Rated " + "<font color='#2d2d2d'>" + String.format("%.2f", movie.getVoteAverage()).toString() + "</font>" + " by " + "<font color='#2d2d2d'>" + String.format("%d", movie.getVoteCount()).toString() + "</font>" + " people"));
        releaseDate.setText(Html.fromHtml("Release date: " + "<font color='#2d2d2d'>" + movie.getReleaseDate() + "</font>"));
        lan.setText(Html.fromHtml("Language: " + "<font color='#2d2d2d'>" + movie.getOriginalLanguage() + "</font>"));

    }

    private void getVideos(){
        MoviesGateway moviesGateway = RestClient.getMoviesGateway();
        moviesGateway.getVideos(movie.getTitle(), new Callback<ArrayList<Video>>() {
            @Override
            public void success(ArrayList<Video> videos, Response response) {
                loadVideos(videos);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e(TAG, error.toString());
            }
        });
    }

    private void getCredit(){
        MoviesGateway moviesGateway = RestClient.getMoviesGateway();
        moviesGateway.getCredit(movie.getId().toString(), new Callback<Credit>() {
            @Override
            public void success(Credit credit, Response response) {
                for (Cast cast : credit.getCast()) {
                    final CastCard castCard = new CastCard(MovieDetailActivity.this);
                    castCard.loadData(cast);
                    creditWrapper.addView(castCard);
                    castCard.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Bundle bundle = new Bundle();
                            bundle.putString(CAST, new Gson().toJson(castCard.getData()));
                            FragmentManager fm = getSupportFragmentManager();

                            FragmentTransaction ft = fm.beginTransaction();
                            CastDetailFragment castDetailFragment = new CastDetailFragment();
                            castDetailFragment.setArguments(bundle);
                            ft.add(castDetailFragment, null);
                            ft.commit();
                        }
                    });
                }
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

    private void getSimilar(){
        MoviesGateway moviesGateway = RestClient.getMoviesGateway();
        moviesGateway.getSimilar(movie.getId().toString(), new Callback<Movies>() {
            @Override
            public void success(Movies movies, Response response) {
                for (final Result movie : movies.getResults()) {
                    SimilarCard similarCard = new SimilarCard(MovieDetailActivity.this);
                    similarCard.loadData(movie);
                    similarWrapper.addView(similarCard);

                    similarCard.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            MovieDetailActivity.launchActivity(MovieDetailActivity.this, movie);
                        }
                    });
                }
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

    private void getChannels(){
        MoviesGateway moviesGateway = RestClient.getMoviesGateway();
        moviesGateway.getAvilableOn(movie.getId().toString(), new Callback<ArrayList<Channel>>() {
            @Override
            public void success(final ArrayList<Channel> channels, Response response) {
                if(channels == null || channels.size() == 0) return;
                channelWrapper.setVisibility(View.VISIBLE);
                channelWrapperRelative.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ViewUtil.intentChooser(MovieDetailActivity.this, new Intent(Intent.ACTION_VIEW, Uri.parse(channels.get(0).getLink())));
                    }
                });
                Resources resources = getResources();
                final int resourceId = resources.getIdentifier(channels.get(0).getSource(), "drawable",
                        getPackageName());
                Picasso.with(MovieDetailActivity.this).load(resourceId).into(channelLogo);

                for(Format format : channels.get(0).getFormats()) {
                    TextView tv = new TextView(MovieDetailActivity.this);
                    tv.setPadding(10, 5, 10, 5);
                    tv.setTextSize(10);
                    tv.setText(Html.fromHtml(format.getPrice()+"$<br/><font color='#8e8e8e'>&nbsp;"+format.getFormat()+"</font><br><font color='#8e8e8e'>"+format.getType()+"</font>"));
                    tv.setTextColor(getResources().getColor(R.color.steel));
                    channelTextWrapper.addView(tv);
                }

                channelTag.setText(channels.get(0).getDisplayName());

                channelViewAll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PurchaseChannelActivity.launchActivity(MovieDetailActivity.this, channels);
                    }
                });

                showtimeViewAll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                            
                    }
                });
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

    private void getShowtime(double lat, double longi){

        MoviesGateway moviesGateway = RestClient.getMoviesGateway();
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(lat, longi, 1);
            if(addresses.size() == 0) return;
            moviesGateway.getShowtime(movie.getTitle().replaceAll(" ", "+"), addresses.get(0).getPostalCode(), "0", new Callback<Showtime>() {
                @Override
                public void success(Showtime showtime, Response response) {
                    Log.i("showtime", Integer.toString(showtime.getMovies().size()));
                    if(showtime.getMovies().size() == 0) return;
                    showtimeView = (LinearLayout) findViewById(R.id.showtime);
                    showtimeView.setVisibility(View.VISIBLE);
                    dateView = (TextView) findViewById(R.id.date);
                    scheduleView = (TextView) findViewById(R.id.schedule);
                    cinemaView = (TextView) findViewById(R.id.cinema);

                    if(showtime.getMovies().get(0).getTitle() != null) {
                        dateView.setText(showtime.getMovies().get(0).getTitle() + " " + ViewUtil.getDate());
                    }else{
                        dateView.setText(ViewUtil.getDate());
                    }
                    if(showtime.getMovies().get(0).getTheaters().size() == 0) return;
                    cinemaView.setText(Html.fromHtml(showtime.getMovies().get(0).getTheaters().get(0).getTheater() + "<br/><font color='#8e8e8e'>" + showtime.getMovies().get(0).getTheaters().get(0).getAddress() + "</font>"));
                    String schedule = "";
                    for(Schedule time : showtime.getMovies().get(0).getTheaters().get(0).getSchedule()){
                        schedule = schedule + time.getTime() + ", ";
                    }
                    scheduleView.setText(schedule);
                }

                @Override
                public void failure(RetrofitError error) {

                }
            });
        }catch (IOException e){
            Log.e(TAG, e.toString());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_ACCESS_COARSE_LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // All good!
                } else {
                    Toast.makeText(this, "Without your location wen can't find any showtime", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

    private void loadVideos(final ArrayList<Video> vidoes){
        if(vidoes.size() > 1 && vidoes.get(0) != null){
            Picasso.with(this).load("https://i.ytimg.com/vi/"+vidoes.get(0).getId()+"/mqdefault.jpg").into(screenShot1);
            videoTitle1.setText(vidoes.get(0).getTitle());
        }
        if(vidoes.size() > 2 && vidoes.get(1) != null){
            Picasso.with(this).load("https://i.ytimg.com/vi/"+vidoes.get(1).getId()+"/mqdefault.jpg").into(screenShot2);
            videoTitle2.setText(vidoes.get(1).getTitle());
        }
        if(vidoes.size() > 3 && vidoes.get(2) != null){
            Picasso.with(this).load("https://i.ytimg.com/vi/"+vidoes.get(2).getId()+"/mqdefault.jpg").into(screenShot3);
            videoTitle3.setText(vidoes.get(2).getTitle());
        }
        if(vidoes.size() > 3 && vidoes.get(3) != null){
            Picasso.with(this).load("https://i.ytimg.com/vi/"+vidoes.get(3).getId()+"/mqdefault.jpg").into(screenShot4);
            videoTitle4.setText(vidoes.get(3).getTitle());
        }
        if(vidoes.size() > 4 && vidoes.get(4) != null){
            Picasso.with(this).load("https://i.ytimg.com/vi/"+vidoes.get(4).getId()+"/mqdefault.jpg").into(screenShot5);
            videoTitle5.setText(vidoes.get(4).getTitle());
        }

        //TODO: change to hori listview

        screenShot1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewUtil.intentChooser(MovieDetailActivity.this, new Intent(Intent.ACTION_VIEW, Uri.parse(vidoes.get(0).getLink())));
            }
        });

        screenShot2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewUtil.intentChooser(MovieDetailActivity.this, new Intent(Intent.ACTION_VIEW, Uri.parse(vidoes.get(1).getLink())));
            }
        });

        screenShot3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewUtil.intentChooser(MovieDetailActivity.this, new Intent(Intent.ACTION_VIEW, Uri.parse(vidoes.get(2).getLink())));
            }
        });

        screenShot4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewUtil.intentChooser(MovieDetailActivity.this, new Intent(Intent.ACTION_VIEW, Uri.parse(vidoes.get(3).getLink())));
            }
        });

        screenShot5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewUtil.intentChooser(MovieDetailActivity.this, new Intent(Intent.ACTION_VIEW, Uri.parse(vidoes.get(4).getLink())));
            }
        });

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        onScrollChanged(mScrollView.getCurrentScrollY(), false, false);
    }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
        int baseColor = getResources().getColor(R.color.colorPrimary);
        float alpha = Math.min(1, (float) scrollY / mParallaxImageHeight);
        if(!movieAdded) {
            if (alpha >= 1) {
                fab.setVisibility(View.GONE);
            } else {
                if (fab.getVisibility() == View.GONE) {
                    fab.setVisibility(View.VISIBLE);
                }
            }

            fab.setAlpha((1 - alpha));
        }
        mToolbarView.setBackgroundColor(ScrollUtils.getColorWithAlpha(alpha, baseColor));
        mToolbarView.setTitleTextColor(ScrollUtils.getColorWithAlpha(alpha, getResources().getColor(R.color.white)));
        ViewHelper.setTranslationY(mImageView, scrollY / 2);
    }

    @Override
    public void onDownMotionEvent() {

    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.movie_detail_bar, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        return true;
    }

    @Override
    public void onConnected(Bundle bundle) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);

            double lat = lastLocation.getLatitude(), lon = lastLocation.getLongitude();
            getShowtime(lat, lon);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (googleApiClient != null) {
            googleApiClient.connect();
        }
    }

    @Override
    protected void onStop() {
        googleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
