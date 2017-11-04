package com.dpckou.agoston.timetale.weekday;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dpckou.agoston.timetale.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by kissa on 2017. 11. 01..
 */

public class WeekDayFragment extends Fragment {

    public final static String ARG_DAY = "day";

    private String day;
    private Date date;
    private static final SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-dd");
    private RecyclerView mRecyclerView;
    HourAdapter mAdapter;

    public static WeekDayFragment create(String day) {
        WeekDayFragment wdf = new WeekDayFragment();
        Bundle args = new Bundle();
        args.putString(ARG_DAY, day);
        wdf.setArguments(args);
        return wdf;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        day=getArguments().getString(ARG_DAY);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_weekday, container, false);

        date = new Date(getArguments().getLong("date"));

        ((TextView)(rootView.findViewById(R.id.dayanddate))).setText(day + ", " + format.format(date));


        mRecyclerView = rootView.findViewById(R.id.hourlist);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(rootView.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        Log.d(this.getClass().getName(), "onCreateView()");

        Hour[] hours = new Hour[24];
        for(int i = 0; i < hours.length; i++) {
            hours[i] = new Hour(i);
        }

        mAdapter = new HourAdapter(hours);
        mRecyclerView.setAdapter(mAdapter);

        return rootView;
    }

}
