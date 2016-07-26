package com.movienearyou.xiaohui.movienearyou.Views;

import android.content.Context;
import android.text.Html;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.movienearyou.xiaohui.movienearyou.R;
import com.movienearyou.xiaohui.movienearyou.model.credit.Cast;
import com.movienearyou.xiaohui.movienearyou.model.movies.Result;
import com.squareup.picasso.Picasso;

/**
 * Created by TQi on 7/25/16.
 */
public class SimilarCard extends LinearLayout implements View.OnClickListener{
    private ImageView similarPoster;
    private TextView similarTitle;
    private Context context;

    public SimilarCard(Context context){
        this(context, null);
        this.context = context;
    }

    public SimilarCard(Context context, AttributeSet attrs){
        this(context, attrs ,0);
    }

    public SimilarCard(Context context, AttributeSet attrs, int defStyle){
        super(context, attrs, defStyle);
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mInflater.inflate(R.layout.similar_card, this, true);
    }

    public void loadData(Result movie){
        similarPoster = (ImageView) this.findViewById(R.id.similarPoster);
        similarTitle = (TextView) this.findViewById(R.id.similarTitle);
        Picasso.with(context).load("https://image.tmdb.org/t/p/w92"+movie.getPosterPath()).into(similarPoster);
        similarTitle.setText(movie.getTitle());
    }

    @Override
    public void onClick(View v) {

    }
}
