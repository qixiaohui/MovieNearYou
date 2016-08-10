package com.movienearyou.xiaohui.movienearyou.Adapter;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.movienearyou.xiaohui.movienearyou.Activity.MovieDetailActivity;
import com.movienearyou.xiaohui.movienearyou.Application.AppController;
import com.movienearyou.xiaohui.movienearyou.R;
import com.movienearyou.xiaohui.movienearyou.model.movies.Result;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by qixiaohui on 8/9/16.
 */
public class MyCollectionAdapter extends RecyclerView.Adapter<MyCollectionAdapter.ViewHolder>{
    private ArrayList<Result> movies = new ArrayList<>();
    private Context context;
    private Activity activity;

    public MyCollectionAdapter(Context context, ArrayList<Result> movies, Activity activity){
        this.movies = movies;
        this.context = context;
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.collection_card, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Picasso.with(context).load(context.getResources().getString(R.string.poster_base_url)+movies.get(position).getPosterPath()).into(holder.poster);
        holder.title.setText(movies.get(position).getTitle());
        holder.rate.setText(Html.fromHtml("Rated "+"<font color='#2d2d2d'>"+movies.get(position).getVoteAverage()+"</font> by <font color='#2d2d2d'>"+movies.get(position).getPopularity()+"</font>  people"));
        holder.releaseDate.setText(Html.fromHtml("Release date: <font color='#2d2d2d'>"+movies.get(position).getReleaseDate()+"</font>"));
        holder.description.setText(Html.fromHtml("Description: <font color='#2d2d2d'>"+movies.get(position).getOverview()+"</font>"));
        holder.poster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MovieDetailActivity.launchActivity(activity, movies.get(position));
            }
        });
        holder.infoWrapper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MovieDetailActivity.launchActivity(activity, movies.get(position));
            }
        });
        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppController.getInstance().removeMovie(movies.get(position).getId());
                movies.remove(position);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView poster;
        public TextView title;
        public TextView rate;
        public TextView releaseDate;
        public TextView description;
        public View view;
        public RelativeLayout infoWrapper;
        public ImageView deleteBtn;

        public ViewHolder(View view){
            super(view);
            this.view = view;
            poster = (ImageView) view.findViewById(R.id.collectionPoster);
            title = (TextView) view.findViewById(R.id.title);
            rate = (TextView) view.findViewById(R.id.rate);
            releaseDate = (TextView) view.findViewById(R.id.releaseDate);
            description = (TextView) view.findViewById(R.id.description);
            infoWrapper = (RelativeLayout) view.findViewById(R.id.collectionWrapper);
            deleteBtn = (ImageView) view.findViewById(R.id.deleteBtn);
        }
    }
}
