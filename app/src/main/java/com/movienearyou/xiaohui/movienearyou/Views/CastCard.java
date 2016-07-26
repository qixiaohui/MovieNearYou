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
import com.movienearyou.xiaohui.movienearyou.model.credit.Crew;
import com.squareup.picasso.Picasso;

import java.util.zip.Inflater;

/**
 * Created by TQi on 7/25/16.
 */
public class CastCard extends LinearLayout implements View.OnClickListener {
    private ImageView crewImage;
    private TextView actorName;
    private Context context;

    public CastCard(Context context){
        this(context, null);
        this.context = context;
    }

    public CastCard(Context context, AttributeSet attrs){
        this(context, attrs ,0);
    }

    public CastCard(Context context, AttributeSet attrs, int defStyle){
        super(context, attrs, defStyle);
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mInflater.inflate(R.layout.cast_card, this, true);
    }

    public void loadData(Cast cast){
        crewImage = (ImageView) this.findViewById(R.id.actorImage);
        actorName = (TextView) this.findViewById(R.id.actorName);
        Picasso.with(context).load("https://image.tmdb.org/t/p/w132_and_h132_bestv2/"+cast.getProfilePath()).into(crewImage);
        actorName.setText(Html.fromHtml(cast.getName()+"<br/>as <font color='#8e8e8e'>"+cast.getCharacter()+"</font>"));
    }

    @Override
    public void onClick(View v) {

    }
}
