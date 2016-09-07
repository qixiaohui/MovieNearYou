package com.movienearyou.xiaohui.movienearyou.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.movienearyou.xiaohui.movienearyou.Adapter.CinemaListAdapter;
import com.movienearyou.xiaohui.movienearyou.Adapter.ShowtimeAdapter;
import com.movienearyou.xiaohui.movienearyou.R;
import com.movienearyou.xiaohui.movienearyou.model.showtime.Showtime;

/**
 * Created by qixiaohui on 9/5/16.
 */
public class ShowtimeListFragment extends Fragment {
    private Showtime showtime;
    private RecyclerView cinemaList;
    private LinearLayoutManager linearLayoutManager;
    private CinemaListAdapter cinemaListAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle bundle = this.getArguments();
        showtime = new Gson().fromJson(bundle.getString(ShowtimeAdapter.SHOWTIME_BUNDLE), Showtime.class);

        View view = inflater.inflate(R.layout.showtime_list_fragment, container, false);
        cinemaList = (RecyclerView) view.findViewById(R.id.cinemaList);
        linearLayoutManager = new LinearLayoutManager(getContext());
        cinemaList.setLayoutManager(linearLayoutManager);
        cinemaList.setHasFixedSize(false);
        cinemaListAdapter = new CinemaListAdapter(showtime, getActivity());
        cinemaList.setAdapter(cinemaListAdapter);
        return view;
    }

    public void updateShowtime(Showtime showtime){
        cinemaListAdapter.updateShowtime(showtime);
        cinemaListAdapter.notifyDataSetChanged();
    }


}
