package com.movienearyou.xiaohui.movienearyou.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.movienearyou.xiaohui.movienearyou.Activity.NewsContentActivity;
import com.movienearyou.xiaohui.movienearyou.R;
import com.movienearyou.xiaohui.movienearyou.model.news.News;
import com.squareup.picasso.Picasso;

/**
 * Created by qixiaohui on 9/27/16.
 */
public class LatestNewsAdapter extends RecyclerView.Adapter<LatestNewsAdapter.ViewHolder>{
    private News news;
    private Context context;
    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView img;
        public TextView title;
        public CardView cardView;
        public TextView author;
        public ViewHolder(View view) {
            super(view);
            img = (ImageView) view.findViewById(R.id.newsCardImg);
            title = (TextView) view.findViewById(R.id.newTitle);
            cardView = (CardView) view.findViewById(R.id.card_view);
            author = (TextView) view.findViewById(R.id.author);
        }
    }

    public LatestNewsAdapter(News news, Context context) {
        this.news = news;
        this.context = context;
    }

    @Override
    public LatestNewsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_row, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final LatestNewsAdapter.ViewHolder holder, final int position) {
        Picasso.with(context).load(news.getList().get(position).getImg()).into(holder.img);
        holder.title.setText(news.getList().get(position).getTitle());
        holder.author.setText(news.getList().get(position).getAuthor());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context, (View) holder.cardView, "newsCardImg");
                NewsContentActivity.launchActivity((Activity) context, news.getList().get(position), options);
            }
        });
    }

    @Override
    public int getItemCount() {
        return news.getList().size();
    }

    public void addMore(News news) {
        this.news.getList().addAll(news.getList());
        notifyDataSetChanged();
    }
}
