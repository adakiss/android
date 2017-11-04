package com.dpckou.agoston.timetale.CustomListeners;

import android.app.DatePickerDialog;
import android.widget.DatePicker;
import android.widget.EditText;

/**
 * Created by agoston on 2017.11.04..
 */

public abstract class ArgumentedDateListener implements DatePickerDialog.OnDateSetListener {

    public EditText textToUpdate;
    public ArgumentedDateListener(EditText textToUpdate) {
        this.textToUpdate = textToUpdate;
    }
}
