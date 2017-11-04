package com.dpckou.agoston.timetale;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.dpckou.agoston.timetale.weekday.WeekDayPagerActivity;

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
                Intent startIntent = new Intent(view.getContext(), WeekDayPagerActivity.class);
                startActivity(startIntent);
            }
        });
        testEventData = (Button)findViewById(R.id.testNewEvent);
        //FIXME fucking crashes on every time I try to test my fragment BaseEventDataFragment
        BasicEventDataFragment edFr = new BasicEventDataFragment();
        FragmentManager fm = getSupportFragmentManager();
        final FragmentTransaction ftr = fm.beginTransaction();
        ftr.replace(R.id.container, edFr, "eventDataFragment");

        testEventData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ftr.commit();
            }
        });
    }
}
