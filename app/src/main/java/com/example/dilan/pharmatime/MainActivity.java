package com.example.dilan.pharmatime;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    static String[] listArray;
    static Cursor c;
    static int arraySize = 0 ;
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    public static SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    public static ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
               mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            DatabaseConnector dc = new DatabaseConnector(getContext());
            dc.open();

            dc.insertPharma("dolorex" , "156983541", 2, "08/07/2017", "08/10/2017");
            dc.insertPharma("famoser" , "123456790", 3, "08/09/2017", "23/12/2017");

            c = dc.getAllPharma();
            showAllPharma(c,rootView);
            dc.close();

            String[] AndroidOS = new String[]{"Cupcake", "Donut", "Eclair", "Froyo", "Gingerbread", "Honeycomb", "Ice Cream SandWich", "Jelly Bean", "KitKat"};
            ListView listView = (ListView) rootView.findViewById(R.id.list);

            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                    getActivity(), android.R.layout.simple_selectable_list_item, listArray
            );
            listView.setAdapter(arrayAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    Toast.makeText(getContext(),
                            "Click ListItem Number " + position, Toast.LENGTH_LONG)
                            .show();
                    Intent intent = new Intent(parent.getContext(), PharmaDetailActivity.class);
                    String message = "Click ListItem Number: " + position;
                    intent.putExtra(EXTRA_MESSAGE, message);
                    startActivity(intent);
                }
            });
            return rootView;
        }


        public void showAllPharma(Cursor cursor, View rootView) {
            listArray = new String[2];
            int arrayIndex = 0;

            while(cursor.moveToNext() ){
                StringBuilder builder = new StringBuilder();
                long id = cursor.getLong(cursor.getColumnIndex("id"));
                String PharmaName = cursor.getString(cursor.getColumnIndex("PharmaName"));
                String Barcode = cursor.getString((cursor.getColumnIndex("Barcode")));
                String BeginDate = cursor.getString((cursor.getColumnIndex("BeginDate")));
                String EndDate = cursor.getString((cursor.getColumnIndex("EndDate")));
                int NumberOfDailyUsing = cursor.getInt(cursor.getColumnIndex("NumberOfDailyUsing"));

                // builder.append("id: ").append(id);
                // builder.append("\n");
                builder.append("PharmaName: ").append(PharmaName);
                builder.append("\n");
                //  builder.append("BeginDate: ").append(BeginDate);
                // builder.append("  EndDate: ").append(EndDate);
                //   builder.append("\n");
                builder.append("NumberOfDailyUsing: ").append(NumberOfDailyUsing);


                if(builder.toString() != ""){
                    listArray[arrayIndex] = builder.toString();
                    if(arrayIndex <2){
                        arrayIndex++;
                    }else{
                        continue;
                    }
                }else{
                    continue;
                }
            }
        }

    }



    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position) {
                case 0:
                    return PlaceholderFragment.newInstance(position + 1);
                case 1:
                    return AddPharmaFragment.newInstance(position + 1);
                default:
                    return null;
            }
        }




        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
              case 0:
                    return "PHARMA LIST";
               case 1:
                    return "ADD PHARMA";
            }
            return null;
        }
    }
}
