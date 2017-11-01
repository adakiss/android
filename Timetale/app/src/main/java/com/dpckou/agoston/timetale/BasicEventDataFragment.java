package com.dpckou.agoston.timetale;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.EditText;

/**
 * Created by agoston on 2017.11.01..
 * a fragment for the views regarding:
 * title (e.g. Add Event)
 * start date, time
 * end date, time
 * description
 *
 */
//on activity created hook up popup datepicker and timepicker.
public class BasicEventDataFragment extends Fragment {
    //the View that contains this fragment.
    View view;

    private EditText fromDate;
    private EditText toDate;
    private EditText fromTime;
    private EditText toTime;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //finding dem view elements.
        fromDate = (EditText)view.findViewById(R.id.fromDate);
        toDate = (EditText)view.findViewById(R.id.toDate);
        fromTime = (EditText)view.findViewById(R.id.fromTime);
        toTime = (EditText)view.findViewById(R.id.toTime);

    }
}
