package com.dpckou.agoston.timetale;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import com.dpckou.agoston.timetale.CustomListeners.ArgumentedDateListener;
import com.dpckou.agoston.timetale.CustomListeners.ArgumentedTimeListener;
import com.dpckou.agoston.timetale.DateTimeModels.DateTime;
import com.dpckou.agoston.timetale.locations.Location;
import com.dpckou.agoston.timetale.locations.LocationFragment;
import com.dpckou.agoston.timetale.persistence.Event;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by agoston on 2017.11.01..
 * It may or may not be used, currently wouldn't want to mess with nested fragments.
 */

public class EventActivity extends AppCompatActivity{

    private static final int PLACE_PICKER_REQUEST = 1;

    public DateTime get_from() {
        return _from;
    }

    public void set_from(DateTime _from) {
        this._from = _from;
    }

    public DateTime get_to() {
        return _to;
    }

    public void set_to(DateTime _to) {
        this._to = _to;
    }

    private EditText fromDate;
    private EditText toDate;
    private EditText fromTime;
    private EditText toTime;
    private EditText eventName;
    private EditText description;
    private Button getLocation;

    private Button submit;
    //the two wrappers for the input data.
    private DateTime _from = new DateTime();
    private DateTime _to = new DateTime();
    Calendar c = Calendar.getInstance();
    private FrameLayout friendsFrame;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        fromDate = (EditText)findViewById(R.id.fromDate);
        toDate = (EditText)findViewById(R.id.toDate);
        fromTime = (EditText)findViewById(R.id.fromTime);
        toTime = (EditText)findViewById(R.id.toTime);
        friendsFrame = findViewById(R.id.friendsList);
        submit = (Button) findViewById(R.id.submitEvent);
        eventName = (EditText) findViewById(R.id.title);
        description = (EditText) findViewById(R.id.description);
        getLocation = (Button) findViewById(R.id.getLocBut);
        /*

            for now: use the contacts from phone only.
            or you can just add a string
        */

        ContactFriendsListFragment fragment = ContactFriendsListFragment.newInstance();

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.friendsList, fragment);

        transaction.commit();

        //the to-s are needed to be implemented separately.
        /*
        the reason is, we need the data set from the _from-s. see, we can't
        go back in time, so anything newer is not allowed.
        TODO: if possible, make it less kókány :)
         */
        fromDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog = new DatePickerDialog(EventActivity.this,
                        android.R.style.Theme_DeviceDefault_Dialog_MinWidth,
                        new DatePickerDialog.OnDateSetListener() {
                            public void onDateSet(DatePicker picker, int year, int month, int day){
                                month++;
                                _from.setYear(year);
                                _from.setMonth(month);
                                _from.setDay(day);
                                fromDate.setText(DateTime.formatDate(year, month, day, '.'));
                                _to.setDay(_from.getDay());
                                _to.setYear(_from.getYear());
                                _to.setMonth(_from.getMonth());
                                toDate.setText(DateTime.formatDate(year, month, day, '.'));
                            }
                        }, _from.getYear(), _from.getMonth(), _from.getDay() );
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        //TODO currently it is possible to go back in time and set a TO time and date earlier than FROM. have to fix that.
        toDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog = new DatePickerDialog(EventActivity.this,
                        android.R.style.Theme_DeviceDefault_Dialog_MinWidth,
                        new DatePickerDialog.OnDateSetListener() {
                            public void onDateSet(DatePicker picker, int year, int month, int day){
                                month++;
                                //if we input a value that would go back to the past
                                _to.setYear(year);
                                _to.setMonth(month);
                                _to.setDay(day);
                                if(_from.compareTo(_to) == 1){
                                    //so the _from is less
                                    _to.setYear(_from.getYear());
                                    _to.setMonth(_from.getMonth());
                                    _to.setDay(_from.getDay());
                                }
                                toDate.setText(DateTime.formatDate(_to.getYear(),
                                        _to.getMonth(), _to.getDay(), '.'));
                            }
                        }, _to.getYear(), _to.getMonth(), _to.getDay() );
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        fromTime.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                TimePickerDialog dialog = new TimePickerDialog(EventActivity.this,
                        android.R.style.Theme_DeviceDefault_Dialog_MinWidth,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker picker, int hour, int minute){
                                fromTime.setText(DateTime.formatTime(hour, minute, ':'));
                                _from.setHour(hour);
                                _from.setMinute(minute);
                                _to.setHour(_from.getHour());
                                _to.setMinute(_from.getMinute());
                                toTime.setText(DateTime.formatTime(hour, minute, ':'));
                            }
                        }, _from.getHour(), _from.getMinute(), true);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        toTime.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                TimePickerDialog dialog = new TimePickerDialog(EventActivity.this,
                        android.R.style.Theme_DeviceDefault_Dialog_MinWidth,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker picker, int hour, int minute){

                                _to.setHour(hour);
                                _to.setMinute(minute);
                                if(_from.compareTo(_to) == 1) {
                                    _to.setHour(_from.getHour());
                                    _to.setMinute(_from.getMinute());
                                }
                                toTime.setText(DateTime.formatTime(_to.getHour(), _to.getMinute(),':'));
                            }
                        }, _to.getHour(), _to.getMinute(),true);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO add location and people to the event.
                Context context = getApplicationContext();
                CharSequence text = "New event added.";
                int duration = Toast.LENGTH_SHORT;


                try{
                    Event e = new Event();
                    e.setEventStart(_from.generateLong());
                    e.setEventEnd(_to.generateLong());
                    e.setEventName(eventName.getText().toString());
                    Log.d("EVENT START",Long.toString(e.getEventStart()));
                    TimetaleApplication.get().getDB().getDaoInstance().addNewEvent(e);

                }catch(Exception ex){
                    text = "Invalid input.";
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    return;
                }

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

                Intent startIntent = new Intent(view.getContext(), MainActivity.class);
                startActivity(startIntent);
            }
        });

        getLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

                try {
                    Intent intent = builder.build(EventActivity.this);
                    startActivityForResult(intent,PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });

        /*
        fromDate.setOnClickListener(HookupDate(_from,fromDate));
        fromTime.setOnClickListener(HookupTime(_from,fromTime));
        toDate.setOnClickListener(HookupDate(_to,toDate));
        toTime.setOnClickListener(HookupTime(_to,toTime));
        */
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if (fragments != null) {
            for (Fragment fragment : fragments) {
                fragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
            }
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(this, data);
                String toastMsg = String.format("Place: %s", place.getName());
                Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();
            }
        }
    }

    private View.OnClickListener HookupDate(final DateTime dateTime, final EditText date){
        return new View.OnClickListener(){
            public void onClick(View view){
                DatePickerDialog dialog = new DatePickerDialog(
                        EventActivity.this,android.R.style.Theme_DeviceDefault_Dialog_MinWidth,
                        new ArgumentedDateListener(date) {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                month++;
                                String _fromDate = year + "/" + month + "/" + day;
                                textToUpdate.setText(_fromDate);
                            }
                        },dateTime.getYear(), dateTime.getMonth(),
                        dateTime.getDay()
                );
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        };
    }
    private View.OnClickListener HookupTime(final DateTime dateTime, final EditText time){
        return new View.OnClickListener(){
            public void onClick(View view){
                TimePickerDialog dialog = new TimePickerDialog(EventActivity.this,
                        android.R.style.Theme_DeviceDefault_Dialog_MinWidth,new ArgumentedTimeListener(time){
                    @Override
                    public void onTimeSet(TimePicker datePicker, int hour, int minute) {
                        String _fromTime = hour + ":" + minute;
                        textToUpdate.setText(_fromTime);
                    }
                },
                        dateTime.getHour(),dateTime.getMinute(),true);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        };
    }
}
