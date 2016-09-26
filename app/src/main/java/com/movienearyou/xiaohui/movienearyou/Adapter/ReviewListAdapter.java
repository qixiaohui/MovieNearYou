package com.movienearyou.xiaohui.movienearyou.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.movienearyou.xiaohui.movienearyou.R;
import com.movienearyou.xiaohui.movienearyou.model.reviews.Reviews;

/**
 * Created by qixiaohui on 9/25/16.
 */
public class ReviewListAdapter extends RecyclerView.Adapter<ReviewListAdapter.ViewHolder>{
    private Reviews reviews;
    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView author;
        public TextView desc;
        public TextView showMore;
        public ViewHolder(View view) {
            super(view);
            author = (TextView) view.findViewById(R.id.reviewAuthor);
            desc = (TextView) view.findViewById(R.id.reviewDes);
            showMore = (TextView) view.findViewById(R.id.showMore);
        }
    }

    public ReviewListAdapter(Reviews reviews) {
        this.reviews = reviews;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_row, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.author.setText("By " + reviews.getResults().get(position).getAuthor());
        holder.desc.setText(reviews.getResults().get(position).getContent());
        holder.showMore.setText("Show More");

        holder.showMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.showMore.getText().equals("Show More")) {
                    holder.showMore.setText("Show Less");
                    holder.desc.setMaxLines(100);
                } else {
                   holder.showMore.setText("Show More");
                    holder.desc.setMaxLines(3);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return reviews.getResults().size();
    }
}
