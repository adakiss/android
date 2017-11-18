package com.dpckou.agoston.timetale.weekday;

import android.app.AlertDialog;
import android.arch.persistence.room.Room;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.Toast;

import com.dpckou.agoston.timetale.EventCards.EventCardActivity;
import com.dpckou.agoston.timetale.EventCards.SelectedEventActivity;
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

        //made this two final.
        final Event[] events = TimetaleApplication.get().getDB().getDaoInstance().eventsOnDay(dayStart, dayEnd);
        final Hour[] hours = converter.convert(events, dayStart, dayEnd);

        RecyclerViewOnClickListener listener = new RecyclerViewOnClickListener() {
            @Override
            public void onClick(final View view, int position) {
                final CharSequence[] eventsInHour = hours[position].isEventPlanned() ? hours[position].getEvent().split(", ") : null;
                if (eventsInHour != null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Events");
                    builder.setItems(eventsInHour, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            for(Event e : events) {
                                if(e.getEventName().equals(eventsInHour[i])) {
                                    //FIXME simpler to pass the event itself instead of its elements? :(
                                    Toast.makeText(getContext(), "Event: " + e.getEventName(), Toast.LENGTH_SHORT).show();
                                    Intent myIntent = new Intent(view.getContext(), SelectedEventActivity.class);
                                    /*
                                    myIntent.putExtra(SelectedEventActivity.TITLE_TAG, e.getEventName());
                                    myIntent.putExtra(SelectedEventActivity.LOCATION_TAG, e.getEventLocation());
                                    myIntent.putExtra(SelectedEventActivity.DESCRIPTION_TAG, e.getEventDescription());
                                    myIntent.putStringArrayListExtra(SelectedEventActivity.FRIENDS_TAG, e.getFriends());
                                    myIntent.putExtra(SelectedEventActivity.FROM_TAG, e.getEventStart());
                                    myIntent.putExtra(SelectedEventActivity.TO_TAG, e.getEventEnd());
                                    */
                                    myIntent.putExtra(EventBundle.NAME, new EventBundle(e));
                                    startActivity(myIntent);
                                }
                            }
                        }
                    });
                    builder.show();
                } else {
                    return;
                }

                /*
                //Toast.makeText(getContext(), "Position " + position, Toast.LENGTH_SHORT).show();
                //TODO make it BUN DEM
                Intent i = new Intent(view.getContext(), SelectedEventActivity.class);
                //i.putExtra(EventCardActivity.TITLE_TAG,hours[position].getEvent());
                startActivity(i);
                */
            }
        };

        mAdapter = new HourAdapter(hours, listener);
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
