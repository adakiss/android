package com.dpckou.agoston.timetale.weekday;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.dpckou.agoston.timetale.EventActivity;
import com.dpckou.agoston.timetale.R;
import com.dpckou.agoston.timetale.TimetaleApplication;
import com.dpckou.agoston.timetale.persistence.Event;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class EventListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent(view.getContext(), EventActivity.class);
                startActivity(startIntent);
            }
        });

        List<Event> myList = Arrays.asList(TimetaleApplication.get().getDB().getDaoInstance().getAll());
        Collections.sort(myList, new Comparator<Event>(){
            public int compare(Event o1, Event o2){
                if(o1.getEventStart() == o2.getEventStart())
                    return 0;
                return o1.getEventStart() < o2.getEventStart() ? -1 : 1;
            }
        });
        EventListAdapter adapter = new EventListAdapter(myList);
        LinearLayoutManager lm = new LinearLayoutManager(this);
        lm.setOrientation(LinearLayoutManager.VERTICAL);

        ListView lv = findViewById(R.id.eventsList);
        lv.setAdapter(adapter);
    }

}