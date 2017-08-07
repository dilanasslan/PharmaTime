package com.example.dilan.pharmatime;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.Toast;

import java.util.Calendar;

import static com.example.dilan.pharmatime.AddPharmaFragment.DIALOG_FRAGMENT;
import static com.example.dilan.pharmatime.AddPharmaFragment.pick_date;
import static com.example.dilan.pharmatime.AddPharmaFragment.pick_date_end;

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {
    int year,month,day;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Do something with the date chosen by the user
        Toast.makeText(getActivity(),
                "year: " + year + " month: " + month + " day: "+ day , Toast.LENGTH_LONG)
                .show();
        switch (DIALOG_FRAGMENT){
            case 0:
                pick_date_end.setText(day+"/"+month+"/"+year);
                break;
            case 1:
                pick_date.setText(day+"/"+month+"/"+year);
                break;
        }

    }
}