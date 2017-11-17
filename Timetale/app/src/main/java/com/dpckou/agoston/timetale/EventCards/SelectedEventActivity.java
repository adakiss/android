package com.dpckou.agoston.timetale.EventCards;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.dpckou.agoston.timetale.DateTimeModels.DateTime;
import com.dpckou.agoston.timetale.R;
import com.dpckou.agoston.timetale.TextFormatter.TextFormatter;
import com.dpckou.agoston.timetale.persistence.Event;

import java.util.Date;

public class SelectedEventActivity extends AppCompatActivity {

    public static final String TITLE_TAG = "eventTitle";
    public static final String FROM_TAG = "eventFrom";
    public static final String TO_TAG = "eventTo";
    public static final String LOCATION_TAG = "eventLocation";
    public static final String FRIENDS_TAG = "eventDescription";
    public static final String DESCRIPTION_TAG = "eventNameStrings";

    public static final char NAMES_SEPARATOR = ',';

    private Event myEvent;

    private TextView title;
    private TextView from;
    private TextView to;
    private TextView description;
    private ListView selectedFriends;
    private TextView location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_event);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        title = findViewById(R.id.title);
        from = findViewById(R.id.from);
        to = findViewById(R.id.to);
        description = findViewById(R.id.description);
        selectedFriends = findViewById(R.id.friends);
        location = findViewById(R.id.location);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "TODO: edit the currently selected Event.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Intent myIntent = getIntent();
        myEvent = new Event();
        //getting the shit from the intent and loading the layout's elements with data.
        //TODO Less copy paste?
        try{
            myEvent.setEventName(myIntent.getStringExtra(TITLE_TAG));
            title.setText(TextFormatter.multiFormat(myEvent.getEventName(),
                    new int[] {1,2,3,4}));
        }catch(Exception ex){
            Log.i("TITLE ERROR", ex.getMessage());
        }
        try{
            myEvent.setEventLocation(myIntent.getStringExtra(LOCATION_TAG));
            location.setText(myEvent.getEventLocation());
        }catch(Exception ex){
            Log.i("LOCATION ERROR", ex.getMessage());
        }
        try{
            myEvent.setEventDescription(myIntent.getStringExtra(DESCRIPTION_TAG));
            description.setText(TextFormatter.multiFormat(myEvent.getEventDescription(),
                    new int[] {1,2,3,4}));
        }catch(Exception ex){
            Log.i("DESCRIPTION ERROR", ex.getMessage());
        }
        try{
            myEvent.setEventFriends(myIntent.getStringArrayListExtra(FRIENDS_TAG));
            selectedFriends.setAdapter(new ArrayAdapter<String>(this,
                    R.layout.array_adapter_item, myEvent.getFriends()));
        }catch(Exception ex){
            Log.i("FRIENDS ERROR", ex.getMessage());
        }
        try{
            myEvent.setEventStart(
                   myIntent.getLongExtra(FROM_TAG, 0)
            );
            String[] res = DateTime.parseLong(myEvent.getEventStart());
            from.setText(res[0] + " - "  + res[1]);
        }catch(Exception ex){
            Log.i("FROM ERROR", ex.getMessage());
        }
        try{
            myEvent.setEventEnd(myIntent.getLongExtra(TO_TAG, 0));
            String[] res = DateTime.parseLong(myEvent.getEventEnd());
            to.setText(res[0] + " - "  + res[1]);
        }catch(Exception ex){
            Log.i("TO ERROR", ex.getMessage());
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
