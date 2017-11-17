package com.dpckou.agoston.timetale;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.dpckou.agoston.timetale.persistence.Event;
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
import java.util.List;

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
    private LocationManager locationManager;
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
            MapFragment _mapFragment = MapFragment.newInstance(options);
            _mapFragment.getMapAsync(this);
            locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            android.app.FragmentTransaction tr = getFragmentManager().beginTransaction();
            tr.replace(R.id.gps,_mapFragment);
            tr.addToBackStack(null);
            tr.commit();
        }


        PlaceAutocompleteFragment autocompleteFragment =
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
                    MY_EVENT.setEventStart(_from.generateLong());
                    MY_EVENT.setEventEnd(_to.generateLong());
                    MY_EVENT.setEventName(eventName.getText().toString());
                    MY_EVENT.setEventLocation(selectedPlace.getName().toString());
                    MY_EVENT.setEventDescription(description.getText().toString());

                    List<String> _friends = new ArrayList<>();
                    //missing fckn LINQ :(
                    for (ContactFriend f : contactFriendsListFragment.friends){
                        if(f.isSelected()){
                            _friends.add(f.getNickName());
                        }
                    }
                    MY_EVENT.setEventFriends(_friends);

                    TimetaleApplication.get().getDB().getDaoInstance().addNewEvent(MY_EVENT);

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
        //commented out, no longer needed.
        /*
        getLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent =
                            new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                                    .build(_me);
                    startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
                } catch (GooglePlayServicesRepairableException e) {
                    // TODO: load an EditText instead.
                    Log.i("ERROR", "ERROR WHEN PLACE AUTO COMPLETE.");
                } catch (GooglePlayServicesNotAvailableException e) {
                    // TODO: Handle the error.
                    Log.i("ERROR", "ERROR WHEN PLACE AUTO COMPLETE.");
                }

            }
        });
*/
        /*
        fromDate.setOnClickListener(HookupDate(_from,fromDate));
        fromTime.setOnClickListener(HookupTime(_from,fromTime));
        toDate.setOnClickListener(HookupDate(_to,toDate));
        toTime.setOnClickListener(HookupTime(_to,toTime));
        */
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
