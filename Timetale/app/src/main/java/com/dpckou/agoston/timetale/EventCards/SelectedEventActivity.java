package com.dpckou.agoston.timetale.EventCards;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.dpckou.agoston.timetale.DateTimeModels.DateTime;
import com.dpckou.agoston.timetale.EventActivity;
import com.dpckou.agoston.timetale.R;
import com.dpckou.agoston.timetale.TextFormatter.TextFormatter;
import com.dpckou.agoston.timetale.TimetaleApplication;
import com.dpckou.agoston.timetale.persistence.Event;
import com.dpckou.agoston.timetale.weekday.EventBundle;
import com.dpckou.agoston.timetale.weekday.WeekDayPagerActivity;

import java.util.Date;

public class SelectedEventActivity extends AppCompatActivity {

    public static final char NAMES_SEPARATOR = ',';

    private Event myEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_event);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        TextView title = findViewById(R.id.title);
        TextView from = findViewById(R.id.from);
        TextView to = findViewById(R.id.to);
        TextView description = findViewById(R.id.description);
        ListView selectedFriends = findViewById(R.id.friends);
        TextView location = findViewById(R.id.location);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), EventActivity.class);
                /*
                myIntent.putExtra(SelectedEventActivity.TITLE_TAG, myEvent.getEventName());
                myIntent.putExtra(SelectedEventActivity.LOCATION_TAG, myEvent.getEventLocation());
                myIntent.putExtra(SelectedEventActivity.DESCRIPTION_TAG, myEvent.getEventDescription());
                myIntent.putStringArrayListExtra(SelectedEventActivity.FRIENDS_TAG, myEvent.getFriends());
                myIntent.putExtra(SelectedEventActivity.FROM_TAG, myEvent.getEventStart());
                myIntent.putExtra(SelectedEventActivity.TO_TAG, myEvent.getEventEnd());
                */
                myIntent.putExtra(EventBundle.NAME, new EventBundle(myEvent));
                startActivity(myIntent);
            }
        });

        Intent myIntent = getIntent();
        myEvent = ((EventBundle)myIntent.getParcelableExtra(EventBundle.NAME)).getEvent();
        if(myEvent == null){
            Log.e("PARCEL ERROR", "NO parcelable was found under the name given.");
            return;
        }
        //getting the shit from the intent and loading the layout's elements with data.
        //TODO Less copy paste?
        try{
            //myEvent.setEventName(myIntent.getStringExtra(TITLE_TAG));
            title.setText(TextFormatter.multiFormat(myEvent.getEventName(),
                    new int[] {1,2,3,4}));
        }catch(Exception ex){
            Log.i("TITLE ERROR", ex.getMessage());
        }
        try{
            //myEvent.setEventLocation(myIntent.getStringExtra(LOCATION_TAG));
            location.setText(myEvent.getEventLocation());
        }catch(Exception ex){
            Log.i("LOCATION ERROR", ex.getMessage());
        }
        try{
            //myEvent.setEventDescription(myIntent.getStringExtra(DESCRIPTION_TAG));
            description.setText(TextFormatter.multiFormat(myEvent.getEventDescription(),
                    new int[] {1,2,3,4}));
        }catch(Exception ex){
            Log.i("DESCRIPTION ERROR", ex.getMessage());
        }
        try{
            //myEvent.setEventFriends(myIntent.getStringArrayListExtra(FRIENDS_TAG));
            selectedFriends.setAdapter(new ArrayAdapter<String>(this,
                    R.layout.array_adapter_item, myEvent.getFriends()));
        }catch(Exception ex){
            Log.i("FRIENDS ERROR", ex.getMessage());
        }
        try{
            //myEvent.setEventStart(
                   //myIntent.getLongExtra(FROM_TAG, 0)
            //);
            String[] res = DateTime.parseLong(myEvent.getEventStart());
            from.setText(res[0] + " - "  + res[1]);
        }catch(Exception ex){
            Log.i("FROM ERROR", ex.getMessage());
        }
        try{
            //myEvent.setEventEnd(myIntent.getLongExtra(TO_TAG, 0));
            String[] res = DateTime.parseLong(myEvent.getEventEnd());
            to.setText(res[0] + " - "  + res[1]);
        }catch(Exception ex){
            Log.i("TO ERROR", ex.getMessage());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_event_card, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete:

                TimetaleApplication.get().getDB().getDaoInstance().deleteEvent(myEvent);
                Intent startIntent = new Intent(this, WeekDayPagerActivity.class);
                startActivity(startIntent);

                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
