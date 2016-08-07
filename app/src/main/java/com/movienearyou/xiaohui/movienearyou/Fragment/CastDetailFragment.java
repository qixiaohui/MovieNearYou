package com.movienearyou.xiaohui.movienearyou.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.ksoichiro.android.observablescrollview.ScrollUtils;
import com.google.gson.Gson;
import com.movienearyou.xiaohui.movienearyou.Activity.MovieDetailActivity;
import com.movienearyou.xiaohui.movienearyou.Network.MoviesGateway;
import com.movienearyou.xiaohui.movienearyou.Network.RestClient;
import com.movienearyou.xiaohui.movienearyou.R;
import com.movienearyou.xiaohui.movienearyou.Util.ViewUtil;
import com.movienearyou.xiaohui.movienearyou.Views.CastCard;
import com.movienearyou.xiaohui.movienearyou.model.credit.Cast;
import com.movienearyou.xiaohui.movienearyou.model.credit.Credit;
import com.squareup.picasso.Picasso;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by qixiaohui on 8/5/16.
 */
public class CastDetailFragment extends DialogFragment {
    private ImageView castPic;
    private TextView birthday;
    private TextView birthPlace;
    private TextView description;
    private Toolbar mToolbarView;
    private Button imdb;

    private View view;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.cast_detail, container, false);
        castPic = (ImageView) view.findViewById(R.id.castPic);
        birthday = (TextView) view.findViewById(R.id.birthday);
        birthPlace = (TextView) view.findViewById(R.id.placeBirth);
        description = (TextView) view.findViewById(R.id.description);
        imdb = (Button) view.findViewById(R.id.imdb);

        mToolbarView = (Toolbar)view.findViewById(R.id.toolbar);
        mToolbarView.setBackgroundColor( getResources().getColor(R.color.colorPrimary));
        mToolbarView.setTitleTextColor(getResources().getColor(R.color.white));

        mToolbarView.setNavigationIcon(R.drawable.back);

        mToolbarView.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CastDetailFragment.this.dismiss();
            }
        });

        birthday.setText(Html.fromHtml("<font color='#8e8e8e'>Birthday: </font>"));
        birthPlace.setText(Html.fromHtml("<font color='#8e8e8e'>Birth place: </font>"));
        description.setText(Html.fromHtml("<font color='#8e8e8e'>Bio: </font>"));

        Cast cast = new Gson().fromJson((String) getArguments().get(MovieDetailActivity.CAST), Cast.class);
        mToolbarView.setTitle(cast.getName());
        getDetail(cast);
        if(cast.getProfilePath() != null) {
            Picasso.with(getContext()).load("https://image.tmdb.org/t/p/w300_and_h300_bestv2/" + cast.getProfilePath()).into(castPic);
        }else{
            Picasso.with(getContext()).load(R.drawable.avatar).into(castPic);
        }

        return view;
    }


    private void getDetail(Cast cast){
        MoviesGateway moviesGateway = RestClient.getMoviesGateway();
        moviesGateway.getPerson(Integer.toString(cast.getId()), new Callback<com.movienearyou.xiaohui.movienearyou.model.cast.Cast>() {
            @Override
            public void success(final com.movienearyou.xiaohui.movienearyou.model.cast.Cast cast, Response response) {
                birthday.setText(Html.fromHtml("<font color='#8e8e8e'>Birthday: </font>"+cast.getBirthday()));
                birthPlace.setText(Html.fromHtml("<font color='#8e8e8e'>Birth place: </font>"+cast.getPlaceOfBirth()));
                description.setText(Html.fromHtml("<font color='#8e8e8e'>Bio: </font>"+cast.getBiography()));

                imdb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ViewUtil.intentChooser(getContext(), new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.imdb.com/name/"+cast.getImdbId())));
                    }
                });
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

}
