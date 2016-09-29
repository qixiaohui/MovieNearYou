package com.movienearyou.xiaohui.movienearyou.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.movienearyou.xiaohui.movienearyou.Activity.MovieDetailActivity;
import com.movienearyou.xiaohui.movienearyou.R;
import com.movienearyou.xiaohui.movienearyou.model.movies.Movies;
import com.movienearyou.xiaohui.movienearyou.model.movies.Result;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by TQi on 7/23/16.
 */
public class MovieGridAdapter extends RecyclerView.Adapter<MovieGridAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Result> movies;
    private Activity activity;

    public MovieGridAdapter(Context context, ArrayList<Result> movies, Activity activity){
        this.context = context;
        this.movies = movies;
        this.activity = activity;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView poster;
        public TextView movieTitle;

        public ViewHolder(View view){
            super(view);
            poster = (ImageView) view.findViewById(R.id.movieCard);
            movieTitle = (TextView) view.findViewById(R.id.movieTitle);
        }
    }

    /**
     * switch between category data
     * @param movies
     */
    public void swapCategory(ArrayList<Result> movies){
        this.movies = movies;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_card, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.movieTitle.setText(movies.get(position).getTitle());
        Picasso.with(context).load(context.getResources().getString(R.string.poster_base_url)+movies.get(position).getPosterPath()).into(holder.poster);
        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(activity, (View)holder.poster, "poster");
        holder.poster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MovieDetailActivity.launchActivity(activity, movies.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }
}
