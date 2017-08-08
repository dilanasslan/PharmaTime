package com.example.dilan.pharmatime;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

import static com.example.dilan.pharmatime.AddPharmaFragment.PickedTimes;
import static com.example.dilan.pharmatime.AddPharmaFragment.REPETATION;
import static com.example.dilan.pharmatime.AddPharmaFragment.daily_usage;
import static com.example.dilan.pharmatime.AddPharmaFragment.pick_time;
import static com.example.dilan.pharmatime.AddPharmaFragment.picked_list;


public class TimePickerFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
                // Use the current time as the default values for the picker
                final Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);


                // Create a new instance of TimePickerDialog and return it
                return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                // Do something with the time chosen by the user
                Toast.makeText(getActivity(),
                        "hour: " + hourOfDay + "min: " + minute , Toast.LENGTH_LONG)
                        .show();

                PickedTimes.add(hourOfDay + ":" + minute);

                while(REPETATION != 1){
                        FragmentManager fm = getActivity().getFragmentManager();
                        DialogFragment newFragment = new TimePickerFragment();
                        newFragment.show(fm, "timePicker");
                        REPETATION--;
                }
                for (int i = 0; i<PickedTimes.size(); i++){
                        System.out.println("Time: " + PickedTimes.get(i));
                }



        }
        @Override
        public void onDismiss(final DialogInterface dialog) {
                if(PickedTimes.size() != 0){
                        picked_list.setText("Picked Times to be Notified");
                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                                getActivity(), android.R.layout.simple_selectable_list_item, PickedTimes
                        );
                        AddPharmaFragment.listView.setAdapter(arrayAdapter);
                }
        }
}