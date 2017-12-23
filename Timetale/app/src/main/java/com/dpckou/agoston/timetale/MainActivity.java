package com.dpckou.agoston.timetale;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.dpckou.agoston.timetale.weekday.EventListActivity;
import com.dpckou.agoston.timetale.weekday.WeekDayPagerActivity;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        TimetaleApplication.get().getDB().getDaoInstance().cleanOldEvents(c.getTimeInMillis());

        Intent startIntent = new Intent(this, EventListActivity.class);
        startActivity(startIntent);
        finish();

        //kept for testing purposes only. the following code obviously doesn't run anymore.

        Button startButton = (Button) findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent(view.getContext(), WeekDayPagerActivity.class);
                startActivity(startIntent);
            }
        });

        Button cleanDb = findViewById(R.id.cleanDB);
        cleanDb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimetaleApplication.get().getDB().getDaoInstance().nukeEventTable();
            }
        });
        Button testEventData = (Button) findViewById(R.id.testNewEvent);

        testEventData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent(view.getContext(), EventActivity.class);
                startActivity(startIntent);
            }
        });

    }
}
