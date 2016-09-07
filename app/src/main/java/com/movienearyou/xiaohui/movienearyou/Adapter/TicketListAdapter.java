package com.movienearyou.xiaohui.movienearyou.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.movienearyou.xiaohui.movienearyou.R;
import com.movienearyou.xiaohui.movienearyou.Util.ViewUtil;
import com.movienearyou.xiaohui.movienearyou.model.showtime.Schedule;

import java.util.List;

/**
 * Created by qixiaohui on 9/6/16.
 */
public class TicketListAdapter extends RecyclerView.Adapter<TicketListAdapter.ViewHolder>{
    private List<Schedule> schedules;
    private Activity fromActivity;

    public TicketListAdapter(List<Schedule> schedules, Activity fromActivity){
        this.schedules = schedules;
        this.fromActivity = fromActivity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ticket_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.time.setText(schedules.get(position).getTime());
        if(schedules.get(position).getUrl() == null){
            holder.time.setTextColor(fromActivity.getResources().getColor(R.color.cement));
            return;
        }
        holder.row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        //when user click on open corresponding app
                        ViewUtil.intentChooser(fromActivity, new Intent(Intent.ACTION_VIEW,Uri.parse(fromActivity.getResources().getString(R.string.googleTicket)+schedules.get(position).getUrl())));
            }
        });
    }

    @Override
    public int getItemCount() {
        return schedules.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView time;
        public RelativeLayout row;
        public ViewHolder(View view){
            super(view);
            row = (RelativeLayout) view.findViewById(R.id.row);
            time = (TextView) view.findViewById(R.id.time);
        }
    }
}
