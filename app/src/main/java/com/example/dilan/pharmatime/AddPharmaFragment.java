package com.example.dilan.pharmatime;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class AddPharmaFragment extends Fragment implements View.OnClickListener {
    private static final String ARG_SECTION_NUMBER = "section_number";
    public static int DIALOG_FRAGMENT = 1;
    public static int REPETATION = 0;
    public static boolean NOTICIATION = false;
    public static ArrayList<String> PickedTimes = new ArrayList<String>();
    Button btn_add;
    static Button pick_time, pick_date, pick_date_end;
    EditText pharma_name;
    public static EditText daily_usage;
    Switch switch1;
    static ListView listView;
    static TextView picked_list;

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

        listView = (ListView) v.findViewById(R.id.picked_time_list);
        pharma_name = (EditText) v.findViewById(R.id.pharma_name);
        daily_usage = (EditText) v.findViewById(R.id.daily_usage);
        btn_add = (Button)v.findViewById(R.id.button);
        switch1 = (Switch)v.findViewById(R.id.send_notification);
        pick_time = (Button)v.findViewById(R.id.pick_time);
        picked_list = (TextView) v.findViewById(R.id.picked_list);
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
            String begin_date = pick_date.getText().toString();
            String end_date = pick_date_end.getText().toString();
            String name = pharma_name.getText().toString();
            String usage = daily_usage.getText().toString();

            if(!nullControl(name, usage, begin_date, end_date)){
                Toast.makeText(getActivity(),"Please fill blank areas!" , Toast.LENGTH_LONG).show();
            }else{
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                Date convertedBeginDate = new Date();
                Date convertedEndDate = new Date();
                try {
                    convertedBeginDate = dateFormat.parse(begin_date);
                    convertedEndDate = dateFormat.parse(end_date);
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                DatabaseConnector db = new DatabaseConnector(getContext());
           //     db.insertContact(name, null, Integer.parseInt(usage), convertedBeginDate,convertedEndDate);
                //TODO time will be added

                Toast.makeText(getActivity(),"Pharma Added!" , Toast.LENGTH_LONG).show();
            }
        }else if (v.getId() == R.id.pick_time) {
            String dailyUsage = daily_usage.getText().toString();
            if(dailyUsage.equals("")){
                Toast.makeText(getActivity(),"Please first enter number of daily usage!" , Toast.LENGTH_LONG).show();
            }
            else{
                REPETATION = Integer.parseInt(daily_usage.getText().toString());
                FragmentManager fm = getActivity().getFragmentManager();
                DialogFragment newFragment = new TimePickerFragment();
                newFragment.show(fm, "timePicker");
            }
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
        }else if (v.getId() == R.id.send_notification) {
            if(((Switch) v).isChecked())
                NOTICIATION = true;
            else
                NOTICIATION = false;
        }
        sendNotification();
    }
    private void  sendNotification(){
        if(NOTICIATION ){
            addNotification();
        }
    }


    private boolean nullControl(String name, String usage, String begin_date, String end_date){
        if(name.equals("") || usage.equals("") || begin_date.equals("Begin Date") || end_date.equals("End Date") || PickedTimes.size() == 0)
            return false;
        else
            return true;
    }
    protected void addNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity());
        builder.setSmallIcon(R.mipmap.notification_img);
        builder.setContentTitle("Pharma Time ");
        builder.setContentText("Time to take your medicine!");


        Intent notificationIntent = new Intent(getActivity(), MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(getActivity(), 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        // Add as notification
        NotificationManager manager = (NotificationManager)getActivity().getSystemService(getActivity().NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }

}

