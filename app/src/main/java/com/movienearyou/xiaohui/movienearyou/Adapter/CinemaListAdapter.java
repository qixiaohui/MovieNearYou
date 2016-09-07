package com.movienearyou.xiaohui.movienearyou.Adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.movienearyou.xiaohui.movienearyou.Activity.CinemaScheduleActivity;
import com.movienearyou.xiaohui.movienearyou.R;
import com.movienearyou.xiaohui.movienearyou.model.showtime.Showtime;

/**
 * Created by qixiaohui on 9/5/16.
 */
public class CinemaListAdapter extends RecyclerView.Adapter<CinemaListAdapter.ViewHolder> {
    private Showtime showtime;
    private Activity fromActivity;

    public CinemaListAdapter(Showtime showtime, Activity fromActivity){
        this.showtime = showtime;
        this.fromActivity = fromActivity;
    }

    public void updateShowtime(Showtime showtime){
        this.showtime = showtime;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cinema_row, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.cinemaName.setText(showtime.getMovies().get(0).getTheaters().get(position).getTheater());
        holder.cinemaAddress.setText(showtime.getMovies().get(0).getTheaters().get(position).getAddress());
        holder.cinemaRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CinemaScheduleActivity.launchActivity(fromActivity, showtime.getMovies().get(0).getTheaters().get(position).getSchedule());
            }
        });
    }

    @Override
    public int getItemCount() {
        return showtime.getMovies().get(0).getTheaters().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        RelativeLayout cinemaRow;
        TextView cinemaName;
        TextView cinemaAddress;
        public ViewHolder(View view){
            super(view);
            cinemaName = (TextView) view.findViewById(R.id.cinemaName);
            cinemaAddress = (TextView) view.findViewById(R.id.cinemaAddress);
            cinemaRow = (RelativeLayout) view.findViewById(R.id.cinemaRow);
        }
    }
}
