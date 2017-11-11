package com.dpckou.agoston.timetale.weekday;

import android.arch.persistence.room.Room;
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
import com.dpckou.agoston.timetale.TimetaleApplication;
import com.dpckou.agoston.timetale.converter.EventsOfDayConverter;
import com.dpckou.agoston.timetale.persistence.Event;
import com.dpckou.agoston.timetale.persistence.EventDao;
import com.dpckou.agoston.timetale.persistence.TimetaleDatabase;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by kissa on 2017. 11. 01..
 */

public class WeekDayFragment extends Fragment {

    public final static String ARG_DATE = "date";
    private static final SimpleDateFormat format = new SimpleDateFormat("EEEE, MMM d, yyyy");

    private EventsOfDayConverter converter;
    private Date date;
    private RecyclerView mRecyclerView;
    HourAdapter mAdapter;

    public static WeekDayFragment create(long date) {
        WeekDayFragment wdf = new WeekDayFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_DATE, date);
        wdf.setArguments(args);
        return wdf;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        converter = new EventsOfDayConverter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_weekday, container, false);

        date = new Date(getArguments().getLong(ARG_DATE));

        ((TextView)(rootView.findViewById(R.id.dayanddate))).setText(format.format(date));


        mRecyclerView = rootView.findViewById(R.id.hourlist);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(rootView.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        long dayStart = getDayStart();
        long dayEnd = getDayEnd();

        Event[] events = TimetaleApplication.get().getDB().getDaoInstance().eventsOnDay(dayStart, dayEnd);
        Hour[] hours = converter.convert(events, dayStart, dayEnd);

        mAdapter = new HourAdapter(hours);
        mRecyclerView.setAdapter(mAdapter);

        return rootView;
    }

    private long getDayStart(){

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        return c.getTimeInMillis();
    }

    private long getDayEnd(){

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        return c.getTimeInMillis();
    }

}
