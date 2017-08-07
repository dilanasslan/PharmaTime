package com.example.dilan.pharmatime;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;


public class AddPharmaFragment extends Fragment implements View.OnClickListener {
    private static final String ARG_SECTION_NUMBER = "section_number";
    public static int DIALOG_FRAGMENT = 1;
    Button btn_add;
    static Button pick_time, pick_date, pick_date_end;
    EditText editText;
    Switch switch1;

    public AddPharmaFragment() {
    }

    public static AddPharmaFragment newInstance(int sectionNumber) {
        AddPharmaFragment fragment = new AddPharmaFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_pharma, container, false);

        editText = (EditText) v.findViewById(R.id.editText);
        btn_add = (Button)v.findViewById(R.id.button);
        switch1 = (Switch)v.findViewById(R.id.switch1);
        pick_time = (Button)v.findViewById(R.id.pick_time);
        pick_date = (Button)v.findViewById(R.id.pick_date);
        pick_date_end = (Button)v.findViewById(R.id.pick_date_end);

        btn_add.setOnClickListener(this);
        pick_time.setOnClickListener(this);
        pick_date.setOnClickListener(this);
        pick_date_end.setOnClickListener(this);
        switch1.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.button){//add pharma
            String message = editText.getText().toString();
            DatabaseConnector db = new DatabaseConnector(getContext());
            db.insertContact(message, "123", 12, "123","123");
            Toast.makeText(getActivity(),
                    "Pharma Added!" , Toast.LENGTH_LONG)
                    .show();

        } else if (v.getId() == R.id.pick_time) {
            FragmentManager fm = getActivity().getFragmentManager();
            DialogFragment newFragment = new TimePickerFragment();
            newFragment.show(fm, "timePicker");
        }else if (v.getId() == R.id.pick_date) {
            DIALOG_FRAGMENT = 1;
            FragmentManager fm = getActivity().getFragmentManager();
            DialogFragment newFragment = new DatePickerFragment();
            newFragment.show(fm, "datePicker");
        }else if (v.getId() == R.id.pick_date_end) {
            DIALOG_FRAGMENT = 0;
            FragmentManager fm = getActivity().getFragmentManager();
            DialogFragment newFragment = new DatePickerFragment();
            newFragment.show(fm, "datePicker");
        }else if (v.getId() == R.id.switch1) {
            FragmentManager fm = getActivity().getFragmentManager();
            DialogFragment newFragment = new RepeatDialogFragment();
            newFragment.show(fm, "repeat");
        }

    }

}

