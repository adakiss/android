package com.dpckou.agoston.timetale;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.dpckou.agoston.timetale.persistence.Event;
import com.dpckou.agoston.timetale.persistence.EventDao;
import com.dpckou.agoston.timetale.persistence.TimetaleDatabase;
import com.dpckou.agoston.timetale.weekday.WeekDayPagerActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;

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

        Intent startIntent = new Intent(this, WeekDayPagerActivity.class);
        startActivity(startIntent);
        finish();

        //kept for testing purposes only.

        Button startButton = (Button) findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //TimetaleApplication.get().getDB().getDaoInstance().nukeEventTable();

                /*Event event = new Event();
                event.setEventName("TestEventName");
                event.setEventStart(new Date().getTime());
                event.setEventEnd(new Date().getTime()+3600000);
                TimetaleApplication.get().getDB().getDaoInstance().addNewEvent(event); */

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
        /*
        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("email");
        // Other app specific specialization
        final CallbackManager callbackManager = CallbackManager.Factory.create();
        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                FB_accessToken = loginResult.getAccessToken();
                //TODO get it sorted out.
                new GraphRequest(
                        AccessToken.getCurrentAccessToken(),
                        getString(R.string.fb_myFriends_httpReq),
                        null,
                        HttpMethod.GET,
                        new GraphRequest.Callback() {
                            public void onCompleted(GraphResponse response) {
                                JSONArray arr = response.getJSONArray();
                                int id;
                                String name;
                                for (int i = 0; i < arr.length(); i++) {
                                    JSONObject row = null;
                                    try {
                                        row = arr.getJSONObject(i);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    try {
                                        id = row.getInt("id");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    try {
                                        name = row.getString("name");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                ).executeAsync();
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                Log.d("fb login error: ", exception.toString());
            }
        });
        */
    }
}
