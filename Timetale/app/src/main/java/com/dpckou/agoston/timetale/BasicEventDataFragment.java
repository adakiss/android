package com.dpckou.agoston.timetale;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;

import static android.R.attr.onClick;

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
public class BasicEventDataFragment extends android.support.v4.app.Fragment {
    //the View that contains this fragment.
    View view;
    private static final String TAG = BasicEventDataFragment.class.getName();
    private EditText fromDate;
    private EditText toDate;
    private EditText fromTime;
    private EditText toTime;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_basiceventdata, container, false);

        //here to initialize all the views and widgets inside the fragment if needed.

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //finding dem view elements.
        fromDate = (EditText)view.findViewById(R.id.fromDate);
        toDate = (EditText)view.findViewById(R.id.toDate);
        fromTime = (EditText)view.findViewById(R.id.fromTime);
        toTime = (EditText)view.findViewById(R.id.toTime);

        fromDate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "boi");
            }
        });
    }
}
