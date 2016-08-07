package com.movienearyou.xiaohui.movienearyou.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.movienearyou.xiaohui.movienearyou.R;
import com.movienearyou.xiaohui.movienearyou.Util.ViewUtil;
import com.movienearyou.xiaohui.movienearyou.model.channels.Channel;
import com.movienearyou.xiaohui.movienearyou.model.channels.Format;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by qixiaohui on 8/4/16.
 */
public class ChannelListAdapter extends RecyclerView.Adapter<ChannelListAdapter.ViewHolder>{
    public static final String Tag = "ChannelListAdapter";
    private Context context;
    private ArrayList<Channel> channels;

    public ChannelListAdapter(Context context, ArrayList<Channel> channels) {
        this.context = context;
        this.channels = channels;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.channel_row, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final int resourceId = context.getResources().getIdentifier(channels.get(position).getSource(), "drawable",
                context.getPackageName());
        try {
            Picasso.with(context).load(resourceId).into(holder.logo);
        }catch (IllegalArgumentException e){
            Log.e(Tag, e.toString());
        }

        if(holder.wrapper.getChildCount() == 0) {
            for (Format format : channels.get(position).getFormats()) {
                TextView tv = new TextView(context);
                tv.setPadding(10, 5, 10, 5);
                tv.setTextSize(10);
                tv.setText(Html.fromHtml(format.getPrice() + "$<br/><font color='#8e8e8e'>&nbsp;" + format.getFormat() + "</font><br><font color='#8e8e8e'>" + format.getType() + "</font>"));
                tv.setTextColor(context.getResources().getColor(R.color.steel));
                holder.wrapper.addView(tv);
            }
        }

        holder.channelTag.setText(channels.get(position).getDisplayName());

        holder.channelWrapper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //when user click on open corresponding app
                ViewUtil.intentChooser(context, new Intent(Intent.ACTION_VIEW, Uri.parse(channels.get(position).getLink())));
            }
        });
    }

    @Override
    public int getItemCount() {
        return channels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView logo;
        public LinearLayout wrapper;
        public RelativeLayout channelWrapper;
        public TextView channelTag;
        public ViewHolder(View view){
            super(view);
            logo = (ImageView) view.findViewById(R.id.channelLogo);
            channelWrapper = (RelativeLayout) view.findViewById(R.id.channelWrapper);
            wrapper = (LinearLayout) view.findViewById(R.id.channelTextWrapper);
            channelTag = (TextView) view.findViewById(R.id.channelTag);
        }
    }
}
