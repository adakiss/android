package com.dpckou.agoston.timetale;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.dpckou.agoston.timetale.CustomListeners.ArgumentedDateListener;
import com.dpckou.agoston.timetale.CustomListeners.ArgumentedTimeListener;
import com.dpckou.agoston.timetale.DateTimeModels.DateTime;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by agoston on 2017.11.01..
 * It may or may not be used, currently wouldn't want to mess with nested fragments.
 */

public class EventActivity extends AppCompatActivity{
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
    //the two wrappers for the input data.
    private DateTime _from = new DateTime();
    private DateTime _to = new DateTime();
    Calendar c = Calendar.getInstance();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        fromDate = (EditText)findViewById(R.id.fromDate);
        toDate = (EditText)findViewById(R.id.toDate);
        fromTime = (EditText)findViewById(R.id.fromTime);
        toTime = (EditText)findViewById(R.id.toTime);




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
                                _to.setYear(year);
                                _to.setMonth(month);
                                _to.setDay(day);
                                toDate.setText(DateTime.formatDate(year, month, day, '.'));
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
                                _to.setMinute(minute);
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
                                toTime.setText(DateTime.formatTime(hour, minute,':'));
                                _to.setHour(hour);
                                _to.setMinute(minute);
                            }
                        }, _to.getHour(), _to.getMinute(),true);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        /*
        fromDate.setOnClickListener(HookupDate(_from,fromDate));
        fromTime.setOnClickListener(HookupTime(_from,fromTime));
        toDate.setOnClickListener(HookupDate(_to,toDate));
        toTime.setOnClickListener(HookupTime(_to,toTime));
        */
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
