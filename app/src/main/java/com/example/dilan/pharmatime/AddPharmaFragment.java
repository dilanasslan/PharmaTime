package com.example.dilan.pharmatime;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


public class AddPharmaFragment extends Fragment implements View.OnClickListener {
    private static final String ARG_SECTION_NUMBER = "section_number";
    Button btn_add,pick_time, pick_date;
    EditText editText;

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
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_add_pharma, container, false);
        btn_add = (Button)v.findViewById(R.id.button);
        pick_time = (Button)v.findViewById(R.id.pick_time);
        pick_date = (Button)v.findViewById(R.id.pick_date);
        editText = (EditText) v.findViewById(R.id.editText);
        btn_add.setOnClickListener(this);
        pick_time.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.button){
            String message = editText.getText().toString();
            DatabaseConnector db = new DatabaseConnector(getContext());
            db.insertContact(message, "123", 12, "123","123");
        } else if (v.getId() == R.id.pick_time) {
            FragmentManager fm = getActivity().getFragmentManager();
            DialogFragment newFragment = new TimePickerFragment();
            newFragment.show(fm, "timePicker");
        }

    }


}