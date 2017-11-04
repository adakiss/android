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

        fromDate.setOnClickListener(HookupDate(_from,fromDate));
        toDate.setOnClickListener(HookupDate(_to,toDate));
        fromTime.setOnClickListener(HookupTime(_from,fromTime));
        toTime.setOnClickListener(HookupTime(_to,toTime));
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
