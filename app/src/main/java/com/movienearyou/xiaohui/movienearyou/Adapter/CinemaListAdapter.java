package com.movienearyou.xiaohui.movienearyou.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.movienearyou.xiaohui.movienearyou.R;
import com.movienearyou.xiaohui.movienearyou.model.showtime.Showtime;

/**
 * Created by qixiaohui on 9/5/16.
 */
public class CinemaListAdapter extends RecyclerView.Adapter<CinemaListAdapter.ViewHolder> {
    private Showtime showtime;

    public CinemaListAdapter(Showtime showtime){
        this.showtime = showtime;
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
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.cinemaName.setText(showtime.getMovies().get(0).getTheaters().get(position).getTheater());
        holder.cinemaAddress.setText(showtime.getMovies().get(0).getTheaters().get(position).getAddress());
    }

    @Override
    public int getItemCount() {
        return showtime.getMovies().get(0).getTheaters().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView cinemaName;
        TextView cinemaAddress;
        public ViewHolder(View view){
            super(view);
            cinemaName = (TextView) view.findViewById(R.id.cinemaName);
            cinemaAddress = (TextView) view.findViewById(R.id.cinemaAddress);
        }
    }
}
