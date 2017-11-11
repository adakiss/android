package com.dpckou.agoston.timetale;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.dpckou.agoston.timetale.persistence.Event;
import com.dpckou.agoston.timetale.persistence.EventDao;
import com.dpckou.agoston.timetale.persistence.TimetaleDatabase;
import com.dpckou.agoston.timetale.weekday.WeekDayPagerActivity;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private Button startButton;
    private Button testEventData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = (Button) findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TimetaleApplication.get().getDB().getDaoInstance().nukeEventTable();

                Event event = new Event();
                event.setEventName("TestEventName");
                event.setEventStart(new Date().getTime());
                event.setEventEnd(new Date().getTime()+3600000);
                TimetaleApplication.get().getDB().getDaoInstance().addNewEvent(event);

                Intent startIntent = new Intent(view.getContext(), WeekDayPagerActivity.class);
                startActivity(startIntent);
            }
        });
        testEventData = (Button)findViewById(R.id.testNewEvent);

        testEventData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent(view.getContext(), EventActivity.class);
                startActivity(startIntent);
            }
        });
    }
}
