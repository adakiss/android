package com.dpckou.agoston.timetale;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.location.LocationManager;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;
import com.dpckou.agoston.timetale.DateTimeModels.DateTime;
import com.dpckou.agoston.timetale.notifications.EventNotification;
import com.dpckou.agoston.timetale.persistence.Event;
import com.dpckou.agoston.timetale.weekday.EventBundle;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

//TODO creating/modifying an event should not work exactly the same because sometimes
// it creates a second entry of the event (NEW - INSERT / MODIFY - UPDATE)

/**
 * Created by agoston on 2017.11.01..
 * It may or may not be used, currently wouldn't want to mess with nested fragments.
 */

public class EventActivity extends AppCompatActivity implements OnMapReadyCallback{

    private static final int PLACE_AUTOCOMPLETE_REQUEST_CODE = 2;

    private Place selectedPlace;
    private GoogleMap mMap;
    private Activity _me;

    public Event getMY_EVENT() {
        return MY_EVENT;
    }

    private Event MY_EVENT;

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
    private FrameLayout gps;
    private ContactFriendsListFragment contactFriendsListFragment;

    private Button submit;
    //the two wrappers for the input data.
    private DateTime _from = new DateTime();
    private DateTime _to = new DateTime();
    Calendar c = Calendar.getInstance();
    private FrameLayout friendsFrame;
    MapFragment _mapFragment;
    PlaceAutocompleteFragment autocompleteFragment;
    private LocationManager locationManager;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        fromDate = (EditText)findViewById(R.id.fromDate);
        toDate = (EditText)findViewById(R.id.toDate);
        fromTime = (EditText)findViewById(R.id.fromTime);
        toTime = (EditText)findViewById(R.id.toTime);
        //friendsFrame = findViewById(R.id.friendsList);
        submit = (Button) findViewById(R.id.submitEvent);
        eventName = (EditText) findViewById(R.id.title);
        description = (EditText) findViewById(R.id.description);
        gps = findViewById(R.id.gps);
        _me = this;

        MY_EVENT = new Event();

        //new shit
        if(gps != null){
            /*
            MapFragment mapFragment = (MapFragment) getFragmentManager()
                    .findFragmentById(R.id.gps);
            mapFragment.getMapAsync(this);
            */

            GoogleMapOptions options = new GoogleMapOptions().liteMode(true);
            _mapFragment = MapFragment.newInstance(options);
            _mapFragment.getMapAsync(this);
            locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            android.app.FragmentTransaction tr = getFragmentManager().beginTransaction();
            tr.replace(R.id.gps,_mapFragment);
            tr.addToBackStack(null);
            tr.commit();
        }


        autocompleteFragment =
                (PlaceAutocompleteFragment) getFragmentManager()
                        .findFragmentById(R.id.place_autocomplete_fragment);
        if(autocompleteFragment != null){

            autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
                @Override
                public void onPlaceSelected(Place place) {
                    Log.i("PLACE",  place.getName().toString());
                    selectedPlace = place;
                    if(gps != null){
                        toSelectedLocation();
                    }
                    Toast toast = Toast.makeText(_me, place.getName(), Toast.LENGTH_SHORT);
                    toast.show();
                }

                @Override
                public void onError(Status status) {
                    Log.i("PLACE", "ERROR WHEN AUTO COMPLETE PLACE");
                }
            });
        }else{
            Log.i("ACF", "fragment was null :(");
        }
        /*

            for now: use the contacts from phone only.
            or you can just add a string
        */

        contactFriendsListFragment = ContactFriendsListFragment.newInstance();

        /*
        HINT WHAT TO DO
        this activity has 2 ways of starting:
        open from add new, open from edit.
        if opened from edit, it awaits an EventBundle, else nothing.

        so to do:
        get an EventBundle from its Intent.
        if it's null, nothing to do.
        else pass the friends list in a Bundle.
         */

        Object _bundle = getIntent().getParcelableExtra(EventBundle.NAME);
        if(_bundle != null){
            //so we HAD an extra EventBundle, it's an edit.
            Bundle b = new Bundle();
            b.putParcelable(EventBundle.NAME,(EventBundle)_bundle);
            contactFriendsListFragment.setArguments(b);
        }


        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.friendsList, contactFriendsListFragment);

        transaction.commit();

        //the to-s are needed to be implemented separately.
        fromDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog = new DatePickerDialog(EventActivity.this,
                        android.R.style.Theme_DeviceDefault_Dialog_MinWidth,
                        new DatePickerDialog.OnDateSetListener() {
                            public void onDateSet(DatePicker picker, int year, int month, int day){
                                month++;
                                DateTime today = new DateTime();
                                if(_from.compareTo(today) == 1){
                                    _from.setYear(today.getYear());
                                    _from.setMonth(today.getMonth());
                                    _from.setDay(today.getDay());
                                }else{
                                    _from.setYear(year);
                                    _from.setMonth(month);
                                    _from.setDay(day);
                                }

                                fromDate.setText(DateTime.formatDate(_from.getYear(), _from.getMonth(),
                                        _from.getDay(), '.'));
                                _to.setDay(_from.getDay());
                                _to.setYear(_from.getYear());
                                _to.setMonth(_from.getMonth());
                                toDate.setText(DateTime.formatDate(_from.getYear(), _from.getMonth(),
                                        _from.getDay(), '.'));
                            }
                        }, _from.getYear(), _from.getMonth(), _from.getDay() );
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
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

                final Context context = getApplicationContext();
                int duration = Toast.LENGTH_SHORT;


                try{
                    MY_EVENT.setEventStart(_from.generateLong());
                    MY_EVENT.setEventEnd(_to.generateLong());
                    MY_EVENT.setEventName(eventName.getText().toString());
                    if (MY_EVENT.getEventLocation() == null ||
                            (selectedPlace != null && selectedPlace.getName() != MY_EVENT.getEventLocation())) {
                        MY_EVENT.setEventLocation(selectedPlace.getName().toString());
                    }
                    MY_EVENT.setEventDescription(description.getText().toString());

                    List<String> _friends = new ArrayList<>();
                    //missing fckn LINQ :(
                    for (ContactFriend f : contactFriendsListFragment.friends){
                        if(f.isSelected()){
                            _friends.add(f.getNickName());
                        }
                    }
                    MY_EVENT.setEventFriends(_friends);

                    //TimetaleApplication.get().getDB().getDaoInstance().addNewEvent(MY_EVENT);

                }catch(Exception ex){
                    CharSequence text = "Invalid input.";
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    return;
                }
               /*
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

                Intent startIntent = new Intent(view.getContext(), MainActivity.class);
                startActivity(startIntent);
                */
                final Dialog dialog = new Dialog(_me);
                dialog.setContentView(R.layout.notify_me);
                dialog.setTitle("Notification?");
                Switch s = dialog.findViewById(R.id.notification_switch);

                //quick n dirty
                final boolean[] _notifyMe = new boolean[1];
                _notifyMe[0] = false;

                s.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        _notifyMe[0] = b;
                    }
                });
                Button but = dialog.findViewById(R.id.confirm_event_popup);
                but.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        TimetaleApplication.get().getDB().getDaoInstance().addNewEvent(MY_EVENT);
                        CharSequence t = "New event added.";
                        Toast toast = Toast.makeText(context, t, Toast.LENGTH_LONG);
                        toast.show();

                        Intent startIntent = new Intent(view.getContext(), MainActivity.class);
                        //scheduling a notification
                        if(_notifyMe[0]){
                            long _delay = MY_EVENT.getEventStart() - Calendar.getInstance().getTimeInMillis();
                            //long _delay = 60000;
                            scheduleNotification(_me,_delay,MY_EVENT.getId());
                        }else{
                            try{
                                cancelNotification(_me,MY_EVENT.getId());
                            }catch(Exception e){
                                Log.i("Alarm notification", "No such notification to remove.");
                            }
                        }

                        dialog.dismiss();
                        startActivity(startIntent);
                    }
                });
                dialog.show();
            }
        });

        //now locading the data - if we had one.

        //TODO is try-catching a bad practice to handle scenarious bearable via if-else?
        try{
            Intent intent = getIntent();
            Event _storedEvent = ((EventBundle)intent.getParcelableExtra(EventBundle.NAME)).getEvent();
            MY_EVENT = _storedEvent;
            eventName.setText(MY_EVENT.getEventName());
            description.setText(MY_EVENT.getEventDescription());
            _to=new DateTime(MY_EVENT.getEventEnd());
            _from=new DateTime(MY_EVENT.getEventStart());
            //String[] _start = DateTime.parseLong(MY_EVENT.getEventStart());
            //String[] _end = DateTime.parseLong(MY_EVENT.getEventEnd());
            fromDate.setText(getDateFromLong(MY_EVENT.getEventStart()));
            fromTime.setText(getTimeFromLong(MY_EVENT.getEventStart()));
            toDate.setText(getDateFromLong(MY_EVENT.getEventEnd()));
            toTime.setText(getTimeFromLong(MY_EVENT.getEventEnd()));

            autocompleteFragment.setText(MY_EVENT.getEventLocation());
            List<ContactFriend> _l = new ArrayList<>();
            for(String f : MY_EVENT.getFriends()){
                _l.add(new ContactFriend(f));
            }
            //contactFriendsListFragment.setContextRuntime(_l, this.getBaseContext());

            contactFriendsListFragment.listView.setAdapter(new ArrayAdapter<String>(this,
                    R.layout.array_adapter_item, MY_EVENT.getFriends()));

        }catch(Exception ex){
            //Log.i("New event","A new event was created.");
        }


    }

    private String getDateFromLong(long timestamp) {

        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(timestamp);
        String res = c.get(Calendar.YEAR) + "." + (c.get(Calendar.MONTH)+1) + "." + c.get(Calendar.DAY_OF_MONTH);

        return res;
    }

    private String getTimeFromLong(long timestamp) {

        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(timestamp);
        String res = c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE);

        return res;
    }

    private void scheduleNotification(Context context, long delay, int notificationId) {//delay is after how much time(in millis) from current time you want to schedule the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setContentTitle(MY_EVENT.getEventName())
                .setContentText(MY_EVENT.getEventDescription())
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.cast_ic_notification_forward)
                .setLargeIcon(((BitmapDrawable) context.getResources()
                        .getDrawable(R.drawable.cast_ic_notification_forward)).getBitmap())
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));

        Intent intent = new Intent(context, EventActivity.class);
        PendingIntent activity = PendingIntent.getActivity(context, notificationId, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        builder.setContentIntent(activity);

        Notification notification = builder.build();

        Intent notificationIntent = new Intent(context, EventNotification.class);
        notificationIntent.putExtra(EventNotification.NOTIFICATION_ID, notificationId);
        notificationIntent.putExtra(EventNotification.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, notificationId, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        //long futureInMillis = SystemClock.elapsedRealtime() + delay;
        long futureInMillis = Calendar.getInstance().getTimeInMillis() + delay;
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);

        /*Long time = new GregorianCalendar().getTimeInMillis()+delay;
        //Intent notificationIntent = new Intent(this, EventNotification.class);
        //AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP,time, PendingIntent.getBroadcast(this,1,  notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT));*/
    }

    private static void cancelNotification(Context ctx, int notifyId) {
        String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager nMgr = (NotificationManager) ctx.getSystemService(ns);
        nMgr.cancel(notifyId);
    }

    LatLng sydney = new LatLng(-34, 151);
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
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

    private void toSelectedLocation() {
        mMap.animateCamera(
                CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder()
                        .target(selectedPlace.getLatLng())
                        .zoom(10f)
                        .tilt(45)
                        .bearing(33)
                        .build()
                ), new GoogleMap.CancelableCallback() {
                    @Override
                    public void onFinish() {

                    }

                    @Override
                    public void onCancel() {

                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                //TODO save the location to String.
                Place place = PlaceAutocomplete.getPlace(this, data);
                Log.i("SELECTED_PLACE", "Place: " + place.getName());
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                Log.i("SELECTED_PLACE", status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }
}
