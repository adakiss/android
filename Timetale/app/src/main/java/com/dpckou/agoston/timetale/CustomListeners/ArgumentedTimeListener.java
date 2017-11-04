package com.dpckou.agoston.timetale.CustomListeners;

import android.app.TimePickerDialog;
import android.widget.EditText;
import android.widget.TimePicker;

/**
 * Created by agoston on 2017.11.04..
 */

public abstract class ArgumentedTimeListener implements TimePickerDialog.OnTimeSetListener {

    public EditText textToUpdate;
    public ArgumentedTimeListener(EditText textToUpdate) {
        this.textToUpdate = textToUpdate;
    }
}
