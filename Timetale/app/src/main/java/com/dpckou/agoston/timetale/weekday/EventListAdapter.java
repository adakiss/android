package com.dpckou.agoston.timetale.weekday;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.dpckou.agoston.timetale.DateTimeModels.DateTime;
import com.dpckou.agoston.timetale.EventActivity;
import com.dpckou.agoston.timetale.EventCards.SelectedEventActivity;
import com.dpckou.agoston.timetale.R;
import com.dpckou.agoston.timetale.TimetaleApplication;
import com.dpckou.agoston.timetale.persistence.Event;

import java.util.Calendar;
import java.util.List;

/**
 * Created by agoston on 2017.12.21..
 */

public class EventListAdapter extends BaseAdapter {

    private List<Event> items;
    public EventListAdapter(List<Event> items){this.items = items;}

    private static long ONE_DAY_IN_MILLIS = 86400000;

    @Override
    public int getCount() {
        return items == null ? 0 : items.size();
    }

    @Override
    public Object getItem(int i) {
        return items == null ? null : items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, final ViewGroup viewGroup) {
        View myView = view;
        if (myView == null) {
            myView = View.inflate(viewGroup.getContext(),
                    R.layout.event_list_item,
                    null);
        }

        TextView eventNameTextView = myView.findViewById(R.id.titleName);
        TextView dateTextView = myView.findViewById(R.id.dates);
        TextView timeTextView = myView.findViewById(R.id.times);
        ImageView warningImageView = myView.findViewById(R.id.warningImageView);
        Switch notifySwitch = myView.findViewById(R.id.toggleNotify);
        final Event myEvent = (Event)getItem(i);

        notifySwitch.setChecked(myEvent.getNotifyMe());

        Calendar c = Calendar.getInstance();
        if((myEvent.getEventStart() - ONE_DAY_IN_MILLIS) < Calendar.getInstance().getTime().getTime()){
            //so if the date is within a day's time.
            //then show to notify icon.
            warningImageView.setImageAlpha(255);
            warningImageView.setImageResource(R.mipmap.ic_warning_sign);
        }else{
            warningImageView.setImageAlpha(0);
        }

        String fromDate = DateTime.parseLong(myEvent.getEventStart())[0];
        String fromTime = DateTime.parseLong(myEvent.getEventStart())[1];

        String toDate = DateTime.parseLong(myEvent.getEventEnd())[0];
        String toTime = DateTime.parseLong(myEvent.getEventEnd())[1];

        if(fromDate.equals(toDate)){
            //they are on the same day
            //then show both start and end time.
            dateTextView.setText(fromDate);
            timeTextView.setText(fromTime + " - " + toTime);
        }else{
            dateTextView.setText(fromDate + " - " + toDate);
            timeTextView.setText(fromTime);
        }

        notifySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                myEvent.setNotifyMe(b);
                if(b){
                    long _delay = myEvent.getEventStart() - Calendar.getInstance().getTimeInMillis();
                    EventActivity.scheduleNotification(myEvent,viewGroup.getContext(),
                            _delay,myEvent.getId());
                }else{
                    try{
                        myEvent.setNotifyMe(false);
                        EventActivity.cancelNotification(viewGroup.getContext(),myEvent.getId());
                    }catch(Exception e){
                        Log.i("Alarm notification", "No such notification to remove.");
                    }
                }
                TimetaleApplication.get().getDB().getDaoInstance().updateEvent(myEvent);
            }
        });

        myView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), SelectedEventActivity.class);
                myIntent.putExtra(EventBundle.NAME, new EventBundle(myEvent));
                viewGroup.getContext().startActivity(myIntent);
            }
        });

        eventNameTextView.setText(myEvent.getEventName());
        return myView;
    }
}
